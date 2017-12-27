package indi.liudalei.eidea.core.service.test;

import indi.liudalei.eidea.core.entity.bo.LanguageBo;
import indi.liudalei.eidea.core.entity.bo.LanguageTrlBo;
import indi.liudalei.eidea.core.params.QueryParams;
import indi.liudalei.eidea.core.service.LanguageService;
import com.googlecode.genericdao.search.Search;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/8 16:26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class LanguageServiceTest {
    private Logger logger= Logger.getLogger(LanguageServiceTest.class);
    @Autowired
    private LanguageService languageService;

    @Test
    public void testFindLanguage() {
        Search search=new Search();
        search.addFilterEqual("code","zh_CN");
        List<LanguageBo> languageBoList = languageService.findLanguage(search, new QueryParams()).getData();
        languageBoList.forEach(e -> {
            System.out.println(e.getCode() + "----------" + e.getName());
           List<LanguageTrlBo> languageTrlBoList= e.getLanguageTrlBoList();
            System.out.println(languageTrlBoList.size());
            for(LanguageTrlBo languageTrlBo:languageTrlBoList)
            {
                System.out.println(languageTrlBo.getName()+"-"+languageTrlBo.getLanguageCode()+" "+languageTrlBo.getLang());
            }
//            e.getLanguageTrlBoList().forEach(b->{
//                System.out.println(b.getName()+"-"+b.getLanguageCode());
//            });
        });
    }
    @Test
    public void testGetLanguageForCode()
    {
      LanguageBo languageBo=  languageService.getLanguageBo("zh_CN");
        Search search=new Search();
        search.addFilterEqual("isactive","Y");
        List<LanguageBo> languageBoListForActivated=languageService.findLanguage(search,new QueryParams()).getData();
        Assert.assertEquals(languageBo.getLanguageTrlBoList().size(),languageBoListForActivated.size());
    }
    @Test
    public void testLogger()
    {
        logger.info(" hello info");
        logger.debug(" hello debug");
        logger.warn(" hello warn");
        logger.error(" hello error");
    }
}
