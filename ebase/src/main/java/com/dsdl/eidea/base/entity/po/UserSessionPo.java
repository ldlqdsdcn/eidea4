package com.dsdl.eidea.base.entity.po;


import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import com.dsdl.eidea.base.entity.po.UserPo;

import lombok.Data;

/**
 * CoreUserSession entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "sys_user_session", catalog = "e_idea")
@Getter
@Setter
public class UserSessionPo implements java.io.Serializable {

	// Fields
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_user_id", nullable = false)
	private UserPo userPo;
	@Column(name = "session_id", nullable = false, length = 100)
	private String sessionId;
	@Column(name = "login_date", length = 19)
	private Date loginDate;
	@Column(name = "logout_date", length = 19)
	private Date logoutDate;
	@Column(name = "remote_addr", length = 50)
	private String remoteAddr;
	@Column(name = "remote_host", length = 50)
	private String remoteHost;
	@Column(name = "token",length = 800)
	private String token;
}