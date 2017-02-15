package com.dsdl.eidea.base.dao.hibernate;

import com.dsdl.eidea.base.dao.OperatorDao;
import com.dsdl.eidea.base.entity.po.OperatorPo;
import com.dsdl.eidea.core.dao.hibernate.BaseDaoHibernate;
import org.springframework.stereotype.Repository;

/**
 * Created by admin on 2016/12/20.
 */
@Repository
public class OperatorDaoHibernate extends BaseDaoHibernate<OperatorPo,Integer>implements OperatorDao{
}
