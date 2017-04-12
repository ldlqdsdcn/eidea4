package com.dsdl.eidea.base.web.controller;

import com.dsdl.eidea.base.def.ActivateDef;
import com.dsdl.eidea.base.def.OperatorDef;
import com.dsdl.eidea.base.entity.bo.ClientBo;
import com.dsdl.eidea.base.service.ClientService;
import com.dsdl.eidea.base.web.annotation.PrivilegesControl;
import com.dsdl.eidea.base.web.def.ReturnType;
import com.dsdl.eidea.core.web.controller.BaseController;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.ApiResult;
import com.dsdl.eidea.core.web.result.def.ErrorCodes;
import com.dsdl.eidea.core.web.util.SearchHelper;
import com.dsdl.eidea.core.web.vo.PagingSettingResult;
import com.googlecode.genericdao.search.Search;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/13 10:50.
 */
@Controller
@RequestMapping("/base/client")
public class ClientController extends BaseController {
    private static final String URI = "sys_client";
    @Autowired
    private ClientService clientService;
    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @RequiresPermissions("client:view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/base/client/client");
        modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDefault());
        modelAndView.addObject(WebConst.PAGE_URI, URI);

        return modelAndView;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("client:view")
    public ApiResult<List<ClientBo>> list(HttpSession session) {
        Search search = SearchHelper.getSearchParam(URI, session);
        List<ClientBo> clientBoList = clientService.getClientList(search);
        return ApiResult.success(clientBoList);
    }

    @RequiresPermissions("client:view")
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult<ClientBo> get(Integer id) {
        ClientBo clientBo = null;
        if (id == null) {
            return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(),getMessage("common.errror.get_object",getLabel("client.title")));
        } else {
            clientBo = clientService.getClientBo(id);
        }
        return ApiResult.success(clientBo);
    }

    @RequiresPermissions("client:add")
    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public ApiResult<ClientBo> create() {
        ClientBo clientBo = new ClientBo();
        clientBo.setIsactive(ActivateDef.INACTIVATED.getKey());
        return ApiResult.success(clientBo);
    }

    /**
     * @param clientBo
     * @return
     */
    @RequiresPermissions("client:add")
    @RequestMapping(value = "/saveForCreated", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult<ClientBo> saveForCreate(@Validated @RequestBody ClientBo clientBo) {
       if (clientService.findExistClient(clientBo.getNo())) {
            return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), getMessage("client.msg.client_code_exists"));
        }
        clientService.save(clientBo);
        return get(clientBo.getId());
    }

    @RequiresPermissions("client:update")
    @RequestMapping(value = "/saveForUpdated", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult<ClientBo> saveForUpdate(@Validated @RequestBody ClientBo clientBo) {

        if(clientBo.getId() == null){
            return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), getMessage("common.primary_key.isempty"));
        }
        clientService.save(clientBo);
        return get(clientBo.getId());
    }

    @RequiresPermissions("client:delete")
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    public ApiResult<List<ClientBo>> deletes(@RequestBody Integer[] ids, HttpSession session) {
        if (ids == null || ids.length == 0) {
            return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), getMessage("client.msg.select_delete"));
        }
        clientService.deletes(ids);
        return list(session);
    }
}
