
/**
 * 版权所有 刘大磊 2013-07-01
 * 作者：刘大磊
 * 电话：13336390671
 * email:ldlqdsd@126.com
 */
package com.dsdl.eidea.base.entity.po;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.dsdl.eidea.base.def.BoolChar;
import com.dsdl.eidea.base.init.InputTypeConverter;
import com.dsdl.eidea.core.def.FieldShowType;
import com.dsdl.eidea.core.entity.po.TableColumnPo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * table name core_field
 *            字段信息
 * Date:2017-05-04 13:22:23
 **/
@Getter
@Setter
@Entity(name = "core_field")
public class FieldPo implements java.io.Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "[id]", length = 11, nullable = false, unique = true)
    @Id
    private Integer id;
    /**
     * 排序号
     **/
    @Column(name = "[seq_no]", length = 11, nullable = false)
    @NotNull(message = "error.sortno.not.null")
    private Integer seqNo;
    /**
     * 名称
     **/
    @Column(name = "[name]", length = 200, nullable = false)
    @Length(min = 2, max = 200, message = "org.error.name.length_error")
    @NotBlank(message = "search.msg.name_not_null")
    private String name;
    /**
     * 字段名
     **/
    @Column(name = "[field_name]", length = 200, nullable = false)
    @Length(min = 2, max = 200, message = "error.field.name.length")
    @NotBlank(message = "error.field.name.not.null")
    private String fieldName;
    /**
     * 列id
     **/
    @Column(name = "column_id", length = 11)
    private Integer columnId;
    /**
     * 是否必填
     **/
    @Column(name = "[required]", length = 1)
    @NotBlank(message = "error.required.not.null")
    @Length(min = 1, max = 1, message = "error.required.length")
    private String required;
    /**
     * 是否有效
     **/
    @Column(name = "[isactive]", length = 1)
    private String isactive;
    /**
     * 创建时间
     **/
    @Column(name = "[created]", length = 19, nullable = false)
    private Date created;
    /**
     * 创建人
     **/
    @Column(name = "[createdby]", nullable = false, length = 11)
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
    private Integer updatedby;
    /**
     * 描述
     **/
    @Column(name = "[description]", length = 500)
    @Length(max = 500, message = "tab.error.description.length")
    private String description;
    /**
     * 输入类型
     **/
    @Column(name = "[input_type]", nullable = false, length = 11)
    @Convert(converter = InputTypeConverter.class)
    private String inputType;
    /**
     * 是否显示
     **/
    @Column(name = "[is_displayed]", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private BoolChar isDisplayed;
    /**
     * 显示逻辑
     **/
    @Column(name = "[displayedlogic]", length = 2000)
    @Length(max = 2000, message = "error.displayedlogic.length")
    private String displayedlogic;
    /**
     * 显示宽度
     **/
    @Column(name = "[displaylength]", length = 11)
    private Integer displaylength;
    /**
     * 是否只读
     **/
    @Column(name = "[isreadonly]", length = 1)
    @Length(max = 1, message = "error.isreadonly.length")
    private BoolChar isreadonly;
    /**
     * 是否同行显示
     **/
    @Column(name = "[issameline]", length = 1)
    @Length(max = 1, message = "error.issameline.length")
    private BoolChar issameline;
    /**
     * 是否加密
     **/
    @Column(name = "[isencrypted]", length = 1)
    @Length(max = 1, message = "error.isencrypted.length")
    private BoolChar isencrypted;
    /**
     * 默认值
     **/
    @Column(name = "[defaultvalue]", length = 2000)
    @Length(max = 2000, message = "error.defaultvalue.length")
    private String defaultvalue;
    /**
     * 是否在列表中显示
     **/
    @Column(name = "[isdisplaygrid]", length = 1)
    @Length(max = 1, message = "error.isdisplaygrid.length")
    private BoolChar isdisplaygrid;
    /**
     * 表格排序 如果没有则用seq_no
     **/
    @Column(name = "[seqnogrid]", length = 11)
    private Integer seqnogrid;
    /**
     * 是否允许打印
     */
    @Column(name = "[isprinted]", nullable = false, length = 255)
    @Length(min = 1, max = 255, message = "error.isprinted.length")
    @NotBlank(message = "error.isprinted.not.null")
    private BoolChar isprinted;
    /**
     * 是否允许复制
     **/
    @Column(name = "[isallowcopy]", length = 1)
    @Length(max = 1, message = "error.isallowcopy.length")
    private BoolChar isallowcopy;
    /**
     * 是否导出报表
     */
    @Column(name = "[isreport]", length = 1, nullable = false)
    @Length(min = 1, max = 1, message = "error.isreport.length")
    @NotBlank(message = "error.isreport.not.null")
    private BoolChar isreport;
    /**
     * 对应的tab信息
     */
    @Column(name = "tab_id", nullable = false)
    private Integer tabId;
    @Column(name="show_type",nullable = false)
    private FieldShowType showType;
    /**
     * 对应的element_id
     */
    @Column(name = "element_id")
    private Integer elementId;
    /**
     * 是否可添加
     */
    @Column(name = "isadded")
    private String isadded;
    /**
     * 是否可更新
     */
    @Column(name = "isupdated")
    private String isupdated;

    @Column(name = "autowired_value",length = 2000)
    private String autowiredValue;
    @JsonIgnore
    @JoinColumn(name = "column_id", updatable = false, insertable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private TableColumnPo tableColumnPo;
}