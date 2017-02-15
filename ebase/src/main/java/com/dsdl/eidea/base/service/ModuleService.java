package com.dsdl.eidea.base.service;

import com.dsdl.eidea.base.entity.bo.ModuleBo;
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
    List<ModuleBo> getModuleList(Search search);

    /**
     * deleteModuleList:批量删除
     * @param ids
     */
    void deleteModuleList(Integer[] ids);

    /**
     * saveModule:保存
     * @param moduleBo
     */
    void saveModule(ModuleBo moduleBo);

    /**
     * getModule:根据id查询module对象
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
}
