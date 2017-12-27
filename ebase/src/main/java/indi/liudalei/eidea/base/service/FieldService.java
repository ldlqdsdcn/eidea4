/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package indi.liudalei.eidea.base.service;

import indi.liudalei.eidea.base.entity.bo.FieldInListPageBo;
import indi.liudalei.eidea.base.entity.bo.SelectItemBo;
import indi.liudalei.eidea.base.entity.bo.UserBo;
import indi.liudalei.eidea.base.entity.po.FieldPo;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.QueryParams;
import indi.liudalei.eidea.general.bo.TabFormStructureBo;
import com.googlecode.genericdao.search.Search;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 刘大磊 2017-05-04 13:22:23
 */
public interface FieldService {
	PaginationResult<FieldPo> getFieldListByPaging(Search search, QueryParams queryParams);
	PaginationResult<FieldPo> getFieldListByTabId(Search search, Integer tabId);
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
	TabFormStructureBo getFormPageFieldList(Integer tabId, String lang);

    /**
     *
     * @param tabId
     * @param queryParams
     * @return
     */
	PaginationResult<Map<String, Object>> getDataList(Integer tabId,  QueryParams queryParams);

	/**
	 * 根据tabId和record获取记录
	 * @param tabId
	 * @param recordId
	 * @return
	 */
	Map<String, Object> getDataForm(Integer tabId, Serializable recordId) ;

	/**
	 * 修改记录
	 * @param param
	 * @return
	 */
	Map<String,Object> saveForUpdated(Integer tabId, Map<String,Object> param, UserBo userBo);

	/**
	 * 添加记录
	 * @param param
	 * @return
	 */
	Map<String,Object> saveForCreated(Integer tabId,Map<String,Object> param,UserBo userBo);

	/**
	 * 新建对象
	 * @param tabId
	 * @return
	 */
	Map<String,Object> getNewObject(Integer tabId,String lang, UserBo userBo);

	/**
	 * 删除记录
	 * @param tabId
	 * @param ids
	 */
	void deleteList(Integer tabId,Object[] ids);

	/**
	 * 获取Select选择列表
	 * @param fieldId
	 * @return
	 */
	List<SelectItemBo> getSelectItemList(Integer fieldId);
}