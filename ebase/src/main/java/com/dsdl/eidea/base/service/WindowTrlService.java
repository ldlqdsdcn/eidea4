/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.base.service;
import com.dsdl.eidea.base.entity.po.WindowTrlPo;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;
import java.util.List;

/**
 * @author 刘大磊 2017-05-02 15:42:28
 */
public interface WindowTrlService {
	PaginationResult<WindowTrlPo> getWindowTrlListByPaging(Search search,QueryParams queryParams);
	PaginationResult<WindowTrlPo> getWindowTrlListByWindowId(Search search,Integer windowId);
	WindowTrlPo getWindowTrl(Integer id);
	void saveWindowTrl(WindowTrlPo windowTrl);
	void deletes(Integer[] ids);
}