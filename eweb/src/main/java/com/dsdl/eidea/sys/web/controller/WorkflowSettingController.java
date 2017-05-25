package com.dsdl.eidea.sys.web.controller;

import com.dsdl.eidea.base.service.FileSettingService;
import com.dsdl.eidea.base.web.vo.UserResource;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.JsonResult;
import com.dsdl.eidea.core.web.vo.PagingSettingResult;
import com.dsdl.eidea.sys.web.vo.ProcessDefinitionVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.io.FilenameUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

/**
 * Created by Bobo on 2017/5/12.
 */
@Controller
@RequestMapping("/sys/workflow")
@Slf4j
public class WorkflowSettingController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;

    @RequestMapping(value = "/showList", method = RequestMethod.GET)
    @RequiresPermissions("view")
    public ModelAndView showList() {
        ModelAndView modelAndView = new ModelAndView("/sys/workflow/workflow");
        modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDefault());
        return modelAndView;
    }

    /**
     * 查看已部署的工作流程
     *
     * @return
     */
    @RequiresPermissions("view")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<List<ProcessDefinitionVo>> list() {
    /*
     * 保存两个对象，一个是ProcessDefinition（流程定义），一个是Deployment（流程部署）
     */
        List<ProcessDefinitionVo> processDefinitionList=getProcessDefinitionList();
        return JsonResult.success(processDefinitionList);
    }

    public List<ProcessDefinitionVo> getProcessDefinitionList(){
        List<ProcessDefinitionVo> processDefinitionVoList = new ArrayList<>();
        ProcessDefinitionQuery processDefinitionQuery = repositoryService.createProcessDefinitionQuery().orderByDeploymentId().desc();
        List<ProcessDefinition> processDefinitionList = processDefinitionQuery.list();
        for (ProcessDefinition processDefinition : processDefinitionList) {
            String deploymentId = processDefinition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            ProcessDefinitionVo processDefinitionVo = new ProcessDefinitionVo();
            processDefinitionVo.setId(processDefinition.getId());
            processDefinitionVo.setKey(processDefinition.getKey());
            processDefinitionVo.setName(processDefinition.getName());
            processDefinitionVo.setDeploymentId(processDefinition.getDeploymentId());
            processDefinitionVo.setDeploymentTime(deployment.getDeploymentTime());
            processDefinitionVo.setResourceName(processDefinition.getResourceName());
            processDefinitionVo.setSuspended(processDefinition.isSuspended());
            processDefinitionVo.setVersion(processDefinition.getVersion());

            processDefinitionVoList.add(processDefinitionVo);
        }
        return processDefinitionVoList;
    }

    @RequiresPermissions("delete")
    @RequestMapping(value = "/deletes", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<List<ProcessDefinitionVo>> deletes(@RequestBody String[] ids)
    {
        for(String id:ids)
        {
            repositoryService.deleteDeployment(id, true);
        }
        return list();
    }
    /**
     * 部署工作流到服务器
     * @param file
     * @return
     */
    @RequiresPermissions("add")
    @RequestMapping(value = "/deploy", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult<Void> deploy(MultipartFile file) {

        String fileName = file.getOriginalFilename();
        try {
            InputStream fileInputStream = file.getInputStream();
            Deployment deployment = null;

            String extension = FilenameUtils.getExtension(fileName);
            if (extension.equals("zip") || extension.equals("bar")) {
                ZipInputStream zip = new ZipInputStream(fileInputStream);
                deployment = repositoryService.createDeployment().addZipInputStream(zip).deploy();
            } else {
                deployment = repositoryService.createDeployment().addInputStream(fileName, fileInputStream).deploy();
            }
            repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
        } catch (Exception e) {
            log.error("error on deploy process, because of file input stream", e);
        }
        return JsonResult.success(null);
    }
    /**
     * 读取资源，通过部署ID
     *
     * @param processDefinitionId 流程定义
     * @param resourceType        资源类型(xml|image)
     * @throws Exception
     */
    @RequestMapping(value = "/resource/read")
    public void loadByDeployment(@RequestParam("processDefinitionId") String processDefinitionId, @RequestParam("resourceType") String resourceType,
                                 HttpServletResponse response) throws Exception {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        String resourceName = "";
        if (resourceType.equals("image")) {
            resourceName = processDefinition.getDiagramResourceName();
        } else if (resourceType.equals("xml")) {
            resourceName = processDefinition.getResourceName();
            response.reset();
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment; filename=" + new String(resourceName.getBytes("utf-8"),"iso8859-1"));
        }
        InputStream resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }

    /**
     * 读取资源，通过流程ID
     *
     * @param resourceType      资源类型(xml|image)
     * @param processInstanceId 流程实例ID
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/resource/process-instance")
    public void loadByProcessInstance(@RequestParam("type") String resourceType, @RequestParam("pid") String processInstanceId, HttpServletResponse response)
            throws Exception {
        InputStream resourceAsStream = null;
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processInstance.getProcessDefinitionId())
                .singleResult();

        String resourceName = "";
        if (resourceType.equals("image")) {
            resourceName = processDefinition.getDiagramResourceName();
        } else if (resourceType.equals("xml")) {
            resourceName = processDefinition.getResourceName();
        }
        resourceAsStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(), resourceName);
        byte[] b = new byte[1024];
        int len = -1;
        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
            response.getOutputStream().write(b, 0, len);
        }
    }
    /**
     * 挂起、激活流程实例
     */
    @RequestMapping(value = "/update/{state}/{processDefinitionId}",method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<Map<String,Object>> updateState(@PathVariable("state") String state, @PathVariable("processDefinitionId") String processDefinitionId) {
        HashMap<String,Object> resultMap=new HashMap<>();
        UserResource resource = (UserResource) request.getSession().getAttribute(WebConst.SESSION_RESOURCE);
        if (state.equals("active")) {
            resultMap.put("message", resource.getMessage("workflow.active.ID.as")+"[" + processDefinitionId + "]"+resource.getMessage("workflow.active.process.definition"));
            repositoryService.activateProcessDefinitionById(processDefinitionId, true, null);
        } else if (state.equals("suspend")) {
            repositoryService.suspendProcessDefinitionById(processDefinitionId, true, null);
            resultMap.put("message", resource.getMessage("workflow.suspend.ID.as")+"[" + processDefinitionId + "]"+resource.getMessage("workflow.active.process.definition"));
        }
        List<ProcessDefinitionVo> processDefinitionList=getProcessDefinitionList();
        resultMap.put("processDefinitionList",processDefinitionList);
        return JsonResult.success(resultMap);
    }

    /**
     * 转换成model
     * @param processDefinitionId
     * @return
     * @throws UnsupportedEncodingException
     * @throws XMLStreamException
     */
    @RequestMapping(value = "/process/convert-to-model/{processDefinitionId}",method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<Map<String,Object>> convertToModel(@PathVariable("processDefinitionId") String processDefinitionId)
            throws UnsupportedEncodingException, XMLStreamException {
        HashMap<String,Object> resultMap=new HashMap<>();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(processDefinitionId).singleResult();
        InputStream bpmnStream = repositoryService.getResourceAsStream(processDefinition.getDeploymentId(),
                processDefinition.getResourceName());
        XMLInputFactory xif = XMLInputFactory.newInstance();
        InputStreamReader in = new InputStreamReader(bpmnStream, "UTF-8");
        XMLStreamReader xtr = xif.createXMLStreamReader(in);
        BpmnModel bpmnModel = new BpmnXMLConverter().convertToBpmnModel(xtr);

        BpmnJsonConverter converter = new BpmnJsonConverter();
        com.fasterxml.jackson.databind.node.ObjectNode modelNode = converter.convertToJson(bpmnModel);
        Model modelData = repositoryService.newModel();
        modelData.setKey(processDefinition.getKey());
        modelData.setName(processDefinition.getResourceName());
        modelData.setCategory(processDefinition.getDeploymentId());

        ObjectNode modelObjectNode = new ObjectMapper().createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, processDefinition.getName());
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, processDefinition.getDescription());
        modelData.setMetaInfo(modelObjectNode.toString());

        repositoryService.saveModel(modelData);

        repositoryService.addModelEditorSource(modelData.getId(), modelNode.toString().getBytes("utf-8"));
        UserResource resource = (UserResource) request.getSession().getAttribute(WebConst.SESSION_RESOURCE);
        resultMap.put("message", resource.getMessage("workflow.convert.model.success"));
        return JsonResult.success(resultMap);
    }
}
