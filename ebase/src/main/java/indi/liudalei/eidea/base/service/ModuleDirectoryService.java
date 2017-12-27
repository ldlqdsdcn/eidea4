package indi.liudalei.eidea.base.service;

import indi.liudalei.eidea.base.entity.bo.ModuleDirectoryBo;

import java.util.List;

/**
 * Created by Bobo on 2017/5/11.
 */
public interface ModuleDirectoryService {
    /**
     * getModuleDirectoryList:查询模块跟目录的对应列表
     * @return
     */
    List<ModuleDirectoryBo> getModuleDirectoryList(int directoryId);
}
