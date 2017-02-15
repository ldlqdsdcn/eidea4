package com.dsdl.eidea.base.dao.hibernate;

import com.dsdl.eidea.base.dao.ModuleRoleDao;
import com.dsdl.eidea.base.entity.po.ModuleRolePo;
import com.dsdl.eidea.core.dao.hibernate.BaseDaoHibernate;
import org.springframework.stereotype.Repository;

/**
 * Created by 刘大磊 on 2016/12/26 9:31.
 */
@Repository
public class ModuleRoleDaoHibernate extends BaseDaoHibernate<ModuleRolePo, Integer> implements ModuleRoleDao {
}
