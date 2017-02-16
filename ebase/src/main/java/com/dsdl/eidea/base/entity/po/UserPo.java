package com.dsdl.eidea.base.entity.po;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



/**
 * SysUser entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_user", catalog = "e_idea")
@Data
public class UserPo implements java.io.Serializable {

	// Fields
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Lob
	@Column(name = "username", unique = true, nullable = false, length = 50)
	private String username;
	@Column(name = "password", nullable = false, length = 45)
	private String password;
	@Column(name = "name", nullable = false, length = 45)
	private String name;
	@Column(name = "init", nullable = false, length = 1)
	private String init;
	@Column(name = "email", length = 100)
	private String email;
	@Column(name = "telephone", length = 45)
	private String telephone;
	@Column(name = "isactive", length = 1)
	private String isactive;
	@Column(name = "user_pic")
	private String userPic;
	@Column(name = "client_id", length = 11)
	private Integer clientId;
	@Column(name = "org_id", length = 11)
	private Integer orgId;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "userPo")
	private List<UserSessionPo> coreUserSessions = new ArrayList<UserSessionPo>(0);
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysUser")
	private List<UserRolePo> sysUserRoles = new ArrayList<UserRolePo>(0);

	// Constructors

	/** default constructor */
	public UserPo() {
	}

}