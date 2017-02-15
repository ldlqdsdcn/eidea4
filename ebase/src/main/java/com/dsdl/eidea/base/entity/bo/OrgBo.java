package com.dsdl.eidea.base.entity.bo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by 刘大磊 on 2016/12/12 17:47.
 */
@Getter
@Setter
public class OrgBo {
    // Fields
    private Integer id;
    @NotNull(message = "entity.information.not.empty")
    private ClientBo client;
    @NotBlank(message = "org.error.no.not_null")
    @Length(min = 3,max = 10,message = "org.error.no.length_error")
    private String no;
    @NotBlank(message = "org.error.name.not_null")
    @Length(min = 2,max = 20,message = "org.error.name.length_error")
    private String name;
    @NotNull(message = "client.error.isactive.not_null")
    @Length(min = 1,max = 1,message = "client.error.isactive.length_error")
    private String isactive;
    @Length(max = 200,message = "client.error.remark.length_error")
    private String remark;
}
