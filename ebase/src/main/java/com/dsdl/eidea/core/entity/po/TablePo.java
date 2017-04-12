package com.dsdl.eidea.core.entity.po;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

/**
 * CoreTable entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "core_table", catalog = "e_idea_public")
@Getter
@Setter
public class TablePo implements java.io.Serializable {

    // Fields
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "table_name", nullable = false, length = 45)
    private String tableName;
    @Column(name = "po_class", nullable = false, length = 200)
    private String poClass;
    @Column(name = "out_log", nullable = false, length = 1)
    private String outLog;
    @Column(name = "bu_pk", length = 40)
    private String buPk;
    @Column(name = "remark", length = 500)
    private String remark;
    @Column(name = "extern_json", length = 3000)
    private String externJson;
    @Column(name = "isactive", nullable = false, length = 1)
    private String isactive;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tablePo")
    private List<TableColumnPo> coreTableColumns = new ArrayList<TableColumnPo>(
            0);

    // Constructors

    /**
     * default constructor
     */
    public TablePo() {
    }

    /**
     * minimal constructor
     */
    public TablePo(Integer id, String name, String tableName, String poClass,
                   String outLog, String isactive) {
        this.id = id;
        this.name = name;
        this.tableName = tableName;
        this.poClass = poClass;
        this.outLog = outLog;
        this.isactive = isactive;
    }

    /**
     * full constructor
     */
    public TablePo(Integer id, String name, String tableName, String poClass,
                   String outLog, String buPk, String remark, String externJson,
                   String isactive, List<TableColumnPo> coreTableColumns) {
        this.id = id;
        this.name = name;
        this.tableName = tableName;
        this.poClass = poClass;
        this.outLog = outLog;
        this.buPk = buPk;
        this.remark = remark;
        this.externJson = externJson;
        this.isactive = isactive;
        this.coreTableColumns = coreTableColumns;
    }

}