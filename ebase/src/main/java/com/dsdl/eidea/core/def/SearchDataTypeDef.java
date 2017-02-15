package com.dsdl.eidea.core.def;

/**
 * @author dalei.liu 2016/8/29 20:38.
 */
public enum SearchDataTypeDef {
    CHAR(1,"字符"),NUMBER(2,"整数"),FLOAT(3,"数值"),DATE(4,"日期"),DATETIME(5,"日期时间");
    SearchDataTypeDef(int key, String desc)
    {
        this.key=key;
        this.desc=desc;
    }
    private final int key;
    private final String desc;

    public int getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
