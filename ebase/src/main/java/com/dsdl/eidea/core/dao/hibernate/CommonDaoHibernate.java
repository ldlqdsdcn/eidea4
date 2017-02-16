package com.dsdl.eidea.core.dao.hibernate;

import com.dsdl.eidea.core.dao.CommonDao;
import com.dsdl.eidea.core.dao.aop.PersistentClassInjection;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by 刘大磊 on 2017/2/15.
 */
@Repository
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Slf4j
public class CommonDaoHibernate<T, ID extends Serializable> extends BaseDaoHibernate<T, ID> implements CommonDao<T, ID>,PersistentClassInjection<T> {
    private static int createCount=0;
    public CommonDaoHibernate() {
        super(false);
        createCount++;
        log.debug("--------------------->createCount="+createCount);
    }
    public void setPersistentClass(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }
}
