package com.dsdl.eidea.core.def;

/**
 * Created by 刘大磊 on 2017/5/9 16:00.
 * entity的类型
 */
public enum EntityType {
    HIBERNATE_PO(1,"Hibernate Po类"),DATABASE_TABLE(2,"数据库表");
    EntityType(int key,String desc)
    {
        this.key=key;
        this.desc=desc;
    }

    private int key;
    private String desc;

    /**
     * key值
     * @return
     */
    public int getKey() {
        return key;
    }

    /**
     * 描述信息
     * @return
     */
    public String getDesc() {
        return desc;
    }
}
