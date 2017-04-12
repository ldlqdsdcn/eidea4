package com.dsdl.eidea.base.entity.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * SysModuleDirectory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_module_directory", catalog = "e_idea_public")
@Getter
@Setter
public class ModuleDirectoryPo implements java.io.Serializable {

	// Fields
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_module_id", nullable = false)
	private ModulePo sysModule;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_directory_id", nullable = false)
	private DirectoryPo sysDirectory;

	// Constructors

	/** default constructor */
	public ModuleDirectoryPo() {
	}

	/** full constructor */
	public ModuleDirectoryPo(Integer id, ModulePo sysModule,
			DirectoryPo sysDirectory) {
		this.id = id;
		this.sysModule = sysModule;
		this.sysDirectory = sysDirectory;
	}

}