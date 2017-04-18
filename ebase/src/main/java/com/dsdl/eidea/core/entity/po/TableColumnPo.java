package com.dsdl.eidea.core.entity.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * CoreTableColumn entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "core_table_column", catalog = "e_idea")
@Getter
@Setter
public class TableColumnPo implements java.io.Serializable {

	// Fields
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "core_table_id", nullable = false)
	private TablePo tablePo;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "column_name", length = 45)
	private String columnName;
	@Column(name = "data_type")
	private Integer dataType;
	@Column(name = "can_show", length = 1)
	private String canShow;
	@Column(name = "property_name", length = 45)
	private String propertyName;
	@Column(name = "out_log", length = 1)
	private String outLog;
	@Column(name = "remark", length = 500)
	private String remark;
	@Column(name = "fk_table_id")
	private Integer fkTableId;
	@Column(name = "fk_column_id")
	private Integer fkColumnId;
	@Column(name = "is_unique", length = 1)
	private String isUnique;
	@Column(name = "column_size")
	private Integer columnSize;
	@Column(name = "digits")
	private Integer digits;
	@Column(name = "nullable", length = 1)
	private String nullable;



}