/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.base.service;
import com.dsdl.eidea.base.entity.bo.FieldBo;
import com.dsdl.eidea.base.entity.bo.FieldInListPageBo;
import com.dsdl.eidea.base.entity.bo.TabBo;
import com.dsdl.eidea.base.entity.po.TabPo;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;
import java.util.List;
import java.util.Map;

/**
 * @author 刘大磊 2017-05-02 15:43:14
 */
public interface TabService {
	PaginationResult<TabPo> getTabListByPaging(Search search,QueryParams queryParams);
	PaginationResult<TabPo> getTabListByWindowId(Search search,Integer id);
	TabPo getTab(Integer id);
	void saveTab(TabPo tab);
	void deletes(Integer[] ids);

	/**
	 * 根据windowId获取Tab 列表
	 * @param windowId
	 * @return
	 */
	List<TabBo> getTabBoListByWindowId(Integer windowId,String lang);

	/**
	 * 根据tabID 查询出编辑页面字段列表
	 * @param tabId
	 * @return
	 */
	List<FieldInListPageBo> getListPageFiledList(Integer tabId);

	/**
	 * 根据tabid查询出form页面字段列表
	 * @param tabId
	 * @return
	 */
	List<FieldBo> getFormPageFieldList(Integer tabId);

	/**
	 * 根据tab_id,语言，获取数据
	 * @param search
	 * @param queryParams
	 * @param tabId
	 * @return
	 */
	PaginationResult<Map<String,String>> getTabList(Search search,QueryParams queryParams,Integer tabId,String lang);
}