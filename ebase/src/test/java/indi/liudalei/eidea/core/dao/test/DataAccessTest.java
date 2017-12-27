package indi.liudalei.eidea.core.dao.test;

import indi.liudalei.eidea.core.spring.annotation.DataAccess;
import indi.liudalei.eidea.base.entity.po.UserPo;
import indi.liudalei.eidea.core.dao.CommonDao;
import com.googlecode.genericdao.search.Search;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

/**
 * Created by 刘大磊 on 2017/2/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class DataAccessTest {
    @DataAccess(entity = UserPo.class)
    private CommonDao<UserPo, Integer> userDao;

    @Test
    @Transactional
    public void testSelectUsers() {
        Assert.assertTrue(userDao.count(new Search()) > 0);
    }
}
