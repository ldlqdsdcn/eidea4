/**
 * 版权所有 刘大磊 2013-07-01
 * 作者：刘大磊
 * 电话：13336390671
 * email:ldlqdsd@126.com
 */
package indi.liudalei.eidea.base.service.impl;

import indi.liudalei.eidea.core.spring.annotation.DataAccess;
import indi.liudalei.eidea.base.service.WindowTrlService;
import indi.liudalei.eidea.core.dao.CommonDao;
import indi.liudalei.eidea.core.dto.PaginationResult;
import org.springframework.stereotype.Service;
import indi.liudalei.eidea.base.entity.po.WindowTrlPo;
import indi.liudalei.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.Search;

import java.util.List;

/**
 * @author 刘大磊 2017-05-02 15:42:28
 */
@Service("windowTrlService")
public class WindowTrlServiceImpl implements WindowTrlService {
    @DataAccess(entity = WindowTrlPo.class)
    private CommonDao<WindowTrlPo, Integer> windowTrlDao;

    public PaginationResult<WindowTrlPo> getWindowTrlListByPaging(Search search, QueryParams queryParams) {
        search.setFirstResult(queryParams.getFirstResult());
        search.setMaxResults(queryParams.getPageSize());
        PaginationResult<WindowTrlPo> paginationResult = null;
        if (queryParams.isInit()) {
            SearchResult<WindowTrlPo> searchResult = windowTrlDao.searchAndCount(search);
            paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
        } else {
            List<WindowTrlPo> windowTrlPoList = windowTrlDao.search(search);
            paginationResult = PaginationResult.pagination(windowTrlPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
        }
        return paginationResult;
    }
    public PaginationResult<WindowTrlPo> getWindowTrlListByWindowId(Search search, Integer windowId) {
        QueryParams queryParams = new QueryParams();
        search.setFirstResult(queryParams.getFirstResult());
        search.setMaxResults(queryParams.getPageSize());
        search.addFilterEqual("windowId", windowId);
        PaginationResult<WindowTrlPo> paginationResult = null;
        if (queryParams.isInit()) {
            SearchResult<WindowTrlPo> searchResult = windowTrlDao.searchAndCount(search);
            paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
        } else {
            List<WindowTrlPo> windowTrlPoList = windowTrlDao.search(search);
            paginationResult = PaginationResult.pagination(windowTrlPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
        }
        return paginationResult;
    }

    public WindowTrlPo getWindowTrl(Integer id) {
        return windowTrlDao.find(id);
    }

    public void saveWindowTrl(WindowTrlPo windowTrl) {
        windowTrlDao.save(windowTrl);
    }

    public void deletes(Integer[] ids) {
        windowTrlDao.removeByIds(ids);
    }
}
