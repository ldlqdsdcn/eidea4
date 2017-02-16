package com.dsdl.eidea.core.dao.test;

import com.dsdl.eidea.core.dao.MessageDao;
import com.dsdl.eidea.core.entity.union.MsgUnion;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.pagination.PaginationInterceptor;
import org.mybatis.pagination.dto.PageMyBatis;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by 刘大磊 on 2017/1/20.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Slf4j
public class MessageDaoTest {
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
    @Autowired
    private MessageDao messageDao;
    @Test
    public void testMybatisPaging()
    {
        PageMyBatis<MsgUnion> msgUnionList= (PageMyBatis) sqlSessionTemplate.selectList("com.delmar.core.mybatis.sql.MessageAndLabelUnionMapper.selectLabelTrl","zh_CN",new RowBounds(1,20));

        System.out.println(msgUnionList.getTotal());
        System.out.println(msgUnionList.size());
        Assert.assertEquals(msgUnionList.size(),20);
        List list= sqlSessionTemplate.selectList("com.delmar.core.mybatis.sql.MessageAndLabelUnionMapper.selectLabelTrl","zh_CN");
        log.debug("list.size()="+list.size());
        log.debug(list.getClass().getName());
        log.debug("end-----------");
    }
}
