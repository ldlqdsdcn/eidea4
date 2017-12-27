/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package indi.liudalei.eidea.base.web.controller;

import indi.liudalei.eidea.base.entity.po.FieldSelectItemPo;
import indi.liudalei.eidea.base.service.FieldSelectItemService;
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
* Created by 刘大磊 on 2017-05-03 17:51:03.
*/ @Controller
@RequestMapping("/base/fieldSelectItem")
public class FieldSelectItemController extends BaseController {
private static final String URI = "fieldSelectItem";
@Autowired
private FieldSelectItemService fieldSelectItemService;
@RequestMapping(value = "/showList", method = RequestMethod.GET)
@RequiresPermissions("view")
public ModelAndView showList() {
ModelAndView modelAndView = new ModelAndView("/base/fieldSelectItem/fieldSelectItem");
modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDbPaging());
modelAndView.addObject(WebConst.PAGE_URI, URI);
return modelAndView;
}
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("view")
public JsonResult<PaginationResult<FieldSelectItemPo>> list(HttpSession session, @RequestBody QueryParams queryParams) {
    Search search = SearchHelper.getSearchParam(URI, session);
    PaginationResult<FieldSelectItemPo> paginationResult = fieldSelectItemService.getFieldSelectItemListByPaging(search, queryParams);
    return JsonResult.success(paginationResult);
    }
    @RequiresPermissions("view")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<FieldSelectItemPo> get(Integer id) {
        FieldSelectItemPo fieldSelectItemPo = null;
        if (id == null) {
        return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(),getMessage("common.errror.get_object",getLabel("fieldSelectItem.title")));
        } else {
        fieldSelectItemPo = fieldSelectItemService.getFieldSelectItem(id);
        }
        return JsonResult.success(fieldSelectItemPo);
        }

        @RequiresPermissions("add")
        @RequestMapping(value = "/create", method = RequestMethod.GET)
        @ResponseBody
        public JsonResult<FieldSelectItemPo> create() {
            FieldSelectItemPo fieldSelectItemPo = new FieldSelectItemPo();
            return JsonResult.success(fieldSelectItemPo);
            }

    /**
    * @param fieldSelectItemPo
    * @return
    */
    @RequiresPermissions("add")
    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<FieldSelectItemPo> saveForCreate(@Validated @RequestBody FieldSelectItemPo fieldSelectItemPo) {
        fieldSelectItemService.saveFieldSelectItem(fieldSelectItemPo);
        return get(fieldSelectItemPo.getId());
        }

        @RequiresPermissions("update")
        @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
        @ResponseBody
        public JsonResult<FieldSelectItemPo> saveForUpdate(@Validated @RequestBody FieldSelectItemPo fieldSelectItemPo) {

            if(fieldSelectItemPo.getId() == null){
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.errror.pk.required"));
            }
            fieldSelectItemService.saveFieldSelectItem(fieldSelectItemPo);
            return get(fieldSelectItemPo.getId());
            }

    @RequiresPermissions("delete")
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody

    public JsonResult<PaginationResult<FieldSelectItemPo>> deletes(@RequestBody DeleteParams<Integer> deleteParams, HttpSession session) {
    if (deleteParams.getIds() == null||deleteParams.getIds().length == 0)  {
                return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.error.delete.failure",getMessage("fieldSelectItem.title")));
                }
            fieldSelectItemService.deletes(deleteParams.getIds());
                return list(session,deleteParams.getQueryParams());
        }


}
