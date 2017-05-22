
/**
 * 版权所有 刘大磊 2013-07-01
 * 作者：刘大磊
 * 电话：13336390671
 * email:ldlqdsd@126.com
 */
package com.dsdl.eidea.test.entity.po;

import java.util.Date;
import java.util.Map;
import javax.persistence.*;

import com.dsdl.eidea.base.entity.po.UserPo;
import lombok.Getter;
import lombok.Setter;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

/**
 * table name test_leave
 *            请假表
 * Date:2017-05-12 13:36:47
 **/
@Getter
@Setter
@Entity(name = "test_leave")
public class LeavePo implements java.io.Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "[id]")
    @Id
    private Integer id;
    /**
     * 标题
     **/
    @Column(name = "[title]", length = 300)
    private String title;
    /**
     * 内容
     **/
    @Column(name = "[content]", length = 65535)
    private String content;
    /**
     * 请假开始时间
     **/
    @Column(name = "[bgn_time]", length = 19)
    private Date bgnTime;
    /**
     * 请假结束时间
     **/
    @Column(name = "[end_time]", length = 19)
    private Date endTime;
    /**
     * 请假人
     **/
    @Column(name = "[leave_user_id]")
    private Integer leaveUserId;

    @Column(name = "process_instance_id")
    private String processInstanceId;

    @Transient
    private String userName;

    /**
     * 流程任务
     */
    @Transient
    private Task task;
    @Transient
    private Map<String, Object> variables;

    /**
     * 运行中的流程实例
     */
    @Transient
    private ProcessInstance processInstance;

    /**
     * 历史的流程实例
     */
    @Transient
    private HistoricProcessInstance historicProcessInstance;

    /**
     * 流程定义
     */
    @Transient
    private ProcessDefinition processDefinition;
}