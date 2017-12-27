package indi.liudalei.eidea.base.entity.bo;

import indi.liudalei.eidea.base.entity.po.FieldPo;
import indi.liudalei.eidea.core.def.FieldInputType;
import indi.liudalei.eidea.core.def.JavaDataType;
import lombok.Getter;
import lombok.Setter;

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

    private FieldPo fieldPo;
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
