package indi.liudalei.eidea.base.web.controller;

import indi.liudalei.eidea.base.entity.bo.UserSessionBo;
import indi.liudalei.eidea.base.service.UserSessionService;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.QueryParams;
import indi.liudalei.eidea.core.web.def.WebConst;
import indi.liudalei.eidea.core.web.result.JsonResult;
import indi.liudalei.eidea.core.web.util.SearchHelper;
import indi.liudalei.eidea.core.web.vo.PagingSettingResult;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.Sort;
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

@Controller
@RequestMapping("/base/userSession")
public class UserSessionController {
    private static final String URI = "sys_user_session";
    @Autowired
    private UserSessionService userSessionService;

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @RequiresPermissions(value = "view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/base/userSession/userSession");
        modelAndView.addObject("pagingSettingResult", PagingSettingResult.getDbPaging());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<List<UserSessionBo>> list(HttpSession session) {
        Search search = SearchHelper.getSearchParam(URI, session);
        search.addSort(Sort.desc("loginDate"));
        List<UserSessionBo> userSessionList = userSessionService.getUserSessionList(search);
        return JsonResult.success(userSessionList);
    }

    @RequestMapping(value = "/listPaging", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<PaginationResult<UserSessionBo>> list(HttpSession session, @RequestBody QueryParams queryParams) {
        Search search = SearchHelper.getSearchParam(URI, session);
        search.addSort(Sort.desc("loginDate"));
        PaginationResult<UserSessionBo> paginationResult = userSessionService.getUserSessionPagingList(search, queryParams);
        return JsonResult.success(paginationResult);
    }
}
