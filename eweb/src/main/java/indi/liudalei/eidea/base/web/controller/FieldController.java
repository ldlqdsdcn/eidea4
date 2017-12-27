/**
 * 版权所有 刘大磊 2013-07-01
 * 作者：刘大磊
 * 电话：13336390671
 * email:ldlqdsd@126.com
 */
package indi.liudalei.eidea.base.web.controller;

import indi.liudalei.eidea.base.entity.po.FieldPo;
import indi.liudalei.eidea.base.service.FieldService;
import indi.liudalei.eidea.core.def.FieldInputType;
import indi.liudalei.eidea.core.def.FieldShowType;
import indi.liudalei.eidea.core.web.controller.BaseController;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import indi.liudalei.eidea.core.dto.PaginationResult;
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
import indi.liudalei.eidea.core.params.QueryParams;
import indi.liudalei.eidea.core.params.DeleteParams;

import javax.servlet.http.HttpSession;

/**
 * Created by 刘大磊 on 2017-05-04 13:22:23.
 */
@Controller
@RequestMapping("/base/field")
public class FieldController extends BaseController {
    private static final String URI = "field";
    @Autowired
    private FieldService fieldService;

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @RequiresPermissions("view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/base/field/field");
        modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDbPaging());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("view")
    public JsonResult<PaginationResult<FieldPo>> list(HttpSession session, @RequestBody QueryParams queryParams) {
        Search search = SearchHelper.getSearchParam(URI, session);
        PaginationResult<FieldPo> paginationResult = fieldService.getFieldListByPaging(search, queryParams);
        return JsonResult.success(paginationResult);
    }

    @RequestMapping(value = "/fieldList", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("view")
    public JsonResult<PaginationResult<FieldPo>> fieldList(HttpSession session, @RequestBody Integer tabId) {
        Search search = SearchHelper.getSearchParam(URI, session);
        PaginationResult<FieldPo> paginationResult = fieldService.getFieldListByTabId(search, tabId);
        return JsonResult.success(paginationResult);
    }

    @RequiresPermissions("view")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<FieldPo> get(Integer id) {
        FieldPo fieldPo = null;
        if (id == null) {
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.errror.get_object", getLabel("field.title")));
        } else {
            fieldPo = fieldService.getField(id);
        }
        return JsonResult.success(fieldPo);
    }

    @RequiresPermissions("add")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<FieldPo> create() {
        FieldPo fieldPo = new FieldPo();
        return JsonResult.success(fieldPo);
    }

    /**
     * @param fieldPo
     * @return
     */
    @RequiresPermissions("add")
    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<FieldPo> saveForCreate(@Validated @RequestBody FieldPo fieldPo) {
        if (fieldService.findExistFieldByName(fieldPo.getName())){
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(),getMessage("field.error.name.exist"));
        }

        fieldService.saveField(fieldPo);
        return get(fieldPo.getId());
    }

    @RequiresPermissions("update")
    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<FieldPo> saveForUpdate(@Validated @RequestBody FieldPo fieldPo) {

        if (fieldPo.getId() == null) {
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.errror.pk.required"));
        }
        fieldService.saveField(fieldPo);
        return get(fieldPo.getId());
    }
    @RequiresPermissions("delete")
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody

    public JsonResult<PaginationResult<FieldPo>> deletes(@RequestBody DeleteParams<Integer> deleteParams, HttpSession session) {
        if (deleteParams.getIds() == null || deleteParams.getIds().length == 0) {
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.error.delete.failure", getMessage("field.title")));
        }
        Integer columnId=fieldService.getField(deleteParams.getIds()[0]).getColumnId();
        fieldService.deletes(deleteParams.getIds());
        return fieldList(session, columnId);
    }
    @RequiresPermissions(value = "view")
    @RequestMapping(value = "/selectInputType",method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<String> getInputType(){
        JsonObject listObject =  new JsonObject();
        JsonArray jsonArray = new JsonArray();
        for (FieldInputType fieldInputType:FieldInputType.values()){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("value",fieldInputType.name());
            jsonObject.addProperty("desc",fieldInputType.getDesc());
            jsonArray.add(jsonObject);
        }
        listObject.add("fieldInputType",jsonArray);
        return JsonResult.success(listObject.toString());
    }
    @RequiresPermissions(value = "view")
    @ResponseBody
    @RequestMapping(value = "/selectShowType",method = RequestMethod.GET)
    public JsonResult<String> getShowType(){
        JsonObject listObject = new JsonObject();
        JsonArray jsonArray= new JsonArray();
        for (FieldShowType fieldShowType:FieldShowType.values()){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("value",fieldShowType.name());
            jsonObject.addProperty("desc",fieldShowType.getDesc());
            jsonArray.add(jsonObject);
        }
        listObject.add("fieldShowType",jsonArray);
        return JsonResult.success(listObject.toString());
    }

}
