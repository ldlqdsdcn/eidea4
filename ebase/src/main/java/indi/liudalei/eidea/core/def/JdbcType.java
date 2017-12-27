package indi.liudalei.eidea.core.def;

/**
 * Created by 刘大磊 on 2016/12/6 12:38.
 */
public enum JdbcType {
    TINYINT(-6,"TINYINT",0),//-128到127  0-255
    SMALLINT(5,"SMALLINT",0),//–32768 到 32767 0-65535
    BIGINT(-5,"BIGINT",0),
    BIT(-7,"BIT",0),
    INTEGER(	4,"整数",0),
    DOUBLE(8,"DOUBLE",1),
    FLOAT(7,"FLOAT",1),
    DECIMAL(3,"DECIMAL",5),
    CHAR(1,"CHAR",2),
    VARCHAR(12,"VARCHAR",2),
    DATE(91,"DATE YEAR",2),
    TIME(92,"TIME",2),
    TIMESTAMP(93,"TIMESTAMP",3),
    TINYBLOB(-2,"TINYBLOB",4),
    BLOB(-4,"BLOB",4),
    TEXT(-1,"Text",4)
    ;
    JdbcType(int key,String desc,int type)
    {
        this.key=key;
        this.desc=desc;
        this.type=type;
    }
    private final int key;
    private final String desc;
    private final int type;

    public int getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
    public int getType()
    {
        return type;
    }
}
