package com.dsdl.eidea.devs.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by 刘大磊 on 2017/1/16 9:02.
 * 生成前端model类
 */
@Getter
@Setter
public class JspModelProp {
    public JspModelProp(String prop, String label)
    {
        this(prop,label,false);
    }
    private JspModelProp(String prop, String label, boolean isDate)
    {
        this(prop,label,isDate,true);
    }
    public JspModelProp(String prop, String label, boolean isDate, boolean edit)
    {
        this.prop=prop;
        this.label=label;
        this.date=isDate;
        this.edit=edit;
    }

    /**
     * 属性名
     */
    private String prop;
    /**
     * 属性标签
     */
    private String label;
    /**
     * 国际化标签
     */
    private String propertyLabel;
    /**
     * 是否为日期类型
     */
    private boolean date;
    /**
     * 是否为日期时间类型
     */
    private boolean datetime;
    /**
     * 是否可编辑
     */
    private boolean edit;
    /**
     * 是否勾选框
     */
    private boolean booleanTag;
    /**
     * css style
     */
    private String cssStyle;
    /**
     * 是否外键管理
     */
    private boolean foreign;
    /**
     * 外键管理模块
     */
    private String foreignModel;
    /**
     * 是否必填
     */
    private boolean required;
    /**
     * 输入类型
     */
    private int inputType;
    private List<Integer> validationList;

}
