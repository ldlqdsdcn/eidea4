package com.dsdl.eidea.test.web.controller;

import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.JsonResult;
import com.dsdl.eidea.core.web.vo.PagingSettingResult;
import com.dsdl.eidea.test.entity.po.LeavePo;
import com.dsdl.eidea.test.service.LeaveService;
import com.dsdl.eidea.test.web.vo.LeaveVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘大磊 on 2017/5/18 13:17.
 */
@Controller
@RequestMapping("/test/leaveProcess")
public class LeaveProcessController {
    private ModelMapper modelMapper = new ModelMapper();

    @Autowired
    private LeaveService leaveService;

    @RequestMapping("/showList")
    @RequiresPermissions("view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/test/leaveProcess/leaveProcess");
        modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDefault());
        return modelAndView;
    }

    @RequestMapping("/list")
    @ResponseBody
    @RequiresPermissions("view")
    public JsonResult<List<LeaveVo>> list() {
        List<LeavePo> leavePoList = leaveService.getRunningProcessInstances();
        List<LeaveVo> leaveVoList = new ArrayList<>();
        leavePoList.forEach(e -> {
            LeaveVo leaveVo = modelMapper.map(e, LeaveVo.class);
            leaveVo.setProcessDefinitionId(e.getProcessInstance().getProcessDefinitionId());
            leaveVo.setTaskCreateTime(e.getTask().getCreateTime());
            leaveVo.setVersion(e.getProcessDefinition().getVersion());
            leaveVo.setTaskName(e.getTask().getName());
            leaveVo.setSuspended(e.getTask().isSuspended());
            leaveVo.setAssignee(e.getTask().getAssignee());
            leaveVoList.add(leaveVo);
        });
        return JsonResult.success(leaveVoList);
    }
}
