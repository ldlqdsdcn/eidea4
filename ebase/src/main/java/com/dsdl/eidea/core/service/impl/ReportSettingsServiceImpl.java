package com.dsdl.eidea.core.service.impl;

import com.dsdl.eidea.core.dao.CommonDao;
import com.dsdl.eidea.core.entity.po.ReportSettingsPo;
import com.dsdl.eidea.core.service.ReportSettingsService;
import com.dsdl.eidea.core.spring.annotation.DataAccess;
import com.googlecode.genericdao.search.Search;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 车东明 on 2017/4/17.
 */
@Service
public class ReportSettingsServiceImpl implements ReportSettingsService {
    @DataAccess(entity = ReportSettingsPo.class)
    private CommonDao<ReportSettingsPo, String> reportSettingsDao;

    @Override
    public List<ReportSettingsPo> getReportSettingsList(Search search) {
        List<ReportSettingsPo> reportSettingsPoList = reportSettingsDao.search(search);
        return reportSettingsPoList;
    }

    @Override
    public ReportSettingsPo getReportSettingsPo(String key) {
        ReportSettingsPo reportSettingsPo = reportSettingsDao.find(key);
        if (reportSettingsPo != null) {
            return reportSettingsPo;
        }
        return null;
    }

    @Override
    public boolean save(ReportSettingsPo reportSettingsPo) {
        reportSettingsPo.setInit("N");
        ReportSettingsPo temp=reportSettingsDao.find(reportSettingsPo.getKey());
        if(temp!=null)
        {
            reportSettingsPo.setInit(temp.getInit());
        }
        return reportSettingsDao.save(reportSettingsPo);
    }

    @Override
    public void deletes(String[] keys) {
        reportSettingsDao.removeByIds(keys);
    }

    @Override
    public boolean findExistReport(String key) {
        ReportSettingsPo reportSettingsPo = reportSettingsDao.find(key);
        if (reportSettingsPo != null) {
            return true;
        } else {
            return false;
        }
    }
}
