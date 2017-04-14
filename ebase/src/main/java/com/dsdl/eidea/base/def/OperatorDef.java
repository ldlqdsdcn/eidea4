package com.dsdl.eidea.base.def;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 刘大磊 on 2016/12/26 13:25.
 * 操作权限enumerate
 */
public enum OperatorDef {

    ADD("add","添加"),UPDATE("update","修改"),DELETE("delete","删除"),VIEW("view","查看");
    OperatorDef(String key, String desc)
    {
        this.key=key;
        this.desc=desc;

    }
    private static final Map<String,OperatorDef> OPERATOR_MAP=new HashMap<>();
    static
    {
        OperatorDef[] operatorDefs=  OperatorDef.values();
        for(OperatorDef operatorDef:operatorDefs)
        {
            OPERATOR_MAP.put(operatorDef.getKey(),operatorDef);
        }
    }
    public static OperatorDef getOperatorDefByKey(String key)
    {
        return OPERATOR_MAP.get(key);
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
