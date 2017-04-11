package com.dsdl.eidea.common.web.controller;

import com.dsdl.eidea.base.def.OperatorDef;
import com.dsdl.eidea.base.entity.bo.UserBo;
import com.dsdl.eidea.base.entity.bo.UserSessionBo;
import com.dsdl.eidea.base.service.PageMenuService;
import com.dsdl.eidea.base.service.UserService;
import com.dsdl.eidea.base.web.content.UserOnlineContent;
import com.dsdl.eidea.base.entity.bo.UserContent;
import com.dsdl.eidea.base.web.vo.UserResource;
import com.dsdl.eidea.core.entity.bo.LanguageBo;
import com.dsdl.eidea.core.i18n.DbResourceBundle;
import com.dsdl.eidea.core.service.LanguageService;
import com.dsdl.eidea.core.service.MessageService;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.ApiResult;
import com.dsdl.eidea.core.web.result.def.ErrorCodes;
import com.dsdl.eidea.core.web.result.def.ResultCode;
import com.dsdl.eidea.util.LocaleHelper;
import com.dsdl.eidea.util.StringUtil;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
public class UserLoginController {
    private static final Logger logger = Logger.getLogger(UserLoginController.class);
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;
    @Autowired
    private PageMenuService pageMenuService;
    @Autowired
    private HttpServletResponse response;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private LanguageService languageService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ApiResult<String> login(@RequestBody UserBo loginBo) {
        if (loginBo == null) {
            return ApiResult.fail(ResultCode.FAILURE.getCode(), "用户名或密码不允许为空！");
        } else {
            if (StringUtil.isEmpty(loginBo.getUsername())) {
                return ApiResult.fail(ResultCode.FAILURE.getCode(), "用户名不允许为空！");
            }
            if (StringUtil.isEmpty(loginBo.getPassword())) {
                return ApiResult.fail(ResultCode.FAILURE.getCode(), "密码不允许为空！");
            }
        }

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginBo.getUsername(), loginBo.getPassword());

        try {
            subject.login(token);
            userInitCommon(loginBo.getUsername());
            UserBo userBo=userService.getUserByUsername(loginBo.getUsername());
            userBo.setCode(loginBo.getCode());
            userInit(userBo, false, request);
            return ApiResult.success("登录成功");
        } catch (IncorrectCredentialsException | UnknownAccountException e) {
            return ApiResult.fail(ErrorCodes.NO_LOGIN.getCode(), "用户名或密码错误，请重新输入");
        } catch (LockedAccountException e) {
            return ApiResult.fail(ErrorCodes.NO_LOGIN.getCode(), "该用户已经被禁用");
        }





    }

    private void userInitCommon(String username) {

        Cookie cookie = new Cookie("userName", username);
        cookie.setMaxAge(60 * 60 * 24 * 7);
        response.addCookie(cookie);
        HttpSession session = request.getSession();
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String ip = addr.getHostAddress().toString();//获得本机IP
            String address = addr.getHostName().toString();//获得本机名称
            session.setAttribute("localIpAddress", ip);
            session.setAttribute("localHostName", address);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void userInit(UserBo loginUser, boolean beSub, HttpServletRequest request) {
        HttpSession session = request.getSession();
        /*Get user privileges*/

        if (beSub == false)
            session.setAttribute(WebConst.SESSION_LOGINUSER, loginUser);
        session.setAttribute(WebConst.SESSION_ACTUALUSER, loginUser);
        String ip = null;
        String address = null;
        try {
            InetAddress addr = InetAddress.getLocalHost();
            ip = addr.getHostAddress().toString();//获得本机IP
            address = addr.getHostName().toString();//获得本机名称
            session.setAttribute("localIpAddress", ip);
            session.setAttribute("localHostName", address);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        String token = userService.generateToken(loginUser);
        UserSessionBo userSessionBo = new UserSessionBo();
        userSessionBo.setLoginDate(new Date());
        userSessionBo.setRemoteAddr(ip);
        userSessionBo.setSessionId(request.getSession().getId());
        userSessionBo.setRemoteHost(address);
        userSessionBo.setUsername(loginUser.getUsername());
        userSessionBo.setUserId(loginUser.getId());
        userSessionBo.setToken(token);
        UserOnlineContent.addUser(session.getId(), userSessionBo);
        userSessionBo = userService.saveUserSessionBo(userSessionBo);
        List<Integer> orgIdList = userService.getCanAccessOrgList(loginUser.getId());
        String contextPath = request.getServletContext().getContextPath();
        String leftMenuStr = pageMenuService.getLeftMenuListByUserId(loginUser.getId(), contextPath);
        session.setAttribute(WebConst.SESSION_LEFTMENU, leftMenuStr);

        if (request.getSession().getAttribute(WebConst.SESSION_RESOURCE) == null) {
            Locale locale = LocaleHelper.parseLocale(loginUser.getCode());
            DbResourceBundle dbResourceBundle = messageService.getResourceBundle(loginUser.getCode());
            UserResource userResource = new UserResource(locale, dbResourceBundle);
            session.setAttribute(WebConst.SESSION_RESOURCE, userResource);
        }


    }

    /**
     * 登出系统
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpSession session) {

        session.removeAttribute(WebConst.SESSION_LOGINUSER);
        session.removeAttribute(WebConst.SESSION_USERCONTENT);
        session.removeAttribute(WebConst.SESSION_RESOURCE);
        session.removeAttribute(WebConst.SESSION_LEFTMENU);
        ModelAndView modelAndView = new ModelAndView("redirect:/login.jsp");

        return modelAndView;
    }

    /**
     * getLanguageForActivated:登录页面语种
     *
     * @return
     */
    @RequestMapping(value = "/languages", method = RequestMethod.GET)
    public ApiResult<List<LanguageBo>> getLanguage() {
        List<LanguageBo> languageList = languageService.getLanguageForActivated();
        return ApiResult.success(languageList);
    }
}