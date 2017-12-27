/**
 * 版权所有 刘大磊 2013-07-01
 * 作者：刘大磊
 * 电话：13336390671
 * email:ldlqdsd@126.com
 */
package indi.liudalei.eidea.base.web.controller;

import indi.liudalei.eidea.base.entity.po.FieldTrlPo;
import indi.liudalei.eidea.base.service.FieldTrlService;
import indi.liudalei.eidea.core.web.controller.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import indi.liudalei.eidea.core.web.def.WebConst;
import indi.liudalei.eidea.core.web.result.JsonResult;
import indi.liudalei.eidea.core.web.result.def.ErrorCodes;
import indi.liudalei.eidea.core.web.util.SearchHelper;
import indi.liudalei.eidea.core.web.vo.PagingSettingResult;
import com.googlecode.genericdao.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.QueryParams;
import indi.liudalei.eidea.core.params.DeleteParams;

import javax.servlet.http.HttpSession;

/**
 * Created by 刘大磊 on 2017-05-02 15:46:44.
 */
@Controller
@RequestMapping("/base/fieldTrl")
public class FieldTrlController extends BaseController {
    private static final String URI = "fieldTrl";
    @Autowired
    private FieldTrlService fieldTrlService;

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @RequiresPermissions("view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/base/fieldTrl/fieldTrl");
        modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDbPaging());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("view")
    public JsonResult<PaginationResult<FieldTrlPo>> list(HttpSession session, @RequestBody QueryParams queryParams) {
        Search search = SearchHelper.getSearchParam(URI, session);
        PaginationResult<FieldTrlPo> paginationResult = fieldTrlService.getFieldTrlListByPaging(search, queryParams);
        return JsonResult.success(paginationResult);
    }

    @RequestMapping(value = "/fieldTrlList", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("view")
    public JsonResult<PaginationResult<FieldTrlPo>> fieldTrlList(HttpSession session, @RequestBody Integer field) {
        Search search = SearchHelper.getSearchParam(URI, session);
        PaginationResult<FieldTrlPo> paginationResult = fieldTrlService.getFieldTrlListByField(search, field);
        return JsonResult.success(paginationResult);
    }

    @RequiresPermissions("view")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<FieldTrlPo> get(Integer id) {
        FieldTrlPo fieldTrlPo = null;
        if (id == null) {
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.errror.get_object", getLabel("fieldTrl.title")));
        } else {
            fieldTrlPo = fieldTrlService.getFieldTrl(id);
        }
        return JsonResult.success(fieldTrlPo);
    }

    @RequiresPermissions("add")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<FieldTrlPo> create() {
        FieldTrlPo fieldTrlPo = new FieldTrlPo();
        return JsonResult.success(fieldTrlPo);
    }

    /**
     * @param fieldTrlPo
     * @return
     */
    @RequiresPermissions("add")
    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<FieldTrlPo> saveForCreate(@Validated @RequestBody FieldTrlPo fieldTrlPo) {
        fieldTrlService.saveFieldTrl(fieldTrlPo);
        return get(fieldTrlPo.getId());
    }

    @RequiresPermissions("update")
    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<FieldTrlPo> saveForUpdate(@Validated @RequestBody FieldTrlPo fieldTrlPo) {

        if (fieldTrlPo.getId() == null) {
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.errror.pk.required"));
        }
        fieldTrlService.saveFieldTrl(fieldTrlPo);
        return get(fieldTrlPo.getId());
    }

    @RequiresPermissions("delete")
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<PaginationResult<FieldTrlPo>> deletes(@RequestBody DeleteParams<Integer> deleteParams, HttpSession session) {
        if (deleteParams.getIds() == null || deleteParams.getIds().length == 0) {
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.error.delete.failure", getMessage("fieldTrl.title")));
        }
        Integer fieldId = fieldTrlService.getFieldTrl(deleteParams.getIds()[0]).getFieldId();
        fieldTrlService.deletes(deleteParams.getIds());
        return fieldTrlList(session, fieldId);
    }


}
