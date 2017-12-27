/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package indi.liudalei.eidea.base.service;
import indi.liudalei.eidea.base.entity.po.WindowTrlPo;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;

/**
 * @author 刘大磊 2017-05-02 15:42:28
 */
public interface WindowTrlService {
	PaginationResult<WindowTrlPo> getWindowTrlListByPaging(Search search, QueryParams queryParams);
	PaginationResult<WindowTrlPo> getWindowTrlListByWindowId(Search search,Integer windowId);
	WindowTrlPo getWindowTrl(Integer id);
	void saveWindowTrl(WindowTrlPo windowTrl);
	void deletes(Integer[] ids);
}