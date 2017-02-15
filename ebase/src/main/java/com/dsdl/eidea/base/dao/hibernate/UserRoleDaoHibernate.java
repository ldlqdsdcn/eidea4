package com.dsdl.eidea.base.dao.hibernate;

import com.dsdl.eidea.base.dao.UserRoleDao;
import com.dsdl.eidea.base.entity.po.UserRolePo;
import com.dsdl.eidea.core.dao.hibernate.BaseDaoHibernate;
import org.springframework.stereotype.Repository;

/**
 * Created by Bobo on 2016/12/20 9:35.
 */
@Repository
public class UserRoleDaoHibernate extends BaseDaoHibernate<UserRolePo, Integer> implements UserRoleDao {
}
