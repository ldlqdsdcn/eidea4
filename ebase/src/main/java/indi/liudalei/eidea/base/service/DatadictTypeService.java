/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package indi.liudalei.eidea.base.service;
import indi.liudalei.eidea.base.entity.po.DatadictTypePo;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;

import java.util.List;

/**
 * @author 刘大磊 2017-04-26 15:54:01
 */
public interface DatadictTypeService {
	PaginationResult<DatadictTypePo> getDatadictTypeListByPaging(Search search, QueryParams queryParams);
	DatadictTypePo getDatadictType(Integer id);
	void saveDatadictType(DatadictTypePo datadictType);
	void deletes(Integer[] ids);
	List<DatadictTypePo> getDatadictTypeList();
	boolean findExistDatadictTypeValue(String value);
	DatadictTypePo findExistDatadictTypeByValue(String value);
}