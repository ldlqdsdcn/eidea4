package com.dsdl.eidea.sys.web.vo;

import lombok.Getter;
import lombok.Setter;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

import java.util.Date;

/**
 * Created by 刘大磊 on 2017/5/17 9:17.
 */
@Getter
@Setter
public class ProcessDefinitionVo {
    private String id;
    private String deploymentId;
    private String name;
    private String key;
    private int version;
    private String resourceName;
    private boolean suspended;
    private Date deploymentTime;
}
