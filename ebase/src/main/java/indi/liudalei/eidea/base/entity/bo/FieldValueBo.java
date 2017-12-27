package indi.liudalei.eidea.base.entity.bo;

import indi.liudalei.eidea.base.entity.po.FieldPo;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by 刘大磊 on 2017/5/26 16:15.
 */
@Getter
@Setter
public class FieldValueBo {
    private FieldPo fieldPo;
    private Object value;
}
