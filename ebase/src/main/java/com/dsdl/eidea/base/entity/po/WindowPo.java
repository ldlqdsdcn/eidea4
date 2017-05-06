
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
* table name core_window
*            窗体信息
* Date:2017-05-02 15:41:30
**/
@Getter
@Setter
@Entity(name = "core_window")
public class WindowPo implements java.io.Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "[id]")
    @Id
    private Integer id;
    /**
    * 名称
    **/
    @Column(name = "[name]",length =50 )
    private String name;
    /**
    * 是否有效
    **/
    @Column(name = "[isactive]",length =1 )
    private String isactive;
    /**
    * 备注
    **/
    @Column(name = "[description]",length =200 )
    private String description;
    /**
    * 用户
    **/
    @Column(name = "[client_id]" )
    private Integer clientId;
    /**
    * 组织
    **/
    @Column(name = "[org_id]" )
    private Integer orgId;
    /**
    * 
    **/
    @Column(name = "[createdby]" )
    private Integer createdby;
    /**
    * 输入时间
    **/
    @Column(name = "[created]",length =19 )
    private Date created;
    /**
    * 修改时间
    **/
    @Column(name = "[updated]",length =19 )
    private Date updated;
    /**
    * 
    **/
    @Column(name = "[updatedby]" )
    private Integer updatedby;
    /**
    * 
    **/
    @Column(name = "[requestMapping]",length =200 )
    private String requestMapping;
}