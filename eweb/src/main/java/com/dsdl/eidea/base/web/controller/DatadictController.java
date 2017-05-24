/**
 * 版权所有 刘大磊 2013-07-01
 * 作者：刘大磊
 * 电话：13336390671
 * email:ldlqdsd@126.com
 */
package com.dsdl.eidea.base.web.controller;

import com.dsdl.eidea.base.entity.po.DatadictPo;
import com.dsdl.eidea.base.entity.po.DatadictTypePo;
import com.dsdl.eidea.base.service.DatadictService;
import com.dsdl.eidea.base.service.DatadictTypeService;
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
 * Created by 刘大磊 on 2017-04-26 15:34:59.
 */
@Controller
@RequestMapping("/base/datadict")
public class DatadictController extends BaseController {
    private static final String URI = "base_datadict";
    @Autowired
    private DatadictService datadictService;
    @Autowired
    private DatadictTypeService datadictTypeService;

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @RequiresPermissions("view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/base/datadict/datadict");
        modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDbPaging());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("view")
    public JsonResult<PaginationResult<DatadictPo>> list(HttpSession session, @RequestBody QueryParams queryParams) {
        Search search = SearchHelper.getSearchParam(URI, session);
        PaginationResult<DatadictPo> paginationResult = datadictService.getDatadictListByPaging(search, queryParams);
        return JsonResult.success(paginationResult);
    }
    @RequestMapping(value = "/detaillist", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("view")
    public JsonResult<PaginationResult<DatadictPo>> detailList(HttpSession session,@RequestBody String dataType) {
        Search search = SearchHelper.getSearchParam(URI, session);
        PaginationResult<DatadictPo> paginationResult = datadictService.getDatadictListByDatadictType(search,dataType);
        return JsonResult.success(paginationResult);
    }

    @RequiresPermissions("view")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<DatadictPo> get(Integer id) {
        DatadictPo datadictPo = null;
        if (id == null) {
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.errror.get_object", getLabel("datadict.title")));
        } else {
            datadictPo = datadictService.getDatadict(id);
        }
        return JsonResult.success(datadictPo);
    }

    @RequiresPermissions("add")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<DatadictPo> create() {
        DatadictPo datadictPo = new DatadictPo();
        return JsonResult.success(datadictPo);
    }

    /**
     * @param datadictPo
     * @return
     */
    @RequiresPermissions("add")
    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<DatadictPo> saveForCreate(@Validated @RequestBody DatadictPo datadictPo) {
            if (datadictService.findExistCode(datadictPo.getCode())) {
                List<DatadictPo> datadictPoList = datadictService.findExistDatadictByCode(datadictPo.getCode());
                for (int i = 0; i < datadictPoList.size(); i++) {
                    if (datadictPoList.get(i).getDataType().equals(datadictPo.getDataType())) {
                        if (datadictPoList.get(i).getId() != datadictPo.getId()) {
                            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("base.datadict.code.already.exist"));
                        } else {
                            datadictService.saveDatadict(datadictPo);
                        }
                    } else {
                        datadictService.saveDatadict(datadictPo);
                    }
                }
            } else {
                datadictService.saveDatadict(datadictPo);
            }
        return get(datadictPo.getId());
    }

    @RequiresPermissions("update")
    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<DatadictPo> saveForUpdate(@Validated @RequestBody DatadictPo datadictPo) {
        if (datadictPo.getId() == null) {
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.errror.pk.required"));
        }
        if (datadictService.findExistCode(datadictPo.getCode())) {
            List<DatadictPo> datadictPoList = datadictService.findExistDatadictByCode(datadictPo.getCode());
            if (datadictPoList != null && datadictPoList.size() > 0) {
                for (int i = 0; i < datadictPoList.size(); i++) {
                    if (datadictPoList.get(i).getDataType().equals(datadictPo.getDataType())) {
                        if (datadictPoList.get(i).getId() != datadictPo.getId()) {
                            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("base.datadict.code.already.exist"));
                        } else {
                            datadictService.saveDatadict(datadictPo);
                        }
                    } else {
                        datadictService.saveDatadict(datadictPo);
                    }
                }
            } else {
                datadictService.saveDatadict(datadictPo);
            }
        } else {
            datadictService.saveDatadict(datadictPo);
        }
        return get(datadictPo.getId());
    }

    @RequiresPermissions("delete")
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<PaginationResult<DatadictPo>> deletes(@RequestBody DeleteParams<Integer> deleteParams, HttpSession session) {
        if (deleteParams.getIds() == null || deleteParams.getIds().length == 0) {
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.error.delete.failure", getMessage("datadict.title")));
        }
        String dataType= datadictService.getDatadict(deleteParams.getIds()[0]).getDataType();
        datadictService.deletes(deleteParams.getIds());
        return detailList(session,dataType);
    }

    @RequiresPermissions(value = "view")
    @ResponseBody
    @RequestMapping(value = "/getDatadictTypeList", method = RequestMethod.POST)
    public JsonResult<List<DatadictTypePo>> getDatadictTypeList() {
        List<DatadictTypePo> datadictTypePoList = datadictTypeService.getDatadictTypeList();
        return JsonResult.success(datadictTypePoList);
    }

}
