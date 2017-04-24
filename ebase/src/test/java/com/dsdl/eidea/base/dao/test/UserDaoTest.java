package com.dsdl.eidea.base.dao.test;

import com.dsdl.eidea.core.spring.annotation.DataAccess;
import com.dsdl.eidea.base.entity.po.UserPo;
import com.dsdl.eidea.core.dao.CommonDao;
import com.dsdl.eidea.core.test.JUnit4ClassRunner;
import com.googlecode.genericdao.search.Search;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by 刘大磊 on 2017/2/6.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Slf4j
public class UserDaoTest {
    @DataAccess(entity = UserPo.class)
    private CommonDao<UserPo,Integer> userDao;
    @Test
    @Transactional
    public void testSearch()
    {
        Search search=new Search();
        List<UserPo> userPoList=userDao.search(search);
        for(UserPo userPo:userPoList)
        {
            log.debug(userPo.toString());
        }

    }
}
