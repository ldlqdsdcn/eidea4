/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.base.service;
import com.dsdl.eidea.base.entity.po.FileSettingPo;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;
import java.util.List;

/**
 * @author 刘大磊 2017-05-02 13:07:50
 */
public interface FileSettingService {
	PaginationResult<FileSettingPo> getFileSettingListByPaging(Search search,QueryParams queryParams);
	FileSettingPo getFileSetting(Integer id);
	void saveFileSetting(FileSettingPo fileSetting);
	void deletes(Integer[] ids);
	List<FileSettingPo> getFileSettingList(Search search);

	/**
	 * 通过访问路径获取文件设置
	 * @param path
	 * @return
	 */
	FileSettingPo getFileSettingsByRequestPath(String path);
}