/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.base.service;
import com.dsdl.eidea.base.entity.po.SettingsPo;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;
import java.util.List;

/**
 * @author 刘大磊 2017-05-06 07:51:36
 */
public interface SettingsService {
	PaginationResult<SettingsPo> getSettingsListByPaging(Search search,QueryParams queryParams);
	SettingsPo getSettings(String key);
	void saveSettings(SettingsPo settings);
	void deletes(String[] keys);

	/**
	 * 是否显示空菜单文件夹
	 * @return
	 */
	boolean getShowEmptyMenuFolder();
}