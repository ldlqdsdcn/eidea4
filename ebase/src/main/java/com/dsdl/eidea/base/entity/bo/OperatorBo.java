package com.dsdl.eidea.base.entity.bo;

import com.dsdl.eidea.base.entity.po.PrivilegesPo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by admin on 2016/12/20.
 */
@Getter
@Setter
public class OperatorBo {
    private Integer id;
    private String no;
    private String name;
    private String isactive;
    private String remark;
    private boolean created=false;
    private boolean checked;
}
