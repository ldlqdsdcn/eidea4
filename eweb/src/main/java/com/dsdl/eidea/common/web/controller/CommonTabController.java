package com.dsdl.eidea.common.web.controller;

import com.dsdl.eidea.base.entity.bo.FieldInListPageBo;
import com.dsdl.eidea.base.service.FieldService;
import com.dsdl.eidea.base.service.TabService;
import com.dsdl.eidea.core.params.QueryParams;
import com.dsdl.eidea.core.web.def.WebConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by 刘大磊 on 2017/5/24 11:08.
 */
@Controller
@RequestMapping("/common/tab")
public class CommonTabController {
    @Autowired
    private TabService tabService;
    @Autowired
    private FieldService fieldService;

    /**
     * 显示tab列表界面
     *
     * @param tabId
     * @return
     */
    @RequestMapping("/showList/{tabId}")
    public ModelAndView showListPage(@PathVariable("tabId") Integer tabId,HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("/common/edit/list");
        String lang=(String)session.getAttribute(WebConst.SESSION_LANGUAGE);
        List<FieldInListPageBo> fieldInListPageBoList=fieldService.getListPageFiledList(tabId,lang);
        modelAndView.addObject("fieldInListPageBoList",fieldInListPageBoList);
        return modelAndView;
    }

    @RequestMapping("/list/{tabId}")
    @ResponseBody
    public List<Map<String, String>> list(@PathVariable("tabId") Integer tabId, @RequestBody QueryParams queryParams) {
        List<Map<String, String>> list = fieldService.getDataList(tabId, queryParams.getFirstResult(), queryParams.getPageSize());
        return list;
    }

    /**
     * 显示tab编辑界面
     *
     * @param tabId
     * @return
     */
    @RequestMapping("/showForm/{tabId}")
    public ModelAndView showFormPage(@PathVariable("tabId") Integer tabId) {
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }
}

