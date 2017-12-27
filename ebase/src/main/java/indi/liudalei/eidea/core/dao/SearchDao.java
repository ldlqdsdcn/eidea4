package indi.liudalei.eidea.core.dao;

import indi.liudalei.eidea.core.entity.bo.CommonSearchParam;
import indi.liudalei.eidea.core.entity.bo.CommonSearchResult;
import indi.liudalei.eidea.core.entity.po.SearchPo;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/17 10:24.
 */
public interface SearchDao extends BaseDao<SearchPo, Integer> {
    List<CommonSearchResult> selectCommonList(CommonSearchParam commonSearchParam);
}
