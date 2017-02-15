package com.dsdl.eidea.base.dao.hibernate;

import com.dsdl.eidea.base.entity.po.PageMenuPo;
import com.dsdl.eidea.base.dao.PageMenuDao;
import com.dsdl.eidea.core.dao.hibernate.BaseDaoHibernate;
import org.springframework.stereotype.Repository;


/**
 * Created by admin on 2016/12/13.
 */
@Repository
public class PageMenuDaoHibernate extends BaseDaoHibernate<PageMenuPo, Integer> implements PageMenuDao {
}
