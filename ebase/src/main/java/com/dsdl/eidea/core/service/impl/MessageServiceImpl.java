package com.dsdl.eidea.core.service.impl;

import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.dsdl.eidea.core.spring.annotation.DataAccess;
import com.dsdl.eidea.base.def.ActivateDef;
import com.dsdl.eidea.core.dao.CommonDao;
import com.dsdl.eidea.core.dao.MessageDao;
import com.dsdl.eidea.core.entity.bo.LanguageBo;
import com.dsdl.eidea.core.entity.bo.MessageBo;
import com.dsdl.eidea.core.entity.bo.MessageTrlBo;
import com.dsdl.eidea.core.entity.bo.MsgBo;
import com.dsdl.eidea.core.entity.po.LabelPo;
import com.dsdl.eidea.core.entity.po.LanguagePo;
import com.dsdl.eidea.core.entity.po.MessagePo;
import com.dsdl.eidea.core.entity.po.MessageTrlPo;
import com.dsdl.eidea.core.entity.union.MsgUnion;
import com.dsdl.eidea.core.i18n.DbResourceBundle;
import com.dsdl.eidea.core.service.LanguageService;
import com.dsdl.eidea.core.service.MessageService;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {
    private static final Logger logger = Logger.getLogger(MessageServiceImpl.class);
    @DataAccess(entity = LabelPo.class)
    private CommonDao<LabelPo,String> labelDao;
    @Autowired
    private MessageDao messageDao;
    @DataAccess(entity = LanguagePo.class)
    private CommonDao<LanguagePo,String> languageDao;
    @Autowired
    private LanguageService languageService;

    private ModelMapper modelMapper = new ModelMapper();
    private PropertyMap<MessagePo, MessageBo> messageBoPropertyMap = new PropertyMap<MessagePo, MessageBo>() {
        @Override
        protected void configure() {
            skip().setMessageTrlBoList(new ArrayList<>());
        }
    };
    private PropertyMap<MessageTrlPo, MessageTrlBo> messageTrlBoPropertyMap = new PropertyMap<MessageTrlPo, MessageTrlBo>() {
        @Override
        protected void configure() {
            map().setLang(source.getLanguagePo().getCode());
        }
    };

    public MessageServiceImpl() {

        modelMapper.addMappings(messageBoPropertyMap);
        modelMapper.addMappings(messageTrlBoPropertyMap);
    }

    @Override
    public PaginationResult<MessageBo> findMessage(Search search, QueryParams queryParams) {
        search.setFirstResult(queryParams.getFirstResult());
        search.setMaxResults(queryParams.getPageSize());
        PaginationResult<MessageBo> paginationResult = null;
        if (queryParams.isInit()){
            SearchResult<MessagePo> searchResult = messageDao.searchAndCount(search);
            List<MessageBo> list = modelMapper.map(searchResult.getResult(),new TypeToken<List<MessageBo>>(){}.getType());
            paginationResult = PaginationResult.pagination(list,searchResult.getTotalCount(),queryParams.getPageNo(),queryParams.getPageSize());
        }else{
            List<MessagePo> messagePoList = messageDao.search(search);
            List<MessageBo> messageBoList = modelMapper.map(messagePoList,new TypeToken<List<MessageBo>>(){}.getType());
            paginationResult = PaginationResult.pagination(messageBoList,queryParams.getTotalRecords(),queryParams.getPageNo(),queryParams.getPageSize());
        }
        return paginationResult;
    }


    @Override
    public MessageBo getMessageBo(String key) {
        MessagePo messagePo = messageDao.find(key);
        if (messagePo != null) {
            List<MessageTrlBo> messageTrlBoList = modelMapper.map(messagePo.getCoreMessageTrls(), new TypeToken<List<MessageTrlBo>>() {
            }.getType());
            MessageBo messageBo = modelMapper.map(messagePo, MessageBo.class);
            messageBo.setMessageTrlBoList(messageTrlBoList);
            List<String> langList = messageTrlBoList.stream().map(e -> e.getLang()).collect(Collectors.toList());
            String[] langs = new String[langList.size()];
            langList.toArray(langs);
            if (langs != null)
                logger.debug("not ni lang--------------------->" + langs.length);
            List<LanguageBo> languageBoList = languageService.findLanguageListForActivated(langs);

            for (LanguageBo lang : languageBoList) {
                MessageTrlBo messageTrlBo = new MessageTrlBo();
                messageTrlBo.setLang(lang.getCode());
                messageTrlBo.setKey(messagePo.getKey());
                messageTrlBoList.add(messageTrlBo);
            }
            return messageBo;
        }
        return null;
    }

    @Override
    public boolean save(MessageBo messageBo) {
        ModelMapper modelMapper = new ModelMapper();
        MessagePo messagePo = modelMapper.map(messageBo, MessagePo.class);
        List<MessageTrlPo> messageTrlPoList = new ArrayList<>();
        for (MessageTrlBo messageTrlBo : messageBo.getMessageTrlBoList()) {
            MessageTrlPo messageTrlPo = new MessageTrlPo();
            messageTrlPo.setId(messageTrlBo.getId());
            messageTrlPo.setMsgtext(messageTrlBo.getMsgtext());
            LanguagePo languagePo = languageDao.find(messageTrlBo.getLang());
            messageTrlPo.setLanguagePo(languagePo);
            messageTrlPo.setCoreMessage(messagePo);
            messageTrlPoList.add(messageTrlPo);
            if (ActivateDef.ACTIVATED.getKey().equals(messagePo.getIsactive())) {
                DbResourceBundle.updateMessage(messageBo.getKey(), messageTrlBo.getMsgtext(), messageBo.getMsgtext(), messageTrlBo.getLang());
            } else {
                DbResourceBundle.removeMessage(messageBo.getKey());
            }
        }
        messagePo.setCoreMessageTrls(messageTrlPoList);

        return messageDao.save(messagePo);
    }

    @Override
    public void deletes(String[] keys) {

        for (String key : keys) {
            DbResourceBundle.removeMessage(key);
        }
        messageDao.removeByIds(keys);
    }


    @Override
    public List<MessageBo> findMessageListForActivated(String[] notInCodes) {
        Search search = new Search();
        search.addFilterEqual("isactive", ActivateDef.ACTIVATED.getKey());
        search.addFilterNotIn("key", notInCodes);
        List<MessagePo> messagePoList = messageDao.search(search);
        return convertPoToBo(messagePoList);
    }

    private List<MessageBo> convertPoToBo(List<MessagePo> messagePoList) {

        List<MessageBo> messageBoList = new ArrayList<>();
        messagePoList.forEach(e -> {
            MessageBo messageBo = modelMapper.map(e, MessageBo.class);
            List<MessageTrlBo> messageTrlBoList = modelMapper.map(e.getCoreMessageTrls(), new TypeToken<List<MessageTrlBo>>() {
            }.getType());
            messageBo.setMessageTrlBoList(messageTrlBoList);
            messageBoList.add(messageBo);
        });
        return messageBoList;
    }


    @Override
    public boolean findExistMessage(String key) {

        MessagePo messagePo = messageDao.find(key);
        if (messagePo != null) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public DbResourceBundle getResourceBundle(String lang) {
        DbResourceBundle dbResourceBundle = DbResourceBundle.getDbResourceBundle(lang);
        if (dbResourceBundle == null) {
            List<MsgUnion> labelUnionList = messageDao.selectLabelTrl(lang);
            List<MsgUnion> messageUnionList = messageDao.selectMessageTrl(lang);

            Map<String, MsgBo> labelMsgMap = convertMsgToBoMap(labelUnionList);
            Map<String, MsgBo> messageMsgMap = convertMsgToBoMap(messageUnionList);
            dbResourceBundle = new DbResourceBundle(lang, labelMsgMap, messageMsgMap);
        }
        return dbResourceBundle;
    }

   private Map<String, MsgBo> convertMsgToBoMap(List<MsgUnion> list) {
        Map<String, MsgBo> msgBoMap = new HashMap<>();
        for (MsgUnion msgUnion : list) {
            MsgBo msgBo = new MsgBo();
            msgBo.setDefaultValue(msgUnion.getDefaultValue());
            msgBo.setValue(msgUnion.getValue());
            msgBoMap.put(msgUnion.getKey(), msgBo);
        }
        return msgBoMap;
    }

}
