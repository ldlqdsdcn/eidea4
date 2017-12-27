package indi.liudalei.eidea.test.web.vo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Transient;
import java.util.Date;

/**
 * Created by 刘大磊 on 2017/5/18 11:14.
 */
@Getter
@Setter
public class LeaveVo {
    private Integer id;
    /**
     * 标题
     **/
    private String title;
    /**
     * 内容
     **/
    private String content;
    /**
     * 请假开始时间
     **/
    private Date bgnTime;
    /**
     * 请假结束时间
     **/
    private Date endTime;
    /**
     * 请假人
     **/
    private Integer leaveUserId;


    private String processInstanceId;

    private String userName;

    private String processDefinitionId;
    private String taskId;
    private String taskName;
    private Date taskCreateTime;

    private boolean suspended;

    private int version;

    private String assignee;
}
