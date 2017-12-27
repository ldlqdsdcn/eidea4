/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package indi.liudalei.eidea.base.service;
import indi.liudalei.eidea.base.entity.po.FieldSelectItemPo;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;

/**
 * @author 刘大磊 2017-05-03 17:51:03
 */
public interface FieldSelectItemService {
	PaginationResult<FieldSelectItemPo> getFieldSelectItemListByPaging(Search search, QueryParams queryParams);
	FieldSelectItemPo getFieldSelectItem(Integer id);
	void saveFieldSelectItem(FieldSelectItemPo fieldSelectItem);
	void deletes(Integer[] ids);
}