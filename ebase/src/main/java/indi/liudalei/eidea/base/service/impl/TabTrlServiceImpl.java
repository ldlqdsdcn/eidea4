/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package indi.liudalei.eidea.base.service.impl;

import indi.liudalei.eidea.core.spring.annotation.DataAccess;
import indi.liudalei.eidea.base.entity.po.TabTrlPo;
import indi.liudalei.eidea.base.service.TabTrlService;
import indi.liudalei.eidea.core.dao.CommonDao;
import indi.liudalei.eidea.core.dto.PaginationResult;
import org.springframework.stereotype.Service;
import indi.liudalei.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.Search;

import java.util.List;
/**
 * @author 刘大磊 2017-05-02 15:43:44
 */
@Service("tabTrlService")
public class TabTrlServiceImpl  implements TabTrlService {
	@DataAccess(entity =TabTrlPo.class)
	private CommonDao<TabTrlPo,Integer> tabTrlDao;
	public PaginationResult<TabTrlPo> getTabTrlListByPaging(Search search, QueryParams queryParams)
    {
		search.setFirstResult(queryParams.getFirstResult());
		search.setMaxResults(queryParams.getPageSize());
		PaginationResult<TabTrlPo> paginationResult = null;
		if (queryParams.isInit()) {
		SearchResult<TabTrlPo> searchResult = tabTrlDao.searchAndCount(search);
		paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		else
		{
		List<TabTrlPo> tabTrlPoList = tabTrlDao.search(search);
		paginationResult = PaginationResult.pagination(tabTrlPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
		}
    	return paginationResult;
    }
	public PaginationResult<TabTrlPo> getTabTrlListByTabId(Search search,Integer tabId)
	{
		QueryParams queryParams = new QueryParams();
		search.setFirstResult(queryParams.getFirstResult());
		search.setMaxResults(queryParams.getPageSize());
		search.addFilterEqual("tabId",tabId);
		PaginationResult<TabTrlPo> paginationResult = null;
		if (queryParams.isInit()) {
			SearchResult<TabTrlPo> searchResult = tabTrlDao.searchAndCount(search);
			paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		else
		{
			List<TabTrlPo> tabTrlPoList = tabTrlDao.search(search);
			paginationResult = PaginationResult.pagination(tabTrlPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		return paginationResult;
	}
    public TabTrlPo getTabTrl(Integer id)
	{
		return tabTrlDao.find(id);
	}
    public void saveTabTrl(TabTrlPo tabTrl)
	{
		tabTrlDao.save(tabTrl);
	}
    public void deletes(Integer[] ids)
	{
		tabTrlDao.removeByIds(ids);
	}
}
