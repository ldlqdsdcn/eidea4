package com.dsdl.eidea.core.web.result;

import com.dsdl.eidea.api.model.Client;
import com.dsdl.eidea.core.web.result.def.ResultCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

/**
 * Created by 刘大磊 on 2017/4/13 18:00.
 */
@Getter
@ToString
public class ClientApiResult {
    private static final long serialVersionUID = 1L;

    private boolean success;
    private int code;
    private String message;
    private String requestId;
    private int errorCode;
    private Client data;

    private ClientApiResult() {
        this.requestId = UUID.randomUUID().toString().replaceAll("\\-", "");
    }

    public static  ClientApiResult success(Client data) {
        ClientApiResult apiResult = new ClientApiResult();
        apiResult.success = true;
        apiResult.code = ResultCode.SUCCESS.getCode();
        apiResult.message = ResultCode.SUCCESS.getMessage();
        apiResult.data = data;
        return apiResult;
    }

    public static ClientApiResult fail(int errorCode, String message) {
        ClientApiResult apiResult = new ClientApiResult();
        apiResult.success = false;
        apiResult.code = ResultCode.FAILURE.getCode();
        apiResult.message = message;
        apiResult.errorCode = errorCode;
        return apiResult;
    }

    public static ClientApiResult fail(int errorCode, Client errorMessage) {
        ClientApiResult apiResult = new ClientApiResult();
        apiResult.success = false;
        apiResult.code = ResultCode.FAILURE.getCode();
        apiResult.message = ResultCode.FAILURE.getMessage();
        apiResult.data = errorMessage;
        apiResult.errorCode = errorCode;
        return apiResult;
    }
}
