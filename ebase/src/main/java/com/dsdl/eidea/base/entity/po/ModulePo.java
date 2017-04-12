package com.dsdl.eidea.base.entity.po;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

/**
 * SysModule entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_module", catalog = "e_idea_public")
@Getter
@Setter
public class ModulePo implements java.io.Serializable {

	// Fields
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "name", nullable = false, length = 45)
	private String name;
	@Column(name = "remark", length = 200)
	private String remark;
	@Column(name = "isactive", nullable = false, length = 1)
	private String isactive;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysModule")
	private List<ModuleMenuPo> sysModuleMenus = new ArrayList<ModuleMenuPo>(0);
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysModule")
	private List<ModuleRolePo> sysModuleRoles = new ArrayList<ModuleRolePo>(0);
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysModule")
	private List<ModuleDirectoryPo> sysModuleDirectories = new ArrayList<ModuleDirectoryPo>(0);

	// Constructors

	/** default constructor */
	public ModulePo() {
	}

	/** minimal constructor */
	public ModulePo(Integer id, String name, String isactive) {
		this.id = id;
		this.name = name;
		this.isactive = isactive;
	}

	/** full constructor */
	public ModulePo(Integer id, String name, String remark, String isactive,
			List<ModuleMenuPo> sysModuleMenus,
			List<ModuleRolePo> sysModuleRoles,
			List<ModuleDirectoryPo> sysModuleDirectories) {
		this.id = id;
		this.name = name;
		this.remark = remark;
		this.isactive = isactive;
		this.sysModuleMenus = sysModuleMenus;
		this.sysModuleRoles = sysModuleRoles;
		this.sysModuleDirectories = sysModuleDirectories;
	}

}