package com.dsdl.eidea.core.dao.test;

import com.dsdl.core.spring.annotation.DataAccess;
import com.dsdl.eidea.base.entity.po.UserPo;
import com.dsdl.eidea.core.dao.BaseDao;
import com.dsdl.eidea.core.dao.hibernate.BaseDaoHibernate;
import com.dsdl.eidea.core.entity.po.LabelPo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.asm.Label;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by 刘大磊 on 2017/2/15 17:20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Slf4j
public class UserTest {
    @DataAccess(entity = UserPo.class)
    private BaseDaoHibernate<UserPo,Integer> userDao;
    @DataAccess(entity = LabelPo.class)
    private BaseDaoHibernate<LabelPo,String> labelDao;
    @Test
    @Transactional
    public void testGetUsers()
    {
        List<UserPo> userPoList=userDao.findAll();
        userPoList.forEach(e->{
            log.debug("-------------------->username="+e.getName());
        });
        List<LabelPo> labelPoList=labelDao.findAll();
        labelPoList.forEach(e->{
            log.debug("------------------------------"+e.getKey()+"----------------"+e.getMsgtext());
        });
    }
}
