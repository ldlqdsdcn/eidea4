/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.base.service;
import com.dsdl.eidea.base.entity.po.CommonFilePo;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;
import java.util.List;

/**
 * @author 刘大磊 2017-05-02 13:09:39
 */
public interface CommonFileService {
	PaginationResult<CommonFilePo> getCommonFileListByPaging(Search search,QueryParams queryParams);
	CommonFilePo getCommonFile(Integer id);
	void saveCommonFile(CommonFilePo commonFile);
	void deletes(Integer[] ids);
}