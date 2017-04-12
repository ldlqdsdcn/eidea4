package com.dsdl.eidea.base.entity.po;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * SysPageMenu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "sys_page_menu", catalog = "e_idea_public")
@Getter
@Setter
public class PageMenuPo implements java.io.Serializable {

    // Fields
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", length = 45)
    private String name;
    @Column(name = "url", length = 200)
    private String url;
    @Column(name = "isactive", length = 1)
    private String isactive;
    @Column(name = "remark", length = 200)
    private String remark;
    @Column(name = "parent_menu_id", length = 45)
    private Integer parentMenuId;
    @Column(name = "menu_type", length = 11)
    private Integer menuType;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sysPageMenu")
    private Set<ModuleMenuPo> sysModuleMenus = new HashSet<ModuleMenuPo>(0);
    @Column(name = "icon", length = 100)
    private String icon;
    @Column(name = "seq_no", length = 11)
    private Integer seqNo;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "pageMenuPo")
    private List<PageMenuTrlPo> pageMenuTrlPoList = new ArrayList<PageMenuTrlPo>(0);
    // Constructors

    /**
     * default constructor
     */
    public PageMenuPo() {
    }


}