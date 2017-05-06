package com.dsdl.eidea.core.service.test;

import com.dsdl.eidea.core.entity.bo.LabelBo;
import com.dsdl.eidea.core.service.LabelService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by 刘大磊 on 2017/4/18 11:24.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class LabelServiceTest {
    Gson gson=new Gson();
    @Autowired
    private LabelService labelService;
    @Test
    public void testGetLabelByKey()
    {
        LabelBo labelBo=    labelService.getLabelBo("core.reportSettings.label.key");
        System.out.println(gson.toJson(labelBo));
    }
}
