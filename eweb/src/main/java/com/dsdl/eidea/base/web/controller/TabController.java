/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.base.web.controller;

import com.dsdl.eidea.base.entity.po.TabPo;
import com.dsdl.eidea.base.service.ChangelogService;
import com.dsdl.eidea.base.service.TabService;
import com.dsdl.eidea.core.entity.bo.TableBo;
import com.dsdl.eidea.core.entity.bo.TableColumnBo;
import com.dsdl.eidea.core.service.TableService;
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

import javax.persistence.Table;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
* Created by 刘大磊 on 2017-05-02 15:43:14.
*/ @Controller
@RequestMapping("/base/tab")
public class TabController extends BaseController {
private static final String URI = "tab";
@Autowired
private TabService tabService;
@Autowired
private TableService tableService;
@Autowired
private ChangelogService changelogService;
@RequestMapping(value = "/showList", method = RequestMethod.GET)
@RequiresPermissions("view")
public ModelAndView showList() {
ModelAndView modelAndView = new ModelAndView("/base/tab/tab");
modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDbPaging());
modelAndView.addObject(WebConst.PAGE_URI, URI);
return modelAndView;
}
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("view")
public JsonResult<PaginationResult<TabPo>> list(HttpSession session,@RequestBody QueryParams queryParams) {
    Search search = SearchHelper.getSearchParam(URI, session);
    PaginationResult<TabPo> paginationResult = tabService.getTabListByPaging(search, queryParams);
    return JsonResult.success(paginationResult);
    }
    @RequiresPermissions("view")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<TabPo> get(Integer id) {
        TabPo tabPo = null;
        if (id == null) {
        return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(),getMessage("common.errror.get_object",getLabel("tab.title")));
        } else {
        tabPo = tabService.getTab(id);
        }
        return JsonResult.success(tabPo);
        }

        @RequiresPermissions("add")
        @RequestMapping(value = "/create", method = RequestMethod.GET)
        @ResponseBody
        public JsonResult<TabPo> create() {
            TabPo tabPo = new TabPo();
            return JsonResult.success(tabPo);
            }

    /**
    * @param tabPo
    * @return
    */
    @RequiresPermissions("add")
    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<TabPo> saveForCreate(@Validated @RequestBody TabPo tabPo) {
        tabService.saveTab(tabPo);
        return get(tabPo.getId());
        }

        @RequiresPermissions("update")
        @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
        @ResponseBody
        public JsonResult<TabPo> saveForUpdate(@Validated @RequestBody TabPo tabPo) {

            if(tabPo.getId() == null){
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.errror.pk.required"));
            }
            tabService.saveTab(tabPo);
            return get(tabPo.getId());
            }

    @RequiresPermissions("delete")
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody

    public JsonResult<PaginationResult<TabPo>> deletes(@RequestBody DeleteParams<Integer> deleteParams, HttpSession session) {
    if (deleteParams.getIds() == null||deleteParams.getIds().length == 0)  {
                return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.error.delete.failure",getMessage("tab.title")));
                }
            tabService.deletes(deleteParams.getIds());
                return list(session,deleteParams.getQueryParams());
        }

    @RequiresPermissions(value = "view")
    @ResponseBody
    @RequestMapping(value = "/getTablePoList",method = RequestMethod.GET)
    public JsonResult<PaginationResult<TableBo>> getTablePoList(){
        Search search = new Search();
        search.addFilterEqual("isactive","Y");
        PaginationResult<TableBo> paginationResult = tableService.findList(search,new QueryParams());
        return JsonResult.success(paginationResult);
    }
    @RequestMapping(value = "/getTableColumnList",method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<List<TableColumnBo>> getTableColumnList(@RequestBody Integer id){
       List<TableColumnBo> tableColumnBoList = changelogService.getChangelogHeader(tableService.getTableBo(id).getTableName());
       return JsonResult.success(tableColumnBoList);
    }

}
