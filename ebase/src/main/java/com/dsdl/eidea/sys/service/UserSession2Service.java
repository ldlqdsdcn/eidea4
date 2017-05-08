/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.sys.service;
import com.dsdl.eidea.sys.entity.po.UserSession2Po;
import com.googlecode.genericdao.search.ISearch;
import java.util.List;

/**
 * @author 刘大磊 2017-05-08 09:55:07
 */
public interface UserSession2Service {
    List<UserSession2Po> getUserSession2List(ISearch search);
	UserSession2Po getUserSession2(Integer id);
	void saveUserSession2(UserSession2Po userSession2);
	void deletes(Integer[] ids);
}