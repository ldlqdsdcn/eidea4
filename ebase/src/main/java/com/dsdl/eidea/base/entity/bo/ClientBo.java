package com.dsdl.eidea.base.entity.bo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by 刘大磊 on 2016/12/12 17:47.
 */
@Getter
@Setter
public class ClientBo {
    private Integer id;
    @NotBlank(message="client.error.no.not_null")
    @Length(min=2,max=10,message="client.error.no.length_error")
    private String no;
    @NotBlank(message="client.error.name.not_null")
    @Length(min=2,max=30,message="client.error.name.length_error")
    private String name;
    @NotBlank(message="client.error.isactive.not_null")
    @Length(min = 1,max = 1,message="client.error.isactive.length_error")
    private String isactive;
    @Length(max = 200,message="client.error.remark.length_error")
    private String remark;
}
