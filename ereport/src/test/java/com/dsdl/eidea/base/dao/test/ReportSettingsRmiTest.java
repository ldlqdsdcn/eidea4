package com.dsdl.eidea.base.dao.test;

import com.dsdl.eidea.core.rmi.ReportSettingsRmi;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by 刘大磊 on 2017/4/24 17:38.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-report.xml")
@Slf4j
public class ReportSettingsRmiTest {
    @Autowired(required = false)
    private ReportSettingsRmi reportSettingsRmi;
    @Test
    public void testGetReportSettingPath()
    {
        String path=reportSettingsRmi.getTemplateFilePath();
        System.out.println("path="+path);
    }
}
