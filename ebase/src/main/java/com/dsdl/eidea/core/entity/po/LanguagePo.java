package com.dsdl.eidea.core.entity.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * CoreLanguage entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "core_language", catalog = "e_idea", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
@org.hibernate.annotations.Cache(usage = org.hibernate.annotations.CacheConcurrencyStrategy.READ_WRITE)
@Setter
@Getter
public class LanguagePo implements java.io.Serializable {

    // Fields
    @Id
    @Column(name = "code", unique = true, nullable = false, length = 10)
    private String code;
    @Column(name = "name", unique = true, nullable = false, length = 200)
    private String name;
    @Column(name = "remark", length = 500)
    private String remark;
    @Column(name = "isactive", nullable = false, length = 1)
    private String isactive;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "coreLanguageByLanguageCode")
    private List<LanguageTrlPo> coreLanguageTrlsForLanguageCode = new ArrayList<>(
            0);
    @OneToMany(cascade = {CascadeType.ALL, CascadeType.PERSIST}, fetch = FetchType.LAZY, mappedBy = "coreLanguage")
    private Set<MessageTrlPo> coreMessageTrls = new HashSet<MessageTrlPo>(0);
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "coreLanguageByLang")
    private Set<LanguageTrlPo> coreLanguageTrlsForLang = new HashSet<LanguageTrlPo>(
            0);
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "coreLanguage")
    private Set<LabelTrlPo> coreLabelTrls = new HashSet<LabelTrlPo>(0);
}