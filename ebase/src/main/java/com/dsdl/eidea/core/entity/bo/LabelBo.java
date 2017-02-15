package com.dsdl.eidea.core.entity.bo;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Data
public class LabelBo {
		private Integer id;
	 @NotNull(message = "identity.not.allowed.empty")
	    private String key;
	@Length(min = 1,max = 45,message = "base.msgtext.length")
	    private String msgtext;
	@Length(min = 1,max = 1,message = "isactive.length")
	    private String isactive;
	    /**
	     * 是否新建 默认为false
	     */
	    private boolean created=false;
	    private List<LabelTrlBo> labelTrlBoList = new ArrayList<>();
}
