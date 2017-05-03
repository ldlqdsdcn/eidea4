package com.dsdl.eidea.core.web.controller;

import com.dsdl.eidea.base.web.vo.UserResource;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.entity.po.ReportSettingsPo;
import com.dsdl.eidea.core.params.DeleteParams;
import com.dsdl.eidea.core.params.QueryParams;
import com.dsdl.eidea.core.service.ReportSettingsService;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.JsonResult;
import com.dsdl.eidea.core.web.result.def.ErrorCodes;
import com.dsdl.eidea.core.web.util.SearchHelper;
import com.dsdl.eidea.core.web.vo.PagingSettingResult;
import com.dsdl.eidea.util.StringUtil;
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
import java.util.List;

/**
 * Created by 车东明 on 2017/4/17.
 */
@Controller
@RequestMapping("/core/reportSettings")
public class ReportSettingsController {
    private static final String URl = "core_report";

    @Autowired
    private ReportSettingsService reportSettingsService;

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @RequiresPermissions(value = "view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/core/reportSettings/reportSettings");
        modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDefault());
        modelAndView.addObject(WebConst.PAGE_URI, URl);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<PaginationResult<ReportSettingsPo>> list(HttpSession session, @RequestBody QueryParams queryParams) {
        Search search = SearchHelper.getSearchParam(URl, session);
        PaginationResult<ReportSettingsPo> reportSettingsPoList = reportSettingsService.getReportSettingsList(search,queryParams);
        return JsonResult.success(reportSettingsPoList);
    }

    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "delete")
    public JsonResult<PaginationResult<ReportSettingsPo>> deletes(@RequestBody DeleteParams<String> deleteParams, HttpSession session) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (deleteParams.getIds() == null || deleteParams.getIds().length == 0) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("common.error.delete.key_empty.failure"));
        }
        for(String key:deleteParams.getIds())
        {
           ReportSettingsPo reportSettingsPo= reportSettingsService.getReportSettingsPo(key);
            if("Y".equals(reportSettingsPo.getInit()))
            {
                return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("不允许删除init为Y的报表属性记录"));
            }
        }
        reportSettingsService.deletes(deleteParams.getIds());
        return list(session,deleteParams.getQueryParams());
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<ReportSettingsPo> get(String key, HttpSession session) {
        UserResource userResource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        ReportSettingsPo reportSettingsPo = null;
        if (StringUtil.isEmpty(key)) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), userResource.getMessage("client.msg.primary_key_validation"));
        } else {
            reportSettingsPo = reportSettingsService.getReportSettingsPo(key);
        }
        return JsonResult.success(reportSettingsPo);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "add")
    public JsonResult<ReportSettingsPo> create() {
        ReportSettingsPo reportBo = new ReportSettingsPo();
        reportBo.setInit("N");
        reportBo.setCreated(true);
        return JsonResult.success(reportBo);
    }

    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "add")
    public JsonResult<ReportSettingsPo> saveForCreated(@Validated @RequestBody ReportSettingsPo reportSettingsPo, HttpSession session) {
        UserResource userResource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (reportSettingsPo.isCreated()) {
            if (reportSettingsService.findExistReport(reportSettingsPo.getKey())) {
                return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), userResource.getMessage("reportSettings.msg.key_exists"));
            }
        }
        if("Y".equals(reportSettingsPo.getInit()))
        {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(),userResource.getMessage("reportSettings.error.init.value"));
        }
        reportSettingsService.save(reportSettingsPo);
        return get(reportSettingsPo.getKey(), session);
    }

    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "update")
    public JsonResult<ReportSettingsPo> saveForUpadted(@Validated@RequestBody ReportSettingsPo reportSettingsPo, HttpSession session) {
        reportSettingsService.save(reportSettingsPo);
        return get(reportSettingsPo.getKey(), session);
    }


}