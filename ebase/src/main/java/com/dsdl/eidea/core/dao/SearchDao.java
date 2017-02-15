package com.dsdl.eidea.core.dao;

import com.dsdl.eidea.core.entity.bo.CommonSearchParam;
import com.dsdl.eidea.core.entity.bo.CommonSearchResult;
import com.dsdl.eidea.core.entity.po.SearchPo;
import com.googlecode.genericdao.dao.hibernate.GenericDAO;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/17 10:24.
 */
public interface SearchDao extends BaseDao<SearchPo, Integer> {
    List<CommonSearchResult> selectCommonList(CommonSearchParam commonSearchParam);
}
