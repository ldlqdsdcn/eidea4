package com.dsdl.eidea.core.service;

import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.entity.bo.CommonSearchResult;
import com.dsdl.eidea.core.entity.bo.SearchBo;
import com.dsdl.eidea.core.entity.bo.SearchColumnBo;
import com.dsdl.eidea.core.entity.bo.TableBo;
import com.dsdl.eidea.core.entity.dto.SearchColumnDto;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Search;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/17 10:21.
 */
public interface SearchService {
    PaginationResult<SearchBo> findList(Search search, QueryParams queryParams);

    SearchBo getSearchBo(Integer id);

    SearchBo saveSearchBo(SearchBo searchBo);

    void deleteSearches(Integer[] ids);

    /**
     * 根据唯一标识符获取uri
     *
     * @param uri
     * @return
     */
    SearchBo getSearchBoByUri(String uri);

    /**
     * 获取查询列表
     *
     * @param columnId
     * @return
     */
    List<CommonSearchResult> getCommonSearchListByColumnId(Integer columnId);

    int[] getRelOpersForOperStr(String operStr);

    Search getSearchParam(List<SearchColumnDto> searchColumnDtoList);
}
