package com.dsdl.eidea.base.entity.bo;

import com.dsdl.eidea.base.entity.po.FieldPo;
import com.dsdl.eidea.base.entity.po.FieldValidatorPo;
import com.dsdl.eidea.core.def.FieldInputType;
import com.dsdl.eidea.core.def.JavaDataType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by 刘大磊 on 2017/5/25 15:51.
 */
@Getter
@Setter
public class FieldInListPageBo {
    public FieldInListPageBo()
    {
        pattern="";
    }
    private Integer id;
    /**
     * 字段名
     */
    private String name;
    /**
     * 字段描述
     */
    private String description;
    /**
     * 字段值
     */
    private String fieldValue;
    /**
     * 数据类型
     */
    private JavaDataType dataType;
    /**
     * 字段输入类型
     */
    private FieldInputType fieldInputType;
    /**
     * 日期时间格式化
     */
    private String pattern;
}
