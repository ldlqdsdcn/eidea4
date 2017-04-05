package com.dsdl.eidea.base.entity.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ChangelogBo {
	private Integer id;
	private String tableName;
	private String poClass;
	private String sysUser;
	private String name;
	private String operateType;
	private String context;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date inDate;
	private String pk;
	private String buPk;
	
}
