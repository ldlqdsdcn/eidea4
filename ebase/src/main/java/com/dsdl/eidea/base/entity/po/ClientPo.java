package com.dsdl.eidea.base.entity.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * SysClient entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_client", catalog = "e_idea", uniqueConstraints = @UniqueConstraint(columnNames = "no"))
@Getter
@Setter
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
public class ClientPo implements java.io.Serializable {

	// Fields
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
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysClient")
	private Set<OrgPo> sysOrgs = new HashSet<OrgPo>(0);

	// Constructors

	/** default constructor */
	public ClientPo() {
	}

	/** minimal constructor */
	public ClientPo(Integer id, String no, String name, String isactive) {
		this.id = id;
		this.no = no;
		this.name = name;
		this.isactive = isactive;
	}

	/** full constructor */
	public ClientPo(Integer id, String no, String name, String isactive,
			String remark, Set<OrgPo> sysOrgs) {
		this.id = id;
		this.no = no;
		this.name = name;
		this.isactive = isactive;
		this.remark = remark;
		this.sysOrgs = sysOrgs;
	}
}