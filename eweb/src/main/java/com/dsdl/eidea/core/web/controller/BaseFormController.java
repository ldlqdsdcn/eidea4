package com.dsdl.eidea.core.web.controller;

import com.dsdl.eidea.core.web.result.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 刘大磊 on 2017/4/28 14:09.
 * 一个模块的基类
 */
public abstract class BaseFormController<T> extends BaseController {
    /**
     * 导出报表
     * @param type
     * 导出范围只导出当前页
     */
    @RequestMapping("/export")
    public void export(String type)
    {

    }

    /**
     * 新建
     * @return
     */
    public JsonResult<T> create()
    {
        return null;
    }


}
