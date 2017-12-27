package indi.liudalei.eidea.common.web.controller;

import indi.liudalei.eidea.base.entity.bo.UserBo;
import indi.liudalei.eidea.base.service.PageMenuService;
import indi.liudalei.eidea.base.web.vo.UserResource;
import indi.liudalei.eidea.core.entity.bo.LanguageBo;
import indi.liudalei.eidea.core.i18n.DbResourceBundle;
import indi.liudalei.eidea.core.service.MessageService;
import indi.liudalei.eidea.core.web.def.WebConst;
import indi.liudalei.eidea.core.web.result.JsonResult;
import indi.liudalei.eidea.util.LocaleHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Locale;

/**
 * Created by Bobo on 2017/1/11 15:06.
 */
@Controller
public class ChangedLanguageController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private MessageService messageService;
    @Autowired
    private PageMenuService pageMenuService;

    @RequestMapping(value = "/common/changeLanguage", method = RequestMethod.GET)
    public ModelAndView changeLanguage(HttpServletRequest request, String language) {
        ModelAndView modelAndView = new ModelAndView("/login");
        if (language == null) {
            language = request.getLocale().toString();
        }
        DbResourceBundle dbResourceBundle = messageService.getResourceBundle(language);
        Locale locale = LocaleHelper.parseLocale(language);
        request.getSession().setAttribute(WebConst.SESSION_RESOURCE, new UserResource(locale, dbResourceBundle));
        request.getSession().setAttribute(WebConst.SESSION_LANGUAGE,language);
        return modelAndView;
    }

    /**
     * changeLanguageCode:header切换语言
     * @return
     */
    @RequestMapping(value = "/common/changeLanguageCode", method = RequestMethod.GET)
    public ModelAndView changeLanguageCode(HttpServletRequest request, String language) {
        HttpSession session = request.getSession();
        ModelAndView modelAndView = new ModelAndView("/index");
        if (language == null) {
            language = request.getLocale().toString();
        }
        DbResourceBundle dbResourceBundle = messageService.getResourceBundle(language);
        Locale locale = LocaleHelper.parseLocale(language);
        request.getSession().setAttribute(WebConst.SESSION_RESOURCE, new UserResource(locale, dbResourceBundle));
        request.getSession().setAttribute(WebConst.SESSION_LANGUAGE,language);
        UserBo loginUser=(UserBo) session.getAttribute(WebConst.SESSION_LOGINUSER);
        String contextPath = request.getServletContext().getContextPath();
        String leftMenuStr = pageMenuService.getLeftMenuListByUserId(loginUser.getId(), contextPath,language);
        session.setAttribute(WebConst.SESSION_LEFTMENU, leftMenuStr);
        return modelAndView;
    }

    /**
     * addCookie:切换语言记住当前输入的用户名称
     * @return
     */
    @RequestMapping(value = "/common/addCookie", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<List<LanguageBo>> addCookie(HttpServletResponse response, @RequestBody UserBo userBo) {
        Cookie cookie = new Cookie("userName", userBo.getUsername());
        cookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(cookie);
        return JsonResult.success(null);
    }
}
