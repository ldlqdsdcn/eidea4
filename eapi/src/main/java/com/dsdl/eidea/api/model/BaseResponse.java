package com.dsdl.eidea.api.model;

/**
 * Created by 刘大磊 on 2017/7/13 8:13.
 */
public enum BaseResponse {
    SERVER_ERROR(101,201,"服务器异常");
    BaseResponse(int code,int businessCode,String errorMessage)
    {
        this.code=code;
        this.businessCode=businessCode;
        this.errorMessage=errorMessage;
    }
    private int code;
    private int businessCode;
    private String errorMessage;
}
