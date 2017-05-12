package com.dsdl.eidea.core.def;

/**
 * Created by 刘大磊 on 2016/12/19 13:21.
 * 编辑页面、查询页面 输入类型
 *
 */
public enum FieldInputType {
    INPUT(1,"输入框"),SELECT(2,"下拉框"),DATEPICKER(3,"日期选择"),DATETIMEPICKER(4,"日期时间选择"),TEXTAREA(5,"文本内容输入"),
    /**
     * 输入自动完成
     * 目前还不支持
     */
    AUTO_COMPLISH(6,"自动完成")
    ;
    FieldInputType(int key, String desc)
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
