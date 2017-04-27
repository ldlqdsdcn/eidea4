/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.base.service;
import com.dsdl.eidea.base.entity.po.DatadictTypePo;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;
import java.util.List;

/**
 * @author 刘大磊 2017-04-26 15:54:01
 */
public interface DatadictTypeService {
	PaginationResult<DatadictTypePo> getDatadictTypeListByPaging(Search search,QueryParams queryParams);
	DatadictTypePo getDatadictType(Integer id);
	void saveDatadictType(DatadictTypePo datadictType);
	void deletes(Integer[] ids);
}