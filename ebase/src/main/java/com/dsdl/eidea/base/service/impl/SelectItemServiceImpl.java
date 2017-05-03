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
import com.dsdl.eidea.base.entity.po.SelectItemPo;
import com.dsdl.eidea.base.service.SelectItemService;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.Search;
import com.dsdl.eidea.core.dao.CommonDao;
import java.util.List;
/**
 * @author 刘大磊 2017-05-03 17:49:28
 */
@Service("selectItemService")
public class SelectItemServiceImpl  implements	SelectItemService {
	@DataAccess(entity =SelectItemPo.class)
	private CommonDao<SelectItemPo,Integer> selectItemDao;
	public PaginationResult<SelectItemPo> getSelectItemListByPaging(Search search,QueryParams queryParams)
    {
		search.setFirstResult(queryParams.getFirstResult());
		search.setMaxResults(queryParams.getPageSize());
		PaginationResult<SelectItemPo> paginationResult = null;
		if (queryParams.isInit()) {
		SearchResult<SelectItemPo> searchResult = selectItemDao.searchAndCount(search);
		paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		else
		{
		List<SelectItemPo> selectItemPoList = selectItemDao.search(search);
		paginationResult = PaginationResult.pagination(selectItemPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
		}
    	return paginationResult;
    }

    public SelectItemPo getSelectItem(Integer id)
	{
		return selectItemDao.find(id);
	}
    public void saveSelectItem(SelectItemPo selectItem)
	{
		selectItemDao.save(selectItem);
	}
    public void deletes(Integer[] ids)
	{
		selectItemDao.removeByIds(ids);
	}
}
