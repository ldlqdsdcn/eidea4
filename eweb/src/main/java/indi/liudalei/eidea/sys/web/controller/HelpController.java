/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package indi.liudalei.eidea.sys.web.controller;

import indi.liudalei.eidea.sys.entity.po.HelpPo;
import indi.liudalei.eidea.sys.service.HelpService;
import indi.liudalei.eidea.core.web.controller.BaseController;
import indi.liudalei.eidea.core.dto.PaginationResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import indi.liudalei.eidea.core.web.def.WebConst;
import indi.liudalei.eidea.core.web.result.JsonResult;
import indi.liudalei.eidea.core.web.result.def.ErrorCodes;
import indi.liudalei.eidea.core.web.util.SearchHelper;
import indi.liudalei.eidea.core.web.vo.PagingSettingResult;
import com.googlecode.genericdao.search.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import indi.liudalei.eidea.core.params.QueryParams;
import indi.liudalei.eidea.core.params.DeleteParams;
import javax.servlet.http.HttpSession;

/**
* Created by 刘大磊 on 2017-04-26 15:55:56.
*/ @Controller
@RequestMapping("/sys/help")
public class HelpController extends BaseController {
private static final String URI = "sys_help";
@Autowired
private HelpService helpService;
@RequestMapping(value = "/showList", method = RequestMethod.GET)
@RequiresPermissions("view")
public ModelAndView showList() {
ModelAndView modelAndView = new ModelAndView("/sys/help/help");
modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDbPaging());
modelAndView.addObject(WebConst.PAGE_URI, URI);
return modelAndView;
}
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("view")
public JsonResult<PaginationResult<HelpPo>> list(HttpSession session, @RequestBody QueryParams queryParams) {
    Search search = SearchHelper.getSearchParam(URI, session);
    PaginationResult<HelpPo> paginationResult = helpService.getHelpListByPaging(search, queryParams);
    return JsonResult.success(paginationResult);
    }
    @RequiresPermissions("view")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<HelpPo> get(Integer id) {
        HelpPo helpPo = null;
        if (id == null) {
        return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(),getMessage("common.errror.get_object",getLabel("help.title")));
        } else {
        helpPo = helpService.getHelp(id);
        }
        return JsonResult.success(helpPo);
        }

        @RequiresPermissions("add")
        @RequestMapping(value = "/create", method = RequestMethod.GET)
        @ResponseBody
        public JsonResult<HelpPo> create() {
            HelpPo helpPo = new HelpPo();
            return JsonResult.success(helpPo);
            }

    /**
    * @param helpPo
    * @return
    */
    @RequiresPermissions("add")
    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<HelpPo> saveForCreate(@Validated @RequestBody HelpPo helpPo) {
        helpService.saveHelp(helpPo);
        return get(helpPo.getId());
        }

        @RequiresPermissions("update")
        @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
        @ResponseBody
        public JsonResult<HelpPo> saveForUpdate(@Validated @RequestBody HelpPo helpPo) {

            if(helpPo.getId() == null){
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.errror.pk.required"));
            }
            helpService.saveHelp(helpPo);
            return get(helpPo.getId());
            }

    @RequiresPermissions("delete")
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody

    public JsonResult<PaginationResult<HelpPo>> deletes(@RequestBody DeleteParams<Integer> deleteParams, HttpSession session) {
    if (deleteParams.getIds() == null||deleteParams.getIds().length == 0)  {
                return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.error.delete.failure",getMessage("help.title")));
                }
            helpService.deletes(deleteParams.getIds());
                return list(session,deleteParams.getQueryParams());
        }


}
