package com.dsdl.eidea.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by 刘大磊 on 2017/4/13 10:38.
 */
@Getter
@Setter
@ApiModel(description="实体信息对象")
public class Client {
    @ApiModelProperty(value = "主键")
    private Integer id;
    @ApiModelProperty(value = "实体名称",required = true)
    private String name;
    @ApiModelProperty(value = "实体地址",required = true)
    private String address;
    @ApiModelProperty(value = "国家")
    private String country;
    @ApiModelProperty(value = "城市",required = true)
    private String city;
    @ApiModelProperty(value = "创建时间",required = true)
    private Date createdDate;
    @ApiModelProperty(value = "修改时间")
    private Date updatedDate;
}
