package com.dsdl.eidea.core.def;

/**
 * Created by admin on 2016/8/23.
 */
public enum JavaDataType implements java.io.Serializable{
    INT(0,"整数","Integer"),FLOAT(1,"浮点数","Double"),STRING(2,"字符串","String"),DATE(3,"日期","Date"),DECIMAL(5,"Decimal","BigDecimal"),OTHER(4,"其他","String");

    JavaDataType(int key, String desc, String value)
    {
        this.key=key;
        this.desc=desc;
        this.value=value;
    }
    private final int key;
    private final String desc;
    private final String value;

    public int getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public String getValue() {
        return value;
    }
}
