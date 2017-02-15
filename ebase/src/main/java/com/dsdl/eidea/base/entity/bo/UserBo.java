package com.dsdl.eidea.base.entity.bo;

import com.dsdl.eidea.base.entity.po.UserRolePo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bobo on 2016/12/17 13:59.
 */
@Getter
@Setter
public class UserBo {
    private Integer id;
    @NotBlank(message = "logon.name.not.empty")
    @Length(min = 1,max = 50,message = "number.length")
    private String username;
    @NotBlank(message = "password.not.allowed.empty")
    @Length(min = 6,max = 45,message = "password.length")
    private String password;
    @NotBlank(message = "pagemenu.name.check")
    @Length(min = 1,max = 45,message = "pagemenu.name.prompt")
    private String name;
    @NotBlank(message = "initial.user.not.allowed.empty")
    @Length(min = 1,max = 1,message = "initial.length")
    private String init;
    @Length(min = 1,max = 100,message = "mailbox.length")
    private String email;
    @Length(min = 1,max = 45,message = "telephone.number.length")
    private String telephone;
    @Length(min = 1,max = 1,message = "isactive.length")
    private String isactive;
    private String userPic;
    @NotNull(message = "entity.information.not.empty")
    private RoleBo role;
    @Length(min = 1,max = 11,message = "entity.id.length")
    private Integer clientId;
    @Length(min = 1,max = 11,message = "tissue.id.length")
    private Integer orgId;
    private Integer[] roleIds;
    private Integer[] roleRemoveIds;
    private boolean created=false;
    private String code;
}
