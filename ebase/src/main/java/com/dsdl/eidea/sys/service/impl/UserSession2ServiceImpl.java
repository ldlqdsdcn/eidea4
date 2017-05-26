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
import com.googlecode.genericdao.search.ISearch;
import com.dsdl.eidea.core.dao.CommonDao;
import java.util.List;
/**
 * @author 刘大磊 2017-05-08 09:55:07
 */
@Service("userSession2Service")
public class UserSession2ServiceImpl  implements	UserSession2Service {
	@DataAccess(entity =UserSession2Po.class)
	private CommonDao<UserSession2Po,Integer> userSession2Dao;
	public List<UserSession2Po> getUserSession2List(ISearch search)
    {
    	return userSession2Dao.search(search);
    }

    public UserSession2Po getUserSession2(Integer id)
	{
		return userSession2Dao.find(id);
	}
    public void saveUserSession2(UserSession2Po userSession2)
	{
		userSession2Dao.saveForLog(userSession2);
	}
    public void deletes(Integer[] ids)
	{
		userSession2Dao.removeByIdsForLog(ids);
	}
}
