package com.dsdl.eidea.sys.web.controller;

import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.JsonResult;
import com.dsdl.eidea.core.web.vo.PagingSettingResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * Created by 刘大磊 on 2017/5/15 8:59.
 */
@Controller
@Slf4j
@RequestMapping("/sys/model")
public class WorkflowModelController {
    @Autowired
    private RepositoryService repositoryService;
    @RequestMapping("showList")
    @RequiresPermissions("view")
    public ModelAndView showList()
    {
        ModelAndView modelAndView = new ModelAndView("/sys/workflow/model");
        modelAndView.addObject(WebConst.PAGING_SETTINGS, PagingSettingResult.getDefault());
        return modelAndView;
    }

    @RequestMapping("/create")
    public void create(@RequestParam("name")String name, @RequestParam("key")String key,
                       @RequestParam("description")String description, HttpServletRequest request, HttpServletResponse response)
    {
        ObjectMapper objectMapper=new ObjectMapper();
        ObjectNode editorNode=objectMapper.createObjectNode();
        editorNode.put("id","canvas");
        editorNode.put("resourceId","canvas");
        ObjectNode stencilSetNode=objectMapper.createObjectNode();
        stencilSetNode.put("namespace","http://b3mn.org/stencilset/bpmn2.0#");
        editorNode.put("stencilset",stencilSetNode);
        Model modelData=repositoryService.newModel();
        ObjectNode modelObjectNode=objectMapper.createObjectNode();
        modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME,name);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION,1);
        description= StringUtils.defaultString(description);
        modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION,description);
        modelData.setMetaInfo(modelObjectNode.toString());
        modelData.setName(name);
        modelData.setKey(StringUtils.defaultString(key));
        repositoryService.saveModel(modelData);
        try
        {
            repositoryService.addModelEditorSource(modelData.getId(),editorNode.toString().getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("读取文件信息错误",e);
        }


        try {
            response.sendRedirect(request.getContextPath()+"/sys/workflow/modeler.jsp?modelId="+modelData.getId());
        } catch (IOException e) {
            log.error("页面跳转出错",e);
        }


    }
    /**
     * 模型列表
     * 查看已经部署的模型
     */
    @RequestMapping(value = "/list")
    @ResponseBody
    @RequiresPermissions("view")
    public JsonResult<List<Model>> modelList() {
        List<Model> list = repositoryService.createModelQuery().list();
                return JsonResult.success(list);
    }

    /**
     * 部署工作流到服务器
     * @param modelId
     * @return
     */
    @RequiresPermissions("add")
    @RequestMapping(value = "/deploy/{modelId}", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult<Void> deploy(@PathVariable("modelId") String modelId) {
        Model modelData = repositoryService.getModel(modelId);
        BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
        byte[] modelEditorSource = repositoryService.getModelEditorSource(modelData.getId());

        JsonNode editorNode = null;
        try {
            editorNode = new ObjectMapper().readTree(modelEditorSource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
        BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
        byte[] exportBytes = xmlConverter.convertToXML(bpmnModel);
        ByteArrayInputStream in = new ByteArrayInputStream(exportBytes);
      Deployment deployment= repositoryService.createDeployment().addInputStream(modelData.getName(),in).deploy();
      List list=  repositoryService.createProcessDefinitionQuery().deploymentId(deployment.getId()).list();
        return JsonResult.success(null);
    }

    /**
     * 导出model对象为指定类型
     *
     * @param modelId 模型ID
     * @param type    导出文件类型(bpmn\json)
     */
    @RequestMapping(value = "/export/{modelId}/{type}")
    @RequiresPermissions("view")
    public void export(@PathVariable("modelId") String modelId,
                       @PathVariable("type") String type,
                       HttpServletResponse response) {
        try {
            Model modelData = repositoryService.getModel(modelId);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            byte[] modelEditorSource = repositoryService.getModelEditorSource(modelData.getId());

            JsonNode editorNode = new ObjectMapper().readTree(modelEditorSource);
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);

            // 处理异常
            if (bpmnModel.getMainProcess() == null) {
                response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
                response.getOutputStream().println("no main process, can't export for type: " + type);
                response.flushBuffer();
                return;
            }

            String filename = "";
            byte[] exportBytes = null;

            String mainProcessId = bpmnModel.getMainProcess().getId();

            if (type.equals("bpmn")) {

                BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
                exportBytes = xmlConverter.convertToXML(bpmnModel);

                filename = mainProcessId + ".bpmn20.xml";
            } else if (type.equals("json")) {

                exportBytes = modelEditorSource;
                filename = mainProcessId + ".json";

            }

            ByteArrayInputStream in = new ByteArrayInputStream(exportBytes);
            IOUtils.copy(in, response.getOutputStream());

            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.flushBuffer();
        } catch (Exception e) {
            log.error("导出model的xml文件失败：modelId={}, type={}", modelId, type, e);
        }
    }
    @RequestMapping(value = "/delete/{modelId}")
    @ResponseBody
    @RequiresPermissions("delete")
    public JsonResult<List<Model>> delete(@PathVariable("modelId") String modelId) {
        repositoryService.deleteModel(modelId);
        return modelList();
    }

}
