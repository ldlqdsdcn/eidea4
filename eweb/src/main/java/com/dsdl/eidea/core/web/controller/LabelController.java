package com.dsdl.eidea.core.web.controller;

import com.dsdl.eidea.base.web.vo.UserResource;
import com.dsdl.eidea.core.entity.bo.LabelBo;
import com.dsdl.eidea.core.entity.bo.LabelTrlBo;
import com.dsdl.eidea.core.entity.bo.LanguageBo;
import com.dsdl.eidea.core.service.LabelService;
import com.dsdl.eidea.core.service.LanguageService;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.ApiResult;
import com.dsdl.eidea.core.web.result.def.ErrorCodes;
import com.dsdl.eidea.core.web.util.SearchHelper;
import com.dsdl.eidea.core.web.vo.PagingSettingResult;
import com.googlecode.genericdao.search.Search;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/core/label")
public class LabelController {
    private Logger logger = LoggerFactory.getLogger(LabelController.class);
    private static final String URI = "core_label";
    @Autowired
    private LabelService labelService;
    @Autowired
    private LanguageService languageService;

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @RequiresPermissions("view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/core/label/label");
        modelAndView.addObject("pagingSettingResult", PagingSettingResult.getDefault());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public ApiResult<List<LabelBo>> list(HttpSession session) {
        Search search = SearchHelper.getSearchParam(URI, session);
        List<LabelBo> labelBoList = labelService.getLabelList(search);
        return ApiResult.success(labelBoList);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "add")
    public ApiResult<LabelBo> create() {
        LabelBo languageBo = new LabelBo();
        languageBo.setCreated(true);
        languageBo.setIsactive("N");
        List<LabelTrlBo> languageTrlBoList = new ArrayList<>();
        List<LanguageBo> languageBoList = languageService.findLanguageListForActivated();
        languageBoList.forEach(e -> {
            LabelTrlBo languageTrlBo = new LabelTrlBo();
            languageTrlBo.setLang(e.getCode());
            languageTrlBoList.add(languageTrlBo);
        });
        languageBo.setLabelTrlBoList(languageTrlBoList);
        return ApiResult.success(languageBo);

    }

    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "add")
    public ApiResult<LabelBo> saveForCreated(@RequestBody LabelBo labelBo, HttpSession session) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (labelBo.getKey() == null) {
            if (labelService.findExistClient(labelBo.getKey())) {
                return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("client.msg.client_code"));
            }
        }
        labelService.save(labelBo);
        return get(labelBo.getKey(), session);
    }

    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "update")
    public ApiResult<LabelBo> saveForUpdated(@RequestBody LabelBo labelBo, HttpSession session) {
        labelService.save(labelBo);
        return get(labelBo.getKey(), session);
    }

    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "core:delete")
    public ApiResult<List<LabelBo>> deletes(@RequestBody String[] codes, HttpSession session) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (codes == null || codes.length == 0) {
            return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("pagemenu.choose.information"));
        }
        labelService.deletes(codes);
        return list(session);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public ApiResult<LabelBo> get(String key, HttpSession session) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        LabelBo labelBo = null;
        if (key == null) {
            return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("client.msg.primary_key_validation"));
        } else {
            labelBo = labelService.getLabelBo(key);
        }
        return ApiResult.success(labelBo);
    }
}
