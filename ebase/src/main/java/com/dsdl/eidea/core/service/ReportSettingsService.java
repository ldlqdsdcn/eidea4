package com.dsdl.eidea.core.service;

import com.dsdl.eidea.core.entity.po.ReportSettingsPo;
import com.googlecode.genericdao.search.Search;

import java.util.List;

/**
 * Created by 车东明 on 2017/4/17.
 */
public interface ReportSettingsService {
    List<ReportSettingsPo> getReportSettingsList(Search search);

    ReportSettingsPo getReportSettingsPo(String key);

    boolean save(ReportSettingsPo reportSettingsPo);


    void deletes(String[] keys);

    boolean findExistReport(String key);

    /**
     * 获取报表服务器设置
     * @param key
     * @return
     */
    String getReportSettingProperty(String key);
}
