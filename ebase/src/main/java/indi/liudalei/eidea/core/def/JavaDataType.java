package indi.liudalei.eidea.core.def;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 刘大磊 on 2016/8/23.
 * eidea项目用到的数据类型
 */
public enum JavaDataType implements java.io.Serializable {


    INT(0, "整数", "Integer"), FLOAT(1, "浮点数", "float"), STRING(2, "字符串", "String"), DATE(3, "日期", "Date"), DECIMAL(5, "Decimal", "BigDecimal"), OTHER(4, "其他", "String"),
    LONG(6, "长整型", "Long"), BYTES(7, "字节型", "byte"), DOUBLE(8, "双精度型", "Double");
    private static Map<Integer, JavaDataType> JAVA_DATA_TYPE_MAP = new HashMap<>();

    static {
        JavaDataType[] javaDataTypes = JavaDataType.values();
        for (JavaDataType javaDataType : javaDataTypes) {
            JAVA_DATA_TYPE_MAP.put(javaDataType.getKey(), javaDataType);
        }
    }

    JavaDataType(int key, String desc, String value) {
        this.key = key;
        this.desc = desc;
        this.value = value;
    }

    public static JavaDataType getJavaDataTypeByKey(Integer key) {
        return JAVA_DATA_TYPE_MAP.get(key);
    }

    private final int key;
    private final String desc;
    private final String value;

    public int getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }

    public String getValue() {
        return value;
    }
}
