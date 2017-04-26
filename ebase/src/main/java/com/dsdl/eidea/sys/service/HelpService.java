/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.sys.service;
import com.dsdl.eidea.sys.entity.po.HelpPo;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;
import java.util.List;

/**
 * @author 刘大磊 2017-04-26 15:55:56
 */
public interface HelpService {
	PaginationResult<HelpPo> getHelpListByPaging(Search search,QueryParams queryParams);
	HelpPo getHelp(Integer id);
	void saveHelp(HelpPo help);
	void deletes(Integer[] ids);
}