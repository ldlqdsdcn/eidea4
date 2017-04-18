package com.dsdl.eidea.core.service.impl;

import com.dsdl.eidea.core.dao.CommonDao;
import com.dsdl.eidea.core.entity.bo.ReportBo;
import com.dsdl.eidea.core.entity.po.ReportPo;
import com.dsdl.eidea.core.service.ReportService;
import com.dsdl.eidea.core.spring.annotation.DataAccess;
import com.googlecode.genericdao.search.Search;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 车东明 on 2017/4/17.
 */
@Service
public class ReportServiceImpl implements ReportService {
    @DataAccess(entity = ReportPo.class)
    private CommonDao<ReportPo,String >reportDao;
    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<ReportBo> findReport(Search search) {
        List<ReportPo> reportPoList = reportDao.search(search);
        List<ReportBo> reportBoList = modelMapper.map(reportPoList, new TypeToken<List<ReportBo>>() {
        }.getType());
        return reportBoList;
    }

    @Override
    public ReportBo getReportBo(String key) {
        ReportPo reportPo = reportDao.find(key);
        if (reportPo != null) {
            ReportBo reportBo = modelMapper.map(reportPo, ReportBo.class);
            return reportBo;
        }
        return null;
    }

    @Override
    public boolean save(ReportBo reportBo) {
        ReportPo reportPo = modelMapper.map(reportBo, ReportPo.class);
        return reportDao.save(reportPo);
    }

    @Override
    public void deletes(String[] keys) {
        reportDao.removeByIds(keys);
    }

    @Override
    public boolean findExistReport(String key) {
        ReportPo reportPo = reportDao.find(key);
        if (reportPo != null) {
            return true;
        } else {
            return false;
        }
    }
}
