package com.dsdl.eidea.general.bo;

import com.dsdl.eidea.base.entity.po.FieldPo;
import com.dsdl.eidea.base.entity.po.FieldTrlPo;
import com.dsdl.eidea.base.entity.po.FieldValidatorPo;
import com.googlecode.genericdao.search.Field;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by 刘大磊 on 2017/5/27 8:58.
 */
@Getter
@Setter
public class FieldStructureBo {
    /**
     * 字段翻译
     */
    private FieldTrlPo fieldTrlPo;
    /**
     * 字段内容
     */
    private FieldPo fieldPo;
    /**
     * 字段验证
     */
    private List<FieldValidatorPo> fieldValidatorPoList;




}
