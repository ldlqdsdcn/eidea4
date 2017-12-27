package indi.liudalei.eidea.core.service;

import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.entity.po.ReportSettingsPo;
import indi.liudalei.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;

/**
 * Created by 车东明 on 2017/4/17.
 */
public interface ReportSettingsService {
    PaginationResult<ReportSettingsPo> getReportSettingsList(Search search, QueryParams queryParams);

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
