package com.dsdl.eidea.base.service;

import com.dsdl.eidea.base.entity.bo.ModuleDirectoryBo;

import java.util.List;

/**
 * Created by Bobo on 2017/5/11.
 */
public interface ModuleDirectoryService {

    List<ModuleDirectoryBo> getModuleDirectoryList(int directoryId);
}
