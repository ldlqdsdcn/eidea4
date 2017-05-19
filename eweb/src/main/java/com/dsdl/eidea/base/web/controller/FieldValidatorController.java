/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.base.web.controller;

import com.dsdl.eidea.base.entity.po.FieldValidatorPo;
import com.dsdl.eidea.base.service.FieldValidatorService;
import com.dsdl.eidea.core.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.JsonResult;
import com.dsdl.eidea.core.web.result.def.ErrorCodes;
import com.dsdl.eidea.core.web.util.SearchHelper;
import com.dsdl.eidea.core.web.vo.PagingSettingResult;
import com.googlecode.genericdao.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.dsdl.eidea.core.params.DeleteParams;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
* Created by 刘大磊 on 2017-05-02 15:49:09.
*/ @Controller
@RequestMapping("/base/fieldValidator")
public class FieldValidatorController extends BaseController {
private static final String URI = "fieldValidator";
@Autowired
private FieldValidatorService fieldValidatorService;
@RequestMapping(value = "/showList", method = RequestMethod.GET)
@RequiresPermissions("view")
public ModelAndView showList() {
ModelAndView modelAndView = new ModelAndView("/base/fieldValidator/fieldValidator");
modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDbPaging());
modelAndView.addObject(WebConst.PAGE_URI, URI);
return modelAndView;
}
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("view")
public JsonResult<PaginationResult<FieldValidatorPo>> list(HttpSession session,@RequestBody QueryParams queryParams) {
    Search search = SearchHelper.getSearchParam(URI, session);
    PaginationResult<FieldValidatorPo> paginationResult = fieldValidatorService.getFieldValidatorListByPaging(search, queryParams);
    return JsonResult.success(paginationResult);
    }
    @RequestMapping(value = "/fieldValidatorList", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("view")
    public JsonResult<PaginationResult<FieldValidatorPo>> fieldValidatorlist(HttpSession session,@RequestBody Integer fieldId) {
        Search search = SearchHelper.getSearchParam(URI, session);
        PaginationResult<FieldValidatorPo> paginationResult = fieldValidatorService.getFieldValidatorListByFieldId(search, fieldId);
        return JsonResult.success(paginationResult);
    }
    @RequiresPermissions("view")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<FieldValidatorPo> get(Integer id) {
        FieldValidatorPo fieldValidatorPo = null;
        if (id == null) {
        return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(),getMessage("common.errror.get_object",getLabel("fieldValidator.title")));
        } else {
        fieldValidatorPo = fieldValidatorService.getFieldValidator(id);
        }
        return JsonResult.success(fieldValidatorPo);
        }

        @RequiresPermissions("add")
        @RequestMapping(value = "/create", method = RequestMethod.GET)
        @ResponseBody
        public JsonResult<FieldValidatorPo> create() {
            FieldValidatorPo fieldValidatorPo = new FieldValidatorPo();
            return JsonResult.success(fieldValidatorPo);
            }

    /**
    * @param fieldValidatorPo
    * @return
    */
    @RequiresPermissions("add")
    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<FieldValidatorPo> saveForCreate(@Validated @RequestBody FieldValidatorPo fieldValidatorPo) {
        fieldValidatorService.saveFieldValidator(fieldValidatorPo);
        return get(fieldValidatorPo.getId());
        }

        @RequiresPermissions("update")
        @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
        @ResponseBody
        public JsonResult<FieldValidatorPo> saveForUpdate(@Validated @RequestBody FieldValidatorPo fieldValidatorPo) {

            if(fieldValidatorPo.getId() == null){
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.errror.pk.required"));
            }
            fieldValidatorService.saveFieldValidator(fieldValidatorPo);
            return get(fieldValidatorPo.getId());
            }

    @RequiresPermissions("delete")
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody

    public JsonResult<PaginationResult<FieldValidatorPo>> deletes(@RequestBody DeleteParams<Integer> deleteParams, HttpSession session) {
    if (deleteParams.getIds() == null||deleteParams.getIds().length == 0)  {
                return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.error.delete.failure",getMessage("fieldValidator.title")));
                }
                Integer fieldId=fieldValidatorService.getFieldValidator(deleteParams.getIds()[0]).getFieldId();
            fieldValidatorService.deletes(deleteParams.getIds());
                return fieldValidatorlist(session,fieldId);
        }


}
