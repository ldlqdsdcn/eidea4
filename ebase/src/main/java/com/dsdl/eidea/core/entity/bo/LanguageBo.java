package com.dsdl.eidea.core.entity.bo;

import com.dsdl.eidea.core.entity.po.LanguageTrlPo;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by 刘大磊 on 2016/12/8 16:06.
 */
@Getter
@Setter
public class LanguageBo {
    @Length(min = 2,max = 5)
    private String code;
    @NotBlank(message="client.error.name.not_null")
    @Length(min=1,max=200,message="language.error.name.length_error")
    private String name;
    @Length(max = 200,message="client.error.remark.length_error")
    private String remark;
    @NotBlank(message="client.error.isactive.not_null")
    @Length(min = 1,max = 1,message="client.error.isactive.length_error")
    private String isactive;
    /**
     * 是否新建 默认为false
     */
    private boolean created=false;
    private List<LanguageTrlBo> languageTrlBoList = new ArrayList<>();
}
