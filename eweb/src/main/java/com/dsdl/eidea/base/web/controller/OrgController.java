package com.dsdl.eidea.base.web.controller;

import com.dsdl.eidea.base.def.OperatorDef;
import com.dsdl.eidea.base.entity.bo.ClientBo;
import com.dsdl.eidea.base.entity.bo.OrgBo;
import com.dsdl.eidea.base.service.ClientService;
import com.dsdl.eidea.base.service.OrgService;
import com.dsdl.eidea.base.web.annotation.PrivilegesControl;
import com.dsdl.eidea.base.web.def.ReturnType;
import com.dsdl.eidea.base.web.vo.UserResource;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.ApiResult;
import com.dsdl.eidea.core.web.result.def.ErrorCodes;
import com.dsdl.eidea.core.web.util.SearchHelper;
import com.dsdl.eidea.core.web.vo.PagingSettingResult;
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
 * Created by 刘大磊 on 2016/12/13 14:39.
 */
@Controller
@RequestMapping("/base/org")
public class OrgController {
    private static final String URI = "sys_org";
    @Autowired
    private OrgService orgService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private HttpSession session;

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @PrivilegesControl(operator = OperatorDef.VIEW, returnType = ReturnType.JSP)
    @RequiresPermissions(value = "org:view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/base/org/org");
        modelAndView.addObject("pagingSettingResult", PagingSettingResult.getDefault());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.VIEW)
    @RequiresPermissions(value = "org:view")
    public ApiResult<List<OrgBo>> list(HttpSession session) {
        Search search = SearchHelper.getSearchParam(URI, session);
        List<OrgBo> orgBoList = orgService.findOrgList(search);
        return ApiResult.success(orgBoList);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.VIEW)
    @RequiresPermissions(value = "org:view")
    public ApiResult<OrgBo> get(Integer id) {
        UserResource resource=(UserResource)session.getAttribute(WebConst.SESSION_RESOURCE);
        OrgBo orgBo = null;
        if (id == null) {
            return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("client.msg.primary_key_is_empty"));
        } else {
            orgBo = orgService.getOrgBo(id);
        }
        return ApiResult.success(orgBo);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.ADD)
    @RequiresPermissions(value = "org:add")
    public ApiResult<OrgBo> create() {
        OrgBo orgBo = new OrgBo();
        orgBo.setIsactive("N");
        return ApiResult.success(orgBo);
    }

    /**
     * @param orgBo
     * @return
     */
    @PrivilegesControl(operator = OperatorDef.ADD)
    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "org:add")
    public ApiResult<OrgBo> saveForAdded(@Validated @RequestBody OrgBo orgBo) {
        UserResource resource=(UserResource)session.getAttribute(WebConst.SESSION_RESOURCE);
        if (orgService.findExistOrg(orgBo.getNo())) {
            return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("org.msg.no_exists"));
        }
        orgService.save(orgBo);
        return get(orgBo.getId());
    }
    @PrivilegesControl(operator = OperatorDef.UPDATE)
    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "org:update")
    public ApiResult<OrgBo> saveForUpdated(@Validated @RequestBody OrgBo orgBo) {
        UserResource resource=(UserResource)session.getAttribute(WebConst.SESSION_RESOURCE);
        if(orgBo.getId() == null){
            return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("common.primary_key.isempty"));
        }
        orgService.save(orgBo);
        return get(orgBo.getId());
    }

    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.DELETE)
    @RequiresPermissions(value = "org:delete")
    public ApiResult<List<OrgBo>> deletes(@RequestBody Integer[] ids, HttpSession session) {
        UserResource resource=(UserResource)session.getAttribute(WebConst.SESSION_RESOURCE);
        if (ids == null || ids.length == 0) {
            return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("client.msg.select_delete"));
        }
        orgService.deletes(ids);
        return list(session);
    }

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.VIEW)
    @RequiresPermissions(value = "org:view")
    public ApiResult<List<ClientBo>> clientList() {
        List<ClientBo> clientBoList = clientService.getClientListForActivated();


        return ApiResult.success(clientBoList);
    }
}
