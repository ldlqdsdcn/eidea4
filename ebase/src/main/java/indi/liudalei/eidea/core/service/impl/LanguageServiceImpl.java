package indi.liudalei.eidea.core.service.impl;

import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.QueryParams;
import indi.liudalei.eidea.core.spring.annotation.DataAccess;
import indi.liudalei.eidea.core.dao.CommonDao;
import indi.liudalei.eidea.core.entity.bo.LanguageBo;
import indi.liudalei.eidea.core.entity.bo.LanguageTrlBo;
import indi.liudalei.eidea.core.entity.po.LanguagePo;
import indi.liudalei.eidea.core.entity.po.LanguageTrlPo;
import indi.liudalei.eidea.core.service.LanguageService;
import indi.liudalei.eidea.util.StringUtil;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 刘大磊 on 2016/12/8 16:15.
 */
@Service
public class LanguageServiceImpl implements LanguageService {
    private static final Logger logger = Logger.getLogger(LanguageServiceImpl.class);
    @DataAccess(entity = LanguagePo.class)
    private CommonDao<LanguagePo, String> languageDao;
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
            map().setLanguageCode(source.getLanguageByLanguageCode().getCode());
            map().setLang(source.getLanguageByLang().getCode());
        }
    };

    public LanguageServiceImpl() {

        modelMapper.addMappings(langTrlMapper);
        modelMapper.addMappings(languageBoPropertyMap);
    }

    @Override
    public PaginationResult<LanguageBo> findLanguage(Search search, QueryParams queryParams) {
        search.setFirstResult(queryParams.getFirstResult());
        search.setMaxResults(queryParams.getPageSize());
        PaginationResult<LanguageBo> paginationResult = null;
        if (queryParams.isInit()){
            SearchResult<LanguagePo> searchResult = languageDao.searchAndCount(search);
            List<LanguageBo> list = modelMapper.map(searchResult.getResult(),new TypeToken<List<LanguageBo>>(){}.getType());
            paginationResult = PaginationResult.pagination(list,searchResult.getTotalCount(),queryParams.getPageNo(),queryParams.getPageSize());
        }else{
            List<LanguagePo> languagePoList = languageDao.search(search);
            List<LanguageBo> languageBoList = modelMapper.map(languagePoList,new TypeToken<List<LanguageBo>>(){}.getType());
            paginationResult = PaginationResult.pagination(languageBoList,queryParams.getTotalRecords(),queryParams.getPageNo(),queryParams.getPageSize());
        }
        return paginationResult;
    }

    @Override
    public boolean findExistLanguageName(String languageName) {
        Search search = new Search();
        search.addFilterEqual("name", languageName);
        List<LanguagePo> languagePoList = languageDao.search(search);
        if (languagePoList != null && languagePoList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public LanguageBo findExistLanguageByName(String languageName){
        Search search = new Search();
        search.addFilterEqual("name",languageName);
        LanguagePo languagePo = languageDao.searchUnique(search);
        LanguageBo languageBo = modelMapper.map(languagePo,LanguageBo.class);
        return languageBo;
    }

    private List<LanguageBo> convertPoToBo(List<LanguagePo> languagePoList) {

        List<LanguageBo> languageBoList = new ArrayList<>();
        languagePoList.forEach(e -> {
            LanguageBo languageBo = modelMapper.map(e, LanguageBo.class);
            List<LanguageTrlBo> languageTrlBoList = modelMapper.map(e.getLanguageTrlsForLanguageCode(), new TypeToken<List<LanguageTrlBo>>() {
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
            List<LanguageTrlBo> languageTrlBoList = modelMapper.map(languagePo.getLanguageTrlsForLanguageCode().stream().filter(e -> e.getLanguageByLang().getIsactive().equals("Y")).collect(Collectors.toList()), new TypeToken<List<LanguageTrlBo>>() {
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
        if(StringUtil.isNotEmpty(languageBo.getCode())){
            String[] codeArray=languageBo.getCode().split("_");
            languageBo.setLanguageIso(codeArray[0]);
            languageBo.setCountryCode(codeArray[1]);
        }
        ModelMapper modelMapper = new ModelMapper();
        LanguagePo languagePo = modelMapper.map(languageBo, LanguagePo.class);
        List<LanguageTrlPo> languageTrlPoList = new ArrayList<>();
        for (LanguageTrlBo languageTrlBo : languageBo.getLanguageTrlBoList()) {
            LanguageTrlPo languageTrlPo = new LanguageTrlPo();
            languageTrlPo.setId(languageTrlBo.getId());
            languageTrlPo.setName(languageTrlBo.getName());
            languageTrlPo.setLanguageByLanguageCode(languagePo);
            LanguagePo lang = languageDao.find(languageTrlBo.getLang());
            languageTrlPo.setLanguageByLang(lang);
            languageTrlPoList.add(languageTrlPo);
        }
        languagePo.setLanguageTrlsForLanguageCode(languageTrlPoList);
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
