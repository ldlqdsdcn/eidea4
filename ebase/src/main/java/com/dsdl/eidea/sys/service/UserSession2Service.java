/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.sys.service;
import com.dsdl.eidea.sys.entity.po.UserSession2Po;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;
import java.util.List;

/**
 * @author 刘大磊 2017-04-20 09:49:45
 */
public interface UserSession2Service {
	PaginationResult<UserSession2Po> getUserSession2ListByPaging(Search search,QueryParams queryParams);
	UserSession2Po getUserSession2(Integer id);
	void saveUserSession2(UserSession2Po userSession2);
	void deletes(Integer[] ids);
}