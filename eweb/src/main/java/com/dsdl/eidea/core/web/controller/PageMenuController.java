package com.dsdl.eidea.core.web.controller;

import com.dsdl.eidea.base.entity.bo.PageMenuBo;
import com.dsdl.eidea.base.entity.bo.PageMenuTrlBo;
import com.dsdl.eidea.base.service.PageMenuService;
import com.dsdl.eidea.base.web.vo.UserResource;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.entity.bo.LanguageBo;
import com.dsdl.eidea.core.params.DeleteParams;
import com.dsdl.eidea.core.params.QueryParams;
import com.dsdl.eidea.core.service.LanguageService;
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
import java.util.ArrayList;
import java.util.List;

//import com.dsdl.eidea.base.web.annotation.PrivilegesControl;

/**
 * Created by admin on 2016/12/13.
 */
@Controller
@RequestMapping("/base/pagemenu")
public class PageMenuController {
    private static final String URI = "sys_pagemenu";
    @Autowired
    private PageMenuService pageMenuService;
    @Autowired
    private LanguageService languageService;

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @RequiresPermissions(value = "view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/base/pagemenu/pagemenu");
        modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDbPaging());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<PaginationResult<PageMenuBo>> list(HttpSession session, @RequestBody QueryParams queryParams) {
        Search search = SearchHelper.getSearchParam(URI, session);
        PaginationResult<PageMenuBo> pageMenuBoList = pageMenuService.findPageMenu(search,queryParams);
        return JsonResult.success(pageMenuBoList);
    }

    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "add")
    public JsonResult<PageMenuBo> saveForCreated(@RequestBody PageMenuBo pageMenuBo, HttpSession session) {
        if (pageMenuBo.isCreated()) {
            if (pageMenuService.findExistUrl(pageMenuBo.getId())) {
                UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
                return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("pagemenu.connection.point"));
            }
        }
        pageMenuService.save(pageMenuBo);
        return get(pageMenuBo.getId(), session);
    }

    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "update")
    public JsonResult<PageMenuBo> saveForUpdated(@RequestBody PageMenuBo pageMenuBo, HttpSession session) {
        if (pageMenuBo.getId() == null) {
            UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("pagemenu.primarykey.check.isnull"));
        } else {
            pageMenuService.save(pageMenuBo);
            return get(pageMenuBo.getId(), null);
        }
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<PageMenuBo> get(Integer id, HttpSession session) {
        PageMenuBo pageMenuBo = null;
        if (id == null) {
            UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("pagemenu.primarykey.information"));
        } else {
            pageMenuBo = pageMenuService.getPageMenuBo(id);
            pageMenuBo.setIsCheck("true");
        }
        return JsonResult.success(pageMenuBo);


    }

    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "delete")
    public JsonResult<PaginationResult<PageMenuBo>> deletes(@RequestBody DeleteParams<Integer> deleteParams, HttpSession session) {
        if (deleteParams.getIds() == null||deleteParams.getIds().length==0) {
            UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("pagemenu.choose.information"));
        }
        /**
         * 查询子集引用
         */
        for(Integer id:deleteParams.getIds()){
                Search search=new Search();
                 search.addFilterEqual("parentMenuId",id);
            PaginationResult<PageMenuBo> pageMenuBos=pageMenuService.findPageMenu(search,deleteParams.getQueryParams());
            if (pageMenuBos.getData().size()>0){
                UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
                return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("pagemenu.exit.information"));
            }
        }
        pageMenuService.deleteMenuById(deleteParams.getIds());
        return list(session,deleteParams.getQueryParams());
    }

    /**
     * getModuleMenuList:模块设置展示菜单列表
     *
     * @return
     * @author:Bobo
     */
    @RequestMapping(value = "/getModuleMenuList", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<List<PageMenuBo>> getModuleMenuList() {
        List<PageMenuBo> pageMenuBoList = pageMenuService.getModuleMenuList();
        return JsonResult.success(pageMenuBoList);
    }

    /**
     * getListMenuType:获取父菜单列表
     *
     * @return
     * @author:Bobo
     */
    @RequestMapping(value = "/getListMenuType", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<List<PageMenuBo>> getListMenuType() {
        List<PageMenuBo> pageMenuBoList = pageMenuService.getListMenuType();
        PageMenuBo pageMenuBo=new PageMenuBo();
        pageMenuBo.setId(null);
        pageMenuBo.setName("  --  ");
        pageMenuBoList.add(0,pageMenuBo);
        return JsonResult.success(pageMenuBoList);
    }

    /**
     * create:再次新建
     *
     * @return
     * @author:Bobo
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "add")
    public JsonResult<PageMenuBo> create() {
        PageMenuBo pageMenuBo = new PageMenuBo();
        List<LanguageBo> languageBoList = languageService.findLanguageListForActivated();
        List<PageMenuTrlBo> pagemenuList = new ArrayList<PageMenuTrlBo>();
        for (LanguageBo lan : languageBoList) {
            PageMenuTrlBo pagemenu = new PageMenuTrlBo();
            pagemenu.setLanguageCode(lan.getCode());
            pagemenuList.add(pagemenu);
        }
        pageMenuBo.setPageMenuTrlBo(pagemenuList);
        return JsonResult.success(pageMenuBo);
    }
}
