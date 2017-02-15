package com.dsdl.eidea.core.web.def;

/**
 * Created by admin on 2016/9/1.
 */
public enum PagingType {

    MEMORY(1, "内存分页"), DATABASE(2, "数据库分页");

    PagingType(int key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    private int key;
    private String desc;

    public int getKey() {
        return key;
    }

    public String getString() {
        return desc;
    }
}
