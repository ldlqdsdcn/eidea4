package com.dsdl.eidea.core.dao;

import com.googlecode.genericdao.dao.hibernate.GenericDAO;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/22 12:42.
 */
public interface BaseDao<T, ID extends Serializable> extends GenericDAO<T, ID> {
    /**
     * save方法，记录操作日志
     *
     * @param entity
     */
    boolean saveForLog(T entity);

    void insertForLog(T entity);
    void updateForLog(T entity);
    /**
     * 删除方法，记录操作日志
     *
     * @param entity
     * @return
     */
    boolean removeForLog(T entity);
    /**
     * 带有日志记录的删除动作
     * @param id
     * @return
     */
    boolean removeByIdForLog(ID id);
    /**
     * 带有日志记录的删除多条记录动作
     * @param ids
     */
    void removeByIdsForLog(ID... ids);


}
