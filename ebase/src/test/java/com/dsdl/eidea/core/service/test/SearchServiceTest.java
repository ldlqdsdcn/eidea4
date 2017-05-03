package com.dsdl.eidea.core.service.test;

import com.dsdl.eidea.core.entity.bo.SearchBo;
import com.dsdl.eidea.core.service.SearchService;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/17 13:38.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SearchServiceTest {
//    @Autowired
//    private SearchService searchService;
//    @Test
//    public void testFindList()
//    {
//        List<SearchBo> searchBoList=searchService.findList(new Search());
//        Assert.assertTrue(searchBoList!=null);
//    }
//    @Test
//    public void testGetSearchBo()
//    {
//        SearchBo searchBo=searchService.getSearchBo(1);
//        Gson gson=new Gson();
//        System.out.println(gson.toJson(searchBo));
//    }
}
