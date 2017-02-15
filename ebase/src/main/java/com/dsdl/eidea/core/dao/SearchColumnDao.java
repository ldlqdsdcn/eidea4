package com.dsdl.eidea.core.dao;

import com.dsdl.eidea.core.entity.po.SearchColumnPo;
import com.googlecode.genericdao.dao.hibernate.GenericDAO;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/19 16:33.
 */
public interface SearchColumnDao extends BaseDao<SearchColumnPo, Integer> {
    void removeSearchColumnIdNotIn(List<Integer> columnIds,Integer searchPoId);
}
