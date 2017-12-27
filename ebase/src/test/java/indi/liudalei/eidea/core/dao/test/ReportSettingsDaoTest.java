package indi.liudalei.eidea.core.dao.test;

import indi.liudalei.eidea.core.dao.CommonDao;
import indi.liudalei.eidea.core.entity.po.ReportSettingsPo;
import indi.liudalei.eidea.core.spring.annotation.DataAccess;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 刘大磊 on 2017/4/17 14:53.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Slf4j
public class ReportSettingsDaoTest {
    @DataAccess(entity = ReportSettingsPo.class)
    private CommonDao<ReportSettingsPo, String> reportSettingsDao;

    /**
     *
     */
    @Test
    @Transactional
    public void testGetAllReportSettings()
    {
        List<ReportSettingsPo> reportSettingsPoList=reportSettingsDao.search(null);
        Gson gson=new Gson();
        log.warn(gson.toJson(reportSettingsPoList));
        System.out.println(gson.toJson(reportSettingsPoList));
    }
}
