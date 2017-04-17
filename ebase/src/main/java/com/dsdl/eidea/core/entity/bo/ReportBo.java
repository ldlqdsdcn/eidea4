package com.dsdl.eidea.core.entity.bo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 车东明 on 2017/4/17.
 */
@Getter
@Setter
public class ReportBo implements Serializable {
    @NotNull(message = "identity.not.allowed.empty")
    private String key;
    @Length(min = 1,max = 45,message = "base.msgtext.length")
    private String value;
    private boolean created = false;
    private List<MessageTrlBo> messageTrlBoList = new ArrayList<>();
}
