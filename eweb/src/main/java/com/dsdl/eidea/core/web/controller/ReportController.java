package com.dsdl.eidea.core.web.controller;

import com.dsdl.eidea.base.web.vo.UserResource;
import com.dsdl.eidea.core.entity.bo.ReportBo;
import com.dsdl.eidea.core.service.ReportService;
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
@RequestMapping("/core/report")
public class ReportController {
    private static final String URl = "core_report";

    @Autowired
    private ReportService reportService;

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @RequiresPermissions(value = "view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/core/report/report");
        modelAndView.addObject("pagingSettingResult", PagingSettingResult.getDefault());
        modelAndView.addObject(WebConst.PAGE_URI,URl);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<List<ReportBo>> list(HttpSession session) {
        Search search = SearchHelper.getSearchParam(URl, session);
        List<ReportBo> reportBoList = reportService.findReport(search);
        return JsonResult.success(reportBoList);
    }

    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "delete")
    public JsonResult<List<ReportBo>> deletes(@RequestBody String[] keys, HttpSession session) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (keys == null || keys.length == 0) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("pagemenu.choose.information"));
        }
        reportService.deletes(keys);
        return list(session);
    }
    @RequestMapping(value = "/get",method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<ReportBo> get(String key,HttpSession session){
        UserResource userResource = (UserResource)session.getAttribute(WebConst.SESSION_RESOURCE);
        ReportBo reportBo = null;
        if (StringUtil.isEmpty(key)){
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(),userResource.getMessage("client.msg.primary_key_validation"));
        }else{
            reportBo = reportService.getReportBo(key);
        }
        return JsonResult.success(reportBo);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "add")
    public JsonResult<ReportBo> create() {
        ReportBo reportBo = new ReportBo();
        reportBo.setCreated(true);
        return JsonResult.success(reportBo);
    }

    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "add")
    public JsonResult<ReportBo> saveForCreated(@RequestBody ReportBo reportBo, HttpSession session) {
        UserResource userResource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (reportBo.isCreated()) {
            if (reportService.findExistReport(reportBo.getKey())) {
                return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), userResource.getMessage("client.msg.client_code"));
            }
        }
        reportService.save(reportBo);
        return get(reportBo.getKey(), session);
    }

    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "update")
    public JsonResult<ReportBo> saveForUpadted(@RequestBody ReportBo reportBo, HttpSession session) {
        reportService.save(reportBo);
        return get(reportBo.getKey(), session);
    }


}