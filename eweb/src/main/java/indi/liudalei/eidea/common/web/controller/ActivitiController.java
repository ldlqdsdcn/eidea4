package indi.liudalei.eidea.common.web.controller;

import indi.liudalei.eidea.base.service.WorkflowTraceService;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.image.ProcessDiagramGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by 刘大磊 on 2017/5/18 15:17.
 */
@Controller
@RequestMapping("/common/activiti")
public class ActivitiController {
    @Autowired
    private WorkflowTraceService traceService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private ProcessEngineConfiguration processEngineConfiguration;
    @Autowired
    private ProcessEngine processEngine;

    @RequestMapping(value = "/show/trace/{executionId}")
    public ModelAndView showTrace(@PathVariable("executionId") String executionId) {
        ModelAndView modelAndView = new ModelAndView("/common/workflow_process");
        modelAndView.addObject("executionId", executionId);
        return modelAndView;
    }


    /**
     * 输出跟踪流程信息
     *
     * @param processInstanceId
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/process/trace")
    @ResponseBody
    public List<Map<String, Object>> traceProcess(@RequestParam("pid") String processInstanceId) throws Exception {
        List<Map<String, Object>> activityInfos = traceService.traceProcess(processInstanceId);
        return activityInfos;
    }

    /**
     * 读取带跟踪的图片
     */
    @RequestMapping(value = "/process/trace/auto/{executionId}")
    public void readResource(@PathVariable("executionId") String executionId, HttpServletResponse response)
            throws Exception {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(executionId).singleResult();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        List<String> activeActivityIds = runtimeService.getActiveActivityIds(executionId);
        // 不使用spring请使用下面的两行代码
//    ProcessEngineImpl defaultProcessEngine = (ProcessEngineImpl) ProcessEngines.getDefaultProcessEngine();
//    Context.setProcessEngineConfiguration(defaultProcessEngine.getProcessEngineConfiguration());

        // 使用spring注入引擎请使用下面的这行代码
        processEngineConfiguration = processEngine.getProcessEngineConfiguration();
        Context.setProcessEngineConfiguration((ProcessEngineConfigurationImpl) processEngineConfiguration);

        ProcessDiagramGenerator diagramGenerator = processEngineConfiguration.getProcessDiagramGenerator();
        //InputStream imageStream = diagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds);
        String font=processEngine.getProcessEngineConfiguration().getActivityFontName();
        InputStream imageStream =diagramGenerator.generateDiagram(bpmnModel, "png", activeActivityIds, Collections.<String>emptyList(), font, font, font, null, 1.0);
       /* InputStream imageStream = diagramGenerator.generateDiagram(
                bpmnModel, "png",
                activeActivityIds, Collections.<String>emptyList(),
                processEngine.getProcessEngineConfiguration().getActivityFontName(),
                processEngine.getProcessEngineConfiguration().getLabelFontName(),
                null, 1.0);*/
        // 输出资源内容到相应对象
        byte[] b = new byte[1024];
        int len;
        while ((len = imageStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }
}
