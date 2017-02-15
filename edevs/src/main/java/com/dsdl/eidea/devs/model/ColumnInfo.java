package com.dsdl.eidea.devs.model;

import lombok.Data;

/**
 * Created by admin on 2016/8/26.
 */
@Data
public class ColumnInfo implements java.io.Serializable{
    /**
     * 字段名
     */
    private String columnName;
    /**
     * 数据类型类型
     */
    private String type;
    /**
     * 数据类型 enum值
     */
    private int dataType;
    /**
     * 字段大小
     */
    private int columnSize;
    /**
     * 小数位数
     */
    private int decimalDigits;
    /**
     * 是否为空
     */
    private Boolean nullable;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 默认值
     */
    private String columnDefault;
    /**
     * 对象属性
     */
    private String propertyName;
    /**
     * 是否关键字
     */
    private boolean keyWord;
    /**
     * 属性类型
     */
    private String propertyType;


}
