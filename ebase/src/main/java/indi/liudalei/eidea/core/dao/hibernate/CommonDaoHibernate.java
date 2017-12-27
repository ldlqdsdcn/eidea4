package indi.liudalei.eidea.core.dao.hibernate;

import indi.liudalei.eidea.core.dao.CommonDao;
import indi.liudalei.eidea.core.dao.aop.PersistentClassInjection;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * Created by 刘大磊 on 2017/2/15.
 */
@Slf4j
public class CommonDaoHibernate<T, ID extends Serializable> extends BaseDaoHibernate<T, ID> implements CommonDao<T, ID>,PersistentClassInjection<T> {
    private static int createCount=0;
    public CommonDaoHibernate() {
        super(false);
        createCount++;
        log.debug("--------------------->createCount="+createCount);
    }
    public CommonDaoHibernate(Class<T> persistentClass) {
        super(false);
        this.persistentClass = persistentClass;
        createCount++;
        log.debug("--------------------->createCount="+createCount);
    }
    public void setPersistentClass(Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }
}
