/**
 * 版权所有 刘大磊 2013-07-01
 * 作者：刘大磊
 * 电话：13336390671
 * email:ldlqdsd@126.com
 */
package indi.liudalei.eidea.base.service.impl;

import indi.liudalei.eidea.core.spring.annotation.DataAccess;
import indi.liudalei.eidea.base.entity.po.SettingsPo;
import indi.liudalei.eidea.core.dao.CommonDao;
import indi.liudalei.eidea.core.dto.PaginationResult;
import org.springframework.stereotype.Service;
import indi.liudalei.eidea.base.service.SettingsService;
import indi.liudalei.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.Search;

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
        settingsDao.saveForLog(settings);
    }

    public void deletes(String[] keys) {
        settingsDao.removeByIdsForLog(keys);
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
