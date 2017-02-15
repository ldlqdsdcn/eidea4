package com.dsdl.eidea.base.dao.hibernate;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.dsdl.eidea.base.dao.RoleDao;
import com.dsdl.eidea.base.entity.po.RolePo;
import com.dsdl.eidea.core.dao.hibernate.BaseDaoHibernate;
import com.googlecode.genericdao.search.ExampleOptions;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;
@Repository
public class RoleDaoHibernate extends BaseDaoHibernate<RolePo, Integer> implements RoleDao {


}
