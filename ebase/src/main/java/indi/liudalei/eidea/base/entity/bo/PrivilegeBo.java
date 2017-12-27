package indi.liudalei.eidea.base.entity.bo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by 刘大磊 on 2016/12/23 15:24.
 * 权限集 Bo 类
 */
@Getter
@Setter
public class PrivilegeBo {
    private Integer id;
    private Integer moduleRoleId;
    private Integer operatorId;
    private String operatorName;
    private boolean checked;
}
