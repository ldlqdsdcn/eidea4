package com.dsdl.eidea.base.entity.bo;

import com.dsdl.eidea.base.entity.po.OrgPo;
import com.dsdl.eidea.base.entity.po.RolePo;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by 刘大磊 on 2016/12/23 16:14.
 */
@Getter
@Setter
public class RoleOrgaccessBo {
    private Integer id;
    private  Integer orgId;
    private String orgName;
    private Integer roleId;
    private String roleName;
    private boolean checked;
}
