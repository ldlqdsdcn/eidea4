package com.dsdl.eidea.common.web.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘大磊 on 2016/10/8 17:24.
 */
public class SearchFormRow implements java.io.Serializable {
    private List<SearchColumnVo> searchColumnVoList=new ArrayList<>();

    public void addSearchColumnVo(SearchColumnVo searchColumnVo)
    {
        searchColumnVoList.add(searchColumnVo);
    }
    public List<SearchColumnVo> getSearchColumnVoList()
    {
        return searchColumnVoList;
    }
}
