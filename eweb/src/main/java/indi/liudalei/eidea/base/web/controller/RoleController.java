package indi.liudalei.eidea.base.web.controller;

import indi.liudalei.eidea.base.entity.bo.RoleBo;
import indi.liudalei.eidea.base.service.RoleService;
import indi.liudalei.eidea.base.web.vo.UserResource;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.DeleteParams;
import indi.liudalei.eidea.core.params.QueryParams;
import indi.liudalei.eidea.core.web.def.WebConst;
import indi.liudalei.eidea.core.web.result.JsonResult;
import indi.liudalei.eidea.core.web.result.def.ErrorCodes;
import indi.liudalei.eidea.core.web.util.SearchHelper;
import indi.liudalei.eidea.core.web.vo.PagingSettingResult;
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
        modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDbPaging());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<PaginationResult<RoleBo>> list(HttpSession session, @RequestBody QueryParams queryParams) {
        Search search = SearchHelper.getSearchParam(URI, session);
        PaginationResult<RoleBo> roleBoList = roleService.getRoleList(search,queryParams);
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
        if (roleService.findExistRole(roleBo.getNo())) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("role.number.equal"));
        }
        roleService.save(roleBo);
        return get(roleBo.getId(), session);
    }

    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "update")
    public JsonResult<RoleBo> saveForUpdated(@RequestBody RoleBo roleBo, HttpSession session) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        RoleBo roleBoOld=roleService.getRoleBo(roleBo.getId());
        if (roleBoOld.getNo().equals(roleBo.getNo())){
            roleService.save(roleBo);
            return get(roleBo.getId(), session);
        }else if (roleService.findExistRole(roleBo.getNo())){
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("role.number.equal"));
        }else{
            roleService.save(roleBo);
            return get(roleBo.getId(), session);
        }
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
    public JsonResult<PaginationResult<RoleBo>> deletes(@RequestBody DeleteParams<Integer> deleteParams, HttpSession session) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (deleteParams.getIds() == null || deleteParams.getIds().length == 0) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("pagemenu.choose.information"));
        }
        for (Integer id : deleteParams.getIds()) {
            boolean isExist = roleService.getHasUsers(id);
            if (isExist) {
                return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("role.error.has_users"));
            }
        }
        roleService.deletes(deleteParams.getIds());
        return list(session,deleteParams.getQueryParams());
    }
}
