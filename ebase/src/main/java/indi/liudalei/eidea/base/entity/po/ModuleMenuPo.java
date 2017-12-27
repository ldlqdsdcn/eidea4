package indi.liudalei.eidea.base.entity.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * SysModuleMenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_module_menu", catalog = "e_idea")
@Getter
@Setter
public class ModuleMenuPo implements java.io.Serializable {

	// Fields
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_page_menu_id", nullable = false)
	private PageMenuPo pageMenuPo;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sys_module_id", nullable = false)
	private ModulePo sysModule;
}