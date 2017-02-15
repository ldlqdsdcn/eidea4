package com.dsdl.eidea.base.dao.hibernate;

import com.dsdl.eidea.base.dao.ChangelogDao;
import com.dsdl.eidea.base.entity.po.ChangelogPo;
import com.dsdl.eidea.core.dao.hibernate.BaseDaoHibernate;
import org.springframework.stereotype.Repository;
@Repository
public class ChangelogDaoHibernate extends BaseDaoHibernate<ChangelogPo, Integer> implements ChangelogDao {


}
