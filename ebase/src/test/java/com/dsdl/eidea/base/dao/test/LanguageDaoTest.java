package com.dsdl.eidea.base.dao.test;

import com.dsdl.eidea.core.dao.CommonDao;
import com.dsdl.eidea.core.entity.po.LanguagePo;
import com.dsdl.eidea.core.spring.annotation.DataAccess;
import com.googlecode.genericdao.search.Search;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 刘大磊 on 2017/4/25 17:24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class LanguageDaoTest {

    @DataAccess(entity = LanguagePo.class)
    private CommonDao<LanguagePo,String> languageDao;
    @Test
    @Transactional
    public void testSearchLanguage()
    {
        Search search=new Search();
        search.addFilterLike("languageTrlsForLanguageCode.name","%Chinese%");
       List<LanguagePo> languagePoList= languageDao.search(search);
        for(LanguagePo languagePo:languagePoList)
        {
            System.out.println(languagePo.getCode()+" "+languagePo.getName());
        }
    }
}
