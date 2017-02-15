package com.dsdl.eidea.core.def;

/**
 * Created by 刘大磊 on 2016/12/22 15:29.
 */
public enum  ChangeOperatorType {
    INSERT("I","插入"),UPDATE("U","更新"),DELETE("D","删除");
    ChangeOperatorType(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    private final String key;
    private final String desc;


    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
