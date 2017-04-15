package com.dsdl.eidea.core.web.controller;

import com.dsdl.eidea.base.entity.bo.OperatorBo;
import com.dsdl.eidea.base.service.OperatorService;
import com.dsdl.eidea.core.web.result.ApiResult;
import com.dsdl.eidea.core.web.result.def.ErrorCodes;
import com.dsdl.eidea.core.web.vo.PagingSettingResult;
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
import java.util.List;

/**
 * Created by admin on 2016/12/20.
 */
@Controller
@RequestMapping("/base/operator")
public class OperatorController {
    @Autowired
    private OperatorService operatorService;

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @RequiresPermissions(value = "operator:view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/base/operator/operator");
        modelAndView.addObject("pagingSettingResult", PagingSettingResult.getDefault());
        return modelAndView;
    }
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "operator:view")
    public ApiResult<List<OperatorBo>> list(HttpSession session) {
        List<OperatorBo> operatorBoList = operatorService.findOperator(new Search());
        return ApiResult.success(operatorBoList);
    }
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "operator:delete")
    public ApiResult<List<OperatorBo>> deletes(@RequestBody Integer[] ids, HttpSession session) {
        if (ids == null ) {
            return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), "请选择再删除");
        }
        operatorService.deleteOperatorById(ids);
        return list(session);
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = "operator:update")
    public ApiResult<OperatorBo> save(@RequestBody OperatorBo operatorBo) {
        List<OperatorBo> operatorBoList = operatorService.findOperator(new Search());
        for (OperatorBo bb:operatorBoList) {
          if(bb.getName().equals(operatorBo.getName())){
              return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(),"该操作名已存在");
          }

        }
        operatorService.save(operatorBo);
        return get(operatorBo.getId());
    }
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "operator:view")
    public ApiResult<OperatorBo> get(Integer id) {
        OperatorBo operatorBo = null;
        if (id==null) {
            return ApiResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(),"主键为空，信息失败");
        } else {
            operatorBo = operatorService.getOperatorBo(id);
        }
        return ApiResult.success(operatorBo);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions(value = "operator:add")
    public ApiResult<OperatorBo> create()
    {
        OperatorBo operatorBo=new OperatorBo();
        operatorBo.setCreated(true);
        operatorBo.setIsactive("N");
        return ApiResult.success(operatorBo);
    }

}
