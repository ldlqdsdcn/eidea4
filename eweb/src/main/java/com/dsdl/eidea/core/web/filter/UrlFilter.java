package com.dsdl.eidea.core.web.filter;

import com.dsdl.eidea.core.web.result.ApiResult;
import com.dsdl.eidea.core.web.result.def.ErrorCodes;
import com.google.gson.Gson;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by 刘大磊 on 2016/12/29 17:22.
 */
public class UrlFilter implements Filter {
    private static final String JSON_KEY_WORDS = "application/json;charset=UTF-8";
    private String[] urls;

    private boolean isJsonResponse(HttpServletRequest request) {
        String contextType = request.getContentType();
        if (contextType != null && contextType.contains(JSON_KEY_WORDS)) {
            return true;
        }
        return false;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String s = filterConfig.getInitParameter("url");
        urls = s.split(",");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        String requestPageUrl = request.getServletPath();
        if (session.getAttribute("loginUser") == null) {


            boolean login = false;
            for (String url : urls) {
                if (requestPageUrl.equals(url)) {
                    login = true;
                    break;
                }
            }
            if (!login) {
                ServletContext context = request.getServletContext();
                if (isJsonResponse(request)) {
                    response.setStatus(200);
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("application/json; charset=utf-8");
                    PrintWriter out = null;
                    try {
                        out = response.getWriter();
                        ApiResult apiResult = ApiResult.fail(ErrorCodes.NO_LOGIN.getCode(), ErrorCodes.NO_LOGIN.getMessage());
                        String result = new Gson().toJson(apiResult);
                        out.append(result);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (out != null) {
                            out.close();
                        }
                    }
                } else {
                    RequestDispatcher rd = context
                            .getRequestDispatcher("/common/frameLogin.jsp");
                    rd.forward(servletRequest, servletResponse);
                }
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
