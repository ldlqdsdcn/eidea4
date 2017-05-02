package com.dsdl.eidea.base.web.controller;

import com.dsdl.eidea.base.entity.bo.ClientBo;
import com.dsdl.eidea.base.entity.bo.OrgBo;
import com.dsdl.eidea.base.service.ClientService;
import com.dsdl.eidea.base.service.OrgService;
import com.dsdl.eidea.base.web.vo.UserResource;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.DeleteParams;
import com.dsdl.eidea.core.params.QueryParams;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.JsonResult;
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

import javax.persistence.criteria.CriteriaBuilder;
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
    @RequiresPermissions(value = "view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/base/org/org");
        modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDefault());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<PaginationResult<OrgBo>> list(HttpSession session, @RequestBody QueryParams queryParams) {
        Search search = SearchHelper.getSearchParam(URI, session);
        PaginationResult<OrgBo> orgBoList = orgService.findOrgList(search,queryParams);
        return JsonResult.success(orgBoList);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<OrgBo> get(Integer id) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        OrgBo orgBo = null;
        if (id == null) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("client.msg.primary_key_is_empty"));
        } else {
            orgBo = orgService.getOrgBo(id);
        }
        return JsonResult.success(orgBo);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "add")
    public JsonResult<OrgBo> create() {
        OrgBo orgBo = new OrgBo();
        orgBo.setIsactive("N");
        return JsonResult.success(orgBo);
    }

    /**
     * @param orgBo
     * @return
     */
    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "add")
    public JsonResult<OrgBo> saveForAdded(@Validated @RequestBody OrgBo orgBo) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (orgService.findExistOrg(orgBo.getNo())) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("org.msg.no_exists"));
        }
        orgService.save(orgBo);
        return get(orgBo.getId());
    }

    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "update")
    public JsonResult<OrgBo> saveForUpdated(@Validated @RequestBody OrgBo orgBo) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (orgBo.getId() == null) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("common.primary_key.isempty"));
        }
        orgService.save(orgBo);
        return get(orgBo.getId());
    }

    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "delete")
    public JsonResult<PaginationResult<OrgBo>> deletes(@RequestBody DeleteParams<Integer> deleteParams, HttpSession session) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (deleteParams.getIds() == null || deleteParams.getIds().length == 0) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("client.msg.select_delete"));
        }
        orgService.deletes(deleteParams.getIds());
        return list(session,deleteParams.getQueryParams());
    }

    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<List<ClientBo>> clientList() {
        List<ClientBo> clientBoList = clientService.getClientListForActivated();


        return JsonResult.success(clientBoList);
    }
}
