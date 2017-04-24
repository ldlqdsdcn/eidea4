package com.dsdl.eidea.core.service.impl;

import com.dsdl.eidea.core.dao.CommonDao;
import com.dsdl.eidea.core.entity.po.ReportSettingsPo;
import com.dsdl.eidea.core.rmi.ReportSettingsRmi;
import com.dsdl.eidea.core.service.ReportSettingsService;
import com.dsdl.eidea.core.spring.annotation.DataAccess;
import com.googlecode.genericdao.search.Search;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 车东明 on 2017/4/17.
 */
@Service
@Slf4j
public class ReportSettingsServiceImpl implements ReportSettingsService,ReportSettingsRmi {
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
    @Cacheable(key = "#key")
    @Override
    public String getReportSettingProperty(String key) {
        ReportSettingsPo reportSettingsPo = reportSettingsDao.find(key);
        if(reportSettingsPo==null)
        {
            String errorMsg="找不到key值为："+key+" 的配置信息";
            log.error(errorMsg);
            throw new IllegalArgumentException(errorMsg);
        }
        return reportSettingsPo.getValue();
    }

    /**
     * 获取模板文件配置
     * @return
     */
    public String getTemplateFilePath()
    {
        return getReportSettingProperty("template_path");
    }
}
