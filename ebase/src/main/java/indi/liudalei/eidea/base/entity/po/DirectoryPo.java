package indi.liudalei.eidea.base.entity.po;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * SysDirectory entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_directory", catalog = "e_idea")
@Getter
@Setter
public class DirectoryPo implements java.io.Serializable {
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "name", nullable = false, length = 45)
	private String name;

	@Column(name = "directory", nullable = false, length = 500)
	private String directory;

	@Column(name = "remark", length = 500)
	private String remark;

	@Column(name = "isactive", nullable = false, length = 1)
	private String isactive;
	@Column(name = "repository",nullable = false,length = 50)
	private String repository;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysDirectory")
	private Set<ModuleDirectoryPo> sysModuleDirectories = new HashSet<ModuleDirectoryPo>(
			0);

}