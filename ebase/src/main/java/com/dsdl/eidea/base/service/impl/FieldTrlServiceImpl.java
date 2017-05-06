/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.base.service.impl;

import com.dsdl.eidea.core.spring.annotation.DataAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dsdl.eidea.base.entity.po.FieldTrlPo;
import com.dsdl.eidea.base.service.FieldTrlService;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.Search;
import com.dsdl.eidea.core.dao.CommonDao;
import java.util.List;
/**
 * @author 刘大磊 2017-05-02 15:46:44
 */
@Service("fieldTrlService")
public class FieldTrlServiceImpl  implements	FieldTrlService {
	@DataAccess(entity =FieldTrlPo.class)
	private CommonDao<FieldTrlPo,Integer> fieldTrlDao;
	public PaginationResult<FieldTrlPo> getFieldTrlListByPaging(Search search,QueryParams queryParams)
    {
		search.setFirstResult(queryParams.getFirstResult());
		search.setMaxResults(queryParams.getPageSize());
		PaginationResult<FieldTrlPo> paginationResult = null;
		if (queryParams.isInit()) {
		SearchResult<FieldTrlPo> searchResult = fieldTrlDao.searchAndCount(search);
		paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		else
		{
		List<FieldTrlPo> fieldTrlPoList = fieldTrlDao.search(search);
		paginationResult = PaginationResult.pagination(fieldTrlPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
		}
    	return paginationResult;
    }

    public FieldTrlPo getFieldTrl(Integer id)
	{
		return fieldTrlDao.find(id);
	}
    public void saveFieldTrl(FieldTrlPo fieldTrl)
	{
		fieldTrlDao.save(fieldTrl);
	}
    public void deletes(Integer[] ids)
	{
		fieldTrlDao.removeByIds(ids);
	}
}
