package indi.liudalei.eidea.base.dao.test;

import indi.liudalei.eidea.report.base.dao.UserSessionDao;
import indi.liudalei.eidea.report.base.model.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by 刘大磊 on 2017/4/24 15:52.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:application-report.xml")
@Slf4j
public class UserSessionTest {
    @Autowired
    UserSessionDao userSessionDao;
    @Test
    public void testGetUserSessionList()
    {
       List<UserSession> userSessionList= userSessionDao.getUserSessionList();
        System.out.println(userSessionList.size());
    }
}
