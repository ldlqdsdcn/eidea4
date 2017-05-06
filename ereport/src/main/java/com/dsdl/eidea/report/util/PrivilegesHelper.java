package com.dsdl.eidea.report.util;

import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by 刘大磊 on 2016/11/25 10:03.
 */
public class PrivilegesHelper {
    private static final Logger logger=Logger.getLogger(PrivilegesHelper.class);
    public static void outNoPrivilegesView(HttpServletResponse response)
    {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out= null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            logger.error("输出权限视图出错",e);
        }
        out.println("<html><head>没有权限</head><body>你需要登录！</body></html>");
        out.close();
    }
}
