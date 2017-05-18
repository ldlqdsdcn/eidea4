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
import com.dsdl.eidea.base.entity.po.FieldPo;
import com.dsdl.eidea.base.service.FieldService;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.Search;
import com.dsdl.eidea.core.dao.CommonDao;
import java.util.List;
/**
 * @author 刘大磊 2017-05-04 13:22:23
 */
@Service("fieldService")
public class FieldServiceImpl  implements	FieldService {
	@DataAccess(entity =FieldPo.class)
	private CommonDao<FieldPo,Integer> fieldDao;
	public PaginationResult<FieldPo> getFieldListByPaging(Search search,QueryParams queryParams)
    {
		search.setFirstResult(queryParams.getFirstResult());
		search.setMaxResults(queryParams.getPageSize());
		PaginationResult<FieldPo> paginationResult = null;
		if (queryParams.isInit()) {
		SearchResult<FieldPo> searchResult = fieldDao.searchAndCount(search);
		paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		else
		{
		List<FieldPo> fieldPoList = fieldDao.search(search);
		paginationResult = PaginationResult.pagination(fieldPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
		}
    	return paginationResult;
    }
	public PaginationResult<FieldPo> getFieldListByColumnId(Search search,Integer columnId)
	{
		QueryParams queryParams = new QueryParams();
		search.setFirstResult(queryParams.getFirstResult());
		search.setMaxResults(queryParams.getPageSize());
		search.addFilterEqual("columnId",columnId);
		PaginationResult<FieldPo> paginationResult = null;
		if (queryParams.isInit()) {
			SearchResult<FieldPo> searchResult = fieldDao.searchAndCount(search);
			paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		else
		{
			List<FieldPo> fieldPoList = fieldDao.search(search);
			paginationResult = PaginationResult.pagination(fieldPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		return paginationResult;
	}
    public FieldPo getField(Integer id)
	{
		return fieldDao.find(id);
	}
    public void saveField(FieldPo field)
	{
		fieldDao.save(field);
	}
    public void deletes(Integer[] ids)
	{
		fieldDao.removeByIds(ids);
	}
}
