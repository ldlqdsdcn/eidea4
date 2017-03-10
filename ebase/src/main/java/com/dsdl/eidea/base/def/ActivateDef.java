package com.dsdl.eidea.base.def;

/**
 * Created by 刘大磊 on 2016/12/27 9:14.
 */
public enum  ActivateDef {
    ACTIVATED("Y","有效"),INACTIVATED("N","无效");
    ActivateDef(String key,String desc)
    {
        this.key=key;
        this.desc=desc;

    }

    private final String key;
    private final String desc;

    public final String getKey() {
        return key;
    }

    public final String getDesc() {
        return desc;
    }
}
