package com.dsdl.eidea.core.web.controller;

import com.dsdl.eidea.base.entity.bo.DirectoryBo;
import com.dsdl.eidea.base.service.DirectoryService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by admin on 2016/12/19.
 */
@Controller
@RequestMapping("/base/directory")
public class DirectoryController {
    private static final String URI = "sys_directory";
    @Autowired
    private DirectoryService directoryService;

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @RequiresPermissions(value = "view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/base/directory/directory");
        modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDbPaging());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<PaginationResult<DirectoryBo>> list(HttpSession session, @RequestBody QueryParams queryParams) {
        Search search = SearchHelper.getSearchParam(URI, session);
        PaginationResult<DirectoryBo> directoryBoList = directoryService.findDirectory(search,queryParams);
        return JsonResult.success(directoryBoList);
    }

    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "delete")
    public JsonResult<PaginationResult<DirectoryBo>> deletes(@RequestBody DeleteParams<Integer> deleteParams, HttpSession session) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (deleteParams.getIds() == null) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("pagemenu.choose.information"));
        }
        directoryService.deleteDirectoryById(deleteParams.getIds());
        return list(session,deleteParams.getQueryParams());
    }

    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "add")
    public JsonResult<DirectoryBo> saveForCreated(@RequestBody DirectoryBo directoryBo, HttpSession session) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (directoryBo.isCreated()&&directoryBo.getId()!=null) {
            if (directoryService.findExistId(directoryBo.getId())) ;
            {
                return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("pagemenu.connection.point"));
            }
        }
        if (directoryBo.getIsactive() == null) {
            directoryBo.setIsactive("N");
        }
        directoryService.save(directoryBo);
        return get(directoryBo.getId(), session);
    }

    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "update")
    public JsonResult<DirectoryBo> saveForUpdated(@RequestBody DirectoryBo directoryBo, HttpSession session) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (directoryBo.getId() == null) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("common.primary_key.isempty"));
        }
        if (directoryBo.getIsactive() == null) {
            directoryBo.setIsactive("N");
        }
        directoryService.save(directoryBo);
        return get(directoryBo.getId(), session);
    }


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<DirectoryBo> get(Integer id, HttpSession session) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        DirectoryBo directoryBo = null;
        if (id == null) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("client.msg.primary_key_validation"));
        } else {
            directoryBo = directoryService.getDirectoryBo(id);
        }
        return JsonResult.success(directoryBo);

    }

    @RequestMapping(value = "create", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "add")
    public JsonResult<DirectoryBo> create() {
        DirectoryBo directoryBo = new DirectoryBo();
        directoryBo.setCreated(true);
        directoryBo.setIsactive("N");
        return JsonResult.success(directoryBo);
    }

}
