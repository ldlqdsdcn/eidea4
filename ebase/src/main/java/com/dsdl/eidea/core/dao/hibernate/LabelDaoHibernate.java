package com.dsdl.eidea.core.dao.hibernate;

import org.springframework.stereotype.Repository;

import com.dsdl.eidea.core.dao.LabelDao;
import com.dsdl.eidea.core.entity.po.LabelPo;
@Repository
public class LabelDaoHibernate extends BaseDaoHibernate<LabelPo, String> implements LabelDao{

}
