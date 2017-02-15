package com.dsdl.eidea.base.def;

/**
 * Created by 刘大磊 on 2016/12/27 12:57.
 */
public enum  MenuTypeDef {
    FOLDER(1,"菜单文件夹"),HREF(2,"菜单");
    MenuTypeDef(int key,String desc)
    {
        this.key=key;
        this.desc=desc;

    }

    private final int key;
    private final String desc;

    public final int getKey() {
        return key;
    }

    public final String getDesc() {
        return desc;
    }
}
