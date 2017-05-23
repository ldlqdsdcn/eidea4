package com.dsdl.eidea.base.dao.mybatis;

import com.dsdl.eidea.base.dao.IdentityDao;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Created by 刘大磊 on 2017/5/19 17:16.
 */
@Repository
public class IdentityDaoMybatis implements IdentityDao{
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public void deleteAllMemerShip() {
        sqlSessionTemplate.delete("com.dsdl.base.dao.sql.IdentityMapper.deleteAllMemerShip");
    }

    @Override
    public void deleteAllUser() {
        sqlSessionTemplate.delete("com.dsdl.base.dao.sql.IdentityMapper.deleteAllUser");
    }

    @Override
    public void deleteAllInfo() {
        sqlSessionTemplate.delete("com.dsdl.base.dao.sql.IdentityMapper.deleteAllInfo");
    }

    @Override
    public void deleteAllGroup() {
        sqlSessionTemplate.delete("com.dsdl.base.dao.sql.IdentityMapper.deleteAllGroup");
    }
}
