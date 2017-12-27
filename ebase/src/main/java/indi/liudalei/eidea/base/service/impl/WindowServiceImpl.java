/**
 * 版权所有 刘大磊 2013-07-01
 * 作者：刘大磊
 * 电话：13336390671
 * email:ldlqdsd@126.com
 */
package indi.liudalei.eidea.base.service.impl;

import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
import indi.liudalei.eidea.base.entity.bo.*;
import indi.liudalei.eidea.base.entity.po.*;
import indi.liudalei.eidea.base.service.TabService;
import indi.liudalei.eidea.base.service.WindowService;
import indi.liudalei.eidea.core.dao.CommonDao;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.QueryParams;
import indi.liudalei.eidea.core.spring.annotation.DataAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.googlecode.genericdao.search.ISearch.RESULT_MAP;

/**
 * @author 刘大磊 2017-05-02 15:41:30
 */
@Service("windowService")
public class WindowServiceImpl implements WindowService {
    @DataAccess(entity = WindowPo.class)
    private CommonDao<WindowPo, Integer> windowDao;
    @DataAccess(entity = FieldPo.class)
    private CommonDao<FieldPo, Integer> fieldDao;
    @DataAccess(entity = WindowTrlPo.class)
    private CommonDao<WindowTrlPo, Integer> windowTrlDao;
    @DataAccess(entity = FieldTrlPo.class)
    private CommonDao<FieldTrlPo, Integer> fieldTrlDao;
    @DataAccess(entity = TabPo.class)
    private CommonDao<TabPo, Integer> tabDao;
    @DataAccess(entity = TabTrlPo.class)
    private CommonDao<TabTrlPo, Integer> tabTrlDao;

    @Autowired
    private TabService tabService;

    public PaginationResult<WindowPo> getWindowListByPaging(Search search, QueryParams queryParams) {
        search.setFirstResult(queryParams.getFirstResult());
        search.setMaxResults(queryParams.getPageSize());
        PaginationResult<WindowPo> paginationResult = null;
        if (queryParams.isInit()) {
            SearchResult<WindowPo> searchResult = windowDao.searchAndCount(search);
            paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
        } else {
            List<WindowPo> windowPoList = windowDao.search(search);
            paginationResult = PaginationResult.pagination(windowPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
        }
        return paginationResult;
    }

    public WindowPo getWindow(Integer id) {
        return windowDao.find(id);
    }

    public void saveWindow(WindowPo window) {
        windowDao.saveForLog(window);
    }

    public void deletes(Integer[] ids) {
        windowDao.removeByIdsForLog(ids);
    }

    public List<WindowBo> getWindows(String lang) {
        List<WindowBo> windowBoList = new ArrayList<>();
        Search search = new Search();
        search.addFilterEqual("lang", lang);
        search.addFilterEqual("windowPo.isactive", "Y");
        search.addField("windowId").addField("name");
        search.setResultMode(RESULT_MAP);
        List<Map> windowTrlPoList = windowTrlDao.search(search);
        windowTrlPoList.forEach(e -> {
            WindowBo windowBo = new WindowBo();
            windowBo.setWindowId((Integer) e.get("windowId"));
            windowBo.setWindowName((String) e.get("name"));
            windowBoList.add(windowBo);
        });
        return windowBoList;
    }

    @Override
    public WindowBo getWindowBo(Integer id, String lang) {
        WindowPo windowPo = windowDao.find(id);
        Search search = new Search();
        search.addFilterEqual("windowId", id);
        search.addFilterEqual("lang", lang);
        WindowTrlPo windowTrlPo = windowTrlDao.searchUnique(search);
        WindowBo windowBo = new WindowBo();
        windowBo.setWindowId(windowPo.getId());
        if (windowTrlPo != null) {
            windowBo.setHelp(windowTrlPo.getHelp());
            windowBo.setWindowName(windowTrlPo.getName());
        } else {
            windowBo.setWindowName(windowPo.getName());
            windowBo.setHelp("");
        }

        List<TabBo> tabBoList = tabService.getTabBoListByWindowId(windowPo.getId(), lang);
        windowBo.setTabList(tabBoList);
        return windowBo;
    }

    @Override
    public boolean findExistWindowByName(String name) {
        Search search = new Search();
        search.addFilterEqual("name", name);
        List<WindowPo> windowPoList = windowDao.search(search);
        if (windowPoList.size() > 0 && windowPoList != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public WindowPo getExistWindowByName(String name) {
        Search search = new Search();
        search.addFilterEqual("name", name);
        WindowPo windowPo = windowDao.searchUnique(search);
        return windowPo;
    }

    @Override
    public WindowHelpBo getWindowHelpBo(Integer windowId, String lang) {
        WindowPo windowPo = windowDao.find(windowId);
        Search windowTrlSearch = new Search();
        windowTrlSearch.addFilterEqual("windowId", windowId);
        windowTrlSearch.addFilterEqual("lang", lang);
        WindowTrlPo windowTrlPo = windowTrlDao.searchUnique(windowTrlSearch);
        WindowHelpBo windowHelpBo = new WindowHelpBo();
        windowHelpBo.setId(windowId);
        if (windowTrlPo == null) {
            windowHelpBo.setName(windowPo.getName());
        } else {
            windowHelpBo.setName(windowTrlPo.getName());
            windowHelpBo.setHelp(windowTrlPo.getHelp());
        }
        Search tabSearch = new Search();
        tabSearch.addFilterEqual("windowId", windowId);
        List<TabPo> tabPoList = tabDao.search(tabSearch);
        List<TabHelpBo> tabHelpBoList = new ArrayList<>();
        for (TabPo tabPo : tabPoList) {
            TabHelpBo tabHelpBo = new TabHelpBo();
            Search tabTrlSearch = new Search();
            tabTrlSearch.addFilterEqual("tabId", tabPo.getId());
            tabTrlSearch.addFilterEqual("lang", lang);
            TabTrlPo tabTrlPo = tabTrlDao.searchUnique(tabTrlSearch);
            tabHelpBo.setTabId(tabPo.getId());
            if (tabTrlPo == null) {
                tabHelpBo.setName(tabPo.getName());
            } else {
                tabHelpBo.setName(tabTrlPo.getName());
                tabHelpBo.setHelp(tabTrlPo.getHelp());
            }
            Search fieldSearch = new Search();
            fieldSearch.addFilterEqual("tabId", tabPo.getId());
            List<FieldPo> fieldPoList = fieldDao.search(fieldSearch);
            List<FieldHelpBo> fieldHelpBoList = new ArrayList<>();
            for (FieldPo fieldPo : fieldPoList) {
                Search fieldTrlSearch = new Search();
                fieldTrlSearch.addFilterEqual("fieldId", fieldPo.getId());
                fieldTrlSearch.addFilterEqual("lang", lang);
                FieldTrlPo fieldTrlPo = fieldTrlDao.searchUnique(fieldTrlSearch);
                FieldHelpBo fieldHelpBo = new FieldHelpBo();
                fieldHelpBo.setId(fieldPo.getId());

                if (fieldTrlPo == null) {
                    fieldHelpBo.setName(fieldPo.getName());
                } else {
                    fieldHelpBo.setName(fieldTrlPo.getName());
                    fieldHelpBo.setHelp(fieldTrlPo.getHelp());
                }
                fieldHelpBoList.add(fieldHelpBo);
            }
            tabHelpBoList.add(tabHelpBo);
            tabHelpBo.setFieldHelpBoList(fieldHelpBoList);
        }
        windowHelpBo.setTabHelpBoList(tabHelpBoList);
        return windowHelpBo;
    }
}
