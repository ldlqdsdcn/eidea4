package indi.liudalei.eidea.base.def;

/**
 * Created by 刘大磊 on 2017/5/31 16:09.
 * char bool枚举
 */
public enum  BoolChar {
    TRUE("Y"),FALSE("N");
    BoolChar(String key)
    {
        this.key=key;
    }
    private String key;

    public String getKey() {
        return key;
    }
}
