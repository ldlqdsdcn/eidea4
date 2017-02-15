package com.dsdl.eidea.base.entity.bo;

import com.dsdl.eidea.base.entity.po.OperatorPo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/23 15:16.
 */
@Getter
@Setter
public class ModuleRoleBo {
    private Integer id;
    private Integer roleId;
    private String roleName;
    private String moduleName;
    private Integer moduleId;
    private List<PrivilegeBo> privilegeBoList;

}
