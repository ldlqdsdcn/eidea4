package com.dsdl.eidea.base.dao.hibernate;

import com.dsdl.eidea.base.dao.OrgDao;
import com.dsdl.eidea.base.entity.po.OrgPo;
import com.dsdl.eidea.core.dao.hibernate.BaseDaoHibernate;
import org.springframework.stereotype.Repository;

/**
 * Created by 刘大磊 on 2016/12/12 17:35.
 */
@Repository
public class OrgDaoHibernate extends BaseDaoHibernate<OrgPo, Integer> implements OrgDao {
}
