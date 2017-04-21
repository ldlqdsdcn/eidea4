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
import com.dsdl.eidea.sys.entity.po.UserSession2Po;
import com.dsdl.eidea.sys.service.UserSession2Service;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.Search;
import com.dsdl.eidea.core.dao.CommonDao;
import java.util.List;
/**
 * @author 刘大磊 2017-04-21 14:21:47
 */
@Service("userSession2Service")
public class UserSession2ServiceImpl  implements	UserSession2Service {
	@DataAccess(entity =UserSession2Po.class)
	private CommonDao<UserSession2Po,Integer> userSession2Dao;
	public PaginationResult<UserSession2Po> getUserSession2ListByPaging(Search search,QueryParams queryParams)
    {
		search.setFirstResult(queryParams.getFirstResult());
		search.setMaxResults(queryParams.getPageSize());
		PaginationResult<UserSession2Po> paginationResult = null;
		if (queryParams.isInit()) {
		SearchResult<UserSession2Po> searchResult = userSession2Dao.searchAndCount(search);
		paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		else
		{
		List<UserSession2Po> userSession2PoList = userSession2Dao.search(search);
		paginationResult = PaginationResult.pagination(userSession2PoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
		}
    	return paginationResult;
    }

    public UserSession2Po getUserSession2(Integer id)
	{
		return userSession2Dao.find(id);
	}
    public void saveUserSession2(UserSession2Po userSession2)
	{
		userSession2Dao.save(userSession2);
	}
    public void deletes(Integer[] ids)
	{
		userSession2Dao.removeByIds(ids);
	}
}
