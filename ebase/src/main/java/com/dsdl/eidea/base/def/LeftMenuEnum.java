package com.dsdl.eidea.base.def;

/**
 * Created by Bobo on 2016/12/26 9:29.
 */
public enum LeftMenuEnum {
    MESSAGE_MENUTYPE_ONE(1,"文件夹"),MESSAGE_MENUTYPE_TWO(2,"超链接");
    private int code;
    private String message;

    LeftMenuEnum(int code, String message)
    {
        this.code=code;
        this.message=message;
    }
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
