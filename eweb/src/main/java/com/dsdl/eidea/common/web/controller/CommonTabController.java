package com.dsdl.eidea.common.web.controller;

import com.dsdl.eidea.base.entity.bo.FieldBo;
import com.dsdl.eidea.base.entity.bo.FieldInListPageBo;
import com.dsdl.eidea.base.service.FieldService;
import com.dsdl.eidea.base.service.TabService;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.JsonResult;
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
    public ModelAndView showListPage(@PathVariable("tabId") Integer tabId, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("/common/edit/list");
        String lang = (String) session.getAttribute(WebConst.SESSION_LANGUAGE);
        List<FieldInListPageBo> fieldInListPageBoList = fieldService.getListPageFiledList(tabId, lang);
        modelAndView.addObject("tabId", tabId);
        modelAndView.addObject("fieldInListPageBoList", fieldInListPageBoList);
        return modelAndView;
    }

    @RequestMapping("/list/{tabId}")
    @ResponseBody
    public JsonResult<PaginationResult<Map<String, String>>> list(@PathVariable("tabId") Integer tabId, @RequestBody QueryParams queryParams) {
        PaginationResult list = fieldService.getDataList(tabId, queryParams);
        return JsonResult.success(list);
    }

    /**
     * 显示tab编辑界面
     *
     * @param tabId
     * @return
     */
    @RequestMapping("/showForm/{tabId}")
    public ModelAndView showFormPage(@PathVariable("tabId") Integer tabId,HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("/common/edit/edit");
        String lang = (String) session.getAttribute(WebConst.SESSION_LANGUAGE);
        fieldService.getFormPageFieldList(tabId,lang);
        return modelAndView;
    }
    @RequestMapping("/get/{tabId}/{recordId}")
    public JsonResult<List<FieldBo>>  edit(@PathVariable("tabId") Integer tabId,@PathVariable("recordId") Integer recordId)
    {

        return null;
    }
}

