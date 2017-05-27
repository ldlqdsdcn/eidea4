package com.dsdl.eidea.base.web.controller;

import com.dsdl.eidea.base.service.WindowService;
import com.dsdl.eidea.core.web.result.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by 刘大磊 on 2017/5/3 9:23.
 * 公共编辑类
 * 增 删 改 查 导入 导出 基本命令
 */
@Controller
@RequestMapping("/common/edit")
public final class CommonEditController {
    @Autowired
    private WindowService windowService;

    /**
     * 根据窗体id显示窗体信息
     * @param windowId
     * @return
     */
    @RequestMapping("/showList/{windowId}")
    public ModelAndView showList(@PathVariable("windowId") Integer windowId)
    {
        ModelAndView modelAndView=new ModelAndView("general/list");

        return modelAndView;
    }

    public JsonResult list()
    {
        return null;
    }
}
