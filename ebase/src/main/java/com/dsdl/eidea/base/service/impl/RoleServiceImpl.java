package com.dsdl.eidea.base.service.impl;

import com.dsdl.eidea.base.service.AccountService;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.dsdl.eidea.core.spring.annotation.DataAccess;
import com.dsdl.eidea.base.entity.bo.ModuleRoleBo;
import com.dsdl.eidea.base.entity.bo.PrivilegeBo;
import com.dsdl.eidea.base.entity.bo.RoleBo;
import com.dsdl.eidea.base.entity.bo.RoleOrgaccessBo;
import com.dsdl.eidea.base.entity.po.*;
import com.dsdl.eidea.base.service.RoleService;
import com.dsdl.eidea.core.dao.CommonDao;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    @DataAccess(entity = RolePo.class)
    private CommonDao<RolePo, Integer> roleDao;
    @DataAccess(entity = OrgPo.class)
    private CommonDao<OrgPo, Integer> orgDao;
    @DataAccess(entity = ModulePo.class)
    private CommonDao<ModulePo, Integer> moduleDao;
    @DataAccess(entity = OperatorPo.class)
    private CommonDao<OperatorPo, Integer> operatorDao;
    @DataAccess(entity = PrivilegesPo.class)
    private CommonDao<PrivilegesPo, Integer> privilegesDao;
    @DataAccess(entity = ModuleRolePo.class)
    private CommonDao<ModuleRolePo, Integer> moduleRoleDao;
    @DataAccess(entity = RoleOrgaccessPo.class)
    private CommonDao<RoleOrgaccessPo, Integer> roleOrgaccessDao;
    @Autowired
    private AccountService accountService;
    private final ModelMapper modelMapper = new ModelMapper();

    public RoleServiceImpl() {
        modelMapper.addMappings(new PropertyMap<RoleOrgaccessPo, RoleOrgaccessBo>() {
            @Override
            protected void configure() {
                map().setOrgId(source.getSysOrg().getId());
                map().setChecked(true);
                map().setOrgName(source.getSysOrg().getName());
                map().setRoleId(source.getSysRole().getId());
                map().setRoleName(source.getSysRole().getName());
            }
        });
        modelMapper.addMappings(new PropertyMap<ModuleRolePo, ModuleRoleBo>() {
            @Override
            protected void configure() {
                map().setRoleId(source.getSysRole().getId());
                map().setRoleName(source.getSysRole().getName());
                map().setModuleId(source.getSysModule().getId());
                map().setModuleName(source.getSysModule().getName());
            }
        });
        modelMapper.addMappings(new PropertyMap<PrivilegesPo, PrivilegeBo>() {
            @Override
            protected void configure() {
                map().setChecked(true);
                map().setModuleRoleId(source.getSysModuleRole().getId());
                map().setOperatorId(source.getSysOperator().getId());
                map().setOperatorName(source.getSysOperator().getName());
            }
        });
    }

    @Override
    public PaginationResult<RoleBo> getRoleList(Search search, QueryParams queryParams) {
        search.setFirstResult(queryParams.getFirstResult());
        search.setMaxResults(queryParams.getPageSize());
        PaginationResult<RoleBo> paginationResult = null;
        if (queryParams.isInit()) {
            SearchResult<RolePo> searchResult = roleDao.searchAndCount(search);
            List<RoleBo> list = modelMapper.map(searchResult.getResult(), new TypeToken<List<RoleBo>>() {
            }.getType());
            paginationResult = PaginationResult.pagination(list, searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
        } else {
            List<RolePo> rolePoList = roleDao.search(search);
            List<RoleBo> roleBoList = modelMapper.map(rolePoList, new TypeToken<List<RoleBo>>() {
            }.getType());
            paginationResult = PaginationResult.pagination(roleBoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
        }
        return paginationResult;
    }

    @Override
    public void save(RoleBo roleBo) {


        RolePo rolePo = modelMapper.map(roleBo, RolePo.class);

        List<ModuleRoleBo> moduleRoleBoList = roleBo.getModuleRoleBoList();

        List<ModuleRolePo> needSaveModuleRoleList = new ArrayList<>();
        for (ModuleRoleBo moduleRoleBo : moduleRoleBoList) {
            ModuleRolePo moduleRolePo = new ModuleRolePo();
            moduleRolePo.setId(moduleRoleBo.getId());
            moduleRolePo.setSysModule(moduleDao.find(moduleRoleBo.getModuleId()));
            moduleRolePo.setSysRole(rolePo);
            needSaveModuleRoleList.add(moduleRolePo);
            List<PrivilegeBo> privilegeBoList = moduleRoleBo.getPrivilegeBoList();
            List<PrivilegesPo> privilegesPoList = new ArrayList<>();
            for (PrivilegeBo privilegeBo : privilegeBoList) {
                if (!privilegeBo.isChecked()) {
                    if (privilegeBo.getId() != null) {
                        privilegesDao.removeByIdForLog(privilegeBo.getId());
                    }
                } else {
                    PrivilegesPo privilegePo = new PrivilegesPo();
                    privilegePo.setId(privilegeBo.getId());
                    privilegePo.setSysOperator(operatorDao.find(privilegeBo.getOperatorId()));
                    privilegePo.setSysModuleRole(moduleRolePo);
                    privilegesPoList.add(privilegePo);
                }
            }
            moduleRolePo.setSysPrivilegeses(privilegesPoList);
        }
        rolePo.setSysModuleRoles(needSaveModuleRoleList);
        List<RoleOrgaccessBo> roleOrgaccessBoList = roleBo.getRoleOrgAccessBoList();
        List<RoleOrgaccessPo> roleOrgaccessPoList = new ArrayList<>();

        for (RoleOrgaccessBo roleOrgaccessBo : roleOrgaccessBoList) {
            if (!roleOrgaccessBo.isChecked()) {
                if (roleOrgaccessBo.getId() != null) {
                    roleOrgaccessDao.removeByIdForLog(roleOrgaccessBo.getId());
                }
            } else {
                RoleOrgaccessPo po = new RoleOrgaccessPo();
                po.setSysOrg(orgDao.find(roleOrgaccessBo.getOrgId()));
                po.setSysRole(rolePo);
                po.setId(roleOrgaccessBo.getId());
                roleOrgaccessPoList.add(po);
            }
        }
        rolePo.setSysRoleOrgaccesses(roleOrgaccessPoList);

        roleDao.saveForLog(rolePo);
       /* roleBo.setId(rolePo.getId());
        accountService.saveRole(rolePo.getId());*/
    }

    @Override
    public boolean findExistRole(String no) {
        Search search = new Search();
        search.addFilterEqual("no", no);
        List<RolePo> clientPoList = roleDao.search(search);
        if (clientPoList.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public RoleBo findExistRoleByName(String roleName) {
        Search search = new Search();
        search.addFilterEqual("name", roleName);
        RolePo rolePo = roleDao.searchUnique(search);
        RoleBo roleBo = modelMapper.map(rolePo, RoleBo.class);
        return roleBo;
    }

    public RoleBo getInitRoleBo(RoleBo roleBo) {
        return initRoleBoByPo(null);
    }

    @Override
    public boolean getHasUsers(Integer id) {
        RolePo rolePo = roleDao.find(id);
        List<UserRolePo> userRolePos = rolePo.getSysUserRoles();
        if (userRolePos.size() > 0) {
            return true;
        }
        return false;
    }

    private RoleBo initRoleBoByPo(RolePo rolePo) {
        RoleBo roleBo = null;
        if (rolePo == null) {
            roleBo = new RoleBo();
        } else {
            roleBo = modelMapper.map(rolePo, RoleBo.class);
        }
        List<ModuleRoleBo> moduleRoleList = initModuleRoleBoList(rolePo);
        roleBo.setModuleRoleBoList(moduleRoleList);
        List<RoleOrgaccessBo> roleOrgAccessList = initOrgAccessForRoleBoList(rolePo);
        roleBo.setRoleOrgAccessBoList(roleOrgAccessList);

        return roleBo;
    }

    /**
     * 初始化 角色模块
     *
     * @param rolePo
     * @return
     */
    private List<ModuleRoleBo> initModuleRoleBoList(RolePo rolePo) {
        Search searchOper = new Search();
        searchOper.addFilterEqual("isactive", "Y");
        List<OperatorPo> operatorList = operatorDao.search(searchOper);
        Search moduleSearch = new Search();
        moduleSearch.addFilterEqual("isactive", "Y");
        List<ModuleRoleBo> moduleRoleBoList = new ArrayList<>();
        if (rolePo != null) {
            List<ModuleRolePo> moduleRolePoList = rolePo.getSysModuleRoles();
            List<ModuleRoleBo> boList = modelMapper.map(moduleRolePoList, new TypeToken<List<ModuleRoleBo>>() {
            }.getType());
            moduleRoleBoList.addAll(boList);

            List<Integer> moduleIdList = moduleRolePoList.stream().map(e -> e.getSysModule().getId()).collect(Collectors.toList());
            moduleSearch.addFilterNotIn("id", moduleIdList);

        }
        List<ModulePo> modulePoList = moduleDao.search(moduleSearch);
        modulePoList.forEach(e -> {
            ModuleRoleBo mrp = new ModuleRoleBo();
            mrp.setModuleId(e.getId());
            mrp.setModuleName(e.getName());
            moduleRoleBoList.add(mrp);
        });

        for (ModuleRoleBo moduleRoleBo : moduleRoleBoList) {
            List<PrivilegeBo> privilegeBoList = initPrivilegeBoList(moduleRoleBo, operatorList);
            moduleRoleBo.setPrivilegeBoList(privilegeBoList);
        }


        return moduleRoleBoList;
    }

    /**
     * 初始化 权限
     *
     * @return
     */
    private List<PrivilegeBo> initPrivilegeBoList(ModuleRoleBo moduleRoleBo, List<OperatorPo> operatorList) {
        List<PrivilegeBo> privilegeBoList = new ArrayList<>();

        List<OperatorPo> neededAdded = operatorList;
        if (moduleRoleBo.getId() != null) {
            Search search = new Search();
            search.addFilterEqual("sysModuleRole.id", moduleRoleBo.getId());
            List<PrivilegesPo> list = privilegesDao.search(search);
            List<PrivilegeBo> boList = modelMapper.map(list, new TypeToken<List<PrivilegeBo>>() {
            }.getType());
            privilegeBoList.addAll(boList);
            final List<Integer> operatorIds = list.stream().map(e -> e.getSysOperator().getId()).collect(Collectors.toList());
            neededAdded = operatorList.stream().filter(e -> !operatorIds.contains(e.getId())).collect(Collectors.toList());
        }
        for (OperatorPo operatorPo : neededAdded) {
            PrivilegeBo privilegeBo = new PrivilegeBo();
            privilegeBo.setChecked(false);
            privilegeBo.setOperatorId(operatorPo.getId());
            privilegeBo.setOperatorName(operatorPo.getName());
            privilegeBoList.add(privilegeBo);
        }
        privilegeBoList = privilegeBoList.stream().sorted((h1, h2) -> h1.getOperatorId().compareTo(h2.getOperatorId())).collect(Collectors.toList());
        return privilegeBoList;
    }

    private List<RoleOrgaccessBo> initOrgAccessForRoleBoList(RolePo rolePo) {
        Search search = new Search();
        search.addFilterEqual("isactive", "Y");
        List<RoleOrgaccessBo> roleOrgAccessBoList = new ArrayList<>();
        if (rolePo != null) {
            List<RoleOrgaccessPo> orgAccessList = rolePo.getSysRoleOrgaccesses();
            List<RoleOrgaccessBo> selectedOrgList = modelMapper.map(orgAccessList, new TypeToken<List<RoleOrgaccessBo>>() {
            }.getType());
            roleOrgAccessBoList.addAll(selectedOrgList);
            List<Integer> roleIdList = orgAccessList.stream().map(e -> e.getSysOrg().getId()).collect(Collectors.toList());
            search.addFilterNotIn("id", roleIdList);

        }
        List<OrgPo> orgPoList = orgDao.search(search);
        orgPoList.forEach(e -> {
            RoleOrgaccessBo bo = new RoleOrgaccessBo();
            bo.setChecked(false);
            bo.setOrgId(e.getId());
            bo.setOrgName(e.getName());
            roleOrgAccessBoList.add(bo);
        });
        return roleOrgAccessBoList;
    }

    @Override
    public RoleBo getRoleBo(Integer id) {

        RolePo rolePo = roleDao.find(id);

        /**
         * 初始化roleOrgAccess
         */
        RoleBo roleBo = initRoleBoByPo(rolePo);
        return roleBo;
    }

    @Override
    public void deletes(Integer[] ids) {
        for (Integer id : ids) {
            accountService.deleteRole(id);
        }
        roleDao.removeByIdsForLog(ids);
    }
}
