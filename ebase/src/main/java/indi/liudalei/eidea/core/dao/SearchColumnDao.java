package indi.liudalei.eidea.core.dao;

import indi.liudalei.eidea.core.entity.po.SearchColumnPo;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/19 16:33.
 */
public interface SearchColumnDao extends BaseDao<SearchColumnPo, Integer> {
    void removeSearchColumnIdNotIn(List<Integer> columnIds,Integer searchPoId);
}
