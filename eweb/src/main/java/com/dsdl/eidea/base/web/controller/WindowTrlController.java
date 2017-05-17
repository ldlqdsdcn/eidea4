/**
 * 版权所有 刘大磊 2013-07-01
 * 作者：刘大磊
 * 电话：13336390671
 * email:ldlqdsd@126.com
 */
package com.dsdl.eidea.base.web.controller;

import com.dsdl.eidea.base.entity.po.WindowTrlPo;
import com.dsdl.eidea.base.service.WindowTrlService;
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
 * Created by 刘大磊 on 2017-05-02 15:42:28.
 */
@Controller
@RequestMapping("/base/windowTrl")
public class WindowTrlController extends BaseController {
    private static final String URI = "windowTrl";
    @Autowired
    private WindowTrlService windowTrlService;

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @RequiresPermissions("view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/base/windowTrl/windowTrl");
        modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDbPaging());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("view")
    public JsonResult<PaginationResult<WindowTrlPo>> list(HttpSession session, @RequestBody QueryParams queryParams) {
        Search search = SearchHelper.getSearchParam(URI, session);
        PaginationResult<WindowTrlPo> paginationResult = windowTrlService.getWindowTrlListByPaging(search, queryParams);
        return JsonResult.success(paginationResult);
    }
    @RequestMapping(value = "/windowTrlList", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("view")
    public JsonResult<PaginationResult<WindowTrlPo>> list(HttpSession session, @RequestBody Integer windowId) {
        Search search = SearchHelper.getSearchParam(URI, session);
        PaginationResult<WindowTrlPo> paginationResult = windowTrlService.getWindowTrlListByWindowId(search, windowId);
        return JsonResult.success(paginationResult);
    }

    @RequiresPermissions("view")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<WindowTrlPo> get(Integer id) {
        WindowTrlPo windowTrlPo = null;
        if (id == null) {
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.errror.get_object", getLabel("windowTrl.title")));
        } else {
            windowTrlPo = windowTrlService.getWindowTrl(id);
        }
        return JsonResult.success(windowTrlPo);
    }

    @RequiresPermissions("add")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<WindowTrlPo> create() {
        WindowTrlPo windowTrlPo = new WindowTrlPo();
        return JsonResult.success(windowTrlPo);
    }

    /**
     * @param windowTrlPo
     * @return
     */
    @RequiresPermissions("add")
    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<WindowTrlPo> saveForCreate(@Validated @RequestBody WindowTrlPo windowTrlPo) {
        windowTrlService.saveWindowTrl(windowTrlPo);
        return get(windowTrlPo.getId());
    }

    @RequiresPermissions("update")
    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<WindowTrlPo> saveForUpdate(@Validated @RequestBody WindowTrlPo windowTrlPo) {

        if (windowTrlPo.getId() == null) {
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.errror.pk.required"));
        }
        windowTrlService.saveWindowTrl(windowTrlPo);
        return get(windowTrlPo.getId());
    }

    @RequiresPermissions("delete")
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody

    public JsonResult<PaginationResult<WindowTrlPo>> deletes(@RequestBody DeleteParams<Integer> deleteParams, HttpSession session) {
        if (deleteParams.getIds() == null || deleteParams.getIds().length == 0) {
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.error.delete.failure", getMessage("windowTrl.title")));
        }
        windowTrlService.deletes(deleteParams.getIds());
        return list(session, deleteParams.getQueryParams());
    }


}
