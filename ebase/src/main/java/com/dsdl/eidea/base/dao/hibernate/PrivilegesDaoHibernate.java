package com.dsdl.eidea.base.dao.hibernate;

import com.dsdl.eidea.base.dao.ChangelogDao;
import com.dsdl.eidea.base.dao.PrivilegesDao;
import com.dsdl.eidea.base.entity.po.ChangelogPo;
import com.dsdl.eidea.base.entity.po.PrivilegesPo;
import com.dsdl.eidea.core.dao.hibernate.BaseDaoHibernate;
import org.springframework.stereotype.Repository;

/**
 * Created by 刘大磊 on 2016/12/23 18:04.
 */
@Repository
public class PrivilegesDaoHibernate extends BaseDaoHibernate<PrivilegesPo, Integer> implements PrivilegesDao {
}
