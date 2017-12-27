/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package indi.liudalei.eidea.base.service;
import indi.liudalei.eidea.base.entity.po.FieldValidatorPo;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;

/**
 * @author 刘大磊 2017-05-02 15:49:09
 */
public interface FieldValidatorService {
	PaginationResult<FieldValidatorPo> getFieldValidatorListByPaging(Search search, QueryParams queryParams);
	PaginationResult<FieldValidatorPo> getFieldValidatorListByFieldId(Search search,Integer fieldId);
	FieldValidatorPo getFieldValidator(Integer id);
	void saveFieldValidator(FieldValidatorPo fieldValidator);
	void deletes(Integer[] ids);
}