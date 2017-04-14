package com.dsdl.eidea.core.web.result;

import com.dsdl.eidea.api.model.Client;
import com.dsdl.eidea.core.web.result.def.ErrorCodes;
import com.dsdl.eidea.core.web.result.def.ResultCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

/**
 * Created by 刘大磊 on 2017/4/13 18:00.
 */
@ToString
public class ClientApiResult extends BaseApiResult<Client> {
    private Client client;
    /**
     * 操作成功返回消息
     * @param
     */
    public ClientApiResult() {
        this.success();
    }
    /**
     * 操作成功返回消息
     * @param client
     */
    public ClientApiResult(Client client) {
        this.client = client;
        this.success();
    }

    /**
     * 操作失败返回消息
     * @param errorCodes
     * @param message
     */
    public ClientApiResult(ErrorCodes errorCodes, String message) {
        fail(errorCodes.getCode(), message);
    }

    @Override
    public Client getData() {
        return client;
    }
}
