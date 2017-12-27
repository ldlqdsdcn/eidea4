package indi.liudalei.eidea.core.dao.aop;

/**
 * Created by 刘大磊 on 2017/2/15.
 */
public interface PersistentClassInjection<T> {
    void setPersistentClass(Class<T> persistentClass);
}
