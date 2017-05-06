package com.dsdl.eidea.core.entity.bo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2016/12/16.
 */
@Getter
@Setter
public class MessageBo {
    @NotNull(message = "identity.not.allowed.empty")
    private String key;
    @Length(min = 1, max = 45, message = "base.msgtext.length")
    private String msgtext;
    @Length(min = 1, max = 1, message = "isactive.length")
    private String isactive;
    private boolean created = false;
    private List<MessageTrlBo> messageTrlBoList = new ArrayList<>();
}
