package indi.liudalei.eidea.base.service;

import indi.liudalei.eidea.base.entity.bo.RoleBo;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;

public interface RoleService {
    void save(RoleBo roleBo);

    boolean findExistRole(String no);

    RoleBo findExistRoleByName(String roleName);

    RoleBo getRoleBo(Integer id);

    PaginationResult<RoleBo> getRoleList(Search search, QueryParams queryParams);

    void deletes(Integer[] ids);

    RoleBo getInitRoleBo(RoleBo roleBo);

    boolean getHasUsers(Integer id);

}
