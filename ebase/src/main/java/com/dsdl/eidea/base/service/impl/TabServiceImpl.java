/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.base.service.impl;

import com.dsdl.eidea.base.entity.bo.FieldBo;
import com.dsdl.eidea.base.entity.bo.FieldInListPageBo;
import com.dsdl.eidea.base.entity.bo.TabBo;
import com.dsdl.eidea.base.entity.po.FieldPo;
import com.dsdl.eidea.base.entity.po.TabTrlPo;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 刘大磊 2017-05-02 15:43:14
 */
@Service("tabService")
public class TabServiceImpl  implements	TabService {
	@DataAccess(entity =TabPo.class)
	private CommonDao<TabPo,Integer> tabDao;
	@DataAccess(entity = TabTrlPo.class)
	private CommonDao<TabTrlPo,Integer> tabTrlDao;
	@DataAccess(entity = FieldPo.class)
	private CommonDao<FieldPo,Integer> fieldDao;


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

	public List<TabBo> getTabBoListByWindowId(Integer windowId, String lang)
	{
		Search search=new Search();
		search.addFilterEqual("windowId",windowId);
		search.addSortAsc("sortno");
		List<TabPo>tabPoList=tabDao.search(search);
		List<TabBo> tabBoList=new ArrayList<>();
		for(TabPo tabPo:tabPoList)
		{
			TabBo tabBo=new TabBo();
			Search trlSearch=new Search();
			trlSearch.addFilterEqual("tabId",tabPo.getId());
			trlSearch.addFilterEqual("lang",lang);
			TabTrlPo tabTrlPo=tabTrlDao.searchUnique(trlSearch);
			tabBo.setSeqNo(tabPo.getSortno());
			Search pkFieldSearch=new Search();
			pkFieldSearch.addFilterEqual("tabId",tabPo.getId());
			pkFieldSearch.addFilterEqual("columnId",tabPo.getTableColumnId());
			FieldPo pk=fieldDao.searchUnique(pkFieldSearch);
			tabBo.setPkFieldId(pk.getId());
			tabBo.setId(tabPo.getId());
			if(tabTrlPo!=null)
			{
				tabBo.setHelp(tabTrlPo.getHelp());
				tabBo.setTabName(tabTrlPo.getName());
			}
			else
			{
				tabBo.setTabName(tabPo.getName());
				tabBo.setHelp("");
			}
			tabBoList.add(tabBo);
		}

		return tabBoList;
	}



	@Override
	public PaginationResult<Map<String, String>> getTabList(Search search, QueryParams queryParams, Integer tabId, String lang) {

		return null;
	}

	@Override
	public Integer getTabPkFieldId(Integer tabId) {
		TabPo tabPo=tabDao.find(tabId);
		Search search=new Search();
		search.addFilterEqual("tabId",tabId);
		search.addFilterEqual("columnId",tabPo.getTableColumnId());
		FieldPo fieldPo=fieldDao.searchUnique(search);
		return fieldPo.getId();
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
