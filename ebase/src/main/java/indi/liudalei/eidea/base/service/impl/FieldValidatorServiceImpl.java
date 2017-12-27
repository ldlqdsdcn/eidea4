/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package indi.liudalei.eidea.base.service.impl;

import indi.liudalei.eidea.core.spring.annotation.DataAccess;
import indi.liudalei.eidea.base.service.FieldValidatorService;
import indi.liudalei.eidea.core.dao.CommonDao;
import indi.liudalei.eidea.core.dto.PaginationResult;
import org.springframework.stereotype.Service;
import indi.liudalei.eidea.base.entity.po.FieldValidatorPo;
import indi.liudalei.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.Search;

import java.util.List;
/**
 * @author 刘大磊 2017-05-02 15:49:09
 */
@Service("fieldValidatorService")
public class FieldValidatorServiceImpl  implements FieldValidatorService {
	@DataAccess(entity =FieldValidatorPo.class)
	private CommonDao<FieldValidatorPo,Integer> fieldValidatorDao;
	public PaginationResult<FieldValidatorPo> getFieldValidatorListByPaging(Search search, QueryParams queryParams)
    {
		search.setFirstResult(queryParams.getFirstResult());
		search.setMaxResults(queryParams.getPageSize());
		PaginationResult<FieldValidatorPo> paginationResult = null;
		if (queryParams.isInit()) {
		SearchResult<FieldValidatorPo> searchResult = fieldValidatorDao.searchAndCount(search);
		paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		else
		{
		List<FieldValidatorPo> fieldValidatorPoList = fieldValidatorDao.search(search);
		paginationResult = PaginationResult.pagination(fieldValidatorPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
		}
    	return paginationResult;
    }
	public PaginationResult<FieldValidatorPo> getFieldValidatorListByFieldId(Search search,Integer fieldId)
	{
		QueryParams queryParams = new QueryParams();
		search.setFirstResult(queryParams.getFirstResult());
		search.setMaxResults(queryParams.getPageSize());
		search.addFilterEqual("fieldId",fieldId);
		PaginationResult<FieldValidatorPo> paginationResult = null;
		if (queryParams.isInit()) {
			SearchResult<FieldValidatorPo> searchResult = fieldValidatorDao.searchAndCount(search);
			paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		else
		{
			List<FieldValidatorPo> fieldValidatorPoList = fieldValidatorDao.search(search);
			paginationResult = PaginationResult.pagination(fieldValidatorPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		return paginationResult;
	}

    public FieldValidatorPo getFieldValidator(Integer id)
	{
		return fieldValidatorDao.find(id);
	}
    public void saveFieldValidator(FieldValidatorPo fieldValidator)
	{
		fieldValidatorDao.save(fieldValidator);
	}
    public void deletes(Integer[] ids)
	{
		fieldValidatorDao.removeByIds(ids);
	}
}
