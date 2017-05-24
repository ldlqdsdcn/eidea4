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
import com.dsdl.eidea.base.entity.po.TabPo;
import com.dsdl.eidea.base.service.TabService;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.Search;
import com.dsdl.eidea.core.dao.CommonDao;
import java.util.List;
/**
 * @author 刘大磊 2017-05-02 15:43:14
 */
@Service("tabService")
public class TabServiceImpl  implements	TabService {
	@DataAccess(entity =TabPo.class)
	private CommonDao<TabPo,Integer> tabDao;
	public PaginationResult<TabPo> getTabListByPaging(Search search,QueryParams queryParams)
    {
		search.setFirstResult(queryParams.getFirstResult());
		search.setMaxResults(queryParams.getPageSize());
		PaginationResult<TabPo> paginationResult = null;
		if (queryParams.isInit()) {
		SearchResult<TabPo> searchResult = tabDao.searchAndCount(search);
		paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		else
		{
		List<TabPo> tabPoList = tabDao.search(search);
		paginationResult = PaginationResult.pagination(tabPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
		}
    	return paginationResult;
    }
	public PaginationResult<TabPo> getTabListByWindowId(Search search,Integer id)
	{
		QueryParams queryParams= new QueryParams();
		search.setFirstResult(queryParams.getFirstResult());
		search.setMaxResults(queryParams.getPageSize());
		search.addFilterEqual("windowId",id);
		PaginationResult<TabPo> paginationResult = null;
		if (queryParams.isInit()) {
			SearchResult<TabPo> searchResult = tabDao.searchAndCount(search);
			paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		else
		{
			List<TabPo> tabPoList = tabDao.search(search);
			paginationResult = PaginationResult.pagination(tabPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		return paginationResult;
	}

    public TabPo getTab(Integer id)
	{
		return tabDao.find(id);
	}
    public void saveTab(TabPo tab)
	{
		tabDao.save(tab);
	}
    public void deletes(Integer[] ids)
	{
		tabDao.removeByIds(ids);
	}
}
