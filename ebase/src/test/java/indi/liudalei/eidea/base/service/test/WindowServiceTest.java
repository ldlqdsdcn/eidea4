package indi.liudalei.eidea.base.service.test;

import indi.liudalei.eidea.base.entity.bo.WindowBo;
import indi.liudalei.eidea.base.service.WindowService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by 刘大磊 on 2017/9/12 11:42.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class WindowServiceTest {
    @Autowired
    private WindowService windowService;
    @Test
    public void testGetPagemenu() {
        List<WindowBo> windowBoList = windowService.getWindows("zh_CN");
        Gson gson = new Gson();
        System.out.println(gson.toJson(windowBoList));
    }
}
