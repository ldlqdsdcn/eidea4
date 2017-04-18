package com.dsdl.eidea.base.entity.po;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.Setter;

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