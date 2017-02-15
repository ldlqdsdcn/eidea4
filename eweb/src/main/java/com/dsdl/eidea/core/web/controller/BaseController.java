package com.dsdl.eidea.core.web.controller;

import com.dsdl.eidea.base.web.vo.UserResource;
import com.dsdl.eidea.core.web.def.WebConst;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by 刘大磊 on 2017/1/13 10:21.
 * 所有Controller基类
 */
public class BaseController {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpSession session;

    public String getMessage(String key) {
        return getUserResource().getMessage(key);
    }

    public String getMessage(String key, Object... value) {
        return getUserResource().getMessage(key, value);
    }

    public String getLabel(String key) {
        return getUserResource().getLabel(key);
    }

    public String getLabel(String key, Object... value) {
        return getUserResource().getMessage(key, value);
    }

    private UserResource getUserResource() {
        return (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
    }

}
