package com.dsdl.eidea.core.entity.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * CoreLabelTrl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "core_label_trl", catalog = "e_idea")
@Getter
@Setter
public class LabelTrlPo implements java.io.Serializable {

	// Fields
	@Id
	@Column(name = "id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "lang", nullable = false)
	private LanguagePo coreLanguage;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "_key", nullable = false)
	private LabelPo coreLabel;
	@Column(name = "msgtext", nullable = false, length = 500)
	private String msgtext;

	// Constructors

	/** default constructor */
	public LabelTrlPo() {
	}

	/** full constructor */
	public LabelTrlPo(Integer id, LanguagePo coreLanguage,
			LabelPo coreLabel, String msgtext) {
		this.id = id;
		this.coreLanguage = coreLanguage;
		this.coreLabel = coreLabel;
		this.msgtext = msgtext;
	}

	// Property accessors


}