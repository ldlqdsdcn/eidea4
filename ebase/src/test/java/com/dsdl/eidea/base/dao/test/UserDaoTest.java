package com.dsdl.eidea.base.dao.test;

import com.dsdl.eidea.base.dao.UserDao;
import com.dsdl.eidea.base.entity.po.UserPo;
import com.dsdl.eidea.base.service.PageMenuService;
import com.dsdl.eidea.core.service.SearchService;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by 刘大磊 on 2017/2/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class UserDaoTest {
    @Autowired
    private UserDao userDao;
    @Test
    @Transactional
    public void testSearch()
    {
        Gson gson=new Gson();
        Search search=new Search();
        search.addField("id");
        List<UserPo> userPoList=userDao.search(search);
        for(UserPo userPo:userPoList)
        {
            System.out.println(userPo.toString());
        }

    }
    public static void main(String[] args)
    {
        Search search=new Search();
        search.addField("id").addField("name");
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        UserDao userDao=applicationContext.getBean(UserDao.class);
        List<UserPo> userPoList=userDao.search(search);
        for(UserPo userPo:userPoList)
        {
            System.out.println(userPo.toString());
        }
    }
}
