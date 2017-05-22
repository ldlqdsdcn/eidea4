package com.dsdl.eidea.base.entity.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * SysRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_role", catalog = "e_idea", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@Getter
@Setter
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
public class RolePo implements java.io.Serializable {

	// Fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	private String no;
	@Column(name = "name", unique = true, nullable = false, length = 45)
	private String name;
	@Column(name = "remark", nullable = false, length = 200)
	private String remark;
	@Column(name = "isactive", length = 1)
	private String isactive;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysRole")
	private List<UserRolePo> sysUserRoles = new ArrayList<UserRolePo>(0);
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysRole")
	private List<RoleOrgaccessPo> sysRoleOrgaccesses = new ArrayList<RoleOrgaccessPo>(
			0);
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysRole")
	private List<ModuleRolePo> sysModuleRoles = new ArrayList<ModuleRolePo>(0);
}