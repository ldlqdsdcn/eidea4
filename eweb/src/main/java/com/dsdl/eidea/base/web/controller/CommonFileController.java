/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.base.web.controller;

import com.dsdl.eidea.base.entity.po.CommonFilePo;
import com.dsdl.eidea.base.service.CommonFileService;
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
* Created by 刘大磊 on 2017-05-02 13:09:39.
*/ @Controller
@RequestMapping("/base/commonFile")
public class CommonFileController extends BaseController {
private static final String URI = "commonFile";
@Autowired
private CommonFileService commonFileService;
@RequestMapping(value = "/showList", method = RequestMethod.GET)
@RequiresPermissions("view")
public ModelAndView showList() {
ModelAndView modelAndView = new ModelAndView("/base/commonFile/commonFile");
modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDbPaging());
modelAndView.addObject(WebConst.PAGE_URI, URI);
return modelAndView;
}
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("view")
public JsonResult<PaginationResult<CommonFilePo>> list(HttpSession session,@RequestBody QueryParams queryParams) {
    Search search = SearchHelper.getSearchParam(URI, session);
    PaginationResult<CommonFilePo> paginationResult = commonFileService.getCommonFileListByPaging(search, queryParams);
    return JsonResult.success(paginationResult);
    }
    @RequiresPermissions("view")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<CommonFilePo> get(Integer id) {
        CommonFilePo commonFilePo = null;
        if (id == null) {
        return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(),getMessage("common.errror.get_object",getLabel("commonFile.title")));
        } else {
        commonFilePo = commonFileService.getCommonFile(id);
        }
        return JsonResult.success(commonFilePo);
        }

        @RequiresPermissions("add")
        @RequestMapping(value = "/create", method = RequestMethod.GET)
        @ResponseBody
        public JsonResult<CommonFilePo> create() {
            CommonFilePo commonFilePo = new CommonFilePo();
            return JsonResult.success(commonFilePo);
            }

    /**
    * @param commonFilePo
    * @return
    */
    @RequiresPermissions("add")
    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<CommonFilePo> saveForCreate(@Validated @RequestBody CommonFilePo commonFilePo) {
        commonFileService.saveCommonFile(commonFilePo);
        return get(commonFilePo.getId());
        }

        @RequiresPermissions("update")
        @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
        @ResponseBody
        public JsonResult<CommonFilePo> saveForUpdate(@Validated @RequestBody CommonFilePo commonFilePo) {

            if(commonFilePo.getId() == null){
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.errror.pk.required"));
            }
            commonFileService.saveCommonFile(commonFilePo);
            return get(commonFilePo.getId());
            }

    @RequiresPermissions("delete")
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody

    public JsonResult<PaginationResult<CommonFilePo>> deletes(@RequestBody DeleteParams<Integer> deleteParams, HttpSession session) {
    if (deleteParams.getIds() == null||deleteParams.getIds().length == 0)  {
                return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.error.delete.failure",getMessage("commonFile.title")));
                }
            commonFileService.deletes(deleteParams.getIds());
                return list(session,deleteParams.getQueryParams());
        }


}
