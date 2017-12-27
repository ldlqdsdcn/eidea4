package indi.liudalei.eidea.core.entity.po;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 * CoreSearch entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "core_search", catalog = "e_idea")
@Getter
@Setter
public class SearchPo implements java.io.Serializable {

	// Fields
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "name", nullable = false, length = 50)
	private String name;
	@Column(name = "uri", nullable = false, length = 45)
	private String uri;
	@Column(name = "show_type")
	private Integer showType;
	@Column(name = "window_id")
	private Integer windowId;
	@Column(name = "isactive", length = 1)
	private String isactive;
	@Column(name = "remark", length = 500)
	private String remark;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "coreSearch")
	private List<SearchColumnPo> coreSearchColumns = new ArrayList<SearchColumnPo>(
			0);

}