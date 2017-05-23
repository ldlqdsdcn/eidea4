package com.dsdl.eidea.test.web.controller;

import com.dsdl.eidea.base.entity.bo.UserBo;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.JsonResult;
import com.dsdl.eidea.core.web.vo.PagingSettingResult;
import com.dsdl.eidea.test.entity.po.LeavePo;
import com.dsdl.eidea.test.service.LeaveService;
import com.dsdl.eidea.test.web.vo.LeaveVo;
import org.activiti.engine.TaskService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 刘大磊 on 2017/5/18 13:17.
 */
@Controller
@RequestMapping("/test/leaveProcess")
public class LeaveProcessController {
    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private TaskService taskService;
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
        List<LeaveVo> leaveVoList = convertPoListToBoList(leavePoList);
        return JsonResult.success(leaveVoList);
    }

    @RequestMapping("/showTodoList")
    @RequiresPermissions("view")
    public ModelAndView showTodoList() {
        ModelAndView modelAndView = new ModelAndView("/test/leaveProcess/todoList");
        modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDefault());
        return modelAndView;
    }

    @RequestMapping("/todoList")
    @ResponseBody
    @RequiresPermissions("view")
    public JsonResult<List<LeaveVo>> getTodoList(HttpServletRequest request) {
        UserBo userBo = (UserBo) request.getSession().getAttribute(WebConst.SESSION_LOGINUSER);
        List<LeavePo> leavePoList = leaveService.getTodoLeaveList(userBo.getUsername());
        List<LeaveVo> leaveVoList = convertPoListToBoList(leavePoList);
        return JsonResult.success(leaveVoList);
    }

    /**
     * 签收任务
     */
    @RequestMapping(value = "/taskClaim/{id}")
    @ResponseBody
    @RequiresPermissions("view")
    public JsonResult claim(@PathVariable("id") String taskId, HttpSession session) {
        UserBo userBo = (UserBo) session.getAttribute(WebConst.SESSION_LOGINUSER);
        String userId = userBo.getUsername();
        taskService.claim(taskId, userId);
        return JsonResult.success("任务已签收");
    }

    /**
     * 完成任务
     *
     * @param taskId
     * @return
     */
    @RequestMapping(value = "complete/{id}/{deptLeaderPass}", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public JsonResult complete(@PathVariable("id") String taskId,@PathVariable("deptLeaderPass")boolean deptLeaderPass) {
        Map<String, Object> variables = new HashMap<>();
        variables.put("deptLeaderPass", deptLeaderPass);
        try {
            taskService.complete(taskId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return JsonResult.success("完成任务");
    }

    private List<LeaveVo> convertPoListToBoList(List<LeavePo> leavePoList) {

        List<LeaveVo> leaveVoList = new ArrayList<>();
        leavePoList.forEach(e -> {
            LeaveVo leaveVo = modelMapper.map(e, LeaveVo.class);
            leaveVo.setProcessDefinitionId(e.getProcessInstance().getProcessDefinitionId());
            leaveVo.setTaskCreateTime(e.getTask().getCreateTime());
            leaveVo.setVersion(e.getProcessDefinition().getVersion());
            leaveVo.setTaskName(e.getTask().getName());
            leaveVo.setSuspended(e.getTask().isSuspended());
            leaveVo.setAssignee(e.getTask().getAssignee());
            leaveVo.setTaskId(e.getTask().getId());
            leaveVoList.add(leaveVo);
        });
        return leaveVoList;
    }
}
