
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

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

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
    @Column(name = "[id]",nullable = false,unique = true,length = 11)
    @Id
    private Integer id;
    /**
    * 名称
    **/
    @Column(name = "[name]",length =50,nullable = false)
    @Length(min = 2,max=50,message = "table.error.name.length_error")
    @NotBlank(message = "search.msg.name_not_null")
    private String name;
    /**
    * 是否有效
    **/
    @Column(name = "[isactive]",length =1,nullable = false)
    @Length(min = 1,max = 1)
    @NotBlank
    private String isactive;
    /**
    * 备注
    **/
    @Column(name = "[description]",length =200 )
    @Length(max = 200,message = "window.error.description.length")
    private String description;
    /**
    * 用户
    **/
    @Column(name = "[client_id]",nullable = false,length = 11)
    @NotNull(message = "error.client.id.not_null")
    private Integer clientId;
    /**
    * 组织
    **/
    @Column(name = "[org_id]",nullable = false,length = 11)
    @NotNull(message = "error.org.id.not_null")
    private Integer orgId;
    /**
    * 
    **/
    @Column(name = "[createdby]",nullable = false,length = 11)
    @NotNull(message = "error.createdby.not_null")
    private Integer createdby;
    /**
    * 输入时间
    **/
    @Column(name = "[created]",length =19,nullable = false)
    private Date created;
    /**
    * 修改时间
    **/
    @Column(name = "[updated]",length =19,nullable = false)
    private Date updated;
    /**
    * 
    **/
    @Column(name = "[updatedby]",nullable = false,length = 11)
    @NotNull(message = "error.updatby.not_null")
    private Integer updatedby;
    /**
    * 
    **/
    @Column(name = "[requestMapping]",length =200 )
    @Length(max = 200,message = "window.error.requestmapping.length")
    private String requestMapping;
}