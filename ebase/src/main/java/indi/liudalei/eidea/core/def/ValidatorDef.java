package indi.liudalei.eidea.core.def;

/**
 * Created by 刘大磊 on 2017/5/4 13:36.
 * 验证规则类型
 */
public enum ValidatorDef {
    /**
     * 长度
     */
    LENGTH(0,"长度"),
    /**
     * 最大值最小值
     */
    SIZE(1,"大小"),
    /**
     * 邮箱
     */
    EMAIL(2,"邮箱"),
    /**
     * 整数
     */
    INT(3,"整数"),
    /**
     * 数字
     */
    FLOAT(4,"数字"),
    /**
     * 正则表达式
     */
    PATTERN(5,"正则表达式")
    ;
    private int key;
    private String desc;
    ValidatorDef(int key,String desc)
    {
        this.key=key;
        this.desc=desc;
    }

    public int getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
