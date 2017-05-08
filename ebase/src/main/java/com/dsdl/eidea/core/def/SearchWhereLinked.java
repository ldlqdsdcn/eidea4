package com.dsdl.eidea.core.def;

/**
 * Created by 刘大磊 on 2017/5/8 15:31.
 * 查询条件连接符
 */
public enum SearchWhereLinked {
    AND("and","and 连接条件"),OR("or","or 连接条件");
    SearchWhereLinked(String key,String desc)
    {
        this.key=key;
        this.desc=desc;
    }
    private String key;
    private String desc;

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
