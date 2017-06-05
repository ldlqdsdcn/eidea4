package com.dsdl.eidea.common.web.controller;

import com.dsdl.eidea.base.def.OperatorDef;
import com.dsdl.eidea.base.entity.bo.UserBo;
import com.dsdl.eidea.base.entity.bo.UserSessionBo;
import com.dsdl.eidea.base.service.PageMenuService;
import com.dsdl.eidea.base.service.UserService;
import com.dsdl.eidea.base.service.UserSessionService;
import com.dsdl.eidea.base.web.content.UserOnlineContent;
import com.dsdl.eidea.base.entity.bo.UserContent;
import com.dsdl.eidea.base.web.vo.UserResource;
import com.dsdl.eidea.core.entity.bo.LanguageBo;
import com.dsdl.eidea.core.i18n.DbResourceBundle;
import com.dsdl.eidea.core.service.LanguageService;
import com.dsdl.eidea.core.service.MessageService;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.JsonResult;
import com.dsdl.eidea.core.web.result.def.ErrorCodes;
import com.dsdl.eidea.core.web.result.def.ResultCode;
import com.dsdl.eidea.util.LocaleHelper;
import com.dsdl.eidea.util.StringUtil;
import com.google.gson.Gson;
import cryptography.AesUtil;
import cryptography.Md5Util;
import cryptography.RsaUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.util.*;

import static java.lang.Thread.sleep;

@Slf4j
@RestController
public class UserLoginController {
    private Gson gson = new Gson();
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
    @Autowired
    private UserSessionService userSessionService;


    @RequestMapping(value = "/login", method = RequestMethod.POST)

    @ResponseBody
    public JsonResult<String> login(@RequestBody String allparam,HttpServletRequest request) {
        /**
         * 在session中存储的时间戳和是否第一次登录，如果是第一次登录输错密码后不用等待，直接弹出密码错误，请重新输入的信息。
         * 如果不是第一次输错密码，之后输错密码会有5秒的睡眠时间，直到输入正确密码后将是否第一次登录进行重置。
         * 时间戳用来和当前时间进行比较，在输错密码的情况下，时间戳与当前时间间隔小于10s时会有5秒的睡眠时间。
         *
         * 利用传来的用户名和密码混合加密后的密文，加密后的密钥enkey和iv进行解密还原出用户名和密码
         */


        //去掉无用的前缀"{allparam:"和后缀"}"
        String param = allparam.substring(13,allparam.length()-1);
        //提取出cipherUsernameAndPassword、enkey和iv
        String[] str = param.split("\\|");
        String cipherUsernameAndPassword = str[0];
        String enkey = str[1];
        String iv = str[2];
        //对加密后的用户名和密码进行解密
        AesUtil aesUtil = new AesUtil();
        RsaUtil rsaUtil = new RsaUtil();
        Md5Util md5Util = new Md5Util();
        String dec = rsaUtil.rsaDecode(enkey);
        String decodeContent = aesUtil.aesDecode(dec ,iv , cipherUsernameAndPassword);
        //对解密后的字符串进行拆解，还原出username和password
        String[] cipherstr = decodeContent.split("\\|");
        String username = cipherstr[0];
        String password = cipherstr[1];
        if (username == null && password == null) {
            return JsonResult.fail(ResultCode.FAILURE.getCode(), "用户名或密码不允许为空！");
        } else {
            if (StringUtil.isEmpty(username)) {
                return JsonResult.fail(ResultCode.FAILURE.getCode(), "用户名不允许为空！");
            }
            if (StringUtil.isEmpty(password)) {
                return JsonResult.fail(ResultCode.FAILURE.getCode(), "密码不允许为空！");
            }
        }
        //再用MD5进行加密,与数据库中的内容进行比对
        String md5Password = md5Util.EncoderByMd5(password).toLowerCase();
        System.out.println(md5Password);

        Subject subject = SecurityUtils.getSubject();

        // 用字符串进行生成Token
        UsernamePasswordToken token = new UsernamePasswordToken(username, md5Password);
        UserBo loginBo = new UserBo();
        loginBo.setUsername(username);

        //获得时间戳和是否第一次登录的信息
        HttpSession session = request.getSession();
        String timestamp = String.valueOf(session.getAttribute("timestamp"));
        String firstloginstr = String.valueOf(session.getAttribute("firstloginstr"));
        Integer firstlogin = Integer.valueOf(firstloginstr);
        Long logintime = Long.valueOf(timestamp);
        Long nowtime = System.currentTimeMillis();

        try {
            subject.login(token);
            UserBo userBo = userService.getUserByUsername(loginBo.getUsername());
            userInitCommon(loginBo);
            userBo.setCode(loginBo.getCode());
            userInit(userBo, false, request);
            //成功登录后将是否第一次登录重置为1：是
            session.setAttribute("firstloginstr",1);
            return JsonResult.success("登录成功");
        } catch (IncorrectCredentialsException | UnknownAccountException e) {
            //在密码输错的情况下进入睡眠时间（第一次输错没有睡眠）
            try {
                if (firstlogin != 1){
                    if(logintime +10000 > nowtime){
                        sleep(5000);
                    }
                }
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            //将是否第一次登录的状态改为0：否
            session.setAttribute("firstloginstr",0);
            //将时间戳重置
            session.setAttribute("timestamp",System.currentTimeMillis());
            return JsonResult.fail(ErrorCodes.NO_LOGIN.getCode(), "密码错误，请重新输入");
        } catch (LockedAccountException e) {
            return JsonResult.fail(ErrorCodes.NO_LOGIN.getCode(), "该用户已经被禁用");
        } catch (AuthenticationException e) {
            return JsonResult.fail(ErrorCodes.NO_LOGIN.getCode(), "用户名错误，请重新输入");
        }

    }

    private void userInitCommon(UserBo loginBo) {

        Cookie cookie = new Cookie("userName", loginBo.getUsername());
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
        if (session.getAttribute(WebConst.SESSION_LANGUAGE) == null) {
            session.setAttribute(WebConst.SESSION_LANGUAGE, request.getLocale().toString());
        }
        Map<String, List<OperatorDef>> privilegesMap = userService.getUserPrivileges(loginUser.getId());
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
        UserContent userContent = new UserContent(privilegesMap, userSessionBo, token, orgIdList);
        session.setAttribute(WebConst.SESSION_USERCONTENT, userContent);
        String contextPath = request.getServletContext().getContextPath();

        Locale locale = LocaleHelper.parseLocale(loginUser.getCode());
        if (request.getSession().getAttribute(WebConst.SESSION_RESOURCE) == null) {

            DbResourceBundle dbResourceBundle = messageService.getResourceBundle(loginUser.getCode());
            UserResource userResource = new UserResource(locale, dbResourceBundle);
            session.setAttribute(WebConst.SESSION_RESOURCE, userResource);
        }
        String leftMenuStr = pageMenuService.getLeftMenuListByUserId(loginUser.getId(), contextPath, locale.toString());
        session.setAttribute(WebConst.SESSION_LEFTMENU, leftMenuStr);

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
        Subject subject = SecurityUtils.getSubject();
        if (subject.isAuthenticated()) {
            subject.logout(); // session 会销毁，在SessionListener监听session销毁，清理权限缓存
            if (log.isDebugEnabled()) {
                log.debug("用户" + subject.getPrincipal() + "退出登录");
            }
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/login.jsp");
        return modelAndView;
    }

    /**
     * getLanguageForActivated:登录页面语种
     *
     * @return
     */
    @RequestMapping(value = "/languages", method = RequestMethod.GET)
    public JsonResult<List<LanguageBo>> getLanguage() {
        List<LanguageBo> languageList = languageService.getLanguageForActivated();
        return JsonResult.success(languageList);
    }
}