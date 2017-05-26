package com.dsdl.eidea.base.entity.bo;

import com.dsdl.eidea.base.entity.po.FieldPo;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by 刘大磊 on 2017/5/26 16:15.
 */
@Getter
@Setter
public class FieldValueBo {
    private FieldPo fieldPo;
    private String key;
    private String value;
}
