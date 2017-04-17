package com.dsdl.eidea.base.web.controller;

import com.dsdl.eidea.base.entity.bo.RoleBo;
import com.dsdl.eidea.base.service.RoleService;
import com.dsdl.eidea.base.web.vo.UserResource;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.JsonResult;
import com.dsdl.eidea.core.web.result.def.ErrorCodes;
import com.dsdl.eidea.core.web.util.SearchHelper;
import com.dsdl.eidea.core.web.vo.PagingSettingResult;
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

@Controller
@RequestMapping("/base/role")
public class RoleController {
    private static final String URI = "sys_role";
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @RequiresPermissions(value = "view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/base/role/role");
        modelAndView.addObject("pagingSettingResult", PagingSettingResult.getDefault());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<List<RoleBo>> list(HttpSession session) {
        Search search = SearchHelper.getSearchParam(URI, session);
        List<RoleBo> roleBoList = roleService.getRoleList(search);
        return JsonResult.success(roleBoList);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "add")
    public JsonResult<RoleBo> create() {
        RoleBo roleBo = roleService.getInitRoleBo(null);
        return JsonResult.success(roleBo);
    }

    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "add")
    public JsonResult<RoleBo> saveForCreated(@RequestBody RoleBo roleBo, HttpSession session) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (roleService.findExistClient(roleBo.getName())) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("client.msg.client_code"));
        }
        roleService.save(roleBo);
        return get(roleBo.getId(), session);
    }

    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "update")
    public JsonResult<RoleBo> saveForUpdated(@RequestBody RoleBo roleBo, HttpSession session) {
        roleService.save(roleBo);
        return get(roleBo.getId(), session);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<RoleBo> get(Integer id, HttpSession session) {
        RoleBo roleBo = null;
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (id == null) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("client.msg.primary_key_validation"));
        } else {
            roleBo = roleService.getRoleBo(id);
        }
        return JsonResult.success(roleBo);
    }

    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "delete")
    public JsonResult<List<RoleBo>> deletes(@RequestBody Integer[] ids, HttpSession session) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (ids == null || ids.length == 0) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("pagemenu.choose.information"));
        }
        roleService.deletes(ids);
        return list(session);
    }
}
