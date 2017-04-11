package com.dsdl.eidea.core.web.controller;

import com.dsdl.eidea.base.def.OperatorDef;
import com.dsdl.eidea.base.web.annotation.PrivilegesControl;
import com.dsdl.eidea.base.web.def.ReturnType;
import com.dsdl.eidea.base.web.vo.UserResource;
import com.dsdl.eidea.core.entity.bo.LanguageBo;
import com.dsdl.eidea.core.entity.bo.MessageBo;
import com.dsdl.eidea.core.entity.bo.MessageTrlBo;
import com.dsdl.eidea.core.service.LanguageService;
import com.dsdl.eidea.core.service.MessageService;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.ApiResult;
import com.dsdl.eidea.core.web.result.def.ErrorCodes;
import com.dsdl.eidea.core.web.util.SearchHelper;
import com.dsdl.eidea.core.web.vo.PagingSettingResult;
import com.dsdl.eidea.util.StringUtil;
import com.googlecode.genericdao.search.Search;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/12/16.
 */
@Controller
@RequestMapping("/core/message")
public class MessageController {
    private static final String URI = "core_message";
    @Autowired
    private MessageService messageService;
    @Autowired
    private LanguageService languageService;

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @PrivilegesControl(operator = OperatorDef.VIEW, returnType = ReturnType.JSP)
    @RequiresPermissions(value = "core:view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/core/message/message");
        modelAndView.addObject("pagingSettingResult", PagingSettingResult.getDefault());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "core:view")
    public ApiResult<List<MessageBo>> list(HttpSession session) {
        Search search = SearchHelper.getSearchParam(URI, session);
        List<MessageBo> messageBoList = messageService.findMessage(search);
        return ApiResult.success(messageBoList);
    }

    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.DELETE)
    @RequiresPermissions(value = "core:delete")
    public ApiResult<List<MessageBo>> deletes(@RequestBody String[] keys, HttpSession session) {
        UserResource resource=(UserResource)session.getAttribute(WebConst.SESSION_RESOURCE);
        if (keys == null || keys.length == 0) {
            return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("pagemenu.choose.information"));
        }
        messageService.deletes(keys);
        return list(session);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "core:view")
    public ApiResult<MessageBo> get(String key,HttpSession session) {
        UserResource resource=(UserResource)session.getAttribute(WebConst.SESSION_RESOURCE);
        MessageBo messageBo = null;
        if (StringUtil.isEmpty(key)) {
            return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("client.msg.primary_key_validation"));
        } else {
            messageBo = messageService.getMessageBo(key);
        }
        return ApiResult.success(messageBo);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.ADD)
    @RequiresPermissions(value = "core:add")
    public ApiResult<MessageBo> create() {
        MessageBo messageBo = new MessageBo();
        messageBo.setCreated(true);
        messageBo.setIsactive("N");
        List<MessageTrlBo> messageTrlBoLists = new ArrayList<>();
        List<LanguageBo> languageBoList = languageService.findLanguageListForActivated();
        languageBoList.forEach(e -> {
            MessageTrlBo messageTrlBo = new MessageTrlBo();
            messageTrlBo.setLang(e.getCode());
            messageTrlBoLists.add(messageTrlBo);
        });
        messageBo.setMessageTrlBoList(messageTrlBoLists);
        return ApiResult.success(messageBo);
    }

    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.ADD)
    @RequiresPermissions(value = "core:add")
    public ApiResult<MessageBo> saveForCreated(@RequestBody MessageBo messageBo,HttpSession session) {
        UserResource resource=(UserResource)session.getAttribute(WebConst.SESSION_RESOURCE);
        if (messageBo.isCreated()) {
            if (messageService.findExistMessage(messageBo.getKey())) {
                return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("client.msg.client_code"));
            }
        }

        messageService.save(messageBo);
        return get(messageBo.getKey(),session);
    }

    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    @PrivilegesControl(operator = OperatorDef.UPDATE)
    @RequiresPermissions(value = "core:update")
    public ApiResult<MessageBo> saveForUpdated(@RequestBody MessageBo messageBo,HttpSession session) {
        messageService.save(messageBo);
        return get(messageBo.getKey(),session);
    }

}
