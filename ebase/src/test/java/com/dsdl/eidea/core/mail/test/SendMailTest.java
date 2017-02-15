package com.dsdl.eidea.core.mail.test;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by 刘大磊 on 2016/12/13 9:03.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class SendMailTest {
    private Logger logger= Logger.getLogger(SendMailTest.class);
    @Autowired
    private JavaMailSender mailSender;
    @Test
    public void testSendEmail()
    {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("swms_admin@126.com");
            message.setTo("solomonliu@dsdl.com");
            message.setSubject("主题：简单邮件");
            message.setText("测试邮件内容");
            mailSender.send(message);
        }
        catch (RuntimeException e)
        {
            logger.error("send mail error",e);
            Assert.assertTrue(false);
        }
        Assert.assertTrue(true);
    }
}
