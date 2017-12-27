/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package indi.liudalei.eidea.base.service;
import indi.liudalei.eidea.base.entity.bo.WindowBo;
import indi.liudalei.eidea.base.entity.bo.WindowHelpBo;
import indi.liudalei.eidea.base.entity.po.WindowPo;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;

import java.util.List;

/**
 * @author 刘大磊 2017-05-02 15:41:30
 */
public interface WindowService {
	PaginationResult<WindowPo> getWindowListByPaging(Search search, QueryParams queryParams);
	WindowPo getWindow(Integer id);
	void saveWindow(WindowPo window);
	void deletes(Integer[] ids);
	/**
	 * 获取窗体信息
	 * @param id 窗体主键
	 * @param lang 语言码
	 * @return
	 */
	WindowBo getWindowBo(Integer id,String lang);
	boolean findExistWindowByName(String name);
	WindowPo getExistWindowByName(String name);

	/**
	 * 获取窗体帮助信息
	 * @param windowId
	 * @return
	 */
	WindowHelpBo getWindowHelpBo(Integer windowId,String lang);

	/**
	 * 获取窗体列表
	 * @param lang
	 * @return
	 */
	List<WindowBo> getWindows(String lang);
}