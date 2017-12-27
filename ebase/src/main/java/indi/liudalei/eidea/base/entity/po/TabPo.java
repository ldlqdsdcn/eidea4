
/**
 * 版权所有 刘大磊 2013-07-01
 * 作者：刘大磊
 * 电话：13336390671
 * email:ldlqdsd@126.com
 */
package indi.liudalei.eidea.base.entity.po;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * table name core_tab
 *            Tab页
 * Date:2017-05-02 15:43:13
 **/
@Getter
@Setter
@Entity(name = "core_tab")
@org.hibernate.annotations.Cache(usage= CacheConcurrencyStrategy.READ_ONLY)
public class TabPo implements java.io.Serializable {
    @Id
    @Column(name = "id", nullable = false, unique = true, length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 窗体id
     **/
    @Column(name = "window_id", nullable = false, length = 11)
    @NotNull(message = "error.window.id.not_null")
    private Integer windowId;
    /**
     * tab名
     **/
    @Column(name = "[name]", length = 200, nullable = false)
    @NotNull(message = "datadicttype.value.not.empty")
    @Length(min = 2, max = 200, message = "error.datadicttype.name.length")
    private String name;
    /**
     * 等级
     **/
    @Column(name = "[level]", nullable = false, length = 11)
    @NotNull(message = "error.level.not.null")
    private Integer level;
    /**
     * 编号
     **/
    @Column(name = "[sortno]", nullable = false, length = 11)
    @NotNull(message = "error.sortno.not.null")
    private Integer sortno;
    /**
     * 描述
     **/
    @Column(name = "[description]", length = 500)
    @Length(max = 500, message = "tab.error.description.length")
    private String description;
    /**
     * 是否包含在另一个tab里
     **/
    @Column(name = "[included_tab_id]", length = 11)
    private Integer includedTabId;
    /**
     * 表id
     **/
    @Column(name = "[table_id]", nullable = false, length = 11)
    @NotNull(message = "error.table.id.not.null")
    private Integer tableId;
    /**
     * 关联列
     **/
    @Column(name = "[table_column_id]", nullable = false, length = 11)
    @NotNull(message = "error.tablecolumn.id.not.null")
    private Integer tableColumnId;
    /**
     * 是否有效
     **/
    @Column(name = "[isactive]", length = 1)
    @Length(min = 1, max = 1, message = "isactive.length")
    private String isactive;
    /**
     * 创建时间
     **/
    @Column(name = "[created]", length = 19, nullable = false)
    private Date created;
    /**
     * 创建人
     **/
    @Column(name = "[createdby]", length = 11, nullable = false)
    @NotNull(message = "error.createdby.not_null")
    private Integer createdby;
    /**
     * 修改时间
     **/
    @Column(name = "[updated]", length = 19, nullable = false)
    private Date updated;
    /**
     * 修改人
     **/
    @Column(name = "[updatedby]", length = 11, nullable = false)
    @NotNull(message = "error.updatby.not_null")
    private Integer updatedby;
}