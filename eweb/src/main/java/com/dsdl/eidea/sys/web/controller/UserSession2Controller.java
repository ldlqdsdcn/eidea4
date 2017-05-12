/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.sys.web.controller;

import com.dsdl.eidea.sys.entity.po.UserSession2Po;
import com.dsdl.eidea.sys.service.UserSession2Service;
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
import javax.servlet.http.HttpSession;
import java.util.List;

/**
* Created by 刘大磊 on 2017-05-08 09:55:07.
*/ @Controller
@RequestMapping("/sys/userSession2")
public class UserSession2Controller extends BaseController {
private static final String URI = "userSession2";
@Autowired
private UserSession2Service userSession2Service;
@RequestMapping(value = "/showList", method = RequestMethod.GET)
@RequiresPermissions("view")
public ModelAndView showList() {
ModelAndView modelAndView = new ModelAndView("/sys/userSession2/userSession2");
modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDefault());
modelAndView.addObject(WebConst.PAGE_URI, URI);
return modelAndView;
}
@RequestMapping(value = "/list", method = RequestMethod.GET)
@ResponseBody
@RequiresPermissions("view")
public JsonResult<List<UserSession2Po>> list(HttpSession session) {
    Search search = SearchHelper.getSearchParam(URI, session);
    List<UserSession2Po> userSession2List = userSession2Service.getUserSession2List(search);
        return JsonResult.success(userSession2List);
    }
    @RequiresPermissions("view")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<UserSession2Po> get(Integer id) {
        UserSession2Po userSession2Po = null;
        if (id == null) {
        return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(),getMessage("common.errror.get_object",getLabel("userSession2.title")));
        } else {
        userSession2Po = userSession2Service.getUserSession2(id);
        }
        return JsonResult.success(userSession2Po);
        }

        @RequiresPermissions("add")
        @RequestMapping(value = "/create", method = RequestMethod.GET)
        @ResponseBody
        public JsonResult<UserSession2Po> create() {
            UserSession2Po userSession2Po = new UserSession2Po();
            return JsonResult.success(userSession2Po);
            }

    /**
    * @param userSession2Po
    * @return
    */
    @RequiresPermissions("add")
    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<UserSession2Po> saveForCreate(@Validated @RequestBody UserSession2Po userSession2Po) {
        userSession2Service.saveUserSession2(userSession2Po);
        return get(userSession2Po.getId());
        }

        @RequiresPermissions("update")
        @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
        @ResponseBody
        public JsonResult<UserSession2Po> saveForUpdate(@Validated @RequestBody UserSession2Po userSession2Po) {

            if(userSession2Po.getId() == null){
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.errror.pk.required"));
            }
            userSession2Service.saveUserSession2(userSession2Po);
            return get(userSession2Po.getId());
            }

    @RequiresPermissions("delete")
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<List<UserSession2Po>> deletes(@RequestBody Integer[] ids, HttpSession session) {
        if (ids == null || ids.length == 0) {
        return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.error.delete.failure",getMessage("userSession2.title")));
        }
        userSession2Service.deletes(ids);
        return list(session);
        }

}
