package indi.liudalei.eidea.test.service.test;

import indi.liudalei.eidea.test.entity.po.LeavePo;
import indi.liudalei.eidea.test.service.LeaveService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by 刘大磊 on 2017/5/23 9:23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Slf4j
public class LeaveServiceTest {
    @Autowired
    private LeaveService leaveService;

    @Test
    public void testGetTodoListByUserId() {
        List<LeavePo> leavePoList = leaveService.getTodoLeaveList("liudalei");
        log.warn("leavePoList.size=" + leavePoList.size());
    }
}
