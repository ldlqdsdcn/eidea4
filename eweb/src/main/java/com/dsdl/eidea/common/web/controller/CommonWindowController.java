package com.dsdl.eidea.common.web.controller;

import com.dsdl.eidea.base.entity.bo.WindowBo;
import com.dsdl.eidea.base.service.WindowService;
import com.dsdl.eidea.core.web.def.WebConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * Created by 刘大磊 on 2017/5/24 11:07.
 * 窗体信息
 */
@Controller
@RequestMapping("/common/window")
public class CommonWindowController {
    @Autowired
    private WindowService windowService;

    @RequestMapping("/show/{windowId}")
    public ModelAndView getWindow(@PathVariable("windowId") Integer windowId, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("/common/window");
        String language=(String)session.getAttribute(WebConst.SESSION_LANGUAGE);
        WindowBo windowBo =  windowService.getWindowBo(windowId, language);
        modelAndView.addObject("windowBo", windowBo);
        return modelAndView;
    }

}
