/**
 * 版权所有 刘大磊 2013-07-01
 * 作者：刘大磊
 * 电话：13336390671
 * email:ldlqdsd@126.com
 */
package com.dsdl.eidea.base.service.impl;

import com.dsdl.eidea.core.spring.annotation.DataAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dsdl.eidea.base.entity.po.SettingsPo;
import com.dsdl.eidea.base.service.SettingsService;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.Search;
import com.dsdl.eidea.core.dao.CommonDao;

import java.util.List;

/**
 * @author 刘大磊 2017-05-06 07:51:36
 */
@Service("settingsService")
public class SettingsServiceImpl implements SettingsService {
    @DataAccess(entity = SettingsPo.class)
    private CommonDao<SettingsPo, String> settingsDao;

    public PaginationResult<SettingsPo> getSettingsListByPaging(Search search, QueryParams queryParams) {
        search.setFirstResult(queryParams.getFirstResult());
        search.setMaxResults(queryParams.getPageSize());
        PaginationResult<SettingsPo> paginationResult = null;
        if (queryParams.isInit()) {
            SearchResult<SettingsPo> searchResult = settingsDao.searchAndCount(search);
            paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
        } else {
            List<SettingsPo> settingsPoList = settingsDao.search(search);
            paginationResult = PaginationResult.pagination(settingsPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
        }
        return paginationResult;
    }

    public SettingsPo getSettings(String key) {
        return settingsDao.find(key);
    }

    public void saveSettings(SettingsPo settings) {
        settingsDao.save(settings);
    }

    public void deletes(String[] keys) {
        settingsDao.removeByIds(keys);
    }

    @Override
    public boolean getShowEmptyMenuFolder() {
        Search search = new Search();
        search.addFilterEqual("key", "menu.show_empty_folder");
        SettingsPo settingsPo = settingsDao.searchUnique(search);
        if ("Y".equals(settingsPo.getValue())) {
            return true;
        }
        return false;
    }
}
