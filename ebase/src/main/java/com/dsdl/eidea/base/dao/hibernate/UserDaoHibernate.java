package com.dsdl.eidea.base.dao.hibernate;

import com.dsdl.eidea.base.dao.UserDao;
import com.dsdl.eidea.base.entity.po.UserPo;
import com.dsdl.eidea.core.dao.hibernate.BaseDaoHibernate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Bobo on 2016/12/17 14:04.
 */
@Repository
public class UserDaoHibernate extends BaseDaoHibernate<UserPo, Integer> implements UserDao {
}
