package com.dsdl.eidea.core.web.result.def;

/**
 * Created by admin on 2016/9/6.
 */
public enum  ResultCode {
    SUCCESS(0,"成功"),FAILURE(1,"失败"),NEEDED_LOGIN(2,"您需要登录"),NO_PRIVILEGES(3,"没有权限");
    private int    code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
