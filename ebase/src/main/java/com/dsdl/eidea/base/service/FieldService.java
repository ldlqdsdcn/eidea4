/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.base.service;
import com.dsdl.eidea.base.entity.bo.FieldBo;
import com.dsdl.eidea.base.entity.bo.FieldInListPageBo;
import com.dsdl.eidea.base.entity.po.FieldPo;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;
import java.util.List;
import java.util.Map;

/**
 * @author 刘大磊 2017-05-04 13:22:23
 */
public interface FieldService {
	PaginationResult<FieldPo> getFieldListByPaging(Search search,QueryParams queryParams);
	PaginationResult<FieldPo> getFieldListByColumnId(Search search,Integer columnId);
	boolean findExistFieldByName(String name);
	FieldPo getField(Integer id);
	void saveField(FieldPo field);
	void deletes(Integer[] ids);

	/**
	 * 根据tabID 查询出编辑页面字段列表
	 * @param tabId
	 * @return
	 */
	List<FieldInListPageBo> getListPageFiledList(Integer tabId,String lang);

	/**
	 * 根据tabid查询出form页面字段列表
	 * @param tabId
	 * @return
	 */
	List<FieldBo> getFormPageFieldList(Integer tabId,String lang);

    /**
     *
     * @param tabId
     * @param bgn
     * @param size
     * @return
     */
	PaginationResult<Map<String, String>> getDataList(Integer tabId,  QueryParams queryParams);
}