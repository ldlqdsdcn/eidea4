package indi.liudalei.eidea.devs.def;

/**
 * Created by admin on 2016/8/29.
 */
public enum  ValidationDef {
    REQUIRED(0,"Required"),INT(1,"INTEGER"),FLOAT(2,"浮点数"),DATE(3,"日期"),DATETIME(4,"日期时间");
    ValidationDef(int key,String desc)
    {
        this.key=key;
        this.desc=desc;
    }
    private int key;
    private String desc;

    public int getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
