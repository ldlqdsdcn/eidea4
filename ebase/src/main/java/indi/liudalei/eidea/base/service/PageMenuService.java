package indi.liudalei.eidea.base.service;

import indi.liudalei.eidea.base.entity.bo.PageMenuBo;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;

import java.util.List;

/**
 * Created by admin on 2016/12/13.
 */
public interface PageMenuService {
    /**
     * 查询出所有的菜单
     * @param search
     * @return
     */
    List<PageMenuBo> findPageMenu(Search search);

    /**
     * 列表界面分页查询
     * @param search
     * @param queryParams
     * @return
     */
    PaginationResult<PageMenuBo> findPageMenu(Search search, QueryParams queryParams);
    //根据id 删除菜单
    void deleteMenuById(Integer[] ids);

    //保存
    void save(PageMenuBo pageMenuBo);

    //判断url是否存在
    boolean findExistUrl(Integer id);

    PageMenuBo getPageMenuBo(Integer id);

    /**
     * getModuleMenuList:模块设置展示菜单列表
     *
     * @return
     * @author:Bobo
     */
    List<PageMenuBo> getModuleMenuList();

    /**
     * listMenuType:查询文件夹菜单
     *
     * @return
     */
    List<PageMenuBo> getListMenuType();

    /**
     * getLeftMenuList:左侧菜单列表
     *
     * @return
     */
    String getLeftMenuListByUserId(Integer userId, String contextPath);

    /**
     * 获取国际化左菜单
     * @param userId
     * @param contextPath
     * @param languageCode
     * @return
     */
    String getLeftMenuListByUserId(Integer userId,String contextPath,String languageCode);
}
