/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package indi.liudalei.eidea.base.service;
import indi.liudalei.eidea.base.entity.po.FieldTrlPo;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;

/**
 * @author 刘大磊 2017-05-02 15:46:44
 */
public interface FieldTrlService {
	PaginationResult<FieldTrlPo> getFieldTrlListByPaging(Search search, QueryParams queryParams);
	PaginationResult<FieldTrlPo> getFieldTrlListByField(Search search, Integer field);
	FieldTrlPo getFieldTrl(Integer id);
	void saveFieldTrl(FieldTrlPo fieldTrl);
	void deletes(Integer[] ids);
}