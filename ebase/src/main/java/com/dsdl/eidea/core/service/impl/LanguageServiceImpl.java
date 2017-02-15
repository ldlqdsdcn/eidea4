package com.dsdl.eidea.core.service.impl;

import com.dsdl.eidea.core.dao.LanguageDao;
import com.dsdl.eidea.core.entity.bo.LanguageBo;
import com.dsdl.eidea.core.entity.bo.LanguageTrlBo;
import com.dsdl.eidea.core.entity.po.LanguagePo;
import com.dsdl.eidea.core.entity.po.LanguageTrlPo;
import com.dsdl.eidea.core.service.LanguageService;
import com.googlecode.genericdao.search.Search;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by 刘大磊 on 2016/12/8 16:15.
 */
@Service
public class LanguageServiceImpl implements LanguageService {
    private static final Logger logger = Logger.getLogger(LanguageServiceImpl.class);
    @Autowired
    private LanguageDao languageDao;
    private ModelMapper modelMapper = new ModelMapper();
    private PropertyMap<LanguagePo, LanguageBo> languageBoPropertyMap = new PropertyMap<LanguagePo, LanguageBo>() {
        @Override
        protected void configure() {
            skip().setLanguageTrlBoList(new ArrayList<>());
        }
    };
    private PropertyMap<LanguageTrlPo, LanguageTrlBo> langTrlMapper = new PropertyMap<LanguageTrlPo, LanguageTrlBo>() {
        @Override
        protected void configure() {
            map().setLanguageCode(source.getCoreLanguageByLanguageCode().getCode());
            map().setLang(source.getCoreLanguageByLang().getCode());
        }
    };

    public LanguageServiceImpl() {

        modelMapper.addMappings(langTrlMapper);
        modelMapper.addMappings(languageBoPropertyMap);
    }

    @Override
    public List<LanguageBo> findLanguage(Search search) {
        List<LanguagePo> languagePoList = languageDao.search(search);

        List<LanguageBo> languageBoList = modelMapper.map(languagePoList, new TypeToken<List<LanguageBo>>() {
        }.getType());
        return languageBoList;
    }

    private List<LanguageBo> convertPoToBo(List<LanguagePo> languagePoList) {

        List<LanguageBo> languageBoList = new ArrayList<>();
        languagePoList.forEach(e -> {
            LanguageBo languageBo = modelMapper.map(e, LanguageBo.class);
            List<LanguageTrlBo> languageTrlBoList = modelMapper.map(e.getCoreLanguageTrlsForLanguageCode(), new TypeToken<List<LanguageTrlBo>>() {
            }.getType());
            languageBo.setLanguageTrlBoList(languageTrlBoList);
            languageBoList.add(languageBo);
        });
        return languageBoList;
    }

    @Override
    public LanguageBo getLanguageBo(String code) {
        LanguagePo languagePo = languageDao.find(code);
        if (languagePo != null) {
            List<LanguageTrlBo> languageTrlBoList = modelMapper.map(languagePo.getCoreLanguageTrlsForLanguageCode().stream().filter(e -> e.getCoreLanguageByLang().getIsactive().equals("Y")).collect(Collectors.toList()), new TypeToken<List<LanguageTrlBo>>() {
            }.getType());
            LanguageBo languageBo = modelMapper.map(languagePo, LanguageBo.class);
            languageBo.setLanguageTrlBoList(languageTrlBoList);
            List<String> langList = languageTrlBoList.stream().map(e -> e.getLang()).collect(Collectors.toList());
            String[] langs = new String[langList.size()];
            langList.toArray(langs);
            if (langs != null)
                logger.debug("not ni lang--------------------->" + langs.length);
            List<LanguageBo> languageBoList = this.findLanguageListForActivated(langs);
            for (LanguageBo bo : languageBoList) {
                LanguageTrlBo languageTrlBo = new LanguageTrlBo();
                languageTrlBo.setLang(bo.getCode());
                languageTrlBo.setLanguageCode(languagePo.getCode());
                languageTrlBoList.add(languageTrlBo);
            }
            return languageBo;
        }
        return null;

    }


    @Override
    public boolean save(LanguageBo languageBo) {
        ModelMapper modelMapper = new ModelMapper();
        LanguagePo languagePo = modelMapper.map(languageBo, LanguagePo.class);
        List<LanguageTrlPo> languageTrlPoList = new ArrayList<>();
        for (LanguageTrlBo languageTrlBo : languageBo.getLanguageTrlBoList()) {
            LanguageTrlPo languageTrlPo = new LanguageTrlPo();
            languageTrlPo.setId(languageTrlBo.getId());
            languageTrlPo.setName(languageTrlBo.getName());
            languageTrlPo.setCoreLanguageByLanguageCode(languagePo);
            LanguagePo lang = languageDao.find(languageTrlBo.getLang());
            languageTrlPo.setCoreLanguageByLang(lang);
            languageTrlPoList.add(languageTrlPo);
        }
        languagePo.setCoreLanguageTrlsForLanguageCode(languageTrlPoList);
        return languageDao.saveForLog(languagePo);
    }

    @Override
    public void deletes(String[] codes) {
        languageDao.removeByIdsForLog(codes);
    }

    @Override
    public List<LanguageBo> findLanguageListForActivated(String[] notInCodes) {
        Search search = new Search();
        search.addFilterEqual("isactive", "Y");
        search.addFilterNotIn("code", notInCodes);
        List<LanguagePo> languagePoList = languageDao.search(search);
        return convertPoToBo(languagePoList);
    }

    @Override
    public List<LanguageBo> findLanguageListForActivated() {
        Search search = new Search();
        search.addFilterEqual("isactive", "Y");
        List<LanguagePo> languagePoList = languageDao.search(search);
        return convertPoToBo(languagePoList);
    }

    public boolean findExistLanguage(String code) {
        LanguagePo po = languageDao.find(code);
        if (po != null) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public List<LanguageBo> getLanguageForActivated() {
        Search search = new Search();
        search.addFilterEqual("isactive", "Y");
        List<LanguagePo> languagePoList = languageDao.search(search);
        return convertPoToBo(languagePoList);
    }

}
