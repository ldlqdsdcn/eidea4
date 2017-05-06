/**
 * 版权所有 刘大磊 2013-07-01
 * 作者：刘大磊
 * 电话：13336390671
 * email:ldlqdsd@126.com
 */
package com.dsdl.eidea.base.service.impl;

import com.dsdl.eidea.base.entity.po.DatadictTypePo;
import com.dsdl.eidea.core.spring.annotation.DataAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dsdl.eidea.base.entity.po.DatadictPo;
import com.dsdl.eidea.base.service.DatadictService;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.Search;
import com.dsdl.eidea.core.dao.CommonDao;

import java.util.List;

/**
 * @author 刘大磊 2017-04-26 15:34:59
 */
@Service("datadictService")
public class DatadictServiceImpl implements DatadictService {
    @DataAccess(entity = DatadictPo.class)
    private CommonDao<DatadictPo, Integer> datadictDao;

    public PaginationResult<DatadictPo> getDatadictListByPaging(Search search, QueryParams queryParams) {
        search.setFirstResult(queryParams.getFirstResult());
        search.setMaxResults(queryParams.getPageSize());
        PaginationResult<DatadictPo> paginationResult = null;
        if (queryParams.isInit()) {
            SearchResult<DatadictPo> searchResult = datadictDao.searchAndCount(search);
            paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
        } else {
            List<DatadictPo> datadictPoList = datadictDao.search(search);
            paginationResult = PaginationResult.pagination(datadictPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
        }
        return paginationResult;
    }

    public DatadictPo getDatadict(Integer id) {
        return datadictDao.find(id);
    }

    public void saveDatadict(DatadictPo datadict) {
        datadictDao.save(datadict);
    }

    public void deletes(Integer[] ids) {
        datadictDao.removeByIds(ids);
    }

    public boolean findExistCode(String code) {
        Search search = new Search();
        search.addFilterEqual("code", code);
        List<DatadictPo> datadictPoList = datadictDao.search(search);
        if (datadictPoList != null && datadictPoList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<DatadictPo> findExistDatadictByCode(String code) {
        Search search = new Search();
        search.addFilterEqual("code",code);
        List<DatadictPo> datadictPoList = datadictDao.search(search);
        return datadictPoList;
    }
}
