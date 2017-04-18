package com.dsdl.eidea.core.service;

import com.dsdl.eidea.core.entity.bo.ReportBo;
import com.googlecode.genericdao.search.Search;

import java.util.List;

/**
 * Created by 车东明 on 2017/4/17.
 */
public interface ReportService {
    List<ReportBo> findReport(Search search);

    ReportBo getReportBo(String key);

    boolean save(ReportBo reportBo);


    void deletes(String[] keys);

    boolean findExistReport(String key);
}
