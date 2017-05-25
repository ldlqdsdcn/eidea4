package com.dsdl.eidea.base.entity.bo;

import com.dsdl.eidea.base.entity.po.FieldPo;
import com.dsdl.eidea.base.entity.po.FieldValidatorPo;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by 刘大磊 on 2017/5/3 16:23.
 * 字段类
 */
@Getter
@Setter
public class  FieldBo {
    /**
     * 字段属性
     */
    private FieldPo fieldPo;
    /**
     * 字段值
     */
    private Object fieldValue;
    /**
     * 字段验证
     */
    private List<FieldValidatorPo> fieldValidatorList;
}
