package com.dsdl.eidea.base.service;

import com.dsdl.eidea.base.entity.bo.ModuleBo;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;

import java.util.List;

/**
 * Created by Bobo on 2016/12/14 8:53.
 */
public interface ModuleService {
    /**
     * getModuleList:查询列表
     * @param search
     * @return
     */
    PaginationResult<ModuleBo> getModuleList(Search search, QueryParams queryParams);

    /**
     * deleteModuleList:批量删除
     * @param ids
     */
    void deleteModuleList(Integer[] ids);

    /**
     * save保存
     * @param moduleBo
     */
    void saveModule(ModuleBo moduleBo);

    /**
     * get根据id查询module对象
     * @param id
     * @return
     */
    ModuleBo getModule(int id);

    /**
     * findExistId:判断url是否存在
     * @param id
     * @return
     */
    boolean findExistId(Integer id);

    /**
     * 通过path获取模块
     */
    ModuleBo getModuleBoByPath(String path);
}
