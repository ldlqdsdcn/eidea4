package com.dsdl.eidea.base.entity.bo;

import lombok.Data;

import java.util.Date;

@Data
public class ChangelogBo extends CommonUploadBo{
	private Integer id;
	private String tableName;
	private String poClass;
	private String sysUser;
	private String name;
	private String operateType;
	private String context;
	private Date inDate;
	private String pk;
	private String buPk;
	
}
