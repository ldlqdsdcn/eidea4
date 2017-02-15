package com.dsdl.eidea.base.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.dsdl.eidea.base.dao.UserSessionDao;
import com.dsdl.eidea.base.entity.po.UserSessionPo;
import com.dsdl.eidea.core.dao.hibernate.BaseDaoHibernate;
@Repository
public class UserSessionDaoHibernate extends BaseDaoHibernate<UserSessionPo, Integer> implements UserSessionDao {

}
