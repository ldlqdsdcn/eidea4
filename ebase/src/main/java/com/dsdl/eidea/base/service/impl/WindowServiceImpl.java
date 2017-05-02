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
import com.dsdl.eidea.base.entity.po.WindowPo;
import com.dsdl.eidea.base.service.WindowService;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.Search;
import com.dsdl.eidea.core.dao.CommonDao;
import java.util.List;
/**
 * @author 刘大磊 2017-05-02 15:41:30
 */
@Service("windowService")
public class WindowServiceImpl  implements	WindowService {
	@DataAccess(entity =WindowPo.class)
	private CommonDao<WindowPo,Integer> windowDao;
	public PaginationResult<WindowPo> getWindowListByPaging(Search search,QueryParams queryParams)
    {
		search.setFirstResult(queryParams.getFirstResult());
		search.setMaxResults(queryParams.getPageSize());
		PaginationResult<WindowPo> paginationResult = null;
		if (queryParams.isInit()) {
		SearchResult<WindowPo> searchResult = windowDao.searchAndCount(search);
		paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		else
		{
		List<WindowPo> windowPoList = windowDao.search(search);
		paginationResult = PaginationResult.pagination(windowPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
		}
    	return paginationResult;
    }

    public WindowPo getWindow(Integer id)
	{
		return windowDao.find(id);
	}
    public void saveWindow(WindowPo window)
	{
		windowDao.save(window);
	}
    public void deletes(Integer[] ids)
	{
		windowDao.removeByIds(ids);
	}
}
