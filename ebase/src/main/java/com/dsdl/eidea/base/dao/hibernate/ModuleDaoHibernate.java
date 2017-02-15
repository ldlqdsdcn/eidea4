package com.dsdl.eidea.base.dao.hibernate;

import com.dsdl.eidea.base.dao.ModuleDao;
import com.dsdl.eidea.base.entity.po.ModulePo;
import com.dsdl.eidea.core.dao.hibernate.BaseDaoHibernate;
import org.springframework.stereotype.Repository;

/**
 * Created by Bobo on 2016/12/15 11:29.
 */
@Repository
public class ModuleDaoHibernate extends BaseDaoHibernate<ModulePo, Integer> implements ModuleDao {
}
