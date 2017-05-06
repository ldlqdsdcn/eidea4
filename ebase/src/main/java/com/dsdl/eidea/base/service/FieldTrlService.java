/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.base.service;
import com.dsdl.eidea.base.entity.po.FieldTrlPo;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;
import java.util.List;

/**
 * @author 刘大磊 2017-05-02 15:46:44
 */
public interface FieldTrlService {
	PaginationResult<FieldTrlPo> getFieldTrlListByPaging(Search search,QueryParams queryParams);
	FieldTrlPo getFieldTrl(Integer id);
	void saveFieldTrl(FieldTrlPo fieldTrl);
	void deletes(Integer[] ids);
}