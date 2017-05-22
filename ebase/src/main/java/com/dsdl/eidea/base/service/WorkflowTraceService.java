package com.dsdl.eidea.base.service;

import java.util.List;
import java.util.Map;

/**
 * Created by 刘大磊 on 2017/5/18 15:23.
 */
public interface WorkflowTraceService {
    /**
     * 流程跟踪图
     * @param processInstanceId
     * @return
     */
    List<Map<String, Object>> traceProcess(String processInstanceId);
}
