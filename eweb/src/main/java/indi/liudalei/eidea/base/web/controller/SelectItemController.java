/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package indi.liudalei.eidea.base.web.controller;

import indi.liudalei.eidea.base.entity.po.SelectItemPo;
import indi.liudalei.eidea.base.service.SelectItemService;
import indi.liudalei.eidea.core.web.controller.BaseController;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.DeleteParams;
import indi.liudalei.eidea.core.web.def.WebConst;
import indi.liudalei.eidea.core.web.result.def.ErrorCodes;
import indi.liudalei.eidea.core.web.util.SearchHelper;
import indi.liudalei.eidea.core.web.vo.PagingSettingResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import indi.liudalei.eidea.core.web.result.JsonResult;
import com.googlecode.genericdao.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import indi.liudalei.eidea.core.params.QueryParams;

import javax.servlet.http.HttpSession;

/**
* Created by 刘大磊 on 2017-05-03 17:49:28.
*/ @Controller
@RequestMapping("/base/selectItem")
public class SelectItemController extends BaseController {
private static final String URI = "selectItem";
@Autowired
private SelectItemService selectItemService;
@RequestMapping(value = "/showList", method = RequestMethod.GET)
@RequiresPermissions("view")
public ModelAndView showList() {
ModelAndView modelAndView = new ModelAndView("/base/selectItem/selectItem");
modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDbPaging());
modelAndView.addObject(WebConst.PAGE_URI, URI);
return modelAndView;
}
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("view")
public JsonResult<PaginationResult<SelectItemPo>> list(HttpSession session, @RequestBody QueryParams queryParams) {
    Search search = SearchHelper.getSearchParam(URI, session);
    PaginationResult<SelectItemPo> paginationResult = selectItemService.getSelectItemListByPaging(search, queryParams);
    return JsonResult.success(paginationResult);
    }
    @RequiresPermissions("view")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<SelectItemPo> get(Integer id) {
        SelectItemPo selectItemPo = null;
        if (id == null) {
        return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(),getMessage("common.errror.get_object",getLabel("selectItem.title")));
        } else {
        selectItemPo = selectItemService.getSelectItem(id);
        }
        return JsonResult.success(selectItemPo);
        }

        @RequiresPermissions("add")
        @RequestMapping(value = "/create", method = RequestMethod.GET)
        @ResponseBody
        public JsonResult<SelectItemPo> create() {
            SelectItemPo selectItemPo = new SelectItemPo();
            return JsonResult.success(selectItemPo);
            }

    /**
    * @param selectItemPo
    * @return
    */
    @RequiresPermissions("add")
    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<SelectItemPo> saveForCreate(@Validated @RequestBody SelectItemPo selectItemPo) {
        selectItemService.saveSelectItem(selectItemPo);
        return get(selectItemPo.getId());
        }

        @RequiresPermissions("update")
        @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
        @ResponseBody
        public JsonResult<SelectItemPo> saveForUpdate(@Validated @RequestBody SelectItemPo selectItemPo) {

            if(selectItemPo.getId() == null){
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.errror.pk.required"));
            }
            selectItemService.saveSelectItem(selectItemPo);
            return get(selectItemPo.getId());
            }

    @RequiresPermissions("delete")
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody

    public JsonResult<PaginationResult<SelectItemPo>> deletes(@RequestBody DeleteParams<Integer> deleteParams, HttpSession session) {
    if (deleteParams.getIds() == null||deleteParams.getIds().length == 0)  {
                return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.error.delete.failure",getMessage("selectItem.title")));
                }
            selectItemService.deletes(deleteParams.getIds());
                return list(session,deleteParams.getQueryParams());
        }


}
