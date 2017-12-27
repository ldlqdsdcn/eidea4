package indi.liudalei.eidea.base.entity.po;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * SysModuleRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_module_role", catalog = "e_idea")
@Getter
@Setter
public class ModuleRolePo implements java.io.Serializable {

	// Fields
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_module_id", nullable = false)
	private ModulePo sysModule;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_role_id", nullable = false)
	private RolePo sysRole;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysModuleRole")
	private List<PrivilegesPo> sysPrivilegeses = new ArrayList<PrivilegesPo>(0);
}