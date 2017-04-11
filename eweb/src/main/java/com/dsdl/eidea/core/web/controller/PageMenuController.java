package com.dsdl.eidea.core.web.controller;

import com.dsdl.eidea.base.def.OperatorDef;
import com.dsdl.eidea.base.entity.bo.PageMenuBo;
import com.dsdl.eidea.base.entity.bo.PageMenuTrlBo;
import com.dsdl.eidea.base.service.PageMenuService;
import com.dsdl.eidea.base.web.annotation.PrivilegesControl;
import com.dsdl.eidea.base.web.def.ReturnType;
import com.dsdl.eidea.base.web.vo.UserResource;
import com.dsdl.eidea.core.entity.bo.LanguageBo;
import com.dsdl.eidea.core.service.LanguageService;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.ApiResult;
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
    @PrivilegesControl(operator = OperatorDef.VIEW, returnType = ReturnType.JSP)
    @RequiresPermissions(value = "base:view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/base/pagemenu/pagemenu");
        modelAndView.addObject("pagingSettingResult", PagingSettingResult.getDefault());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "base:view")
    public ApiResult<List<PageMenuBo>> list(HttpSession session) {
        Search search = SearchHelper.getSearchParam(URI, session);
        List<PageMenuBo> pageMenuBoList = pageMenuService.findPageMenu(search);
        return ApiResult.success(pageMenuBoList);
    }

    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.ADD)
    @RequiresPermissions(value = "base:add")
    public ApiResult<PageMenuBo> saveForCreated(@RequestBody PageMenuBo pageMenuBo,HttpSession session) {
        if (pageMenuBo.isCreated()) {
            if (pageMenuService.findExistUrl(pageMenuBo.getId())) {
            	UserResource resource=(UserResource)session.getAttribute(WebConst.SESSION_RESOURCE);
                return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("pagemenu.connection.point"));
            }
        }
        pageMenuService.save(pageMenuBo);
        return get(pageMenuBo.getId(), session);
    }

    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.UPDATE)
    @RequiresPermissions(value = "base:update")
    public ApiResult<PageMenuBo> saveForUpdated(@RequestBody PageMenuBo pageMenuBo,HttpSession session) {
    	if(pageMenuBo.getId()==null){
    		UserResource resource=(UserResource)session.getAttribute(WebConst.SESSION_RESOURCE);
    		 return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("pagemenu.primarykey.check.isnull"));
    	}else{
        pageMenuService.save(pageMenuBo);
        return get(pageMenuBo.getId(), null);
    	}
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "base:view")
    public ApiResult<PageMenuBo> get(Integer id,HttpSession session) {
        PageMenuBo pageMenuBo = null;
        if (id == null) {
        	UserResource resource=(UserResource)session.getAttribute(WebConst.SESSION_RESOURCE);
            return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(),resource.getMessage("pagemenu.primarykey.information"));
        } else {
            pageMenuBo = pageMenuService.getPageMenuBo(id);
            
        }
        return ApiResult.success(pageMenuBo);


    }

    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.DELETE)
    @RequiresPermissions(value = "base:delete")
    public ApiResult<List<PageMenuBo>> deletes(@RequestBody Integer[] ids, HttpSession session) {
        if (ids == null) {
        	UserResource resource=(UserResource)session.getAttribute(WebConst.SESSION_RESOURCE);
            return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(),resource.getMessage("pagemenu.choose.information"));
        }
        pageMenuService.deleteMenuById(ids);
        return list(session);
    }

    /**
     * getModuleMenuList:模块设置展示菜单列表
     *
     * @return
     * @author:Bobo
     */
    @RequestMapping(value = "/getModuleMenuList", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "base:view")
    public ApiResult<List<PageMenuBo>> getModuleMenuList() {
        List<PageMenuBo> pageMenuBoList = pageMenuService.getModuleMenuList();
        return ApiResult.success(pageMenuBoList);
    }

    /**
     * getListMenuType:获取父菜单列表
     *
     * @return
     * @author:Bobo
     */
    @RequestMapping(value = "/getListMenuType", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "base:view")
    public ApiResult<List<PageMenuBo>> getListMenuType() {
        List<PageMenuBo> pageMenuBoList = pageMenuService.getListMenuType();
        return ApiResult.success(pageMenuBoList);
    }

    /**
     * create:再次新建
     *
     * @return
     * @author:Bobo
     */
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.ADD)
    @RequiresPermissions(value = "base:add")
    public ApiResult<PageMenuBo> create() {
    	 PageMenuBo pageMenuBo=new PageMenuBo();
         List<LanguageBo> languageBoList = languageService.findLanguageListForActivated();
         List<PageMenuTrlBo> pagemenuList=new ArrayList<PageMenuTrlBo>();
         	for(LanguageBo lan:languageBoList){
         		PageMenuTrlBo pagemenu=new PageMenuTrlBo();
         		pagemenu.setLanguageCode(lan.getCode());
         		pagemenuList.add(pagemenu);
         	}
         	pageMenuBo.setPageMenuTrlBo(pagemenuList);
            return ApiResult.success(pageMenuBo);
    }
}
