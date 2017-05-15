/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.test.service;
import com.dsdl.eidea.test.entity.po.LeavePo;
import com.googlecode.genericdao.search.ISearch;
import java.util.List;

/**
 * @author 刘大磊 2017-05-12 13:36:48
 */
public interface LeaveService {
    List<LeavePo> getLeaveList(ISearch search);
	LeavePo getLeave(Integer id);
	void saveLeave(LeavePo leave);

	/**
	 * 启用请假申请
	 */
	void saveStartLeave(LeavePo leave);
	void deletes(Integer[] ids);
}