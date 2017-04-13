package com.dsdl.eidea.core.web.result.def;

import lombok.Getter;

/**
 * Created by admin on 2016/9/6.
 * 错误状态码
 */
@Getter
public enum  ErrorCodes {
    BUSINESS_EXCEPTION(1,"业务逻辑异常"),NO_PRIVILEGES(2,"没有操作权限"),VALIDATE_PARAM_ERROR(3,"参数验证错误"),NO_LOGIN(4,"你需要登录");
    private int    code;
    private String message;

    ErrorCodes(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
