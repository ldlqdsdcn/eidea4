/**
 * 版权所有 刘大磊 2013-07-01
 * 作者：刘大磊
 * 电话：13336390671
 * email:ldlqdsd@126.com
 */
package com.dsdl.eidea.base.service.impl;

import com.dsdl.eidea.core.spring.annotation.DataAccess;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dsdl.eidea.base.entity.po.DatadictTypePo;
import com.dsdl.eidea.base.service.DatadictTypeService;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.Search;
import com.dsdl.eidea.core.dao.CommonDao;

import java.util.List;

/**
 * @author 刘大磊 2017-04-26 15:54:01
 */
@Service("datadictTypeService")
public class DatadictTypeServiceImpl implements DatadictTypeService {
    @DataAccess(entity = DatadictTypePo.class)
    private CommonDao<DatadictTypePo, Integer> datadictTypeDao;

    public PaginationResult<DatadictTypePo> getDatadictTypeListByPaging(Search search, QueryParams queryParams) {
        search.setFirstResult(queryParams.getFirstResult());
        search.setMaxResults(queryParams.getPageSize());
        PaginationResult<DatadictTypePo> paginationResult = null;
        if (queryParams.isInit()) {
            SearchResult<DatadictTypePo> searchResult = datadictTypeDao.searchAndCount(search);
            paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
        } else {
            List<DatadictTypePo> datadictTypePoList = datadictTypeDao.search(search);
            paginationResult = PaginationResult.pagination(datadictTypePoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
        }
        return paginationResult;
    }

    public DatadictTypePo getDatadictType(Integer id) {
        return datadictTypeDao.find(id);
    }

    public void saveDatadictType(DatadictTypePo datadictType) {
        datadictTypeDao.saveForLog(datadictType);
    }

    public void deletes(Integer[] ids) {
        datadictTypeDao.removeByIdsForLog(ids);
    }

    @Override
    public List<DatadictTypePo> getDatadictTypeList() {
        List<DatadictTypePo> datadictTypePoList = datadictTypeDao.search(new Search());
        return datadictTypePoList;
    }

    @Override
    public boolean findExistDatadictTypeValue(String value) {
        Search search = new Search();
        search.addFilterEqual("value", value);
        List<DatadictTypePo> datadictTypePoList = datadictTypeDao.search(search);
        if (datadictTypePoList != null && datadictTypePoList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public DatadictTypePo findExistDatadictTypeByValue(String value){
        Search search = new Search();
        search.addFilterEqual("value",value);
        DatadictTypePo datadictTypePo = datadictTypeDao.searchUnique(search);
        return datadictTypePo;
    }
}
