package com.dsdl.eidea.base.entity.po;

import com.dsdl.eidea.base.entity.po.UserPo;
import com.dsdl.eidea.core.entity.po.TablePo;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.*;


/**
 * CoreChangelog entity. @author MyEclipse Persistence Tools
 */
@Getter
@Setter
@Entity
@Table(name = "sys_changelog", catalog = "e_idea")
public class ChangelogPo implements java.io.Serializable {

	// Fields
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Transient
	private Integer tableId;
	@Transient
	private Integer  userId;

	@Column(name = "operate_type")
	private String operateType;
	@Column(name = "context")
	private String context;
	@Column(name = "in_date")
	private Date inDate;
	@Column(name = "pk")
	private String pk;
	@Column(name = "bu_pk")
	private String buPk;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_user_id", nullable = false)
	private UserPo userPo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "core_table_id", nullable = false)
	private TablePo tablePo;

	// Constructors

	/** default constructor */
	public ChangelogPo() {
	}
}