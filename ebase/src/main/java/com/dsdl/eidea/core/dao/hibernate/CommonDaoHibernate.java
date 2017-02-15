package com.dsdl.eidea.core.dao.hibernate;

import com.dsdl.eidea.core.dao.CommonDao;
import com.dsdl.eidea.core.dao.aop.PersistentClassInjection;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by 刘大磊 on 2017/2/15.
 */
@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class CommonDaoHibernate<T, ID extends Serializable> extends BaseDaoHibernate<T, ID> implements CommonDao<T, ID>,PersistentClassInjection<T> {
    public CommonDaoHibernate() {
        super(false);
    }
    public void setPersistentClass(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }
}
