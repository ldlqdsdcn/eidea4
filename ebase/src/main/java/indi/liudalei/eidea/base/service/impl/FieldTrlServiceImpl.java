/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package indi.liudalei.eidea.base.service.impl;

import indi.liudalei.eidea.core.spring.annotation.DataAccess;
import indi.liudalei.eidea.base.entity.po.FieldTrlPo;
import indi.liudalei.eidea.core.dao.CommonDao;
import indi.liudalei.eidea.core.dto.PaginationResult;
import org.springframework.stereotype.Service;
import indi.liudalei.eidea.base.service.FieldTrlService;
import indi.liudalei.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.Search;

import java.util.List;
/**
 * @author 刘大磊 2017-05-02 15:46:44
 */
@Service("fieldTrlService")
public class FieldTrlServiceImpl  implements	FieldTrlService {
	@DataAccess(entity =FieldTrlPo.class)
	private CommonDao<FieldTrlPo,Integer> fieldTrlDao;
	public PaginationResult<FieldTrlPo> getFieldTrlListByPaging(Search search, QueryParams queryParams)
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
	public PaginationResult<FieldTrlPo> getFieldTrlListByField(Search search,Integer field)
	{
		QueryParams queryParams = new QueryParams();
		search.setFirstResult(queryParams.getFirstResult());
		search.setMaxResults(queryParams.getPageSize());
		search.addFilterEqual("fieldId",field);
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
