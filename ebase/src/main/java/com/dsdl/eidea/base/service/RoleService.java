package com.dsdl.eidea.base.service;

import java.util.List;

import com.dsdl.eidea.base.entity.bo.RoleBo;
import com.dsdl.eidea.base.entity.po.RolePo;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Search;

public interface RoleService {
    void save(RoleBo roleBo);

    boolean findExistClient(String no);

    RoleBo getRoleBo(Integer id);

    List<RoleBo> getRoleList(ISearch search);

    void deletes(Integer[] ids);

    RoleBo getInitRoleBo(RoleBo roleBo);

    boolean getIsExit(Integer id);

}
