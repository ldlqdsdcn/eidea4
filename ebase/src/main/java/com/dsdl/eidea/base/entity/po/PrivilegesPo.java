package com.dsdl.eidea.base.entity.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * SysPrivileges entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_privileges", catalog = "e_idea")
@Getter
@Setter
public class PrivilegesPo implements java.io.Serializable {

	// Fields
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_module_role_id", nullable = false)
	private ModuleRolePo sysModuleRole;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_operator_id", nullable = false)
	private OperatorPo sysOperator;

	// Constructors

	/** default constructor */
	public PrivilegesPo() {
	}

	/** full constructor */
	public PrivilegesPo(Integer id, ModuleRolePo sysModuleRole,
			OperatorPo sysOperator) {
		this.id = id;
		this.sysModuleRole = sysModuleRole;
		this.sysOperator = sysOperator;
	}

}