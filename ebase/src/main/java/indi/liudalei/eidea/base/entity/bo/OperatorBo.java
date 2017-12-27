package indi.liudalei.eidea.base.entity.bo;

import lombok.Getter;
import lombok.Setter;

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
