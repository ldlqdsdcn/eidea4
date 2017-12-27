package indi.liudalei.eidea.base.entity.bo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by 刘大磊 on 2017-01-27.
 */
@Getter
@Setter
public class ChangePasswordBo {
    @NotBlank(message = "oldpassword.not.allowed.empty")
    private String oldPassword;
    @NotBlank(message = "password.not.allowed.empty")
    @Length(min = 6, max = 45, message = "password.length")
    private String password;
}
