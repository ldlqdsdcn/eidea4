package com.dsdl.eidea.base.entity.bo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserSessionBo implements java.io.Serializable{
	private Integer id;
	private String sessionId;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date loginDate;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date logoutDate;
	private Integer userId;
	private String remoteAddr;
	private String remoteHost;
	private String username;
	/**
	 * 保存当前用户的token
	 */
	private String token;
}
