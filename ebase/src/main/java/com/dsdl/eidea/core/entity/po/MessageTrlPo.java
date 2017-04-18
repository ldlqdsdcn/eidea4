package com.dsdl.eidea.core.entity.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * CoreMessageTrl entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "core_message_trl", catalog = "e_idea")
@Getter
@Setter
public class MessageTrlPo implements java.io.Serializable {

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
	private MessagePo coreMessage;
	@Column(name = "msgtext", nullable = false, length = 500)
	private String msgtext;

}