package com.dsdl.eidea.base.service.test;

import com.dsdl.eidea.base.service.OrgService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

import java.io.FileNotFoundException;

/**
 * Created by 刘大磊 on 2017/2/16 8:15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class OrgServiceTest {
    @Autowired
    private OrgService orgService;
    @Test
    public void testGetOrg()
    {
        orgService.getOrgBo(1);
    }
}
