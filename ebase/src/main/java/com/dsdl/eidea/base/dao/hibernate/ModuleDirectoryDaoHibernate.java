package com.dsdl.eidea.base.dao.hibernate;

import com.dsdl.eidea.base.dao.ModuleDirectoryDao;
import com.dsdl.eidea.base.entity.bo.ModuleDirectoryBo;
import com.dsdl.eidea.base.entity.po.ModuleDirectoryPo;
import com.dsdl.eidea.core.dao.hibernate.BaseDaoHibernate;
import org.springframework.stereotype.Repository;

/**
 * Created by Bobo on 2016/12/22 10:17.
 */
@Repository
public class ModuleDirectoryDaoHibernate extends BaseDaoHibernate<ModuleDirectoryPo, Integer> implements ModuleDirectoryDao{
}
