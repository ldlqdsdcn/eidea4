package indi.liudalei.eidea.base.service.test;

import indi.liudalei.eidea.base.service.PageMenuService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by 刘大磊 on 2016/12/27 9:43.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class PagemenuServiceTest {
    @Autowired
    private PageMenuService pageMenuService;
    @Test
    public void testGetPagemenu()
    {
        String pagemenu=pageMenuService.getLeftMenuListByUserId(1,"/");
        System.out.println(pagemenu);
    }
}
