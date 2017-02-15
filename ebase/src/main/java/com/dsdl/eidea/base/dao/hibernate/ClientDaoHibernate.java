package com.dsdl.eidea.base.dao.hibernate;

import com.dsdl.eidea.base.dao.ClientDao;
import com.dsdl.eidea.base.entity.po.ClientPo;
import com.dsdl.eidea.core.dao.hibernate.BaseDaoHibernate;
import org.springframework.stereotype.Repository;

/**
 * Created by 刘大磊 on 2016/12/12 17:27.
 */
@Repository
public class ClientDaoHibernate extends BaseDaoHibernate<ClientPo, Integer> implements ClientDao{

}
