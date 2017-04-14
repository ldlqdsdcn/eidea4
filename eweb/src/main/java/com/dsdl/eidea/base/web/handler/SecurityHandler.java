package com.dsdl.eidea.base.web.handler;

import com.dsdl.eidea.base.def.OperatorDef;
import com.dsdl.eidea.base.util.JwtUtil;
import com.dsdl.eidea.base.web.annotation.PrivilegesControl;
import com.dsdl.eidea.base.web.def.ReturnType;
import com.dsdl.eidea.base.web.util.SecurityHelper;
import com.dsdl.eidea.base.web.vo.VerifiedResult;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.JsonResult;
import com.dsdl.eidea.core.web.result.def.ErrorCodes;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * Created by 刘大磊 on 2016/12/26 16:25.
 */
public class SecurityHandler extends HandlerInterceptorAdapter {
    private static final String JSON_KEY_WORDS = "application/json;charset=UTF-8";
    private static final SecurityHelper SECURITY_HELPER = SecurityHelper.getSecurityHelper();
    private static Logger log = Logger.getLogger(SecurityHandler.class);
    /**
     * 不需要登录系统就可以访问的界面
     */
    private static String[] noLoginAuthorizationUrls = new String[]{"/login", "/js/", "/img/", "/css/", "/fonts", "/error/", "/logout", "/languages", "/common/changeLanguage"};
    /**
     * 登录以后，任何人都可以访问的界面
     */
    private static String[] noAuthorizationUrls = new String[]{"/login", "/js/", "/img/", "/css/", "/fonts", "/common/", "/error/", "/logout", "/languages", "/common/changeLanguage"};
    private static Gson GSON = new Gson();

    private boolean isJsonResponse(HttpServletRequest request) {
        String contextType = request.getContentType();
        if (contextType != null && contextType.contains(JSON_KEY_WORDS)) {
            return true;
        }
        return false;
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession();
        String servletPath = request.getServletPath();
        isJsonResponse(request);
        if (containNoLoginAuthorizationUrls(servletPath)) {
            return true;
        }
        String token = request.getHeader("token");
        if (token != null) {
            /**
             * 如果token验证通过
             */
            if (JwtUtil.validateToken(token)) {

            }
        } else if (session.getAttribute(WebConst.SESSION_LOGINUSER) == null) {
            log.warn("请先登录");
            if (isJsonResponse(request)) {
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                response.setContentType("application/json; charset=utf-8");
                PrintWriter out = null;
                try {
                    out = response.getWriter();
                    JsonResult apiResult = JsonResult.fail(ErrorCodes.NO_LOGIN.getCode(), ErrorCodes.NO_LOGIN.getMessage());
                    String result = GSON.toJson(apiResult);
                    out.append(result);
                    log.debug("返回是\n");
                    log.debug(result);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        out.close();
                    }
                }
            } else {
                response.sendRedirect(request.getContextPath() + "/common/frameLogin.jsp");
            }
            return false;
        }
        if ("/".equals(servletPath)) {
            return true;
        }
        if (containNoAuthorizationUrls(servletPath)) {
            return true;
        }
        //角色权限控制访问
        return privilegesControl(request, response, handler);
    }

    private boolean containNoLoginAuthorizationUrls(String servletPath) {
        for (String url : noLoginAuthorizationUrls) {
            if (servletPath.startsWith(url)) {
                return true;
            }
        }
        return false;
    }

    private boolean containNoAuthorizationUrls(String servletPath) {
        for (String url : noAuthorizationUrls) {
            if (servletPath.startsWith(url)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 角色权限控制访问
     */
    private boolean privilegesControl(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
            Object target = hm.getBean();
            Class<?> clazz = hm.getBeanType();
            Method m = hm.getMethod();
            try {
                if (clazz != null && m != null) {
                    boolean isClzAnnotation = clazz.isAnnotationPresent(PrivilegesControl.class);
                    boolean isMethondAnnotation = m.isAnnotationPresent(PrivilegesControl.class);
                    PrivilegesControl rc = null;
                    //如果方法和类声明中同时存在这个注解，那么方法中的会覆盖类中的设定。
                    if (isMethondAnnotation) {
                        rc = m.getAnnotation(PrivilegesControl.class);
                    } else if (isClzAnnotation) {
                        rc = clazz.getAnnotation(PrivilegesControl.class);
                    }
                    OperatorDef[] value = new OperatorDef[]{OperatorDef.VIEW};
                    ReturnType returnType = ReturnType.JSP;
                    if (rc != null) {
                        value = rc.operator();
                        returnType = rc.returnType();
                    }

                    //Object obj = session.getAttribute(GeneUtil.SESSION_USERTYPE_KEY);
                    //String curUserType = obj == null ? "" : obj.toString();
                    //进行角色访问的权限控制，只有当前用户是需要的角色才予以访问。
                    //boolean isEquals = StringUtils.checkEquals(value, curUserType);
                    VerifiedResult verifiedResult = SECURITY_HELPER.verifiedResult(request, value);
                    boolean isEquals = verifiedResult.isCanAccessed();
                    if (!isEquals) {
                        sendToMessage(returnType, request, response);
                        return false;
                    }
                }
            } catch (Exception e) {
                log.error("操作权限错误", e);
            }
        }

        return true;
    }

    private void sendToMessage(ReturnType returnType, HttpServletRequest request, HttpServletResponse response) {
        //TODO
        if (returnType == ReturnType.JSON) {
            response.setStatus(200);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            PrintWriter out = null;
            try {
                out = response.getWriter();
                JsonResult apiResult = JsonResult.fail(ErrorCodes.NO_PRIVILEGES.getCode(), ErrorCodes.NO_PRIVILEGES.getMessage());
                String result = GSON.toJson(apiResult);
                out.append(result);
                log.debug("返回是\n");
                log.debug(result);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        } else {
            try {

                response.sendRedirect(request.getContextPath() + "/error/noprivileges.jsp");
            } catch (IOException e) {
                log.error(e);
            }
        }
    }
}
