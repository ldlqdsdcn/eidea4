package com.dsdl.eidea.core.service.test;

import com.dsdl.eidea.base.entity.bo.PageMenuBo;
import com.dsdl.eidea.base.service.PageMenuService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by admin on 2016/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PageMenuTest {
    @Autowired
    private  PageMenuService pageMenuService;
    @Test
    public  void savePageMenu(){
        PageMenuBo pageMenuBo = new PageMenuBo();
        try {
        pageMenuBo.setName("dd");
        pageMenuBo.setUrl("com.dsdl.cn");
        pageMenuBo.setRemark("hapor");
        pageMenuBo.setParentMenuId(1);
        pageMenuBo.setIsactive("1");
        pageMenuBo.setMenuType(0);
        pageMenuService.save(pageMenuBo);
         }catch (Exception e){
            Assert.assertTrue(false);
               }

        Assert.assertTrue(true);
    }
}
