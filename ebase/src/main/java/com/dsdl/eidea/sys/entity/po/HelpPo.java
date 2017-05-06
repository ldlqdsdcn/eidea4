
/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.sys.entity.po;

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
/**
* table name sys_help
*            帮助文档
* Date:2017-04-26 15:55:56
**/
@Getter
@Setter
@Entity(name = "sys_help")
public class HelpPo implements java.io.Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "[id]")
    @Id
    private Integer id;
    /**
    * 标识符
    **/
    @Column(name = "[uri]",length =50 )
    private String uri;
    /**
    * 
    **/
    @Column(name = "[msgtext]",length =65535 )
    private String msgtext;
    /**
    * 
    **/
    @Column(name = "[created]",length =19 )
    private Date created;
    /**
    * 
    **/
    @Column(name = "[createdby]" )
    private Integer createdby;
    /**
    * 
    **/
    @Column(name = "[updated]",length =19 )
    private Date updated;
    /**
    * 
    **/
    @Column(name = "[updatedby]" )
    private Integer updatedby;
}