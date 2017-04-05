package com.dsdl.eidea.core.dao.hibernate;

import com.dsdl.eidea.core.dao.BaseDao;
import com.dsdl.eidea.core.dao.ChangeForLogDao;
import com.googlecode.genericdao.dao.hibernate.GenericDAOImpl;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

/**
 * Created by 刘大磊 on 2016/12/6 9:41.
 */
public class BaseDaoHibernate<T, ID extends Serializable> extends GenericDAOImpl<T, ID> implements BaseDao<T, ID> {
    @Autowired
    private ChangeForLogDao changeForLogDao;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    public BaseDaoHibernate() {
        this(true);
    }
    public BaseDaoHibernate(boolean init)
    {
        super(init);
    }

    /**
     * save方法，记录操作日志
     *
     * @param entity
     */
    public boolean saveForLog(T entity) {
        Serializable id = getMetadataUtil().getId(entity);
        if (getSession().contains(entity))
            return false;

        if (id == null || (new Long(0)).equals(id) || !_exists(entity)) {
            _save(entity);
            id = getMetadataUtil().getId(entity);
            changeForLogDao.insert(entity, id);
            return true;
        } else {
            _merge(entity);
            changeForLogDao.update(entity, id);
            return false;
        }
    }

    public void insertForLog(T entity) {
        _save(entity);
        Serializable id  = getMetadataUtil().getId(entity);
        changeForLogDao.insert(entity, id);
    }


    public void updateForLog(T entity) {
        Serializable id = getMetadataUtil().getId(entity);
        _merge(entity);
        changeForLogDao.update(entity, id);
    }

    /**
     * 删除方法，记录操作日志
     *
     * @param entity
     * @return
     */
    public boolean removeForLog(T entity) {
        Serializable id = getMetadataUtil().getId(entity);
        changeForLogDao.delete(entity, id);
        remove(entity);
        return true;
    }

    public boolean removeByIdForLog(ID id) {
        return _deleteByIdForLog(persistentClass, id);
    }

    public void removeByIdsForLog(ID... ids) {
        _deleteByIdForLog(persistentClass, ids);
    }

    protected boolean _deleteByIdForLog(Class<?> type, Serializable id) {
        if (id != null) {
            type = getMetadataUtil().getUnproxiedClass(type); //Get the real entity class
            Object entity = getSession().get(type, id);
            if (entity != null) {
                getSession().delete(entity);
                changeForLogDao.delete(entity, id);
                return true;
            }
        }
        return false;
    }

    /**
     * Remove all the entities of the given type from the datastore that have
     * one of these ids.
     * 需要优化处理
     */
    protected void _deleteByIdForLog(Class<?> type, Serializable... ids) {
        type = getMetadataUtil().getUnproxiedClass(type); //Get the real entity class
        Criteria c = getSession().createCriteria(type);
        c.add(Restrictions.in("id", ids));
        for (Object entity : c.list()) {
            getSession().delete(entity);
            changeForLogDao.delete(entity, getMetadataUtil().getId(entity));
        }
    }
}
