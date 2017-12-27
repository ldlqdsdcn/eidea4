/**
 * 版权所有 刘大磊 2013-07-01
 * 作者：刘大磊
 * 电话：13336390671
 * email:ldlqdsd@126.com
 */
package indi.liudalei.eidea.base.web.controller;

import indi.liudalei.eidea.base.entity.po.TabTrlPo;
import indi.liudalei.eidea.base.service.TabService;
import indi.liudalei.eidea.base.service.TabTrlService;
import indi.liudalei.eidea.core.web.controller.BaseController;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.DeleteParams;
import indi.liudalei.eidea.core.web.def.WebConst;
import indi.liudalei.eidea.core.web.result.JsonResult;
import indi.liudalei.eidea.core.web.result.def.ErrorCodes;
import indi.liudalei.eidea.core.web.util.SearchHelper;
import indi.liudalei.eidea.core.web.vo.PagingSettingResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

import javax.servlet.http.HttpSession;

/**
 * Created by 刘大磊 on 2017-05-02 15:43:44.
 */
@Controller
@RequestMapping("/base/tabTrl")
public class TabTrlController extends BaseController {
    private static final String URI = "tabTrl";
    @Autowired
    private TabTrlService tabTrlService;
    @Autowired
    private TabService tabService;

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @RequiresPermissions("view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/base/tabTrl/tabTrl");
        modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDbPaging());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("view")
    public JsonResult<PaginationResult<TabTrlPo>> list(HttpSession session, @RequestBody QueryParams queryParams) {
        Search search = SearchHelper.getSearchParam(URI, session);
        PaginationResult<TabTrlPo> paginationResult = tabTrlService.getTabTrlListByPaging(search, queryParams);
        return JsonResult.success(paginationResult);
    }

    @RequestMapping(value = "/tabTrlList", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("view")
    public JsonResult<PaginationResult<TabTrlPo>> tabTrlList(HttpSession session, @RequestBody Integer id) {
        Search search = SearchHelper.getSearchParam(URI, session);
        PaginationResult<TabTrlPo> paginationResult = tabTrlService.getTabTrlListByTabId(search, id);
        return JsonResult.success(paginationResult);
    }

    @RequiresPermissions("view")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<TabTrlPo> get(Integer id) {
        TabTrlPo tabTrlPo = null;
        if (id == null) {
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.errror.get_object", getLabel("tabTrl.title")));
        } else {
            tabTrlPo = tabTrlService.getTabTrl(id);
        }
        return JsonResult.success(tabTrlPo);
    }

    @RequiresPermissions("add")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<TabTrlPo> create() {
        TabTrlPo tabTrlPo = new TabTrlPo();
        return JsonResult.success(tabTrlPo);
    }

    /**
     * @param tabTrlPo
     * @return
     */
    @RequiresPermissions("add")
    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<TabTrlPo> saveForCreate(@Validated @RequestBody TabTrlPo tabTrlPo) {
        if (tabService.getTab(tabTrlPo.getTabId())==null){
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(),getMessage("error.tab.id.not.exist"));
        }
        tabTrlService.saveTabTrl(tabTrlPo);
        return get(tabTrlPo.getId());
    }

    @RequiresPermissions("update")
    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<TabTrlPo> saveForUpdate(@Validated @RequestBody TabTrlPo tabTrlPo) {

        if (tabTrlPo.getId() == null) {
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.errror.pk.required"));
        }
        if (tabService.getTab(tabTrlPo.getTabId())==null){
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(),getMessage("error.tab.id.not.exist"));
        }
        tabTrlService.saveTabTrl(tabTrlPo);
        return get(tabTrlPo.getId());
    }
    @RequiresPermissions("delete")
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody

    public JsonResult<PaginationResult<TabTrlPo>> deletes(@RequestBody DeleteParams<Integer> deleteParams, HttpSession session) {
        if (deleteParams.getIds() == null || deleteParams.getIds().length == 0) {
            return JsonResult.fail(ErrorCodes.VALIDATE_PARAM_ERROR.getCode(), getMessage("common.error.delete.failure", getMessage("tabTrl.title")));
        }
        Integer tabId=tabTrlService.getTabTrl(deleteParams.getIds()[0]).getTabId();
        tabTrlService.deletes(deleteParams.getIds());
        return tabTrlList(session, tabId);
    }


}
