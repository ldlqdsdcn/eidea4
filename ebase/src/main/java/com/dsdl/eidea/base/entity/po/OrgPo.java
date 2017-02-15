package com.dsdl.eidea.base.entity.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * SysOrg entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_org", catalog = "e_idea", uniqueConstraints = @UniqueConstraint(columnNames = "no"))
@Getter
@Setter
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
public class OrgPo implements java.io.Serializable {

	// Fields
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_client_id", nullable = false)
	private ClientPo sysClient;
	@Column(name = "no", unique = true, nullable = false, length = 45)
	private String no;
	@Column(name = "name", nullable = false, length = 200)
	private String name;
	@Column(name = "isactive", nullable = false, length = 1)
	private String isactive;
	@Column(name = "remark", length = 200)
	private String remark;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysOrg")
	private Set<RoleOrgaccessPo> sysRoleOrgaccesses = new HashSet<RoleOrgaccessPo>(
			0);

	// Constructors

	/** default constructor */
	public OrgPo() {
	}

	/** minimal constructor */
	public OrgPo(Integer id, ClientPo sysClient, String no, String name,
			String isactive) {
		this.id = id;
		this.sysClient = sysClient;
		this.no = no;
		this.name = name;
		this.isactive = isactive;
	}

	/** full constructor */
	public OrgPo(Integer id, ClientPo sysClient, String no, String name,
			String isactive, String remark,
			Set<RoleOrgaccessPo> sysRoleOrgaccesses) {
		this.id = id;
		this.sysClient = sysClient;
		this.no = no;
		this.name = name;
		this.isactive = isactive;
		this.remark = remark;
		this.sysRoleOrgaccesses = sysRoleOrgaccesses;
	}
}