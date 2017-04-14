package com.dsdl.eidea.core.web.controller;

import com.dsdl.eidea.base.entity.bo.OperatorBo;
import com.dsdl.eidea.base.service.OperatorService;
import com.dsdl.eidea.core.web.result.JsonResult;
import com.dsdl.eidea.core.web.result.def.ErrorCodes;
import com.dsdl.eidea.core.web.vo.PagingSettingResult;
import com.googlecode.genericdao.search.Search;
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
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/base/operator/operator");
        modelAndView.addObject("pagingSettingResult", PagingSettingResult.getDefault());
        return modelAndView;
    }
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<List<OperatorBo>> list(HttpSession session) {
        List<OperatorBo> operatorBoList = operatorService.findOperator(new Search());
        return JsonResult.success(operatorBoList);
    }
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<List<OperatorBo>> deletes(@RequestBody Integer[] ids, HttpSession session) {
        if (ids == null ) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(), "请选择再删除");
        }
        operatorService.deleteOperatorById(ids);
        return list(session);
    }
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<OperatorBo> save(@RequestBody OperatorBo operatorBo) {
        List<OperatorBo> operatorBoList = operatorService.findOperator(new Search());
        for (OperatorBo bb:operatorBoList) {
          if(bb.getName().equals(operatorBo.getName())){
              return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(),"该操作名已存在");
          }

        }
        operatorService.save(operatorBo);
        return get(operatorBo.getId());
    }
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<OperatorBo> get(Integer id) {
        OperatorBo operatorBo = null;
        if (id==null) {
            return JsonResult.fail(ErrorCodes.BUSINESS_EXCEPTION.getCode(),"主键为空，信息失败");
        } else {
            operatorBo = operatorService.getOperatorBo(id);
        }
        return JsonResult.success(operatorBo);
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<OperatorBo> create()
    {
        OperatorBo operatorBo=new OperatorBo();
        operatorBo.setCreated(true);
        operatorBo.setIsactive("N");
        return JsonResult.success(operatorBo);
    }

}
