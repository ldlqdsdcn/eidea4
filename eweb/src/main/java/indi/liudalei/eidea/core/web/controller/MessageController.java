package indi.liudalei.eidea.core.web.controller;

import indi.liudalei.eidea.base.web.vo.UserResource;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.entity.bo.LanguageBo;
import indi.liudalei.eidea.core.entity.bo.MessageBo;
import indi.liudalei.eidea.core.entity.bo.MessageTrlBo;
import indi.liudalei.eidea.core.params.DeleteParams;
import indi.liudalei.eidea.core.params.QueryParams;
import indi.liudalei.eidea.core.service.LanguageService;
import indi.liudalei.eidea.core.service.MessageService;
import indi.liudalei.eidea.core.web.def.WebConst;
import indi.liudalei.eidea.core.web.result.JsonResult;
import indi.liudalei.eidea.core.web.result.def.ErrorCodes;
import indi.liudalei.eidea.core.web.util.SearchHelper;
import indi.liudalei.eidea.core.web.vo.PagingSettingResult;
import indi.liudalei.eidea.util.StringUtil;
import com.googlecode.genericdao.search.Search;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
    @RequiresPermissions(value = "view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/core/message/message");
        modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDbPaging());
        modelAndView.addObject(WebConst.PAGE_URI, URI);
        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<PaginationResult<MessageBo>> list(HttpSession session, @RequestBody QueryParams queryParams) {
        Search search = SearchHelper.getSearchParam(URI, session);
        PaginationResult<MessageBo> messageBoList = messageService.findMessage(search,queryParams);
        return JsonResult.success(messageBoList);
    }

    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "delete")
    public JsonResult<PaginationResult<MessageBo>> deletes(@RequestBody DeleteParams<String> deleteParams, HttpSession session) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (deleteParams.getIds() == null || deleteParams.getIds().length == 0) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("pagemenu.choose.information"));
        }
        messageService.deletes(deleteParams.getIds());
        return list(session,deleteParams.getQueryParams());
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "view")
    public JsonResult<MessageBo> get(String key, HttpSession session) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        MessageBo messageBo = null;
        if (StringUtil.isEmpty(key)) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("client.msg.primary_key_validation"));
        } else {
            messageBo = messageService.getMessageBo(key);
        }
        return JsonResult.success(messageBo);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "add")
    public JsonResult<MessageBo> create() {
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
        return JsonResult.success(messageBo);
    }

    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "add")
    public JsonResult<MessageBo> saveForCreated(@RequestBody MessageBo messageBo, HttpSession session) {
        UserResource resource = (UserResource) session.getAttribute(WebConst.SESSION_RESOURCE);
        if (messageBo.isCreated()) {
            if (messageService.findExistMessage(messageBo.getKey())) {
                return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), resource.getMessage("client.msg.key_exists"));
            }
        }

        messageService.save(messageBo);
        return get(messageBo.getKey(), session);
    }

    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "update")
    public JsonResult<MessageBo> saveForUpdated(@RequestBody MessageBo messageBo, HttpSession session) {
        messageService.save(messageBo);
        return get(messageBo.getKey(), session);
    }

}
