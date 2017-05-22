package com.dsdl.eidea.base.activiti.test;

import com.dsdl.eidea.base.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by 刘大磊 on 2017/5/22 10:42.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class AccountServiceTest {
    @Autowired
    private AccountService accountService;
    @Test
    public void testAccount()
    {
        accountService.saveSynAllUserAndRoleToActiviti();
    }
}
