package com.dsdl.eidea.core.web.result;

import com.dsdl.eidea.core.web.result.def.ResultCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by 刘大磊 on 2017/4/14 8:42.
 */
@Getter
@Setter
public abstract class BaseApiResult<T> implements Serializable {
    private boolean success;
    private int code;
    private String message;
    private String requestId;
    private int errorCode;
    public abstract  T getData();
    public BaseApiResult  initApiResult() {
        this.requestId = UUID.randomUUID().toString().replaceAll("\\-", "");
        return this;
    }
    public  BaseApiResult success() {

        this.success = true;
        this.code = ResultCode.SUCCESS.getCode();
        this.message = ResultCode.SUCCESS.getMessage();
        return this;
    }

    public BaseApiResult fail(int errorCode, String message) {
        this.success = false;
        this.code = ResultCode.FAILURE.getCode();
        this.message = message;
        this.errorCode = errorCode;
        return this;
    }
}
