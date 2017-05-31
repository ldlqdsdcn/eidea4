package com.dsdl.eidea.core.def;

/**
 * Created by 刘大磊 on 2017/5/26 8:52.
 */
public enum  FieldShowType {
    STRING(0,"字符串"),DATE(1,"日期"),DATETIME(2,"日期时间"),NUMBER(3,"数值"),SELECT(4,"关联SELECT"),LINKED(5,"关联");
    private int key;
    private String desc;
    FieldShowType(int key,String desc)
    {
        this.key=key;
        this.desc=desc;
    }

    public int getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
