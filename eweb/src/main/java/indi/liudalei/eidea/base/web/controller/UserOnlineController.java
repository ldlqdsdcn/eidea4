package indi.liudalei.eidea.base.web.controller;

import indi.liudalei.eidea.base.entity.bo.UserSessionBo;
import indi.liudalei.eidea.base.web.content.UserOnlineContent;
import indi.liudalei.eidea.core.web.def.WebConst;
import indi.liudalei.eidea.core.web.result.JsonResult;
import indi.liudalei.eidea.core.web.vo.PagingSettingResult;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Set;

/**
 * Created by 刘大磊 on 2017/1/3 14:28.
 */
@Controller
@RequestMapping("/base/userOnline")
public class UserOnlineController {
    private static final String URI = "sys_user";

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @RequiresPermissions(value = "view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/base/online/online");
        modelAndView.addObject("pagingSettingResult", PagingSettingResult.getDbPaging());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<Set<UserSessionBo>> list() {
        Set<UserSessionBo> userSessionBoSet = UserOnlineContent.getOnlineUsers();
        return JsonResult.success(userSessionBoSet);
    }

}
