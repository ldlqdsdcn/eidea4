package com.dsdl.eidea.core.web.util;

import com.dsdl.eidea.base.entity.bo.UserBo;
import com.dsdl.eidea.core.web.def.WebConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by 刘大磊 on 2017/5/31 15:49.
 */
public class WebUtil {
    public static UserBo getUserBoInSession(HttpSession session) {
        return (UserBo) session.getAttribute(WebConst.SESSION_LOGINUSER);
    }

    public static UserBo getUserBoInSession(HttpServletRequest request) {
        return getUserBoInSession(request.getSession());
    }
}
