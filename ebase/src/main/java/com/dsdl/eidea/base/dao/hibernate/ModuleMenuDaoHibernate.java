package com.dsdl.eidea.base.dao.hibernate;

import com.dsdl.eidea.base.dao.ModuleMenuDao;
import com.dsdl.eidea.base.entity.po.ModuleMenuPo;
import com.dsdl.eidea.core.dao.hibernate.BaseDaoHibernate;
import org.springframework.stereotype.Repository;

/**
 * Created by Bobo on 2016/12/22 9:24.
 */
@Repository
public class ModuleMenuDaoHibernate extends BaseDaoHibernate<ModuleMenuPo, Integer> implements ModuleMenuDao {
}
