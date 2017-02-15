package com.dsdl.eidea.base.dao.hibernate;

import com.dsdl.eidea.base.dao.DirectoryDao;
import com.dsdl.eidea.base.entity.po.DirectoryPo;
import com.dsdl.eidea.core.dao.hibernate.BaseDaoHibernate;
import org.springframework.stereotype.Repository;


/**
 * Created by admin on 2016/12/19.
 */
@Repository
public class DirectoryDaoHibernate extends BaseDaoHibernate<DirectoryPo,Integer> implements DirectoryDao{
}
