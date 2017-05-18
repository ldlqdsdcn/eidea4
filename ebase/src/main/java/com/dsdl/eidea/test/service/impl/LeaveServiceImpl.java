/**
 * 版权所有 刘大磊 2013-07-01
 * 作者：刘大磊
 * 电话：13336390671
 * email:ldlqdsd@126.com
 */
package com.dsdl.eidea.test.service.impl;

import com.dsdl.eidea.base.entity.po.UserPo;
import com.dsdl.eidea.core.spring.annotation.DataAccess;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.hibernate.HibernateUtil;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dsdl.eidea.test.entity.po.LeavePo;
import com.dsdl.eidea.test.service.LeaveService;
import com.googlecode.genericdao.search.ISearch;
import com.dsdl.eidea.core.dao.CommonDao;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 刘大磊 2017-05-12 13:36:48
 */
@Service("leaveService")
@Slf4j
public class LeaveServiceImpl implements LeaveService {
    @DataAccess(entity = LeavePo.class)
    private CommonDao<LeavePo, Integer> leaveDao;
    @DataAccess(entity = UserPo.class)
    private CommonDao<UserPo, Integer> userDao;

    @Autowired
    private IdentityService identityService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private TaskService taskService;

    public List<LeavePo> getLeaveList(ISearch search) {
        List<LeavePo> leavePoList = leaveDao.search(search);
        for (LeavePo leavePo : leavePoList) {
            UserPo userPo = userDao.find(leavePo.getLeaveUserId());
            leavePo.setUserName(userPo.getName());
        }
        return leaveDao.search(search);
    }

    public void saveStartLeave(Integer id) {
        String businessKey = String.valueOf(id);
        LeavePo leavePo = leaveDao.find(id);
        ProcessInstance processInstance = null;
        try {
            // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
            identityService.setAuthenticatedUserId(leavePo.getLeaveUserId().toString());

            processInstance = runtimeService.startProcessInstanceByKey("leave", businessKey);
            String processInstanceId = processInstance.getId();
            leavePo.setProcessInstanceId(processInstanceId);
            leaveDao.saveForLog(leavePo);
            log.debug("start process of {key={}, bkey={}, pid={}, variables={}}", new Object[]{"leave", businessKey, processInstanceId, null});
        } finally {
            identityService.setAuthenticatedUserId(null);
        }
    }

    public LeavePo getLeave(Integer id) {
        return leaveDao.find(id);
    }

    public void saveLeave(LeavePo leave) {
        leaveDao.save(leave);
    }

    public void deletes(Integer[] ids) {
        leaveDao.removeByIds(ids);
    }


    /**
     * 读取运行中的流程
     *
     * @return
     */
    public List<LeavePo> getRunningProcessInstances() {
        List<LeavePo> results = new ArrayList<>();
        ProcessInstanceQuery query = runtimeService.createProcessInstanceQuery().processDefinitionKey("leave").active().orderByProcessInstanceId().desc();
        List<ProcessInstance> list = query.list();

        // 关联业务实体
        for (ProcessInstance processInstance : list) {
            String businessKey = processInstance.getBusinessKey();
            if (businessKey == null) {
                continue;
            }
            LeavePo leave = leaveDao.find(new Integer(businessKey));
            leave.setProcessInstance(processInstance);
            leave.setProcessDefinition(getProcessDefinition(processInstance.getProcessDefinitionId()));
            UserPo userPo=userDao.find(leave.getLeaveUserId());
            leave.setUserName(userPo.getName());
            results.add(leave);
            // 设置当前任务信息
            Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
            leave.setTask(task);

        }
        return results;
    }

    /**
     * 查询流程定义对象
     *
     * @param processDefinitionId 流程定义ID
     * @return
     */
    protected ProcessDefinition getProcessDefinition(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        return processDefinition;
    }
}
