
/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.base.entity.po;

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
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
    @Column(name = "[id]")
    @Id
    private Integer id;
    /**
    * 排序号
    **/
    @Column(name = "[seq_no]" )
    private Integer seqNo;
    /**
    * 名称
    **/
    @Column(name = "[name]",length =200 )
    private String name;
    /**
    * 字段名
    **/
    @Column(name = "[field_name]",length =200 )
    private String fieldName;
    /**
    * 列id
    **/
    @Column(name = "[column_id]" )
    private Integer columnId;
    /**
    * 是否必填
    **/
    @Column(name = "[required]",length =1 )
    private String required;
    /**
    * 是否有效
    **/
    @Column(name = "[isactive]",length =1 )
    private String isactive;
    /**
    * 创建时间
    **/
    @Column(name = "[created]",length =19 )
    private Date created;
    /**
    * 创建人
    **/
    @Column(name = "[createdby]" )
    private Integer createdby;
    /**
    * 修改时间
    **/
    @Column(name = "[updated]",length =19 )
    private Date updated;
    /**
    * 修改人
    **/
    @Column(name = "[updatedby]" )
    private Integer updatedby;
    /**
    * 描述
    **/
    @Column(name = "[description]",length =500 )
    private String description;
    /**
    * 输入类型
    **/
    @Column(name = "[input_type]" )
    private Integer inputType;
    /**
    * 是否显示
    **/
    @Column(name = "[is_displayed]",length =1 )
    private String isDisplayed;
    /**
    * 显示逻辑
    **/
    @Column(name = "[displayedlogic]",length =2000 )
    private String displayedlogic;
    /**
    * 显示宽度
    **/
    @Column(name = "[displaylength]" )
    private Integer displaylength;
    /**
    * 是否只读
    **/
    @Column(name = "[isreadonly]",length =1 )
    private String isreadonly;
    /**
    * 是否同行显示
    **/
    @Column(name = "[issameline]",length =1 )
    private String issameline;
    /**
    * 是否加密
    **/
    @Column(name = "[isencrypted]",length =1 )
    private String isencrypted;
    /**
    * 默认值
    **/
    @Column(name = "[defaultvalue]",length =2000 )
    private String defaultvalue;
    /**
    * 是否在列表中显示
    **/
    @Column(name = "[isdisplaygrid]",length =1 )
    private String isdisplaygrid;
    /**
    * 表格排序 如果没有则用seq_no
    **/
    @Column(name = "[seqnogrid]" )
    private Integer seqnogrid;
    /**
    * 是否允许复制
    **/
    @Column(name = "[isallowcopy]",length =1 )
    private String isallowcopy;
}