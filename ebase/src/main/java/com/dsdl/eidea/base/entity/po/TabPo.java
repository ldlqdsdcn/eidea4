
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
* table name core_tab
*            Tab页
* Date:2017-05-02 15:43:13
**/
@Getter
@Setter
@Entity(name = "core_tab")
public class TabPo implements java.io.Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "[id]")
    @Id
    private Integer id;
    /**
    * 窗体id
    **/
    @Column(name = "[window_id]" )
    private Integer windowId;
    /**
    * tab名
    **/
    @Column(name = "[name]",length =200 )
    private String name;
    /**
    * 等级
    **/
    @Column(name = "[level]" )
    private Integer level;
    /**
    * 编号
    **/
    @Column(name = "[sortno]" )
    private Integer sortno;
    /**
    * 描述
    **/
    @Column(name = "[description]",length =500 )
    private String description;
    /**
    * 是否包含在另一个tab里
    **/
    @Column(name = "[included_tab_id]" )
    private Integer includedTabId;
    /**
    * 表id
    **/
    @Column(name = "[table_id]" )
    private Integer tableId;
    /**
    * 关联列
    **/
    @Column(name = "[table_column_id]" )
    private Integer tableColumnId;
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
}