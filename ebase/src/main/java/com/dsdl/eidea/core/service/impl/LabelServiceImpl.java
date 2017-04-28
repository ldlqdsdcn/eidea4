package com.dsdl.eidea.core.service.impl;

import com.dsdl.eidea.core.spring.annotation.DataAccess;
import com.dsdl.eidea.base.def.ActivateDef;
import com.dsdl.eidea.base.entity.po.RolePo;
import com.dsdl.eidea.core.dao.CommonDao;
import com.dsdl.eidea.core.entity.bo.LabelBo;
import com.dsdl.eidea.core.entity.bo.LabelTrlBo;
import com.dsdl.eidea.core.entity.bo.LanguageBo;
import com.dsdl.eidea.core.entity.po.LabelPo;
import com.dsdl.eidea.core.entity.po.LabelTrlPo;
import com.dsdl.eidea.core.entity.po.LanguagePo;
import com.dsdl.eidea.core.i18n.DbResourceBundle;
import com.dsdl.eidea.core.service.LabelService;
import com.dsdl.eidea.core.service.LanguageService;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Search;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LabelServiceImpl implements LabelService {
    private static final Logger logger = Logger.getLogger(LabelServiceImpl.class);
    @DataAccess(entity = LabelPo.class)
    private CommonDao<LabelPo, String> labelDao;
    private final ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private LanguageService languageService;
    @DataAccess(entity = LanguagePo.class)
    private CommonDao<LanguagePo, String> languageDao;

    public LabelServiceImpl() {
        modelMapper.addMappings(new PropertyMap<LabelTrlPo, LabelTrlBo>() {
            @Override
            protected void configure() {
                map().setLang(source.getLanguagePo().getCode());

            }
        });
    }

    @Override
    public List<LabelBo> getLabelList(ISearch search) {
        List<LabelPo> clientPoList = labelDao.search(search);
        return modelMapper.map(clientPoList, new TypeToken<List<LabelBo>>() {
        }.getType());
    }

    @Override
    public void save(LabelBo labelBo) {
        LabelPo labelPo = modelMapper.map(labelBo, LabelPo.class);
        List<LabelTrlPo> labelTrlPoList = new ArrayList<>();

        labelBo.getLabelTrlBoList().forEach(e ->
        {
            LabelTrlPo labelTrlPo = new LabelTrlPo();
            labelTrlPo.setId(e.getId());
            labelTrlPo.setMsgtext(e.getMsgtext());
            labelTrlPo.setLanguagePo(languageDao.find(e.getLang()));
            labelTrlPo.setLabelPo(labelPo);
            labelTrlPoList.add(labelTrlPo);
            if (ActivateDef.ACTIVATED.getKey().equals(labelPo.getIsactive())) {
                DbResourceBundle.updateLabel(labelBo.getKey(), e.getMsgtext(), e.getMsgtext(), e.getLang());
            } else {
                DbResourceBundle.removeLabel(labelBo.getKey());
            }
        });
        labelPo.setLabelTrls(labelTrlPoList);
        labelDao.save(labelPo);
    }

    @Override
    public boolean findExistClient(String no) {
        Search search = new Search();
        search.addFilterEqual("key", no);
        List<RolePo> clientPoList = labelDao.search(search);
        if (clientPoList.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void deletes(String[] codes) {
        for (String key : codes) {
            DbResourceBundle.removeLabel(key);
        }
        labelDao.removeByIds(codes);
    }

    @Override
    public LabelBo getLabelBo(String key) {
        LabelPo labelPo = labelDao.find(key);
        if (labelPo != null) {

            List<LabelTrlBo> labelTrlBoList = modelMapper.map(labelPo.getLabelTrls(), new TypeToken<List<LabelTrlBo>>() {
            }.getType());
            LabelBo labelBo = modelMapper.map(labelPo, LabelBo.class);
            labelBo.setLabelTrlBoList(labelTrlBoList);
            List<String> langList = labelTrlBoList.stream().map(e -> e.getLang()).collect(Collectors.toList());
            String[] langs = new String[langList.size()];
            langList.toArray(langs);
            if (langs != null)
                logger.debug("not ni lang--------------------->" + langs.length);
            List<LanguageBo> languageBoList = languageService.findLanguageListForActivated(langs);
            for (LanguageBo bo : languageBoList) {
                LabelTrlBo languageTrlBo = new LabelTrlBo();
                languageTrlBo.setLang(bo.getCode());
                labelTrlBoList.add(languageTrlBo);
            }
            return labelBo;
        }
        return null;
    }

    @Override
    public List<LabelBo> findLanguageListForActivated(String[] notInCodes) {
        Search search = new Search();
        search.addFilterEqual("isactive", "Y");
        search.addFilterNotIn("key", notInCodes);
        List<LabelPo> labelPoList = labelDao.search(search);
        return convertPoToBo(labelPoList);
    }

    private List<LabelBo> convertPoToBo(List<LabelPo> labelPoList) {

        List<LabelBo> labelBoList = new ArrayList<>();
        labelPoList.forEach(e -> {
            LabelBo labelBo = modelMapper.map(e, LabelBo.class);
            List<LabelTrlBo> labelTrlBoList = modelMapper.map(e.getLabelTrls(), new TypeToken<List<LabelTrlBo>>() {
            }.getType());
            labelBo.setLabelTrlBoList(labelTrlBoList);
            labelBoList.add(labelBo);
        });
        return labelBoList;
    }
}
