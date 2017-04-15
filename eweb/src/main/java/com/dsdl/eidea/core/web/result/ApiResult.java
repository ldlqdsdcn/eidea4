package com.dsdl.eidea.core.web.result;


import com.dsdl.eidea.core.web.result.def.ResultCode;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by admin on 2016/8/23.
 */
@Getter
@ToString
public class ApiResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success;
    private int code;
    private String message;
    private String requestId;
    private int errorCode;
    private T data;

    private ApiResult() {
        this.requestId = UUID.randomUUID().toString().replaceAll("\\-", "");
    }

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.success = true;
        apiResult.code = ResultCode.SUCCESS.getCode();
        apiResult.message = ResultCode.SUCCESS.getMessage();
        apiResult.data = data;
        return apiResult;
    }

    public static <T> ApiResult<T> fail(int errorCode, String message) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.success = false;
        apiResult.code = ResultCode.FAILURE.getCode();
        apiResult.message = message;
        apiResult.errorCode = errorCode;
        return apiResult;
    }

    public static <T> ApiResult<T> fail(int errorCode, T errorMessage) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.success = false;
        apiResult.code = ResultCode.FAILURE.getCode();
        apiResult.message = ResultCode.FAILURE.getMessage();
        apiResult.data = errorMessage;
        apiResult.errorCode = errorCode;
        return apiResult;
    }
}
