package com.dsdl.eidea.sys.web.controller;

import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.core.web.result.JsonResult;
import com.dsdl.eidea.core.web.vo.PagingSettingResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.json.Json;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

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
            response.sendRedirect(request.getContextPath()+"/sys/workFlow/modeler.html?modelId="+modelData.getId());
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
}
