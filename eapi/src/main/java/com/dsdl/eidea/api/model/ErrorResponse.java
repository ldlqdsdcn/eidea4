package com.dsdl.eidea.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by 刘大磊 on 2017/7/13 8:08.
 */
@Getter
@Setter
@ApiModel(description="实体信息对象")
public class ErrorResponse {
    @ApiModelProperty(value = "业务编码",required = true,example = "201201 是该实体不允许修改")
    private int businessCode=200;
    @ApiModelProperty(value = "错误编码",required = true,example = "401 没有权限 500 系统错误 333 房产信息统计错误 545 你需要充值")
    private int errorCode=400;
    @ApiModelProperty(value = "invalidApiKey",required = true)
    private String message;
    @ApiModelProperty(value = "输入的 ApiKey 无效",required = true)
    private String detail;
}
