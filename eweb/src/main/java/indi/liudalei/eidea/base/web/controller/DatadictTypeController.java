/**
 * 版权所有 刘大磊 2013-07-01
 * 作者：刘大磊
 * 电话：13336390671
 * email:ldlqdsd@126.com
 */
package indi.liudalei.eidea.base.web.controller;

import indi.liudalei.eidea.base.entity.po.DatadictTypePo;
import indi.liudalei.eidea.base.service.DatadictTypeService;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.DeleteParams;
import indi.liudalei.eidea.core.params.QueryParams;
import indi.liudalei.eidea.core.web.controller.BaseController;
import indi.liudalei.eidea.core.web.def.WebConst;
import indi.liudalei.eidea.core.web.result.JsonResult;
import indi.liudalei.eidea.core.web.result.def.ErrorCodes;
import indi.liudalei.eidea.core.web.util.SearchHelper;
import indi.liudalei.eidea.core.web.vo.PagingSettingResult;
import com.googlecode.genericdao.search.Search;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by 刘大磊 on 2017-04-26 15:34:17.
 */
@Controller
@RequestMapping("/base/datadictType")
public class DatadictTypeController extends BaseController {
    private static final String URI = "base_datadictType";
    @Autowired
    private DatadictTypeService datadictTypeService;

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @RequiresPermissions("view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/base/datadictType/datadictType");
        modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDbPaging());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("view")
    public JsonResult<PaginationResult<DatadictTypePo>> list(HttpSession session, @RequestBody QueryParams queryParams) {
        Search search = SearchHelper.getSearchParam(URI, session);
        PaginationResult<DatadictTypePo> paginationResult = datadictTypeService.getDatadictTypeListByPaging(search, queryParams);
        return JsonResult.success(paginationResult);
    }

    @RequiresPermissions("view")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<DatadictTypePo> get(Integer id) {
        DatadictTypePo datadictTypePo = null;
        if (id == null) {
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.errror.get_object", getLabel("datadictType.title")));
        } else {
            datadictTypePo = datadictTypeService.getDatadictType(id);
        }
        return JsonResult.success(datadictTypePo);
    }

    @RequiresPermissions("add")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<DatadictTypePo> create() {
        DatadictTypePo datadictTypePo = new DatadictTypePo();
        return JsonResult.success(datadictTypePo);
    }

    /**
     * @param datadictTypePo
     * @return
     */
    @RequiresPermissions("add")
    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<DatadictTypePo> saveForCreate(@Validated @RequestBody DatadictTypePo datadictTypePo) {
        if (datadictTypeService.findExistDatadictTypeValue(datadictTypePo.getValue())) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), getMessage("datadictType.value.already.exist"));
        }
        datadictTypeService.saveDatadictType(datadictTypePo);
        return get(datadictTypePo.getId());
    }

    @RequiresPermissions("update")
    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<DatadictTypePo> saveForUpdate(@Validated @RequestBody DatadictTypePo datadictTypePo) {
        if (datadictTypePo.getId() == null) {
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.errror.pk.required"));
        }
        if (datadictTypeService.findExistDatadictTypeValue(datadictTypePo.getValue())) {
            if (datadictTypeService.findExistDatadictTypeByValue(datadictTypePo.getValue()).getId() == datadictTypePo.getId()) {
                datadictTypeService.saveDatadictType(datadictTypePo);
            } else {
                return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), getMessage("datadictType.value.already.exist"));
            }
        } else {
            datadictTypeService.saveDatadictType(datadictTypePo);
        }
        return get(datadictTypePo.getId());
    }

    @RequiresPermissions("delete")
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody

    public JsonResult<PaginationResult<DatadictTypePo>> deletes(@RequestBody DeleteParams<Integer> deleteParams, HttpSession session) {
        if (deleteParams.getIds() == null || deleteParams.getIds().length == 0) {
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.error.delete.failure", getMessage("datadictType.title")));
        }
        datadictTypeService.deletes(deleteParams.getIds());
        return list(session, deleteParams.getQueryParams());
    }
}
