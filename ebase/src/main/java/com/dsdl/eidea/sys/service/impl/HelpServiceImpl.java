/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.sys.service.impl;

import com.dsdl.eidea.core.spring.annotation.DataAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dsdl.eidea.sys.entity.po.HelpPo;
import com.dsdl.eidea.sys.service.HelpService;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.Search;
import com.dsdl.eidea.core.dao.CommonDao;
import java.util.List;
/**
 * @author 刘大磊 2017-04-26 15:55:56
 */
@Service("helpService")
public class HelpServiceImpl  implements	HelpService {
	@DataAccess(entity =HelpPo.class)
	private CommonDao<HelpPo,Integer> helpDao;
	public PaginationResult<HelpPo> getHelpListByPaging(Search search,QueryParams queryParams)
    {
		search.setFirstResult(queryParams.getFirstResult());
		search.setMaxResults(queryParams.getPageSize());
		PaginationResult<HelpPo> paginationResult = null;
		if (queryParams.isInit()) {
		SearchResult<HelpPo> searchResult = helpDao.searchAndCount(search);
		paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		else
		{
		List<HelpPo> helpPoList = helpDao.search(search);
		paginationResult = PaginationResult.pagination(helpPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
		}
    	return paginationResult;
    }

    public HelpPo getHelp(Integer id)
	{
		return helpDao.find(id);
	}
    public void saveHelp(HelpPo help)
	{
		helpDao.save(help);
	}
    public void deletes(Integer[] ids)
	{
		helpDao.removeByIds(ids);
	}
}
