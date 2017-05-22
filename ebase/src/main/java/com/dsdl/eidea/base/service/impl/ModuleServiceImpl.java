package com.dsdl.eidea.base.service.impl;


import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.dsdl.eidea.core.spring.annotation.DataAccess;
import com.dsdl.eidea.base.entity.bo.ModuleBo;
import com.dsdl.eidea.base.entity.bo.ModuleDirectoryBo;
import com.dsdl.eidea.base.entity.bo.ModuleMenuBo;
import com.dsdl.eidea.base.entity.po.*;
import com.dsdl.eidea.base.service.ModuleService;
import com.dsdl.eidea.core.dao.CommonDao;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by Bobo on 2016/12/14 8:53.
 */
@Service
public class ModuleServiceImpl implements ModuleService {
    @DataAccess(entity = ModulePo.class)
    private CommonDao<ModulePo, Integer> moduleDao;
    @DataAccess(entity = PageMenuPo.class)
    private CommonDao<PageMenuPo, Integer> pageMenuDao;
    @DataAccess(entity = DirectoryPo.class)
    private CommonDao<DirectoryPo, Integer> directoryDao;
    @DataAccess(entity = ModuleMenuPo.class)
    private CommonDao<ModuleMenuPo, Integer> moduleMenuDao;
    @DataAccess(entity = ModuleDirectoryPo.class)
    private CommonDao<ModuleDirectoryPo, Integer> moduleDirectoryDao;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public PaginationResult<ModuleBo> getModuleList(Search search, QueryParams queryParams) {
        search.setFirstResult(queryParams.getFirstResult());
        search.setMaxResults(queryParams.getPageSize());
        PaginationResult<ModuleBo> paginationResult = null;
        if (queryParams.isInit()) {
            SearchResult<ModulePo> searchResult = moduleDao.searchAndCount(search);
            List<ModuleBo> list = modelMapper.map(searchResult.getResult(), new TypeToken<List<ModuleBo>>() {
            }.getType());
            paginationResult = PaginationResult.pagination(list, searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
        } else {
            List<ModulePo> languagePoList = moduleDao.search(search);
            List<ModuleBo> languageBoList = modelMapper.map(languagePoList, new TypeToken<List<ModuleBo>>() {
            }.getType());
            paginationResult = PaginationResult.pagination(languageBoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
        }
        return paginationResult;
    }

    @Override
    public void deleteModuleList(Integer[] ids) {
        moduleDao.removeByIdsForLog(ids);
    }

    @Override
    public void saveModule(ModuleBo moduleBo) {
        if (moduleBo.getIsactive() == null) {
            moduleBo.setIsactive("N");
        }
        ModulePo modulePo = modelMapper.map(moduleBo, ModulePo.class);
        Search search = new Search();
        if (moduleBo.getMenuIds().length > 0) {
            search.addFilterIn("sysModule.id", moduleBo.getId());
            List<ModuleMenuPo> moduleMenuAllList = moduleMenuDao.search(search);
            if (moduleMenuAllList != null && moduleMenuAllList.size() > 0) {
                for (ModuleMenuPo moduleMenu : moduleMenuAllList) {
                    moduleMenuDao.removeForLog(moduleMenu);
                }
            }
            List<ModuleMenuPo> moduleMenuList = new ArrayList<ModuleMenuPo>();
            for (Integer menuId : moduleBo.getMenuIds()) {
                ModuleMenuPo moduleMenuPo = new ModuleMenuPo();
                PageMenuPo pageMenuPo = pageMenuDao.find(menuId);
                moduleMenuPo.setPageMenuPo(pageMenuPo);
                moduleMenuPo.setSysModule(modulePo);
                moduleMenuList.add(moduleMenuPo);
            }
            modulePo.setSysModuleMenus(moduleMenuList);
        }
        if (moduleBo.getDirIds().length > 0) {
            search.addFilterIn("sysModule.id", moduleBo.getId());
            List<ModuleDirectoryPo> moduleDirectoryAllList = moduleDirectoryDao.search(search);
            if (moduleDirectoryAllList != null && moduleDirectoryAllList.size() > 0) {
                for (ModuleDirectoryPo moduleDirectory : moduleDirectoryAllList) {
                    moduleDirectoryDao.removeForLog(moduleDirectory);
                }
            }
            List<ModuleDirectoryPo> moduleDirectoryList = new ArrayList<ModuleDirectoryPo>();
            for (Integer dirId : moduleBo.getDirIds()) {
                ModuleDirectoryPo moduleDirectoryPo = new ModuleDirectoryPo();
                DirectoryPo directoryPo = directoryDao.find(dirId);
                moduleDirectoryPo.setSysDirectory(directoryPo);
                moduleDirectoryPo.setSysModule(modulePo);
                moduleDirectoryList.add(moduleDirectoryPo);
            }
            modulePo.setSysModuleDirectories(moduleDirectoryList);
        }
        moduleDao.saveForLog(modulePo);
        moduleBo.setId(modulePo.getId());
    }

    @Override
    public ModuleBo getModule(int id) {
        ModulePo modulePo = moduleDao.find(id);
        return getModubleBoByPo(modulePo);
    }

    private ModuleBo getModubleBoByPo(ModulePo modulePo) {
        ModuleBo moduleBo = modelMapper.map(modulePo, ModuleBo.class);
        if (modulePo != null) {
            List<ModuleMenuBo> moduleMenuBoList = modelMapper.map(modulePo.getSysModuleMenus(), new TypeToken<List<ModuleMenuBo>>() {
            }.getType());
            if (moduleMenuBoList != null && moduleMenuBoList.size() > 0) {
                Integer[] ids = new Integer[moduleMenuBoList.size()];
                for (int i = 0; i < moduleMenuBoList.size(); i++) {
                    ids[i] = moduleMenuBoList.get(i).getPageMenuId();
                }
                moduleBo.setMenuIds(ids);
            }
            List<ModuleDirectoryBo> moduleDirectoryBoList = modelMapper.map(modulePo.getSysModuleDirectories(), new TypeToken<List<ModuleDirectoryBo>>() {
            }.getType());
            if (moduleDirectoryBoList != null && moduleDirectoryBoList.size() > 0) {
                Integer[] ids = new Integer[moduleDirectoryBoList.size()];
                for (int i = 0; i < moduleDirectoryBoList.size(); i++) {
                    ids[i] = moduleDirectoryBoList.get(i).getSysDirectoryId();
                }
                moduleBo.setDirIds(ids);
            }
        }
        return moduleBo;
    }

    @Override
    public boolean findExistId(Integer id) {
        ModulePo modulePo = moduleDao.find(id);
        if (modulePo != null) {
            return true;
        } else {
            return false;
        }
    }

    public ModuleBo getModuleBoByPath(String path) {
        Search search = new Search();
        search.addFilterEqual("isactive", "Y");
        List<DirectoryPo> directoryPoList = directoryDao.search(search);
        DirectoryPo currentDirectory = null;
        for (DirectoryPo directoryPo : directoryPoList) {
            if (path.startsWith(directoryPo.getDirectory())) {
                currentDirectory = directoryPo;
                break;
            }
        }
        if (currentDirectory != null) {
            Set<ModuleDirectoryPo> moduleDirectoryPoSet = currentDirectory.getSysModuleDirectories();
            for (ModuleDirectoryPo moduleDirectoryPo : moduleDirectoryPoSet) {
                return getModubleBoByPo(moduleDirectoryPo.getSysModule());
            }
        }


        return null;
    }

    @Override
    public List<ModuleBo> getModulePos() {
        Search search=new Search();
        search.addFilterEqual("isactive","Y");
        List<ModulePo> modulePos=moduleDao.search(search);
        List<ModuleBo> moduleBos= modelMapper.map(modulePos,new TypeToken<List<ModuleBo>>(){}.getType());
        return moduleBos;
    }
}
