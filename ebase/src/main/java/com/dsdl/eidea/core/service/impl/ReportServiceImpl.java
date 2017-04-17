package com.dsdl.eidea.core.service.impl;

import com.dsdl.eidea.core.dao.CommonDao;
import com.dsdl.eidea.core.dao.ReportDao;
import com.dsdl.eidea.core.entity.bo.LanguageBo;
import com.dsdl.eidea.core.entity.bo.MessageBo;
import com.dsdl.eidea.core.entity.bo.MessageTrlBo;
import com.dsdl.eidea.core.entity.bo.ReportBo;
import com.dsdl.eidea.core.entity.po.*;
import com.dsdl.eidea.core.i18n.DbResourceBundle;
import com.dsdl.eidea.core.service.LanguageService;
import com.dsdl.eidea.core.service.ReportService;
import com.dsdl.eidea.core.spring.annotation.DataAccess;
import com.googlecode.genericdao.search.Search;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 车东明 on 2017/4/17.
 */
@Service
public class ReportServiceImpl implements ReportService {
    private static final Logger logger = Logger.getLogger(ReportServiceImpl.class);
    @DataAccess(entity = LabelPo.class)
    private CommonDao<LabelPo, String> labelDao;
    @Autowired
    private ReportDao reportDao;
    @DataAccess(entity = LanguagePo.class)
    private CommonDao<LanguagePo, String> languageDao;
    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private LanguageService languageService;
    private PropertyMap<MessagePo, MessageBo> messageBoPropertyMap = new PropertyMap<MessagePo, MessageBo>() {
        @Override
        protected void configure() {
            skip().setMessageTrlBoList(new ArrayList<>());
        }
    };
    private PropertyMap<MessageTrlPo, MessageTrlBo> messageTrlBoPropertyMap = new PropertyMap<MessageTrlPo, MessageTrlBo>() {
        @Override
        protected void configure() {
            map().setLang(source.getCoreLanguage().getCode());
        }
    };

    @Override
    public List<ReportBo> findReport(Search search) {
        List<ReportPo> reportPoList = reportDao.search(search);
        List<ReportBo> reportBoList = modelMapper.map(reportPoList, new TypeToken<List<ReportBo>>() {
        }.getType());
        return reportBoList;
    }

    @Override
    public ReportBo getReportBo(String key) {
        ReportPo reportPo = reportDao.find(key);
        if (reportPo != null) {
            List<MessageTrlBo> messageTrlBoList = modelMapper.map(reportPo.getCoreMessageTrls(),new TypeToken<List<MessageTrlBo>>(){
            }.getType());
            ReportBo reportBo = modelMapper.map(reportPo, ReportBo.class);
            reportBo.setMessageTrlBoList(messageTrlBoList);
            List<String> langList = messageTrlBoList.stream().map(e->e.getLang()).collect(Collectors.toList());
            String[] langs = new String[langList.size()];
            langList.toArray(langs);
            if (langs!=null){
                logger.debug("not ni lang-------------------->"+ langs.length);
            }
            List<LanguageBo> languageBoList = languageService.findLanguageListForActivated(langs);

            for (LanguageBo lang : languageBoList) {
                MessageTrlBo messageTrlBo = new MessageTrlBo();
                messageTrlBo.setLang(lang.getCode());
                messageTrlBoList.add(messageTrlBo);
            }
            return reportBo;
        }
        return null;
    }

    @Override
    public boolean save(ReportBo reportBo) {
        ModelMapper modelMapper = new ModelMapper();
        ReportPo reportPo = modelMapper.map(reportBo, ReportPo.class);
        List<MessageTrlPo> messageTrlPoList = new ArrayList<>();
        for (MessageTrlBo messageTrlBo:reportBo.getMessageTrlBoList()){
            MessageTrlPo messageTrlPo = new MessageTrlPo();
            messageTrlPo.setId(messageTrlBo.getId());
            messageTrlBo.setMsgtext(messageTrlBo.getMsgtext());
            LanguagePo ss = languageDao.find(messageTrlBo.getLang());
            messageTrlPo.setCoreLanguage(ss);
//            messageTrlPo.setReportPo(reportPo);
            messageTrlPoList.add(messageTrlPo);
        }
        reportPo.setCoreMessageTrls(messageTrlPoList);
        return reportDao.save(reportPo);
    }

    @Override
    public void deletes(String[] keys) {
        for (String key : keys) {
        }
        reportDao.removeByIds(keys);
    }

    @Override
    public boolean findExistReport(String key) {
        ReportPo reportPo = reportDao.find(key);
        if (reportPo != null) {
            return true;
        } else {
            return false;
        }
    }
}
