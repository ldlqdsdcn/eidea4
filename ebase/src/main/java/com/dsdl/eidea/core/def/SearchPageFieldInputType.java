package com.dsdl.eidea.core.def;

/**
 * Created by 刘大磊 on 2016/12/19 13:21.
 *
 */
public enum SearchPageFieldInputType {
    INPUT(1,"输入框"),SELECT(2,"下拉框"),DATEPICKER(3,"日期选择"),DATETIMEPICKER(4,"日期时间选择");
    SearchPageFieldInputType(int key,String desc)
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
