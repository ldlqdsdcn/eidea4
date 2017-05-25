/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.base.service.impl;

import com.dsdl.eidea.base.entity.bo.TabBo;
import com.dsdl.eidea.base.entity.bo.WindowBo;
import com.dsdl.eidea.base.entity.po.FieldPo;
import com.dsdl.eidea.base.entity.po.TabPo;
import com.dsdl.eidea.base.entity.po.WindowTrlPo;
import com.dsdl.eidea.base.service.TabService;
import com.dsdl.eidea.base.service.WindowTrlService;
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
	@DataAccess(entity =FieldPo.class)
	private CommonDao<FieldPo,Integer> fieldDao;
	@DataAccess(entity =WindowTrlPo.class)
	private CommonDao<WindowTrlPo,Integer> windowTrlDao;
	@Autowired
	private TabService tabService;
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

	@Override
	public WindowBo getWindowBo(Integer id,String lang) {
		WindowPo windowPo=windowDao.find(id);
		Search search=new Search();
		search.addFilterEqual("windowId",id);
		search.addFilterEqual("lang",lang);
		WindowTrlPo windowTrlPo=windowTrlDao.searchUnique(search);
		WindowBo windowBo=new WindowBo();
		windowBo.setWindowId(windowPo.getId());
		if(windowTrlPo!=null)
		{
			windowBo.setHelp(windowTrlPo.getHelp());
			windowBo.setWindowName(windowTrlPo.getName());
		}
		else
		{
			windowBo.setWindowName(windowPo.getName());
			windowBo.setHelp("");
		}
		List<TabBo> tabBoList=tabService.getTabBoListByWindowId(windowPo.getId(),lang);
		windowBo.setTabList(tabBoList);
		return windowBo;
	}
	@Override
	public boolean findExistWindowByName(String name){
		Search search = new Search();
		search.addFilterEqual("name",name);
		List<WindowPo> windowPoList = windowDao.search(search);
		if (windowPoList.size()>0&&windowPoList!=null){
			return true;
		}else{
			return false;
		}
	}
	@Override
	public WindowPo getExistWindowByName(String name){
		Search search = new Search();
		search.addFilterEqual("name",name);
		WindowPo windowPo = windowDao.searchUnique(search);
		return windowPo;
	}
}
