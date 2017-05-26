/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.test.web.controller;

import com.dsdl.eidea.base.entity.bo.UserBo;
import com.dsdl.eidea.base.entity.bo.UserSessionBo;
import com.dsdl.eidea.test.entity.po.LeavePo;
import com.dsdl.eidea.test.service.LeaveService;
import com.dsdl.eidea.core.web.controller.BaseController;
import com.dsdl.eidea.test.web.vo.LeaveVo;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
* Created by 刘大磊 on 2017-05-12 13:36:48.
*/ @Controller
@RequestMapping("/test/leave")
public class LeaveController extends BaseController {
private static final String URI = "leave";
@Autowired
private LeaveService leaveService;
@RequestMapping(value = "/showList", method = RequestMethod.GET)
@RequiresPermissions("view")
public ModelAndView showList() {
ModelAndView modelAndView = new ModelAndView("/test/leave/leave");
modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDefault());
modelAndView.addObject(WebConst.PAGE_URI, URI);
return modelAndView;
}
@RequestMapping(value = "/list", method = RequestMethod.GET)
@ResponseBody
@RequiresPermissions("view")
public JsonResult<List<LeavePo>> list(HttpSession session) {
    Search search = SearchHelper.getSearchParam(URI, session);
    List<LeavePo> leaveList = leaveService.getLeaveList(search);
        return JsonResult.success(leaveList);
    }
    @RequiresPermissions("view")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<LeavePo> get(Integer id) {
        LeavePo leavePo = null;
        if (id == null) {
        return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(),getMessage("common.errror.get_object",getLabel("leave.title")));
        } else {
        leavePo = leaveService.getLeave(id);
        }
        return JsonResult.success(leavePo);
        }

        @RequiresPermissions("add")
        @RequestMapping(value = "/create", method = RequestMethod.GET)
        @ResponseBody
        public JsonResult<LeavePo> create() {
            UserBo userBo=(UserBo) request.getSession().getAttribute(WebConst.SESSION_LOGINUSER);
            LeavePo leavePo = new LeavePo();
            leavePo.setLeaveUserId(userBo.getId());
            return JsonResult.success(leavePo);
            }

    /**
    * @param leavePo
    * @return
    */
    @RequiresPermissions("add")
    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<LeavePo> saveForCreate(@Validated @RequestBody LeavePo leavePo) {
        UserBo userBo=(UserBo) request.getSession().getAttribute(WebConst.SESSION_LOGINUSER);
        leavePo.setLeaveUserId(userBo.getId());
        leaveService.saveLeave(leavePo);
        return get(leavePo.getId());
        }

        @RequiresPermissions("update")
        @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
        @ResponseBody
        public JsonResult<LeavePo> saveForUpdate(@Validated @RequestBody LeavePo leavePo) {

            if(leavePo.getId() == null){
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.errror.pk.required"));
            }
            leaveService.saveLeave(leavePo);
            return get(leavePo.getId());
            }

    @RequiresPermissions("delete")
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<List<LeavePo>> deletes(@RequestBody Integer[] ids, HttpSession session) {
        if (ids == null || ids.length == 0) {
        return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.error.delete.failure",getMessage("leave.title")));
        }
        leaveService.deletes(ids);
        return list(session);
        }
        @RequiresPermissions("update")
        @RequestMapping(value = "/saveForApprove", method = RequestMethod.POST)
        @ResponseBody
        public JsonResult<LeavePo> saveForApprove(@Validated @RequestBody LeavePo leavePo) {

            if(leavePo.getId() == null){
                return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.errror.pk.required"));
            }
            leaveService.saveStartLeave(leavePo.getId());
            return get(leavePo.getId());
        }


}
