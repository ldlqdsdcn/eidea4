package com.dsdl.eidea.base.dao.hibernate;

import com.dsdl.eidea.base.dao.RoleOrgaccessDao;
import com.dsdl.eidea.base.entity.po.RoleOrgaccessPo;
import com.dsdl.eidea.core.dao.hibernate.BaseDaoHibernate;
import org.springframework.stereotype.Repository;

/**
 * Created by 刘大磊 on 2016/12/26 10:27.
 */
@Repository
public class RoleOrgaccessDaoHibernate extends BaseDaoHibernate<RoleOrgaccessPo, Integer> implements RoleOrgaccessDao {
}
