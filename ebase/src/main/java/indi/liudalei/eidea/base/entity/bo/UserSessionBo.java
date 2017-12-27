package indi.liudalei.eidea.base.entity.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserSessionBo implements java.io.Serializable{
	private Integer id;
	private String sessionId;
	private Date loginDate;
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
