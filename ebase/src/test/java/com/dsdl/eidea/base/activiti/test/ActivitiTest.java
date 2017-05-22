package com.dsdl.eidea.base.activiti.test;

/**
 * Created by 刘大磊 on 2017/5/10 11:16.
 */

        import org.activiti.engine.*;
        import org.activiti.engine.history.HistoricProcessInstance;
        import org.activiti.engine.history.HistoricTaskInstance;
        import org.activiti.engine.repository.Deployment;
        import org.activiti.engine.repository.DeploymentBuilder;
        import org.activiti.engine.runtime.ProcessInstance;
        import org.activiti.engine.task.Task;
        import org.junit.Test;
        import org.junit.runner.RunWith;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.test.context.ContextConfiguration;
        import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
        import org.springframework.transaction.annotation.Transactional;

        import java.io.File;
        import java.io.FileNotFoundException;
        import java.io.InputStream;
        import java.util.List;
        import java.util.zip.ZipInputStream;
        import org.activiti.engine.ProcessEngine;
        import org.activiti.engine.ProcessEngineConfiguration;
        import org.activiti.engine.runtime.ProcessInstanceQuery;
        import org.activiti.spring.ProcessEngineFactoryBean;
        import org.junit.Before;
        import org.junit.Test;
        import org.springframework.util.ResourceUtils;

/**
 * Created by admin on 2017/1/13.
 */


/**
 * Created by admin on 2017/1/13.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class ActivitiTest {
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    RuntimeService runtimeService ;

    //创建数据库表
    @Test
    @Transactional
    public void createTable() {
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
        //连接数据库的配置
        processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
        processEngineConfiguration.setJdbcUrl("jdbc:mysql://192.168.0.157:3306/e_idea");
        processEngineConfiguration.setJdbcUsername("root");
        processEngineConfiguration.setJdbcPassword("wms123456");

      /*   public static final String DB_SCHEMA_UPDATE_FALSE = "false";不能自动创建表，需要表存在
         public static final String DB_SCHEMA_UPDATE_CREATE_DROP = "create-drop";先删除表再创建表
         public static final String DB_SCHEMA_UPDATE_TRUE = "true";如果表不存在，自动创建表*/

        processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        //工作流的核心对象，ProcessEnginee对象

        ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
        System.out.println("processEngine:" + processEngine);
    }

    /*
    * SELECT * FROM act_re_deployment ;-- 工作流部署表
SELECT * FROM act_ge_bytearray -- 存储二进制相关的图片
SELECT * FROM act_re_procdef   -- 流程定义的数据
如果KEY相同，做二次部署会在原有的key的流程上做版本的累加
    * */

    @Test
    @Transactional
    public void processTestsss() throws FileNotFoundException {
        // String depolementId="22501";
        // 加载配置文件读取配置文件创建流程引擎的配置对象，创建对象
        //  ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
        // System.out.println("processEngine:" + processEngine);
   /*  repositoryService = processEngine.getRepositoryService();
    RuntimeService runtimeService = processEngine.getRuntimeService();*/
        //部署流程
        /*File cfgFile = ResourceUtils.getFile("classpath:activi/qing_jia.bpmn");
        repositoryService.createDeployment().addInputStream("jbpm")*/
        repositoryService.createDeployment().addClasspathResource("activiti/qing_jia.bpmn").name("请假流程").category("请假").deploy();
        // 删除流程部署,，该删除只能是针对没有启动的流程，根据id删除
        //repositoryService.deleteDeployment("22501");
        /* 删除正在运行的流程部署,级联删除,第二个参数是true代表及时有运行也可以删除*/
        //repositoryService.deleteDeployment(depolementId,true);
        //启动流程
        // runtimeService.startProcessInstanceByKey("myProcess_1");
        //根据key 启动，启动的是最新版本的流程，根据Id启动 可以启动任何版本的流程，以act_re_procdef表为准
        // ProcessInstance processInstance= runtimeService.startProcessInstanceById("myProcess_1:3:35004");
        ProcessInstance processInstance=runtimeService.startProcessInstanceByKey("myProcess_1");
        System.out.println("流程实例ID:" + processInstance.getId());
        System.out.println("正在活动的流程节点:" + processInstance.getActivityId());//正在活动的流程节点
        System.out.println("流程定义的ID:" + processInstance.getProcessDefinitionId());//流程定义的ID
    }
    @Test
    public  void  queryProcessInstance(){
        String dd="myProcess_1";
        ProcessInstanceQuery processInstanceQuery= runtimeService.createProcessInstanceQuery();
        List<ProcessInstance> ss=  processInstanceQuery.processDefinitionKey(dd).orderByProcessDefinitionKey().desc().list();
        for (ProcessInstance sss:ss ) {
            System.out.println("流程实例ID:" + sss.getId());
        }
    }
}
