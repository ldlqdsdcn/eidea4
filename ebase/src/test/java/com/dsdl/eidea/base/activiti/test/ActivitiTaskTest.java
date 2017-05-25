package com.dsdl.eidea.base.activiti.test;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 刘大磊 on 2017/5/18 17:45.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ActivitiTaskTest {
    @Autowired
    RuntimeService runtimeService ;
    @Autowired
    private TaskService taskService;
    @Autowired
    private RepositoryService repositoryService;

    @Test
    public void testGetTask()
    {
        repositoryService.createDeployment().addClasspathResource("diagrams/leave.bpmn").name("leave").category("leave").deploy();
        Map<String, Object> variables = new HashMap<>();
        variables.put("username","liqun");
        ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("leave","14",variables);
        System.out.println(processInstance.getProcessInstanceId());
        List<Task> taskList=taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().list();
        System.out.println(taskList.size());
    }
    @Test
    public void testGetTaskById()
    {
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().processDefinitionKey("leave").active().orderByProcessInstanceId().desc();
        List<ProcessInstance> list = query.list();
        for(ProcessInstance processInstance:list)
        {
            // 设置当前任务信息
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
            if(task==null)
            {
                System.out.println("任务为空");
            }
            else
            {
                System.out.println(task.getName());
            }
        }
    }
    @Test
    public void testDeployWorkflow()
    {
        repositoryService.createDeployment().addClasspathResource("activiti/purchase.bpmn").name("采购审批").category("采购").deploy();
        Map<String, Object> variables = new HashMap<>();
        variables.put("name","李群");
        ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("purchase","28",variables);
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().processDefinitionKey("purchase").active().orderByProcessInstanceId().desc();
        List<ProcessInstance> list = query.list();
        System.out.println("processInstance list.size="+list.size());
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        if(task==null)
        {
            System.out.println("任务为空");
        }
        else
        {
            System.out.println(task.getName());
        }

    }
}
