package com.dsdl.eidea.base.entity.bo;

import com.dsdl.eidea.base.entity.po.RolePo;
import com.dsdl.eidea.base.entity.po.UserPo;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Bobo on 2016/12/17 18:06.
 */
@Getter
@Setter
public class UserRoleBo {
    private Integer id;
    private Integer sysUserId;
    private Integer sysRoleId;
}
