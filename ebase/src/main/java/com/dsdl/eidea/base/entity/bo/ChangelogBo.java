package com.dsdl.eidea.base.entity.bo;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import com.dsdl.eidea.base.entity.po.UserPo;
import com.dsdl.eidea.core.entity.po.TablePo;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ChangelogBo {
	private Integer id;
	private String tableName;
	private String poClass;
	private String sysUser;
	private String name;
	private String operateType;
	private String context;
	@JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
	private Date inDate;
	private String pk;
	private String buPk;
	
}
