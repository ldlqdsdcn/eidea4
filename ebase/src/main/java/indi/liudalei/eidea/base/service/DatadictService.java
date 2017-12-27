/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package indi.liudalei.eidea.base.service;
import indi.liudalei.eidea.base.entity.po.DatadictPo;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;

import java.util.List;

/**
 * @author 刘大磊 2017-04-26 15:34:59
 */
public interface DatadictService {
	PaginationResult<DatadictPo> getDatadictListByPaging(Search search, QueryParams queryParams);
	PaginationResult<DatadictPo> getDatadictListByDatadictType(Search search, String datadcitType);
	DatadictPo getDatadict(Integer id);
	void saveDatadict(DatadictPo datadict);
	void deletes(Integer[] ids);
	boolean findExistCode(String code);
	List<DatadictPo> findExistDatadictByCode(String code);
}