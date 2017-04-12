package com.dsdl.eidea.core.entity.po;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * CoreLabel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "core_label", catalog = "e_idea_public")
@Setter
@Getter
public class LabelPo implements java.io.Serializable {

    // Fields
    @Id
    @Column(name = "_key", unique = true, nullable = false, length = 100)
    private String key;
    @Column(name = "msgtext", nullable = false, length = 500)
    private String msgtext;
    @Column(name = "isactive", length = 1)
    private String isactive;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "coreLabel")
    private List<SearchColumnPo> coreSearchColumns = new ArrayList<>(
            0);

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "coreLabel")
    private List<LabelTrlPo> coreLabelTrls = new ArrayList<>(0);

    // Constructors

    /**
     * default constructor
     */
    public LabelPo() {
    }

    /**
     * minimal constructor
     */
    public LabelPo(String key, String msgtext) {
        this.key = key;
        this.msgtext = msgtext;
    }

    /**
     * full constructor
     */
    public LabelPo(String key, String msgtext, String isactive,
                   List<SearchColumnPo> coreSearchColumns,
                   List<LabelTrlPo> coreLabelTrls) {
        this.key = key;
        this.msgtext = msgtext;
        this.isactive = isactive;
        this.coreSearchColumns = coreSearchColumns;
        this.coreLabelTrls = coreLabelTrls;
    }

    // Property accessors


}