package com.dsdl.eidea.base.entity.po;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * SysOperator entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_operator", catalog = "e_idea_public", uniqueConstraints = @UniqueConstraint(columnNames = "no"))
@Getter
@Setter
public class OperatorPo implements java.io.Serializable {

	public OperatorPo() {
	}

	/** minimal constructor */
	public OperatorPo(Integer id, String no, String name, String isactive) {
		this.id = id;
		this.no = no;
		this.name = name;
		this.isactive = isactive;
	}

	/** full constructor */
	public OperatorPo(Integer id, String no, String name, String isactive,
			String remark, Set<PrivilegesPo> sysPrivilegeses) {
		this.id = id;
		this.no = no;
		this.name = name;
		this.isactive = isactive;
		this.remark = remark;
		this.sysPrivilegeses = sysPrivilegeses;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "no", unique = true, nullable = false, length = 45)
	private String no;

	@Column(name = "name", nullable = false, length = 45)
	private String name;

	@Column(name = "isactive", nullable = false, length = 1)
	private String isactive;

	@Column(name = "remark", length = 200)
	private String remark;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysOperator")
	private Set<PrivilegesPo> sysPrivilegeses = new HashSet<PrivilegesPo>(0);
}