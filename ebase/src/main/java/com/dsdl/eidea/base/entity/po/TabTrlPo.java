
/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.base.entity.po;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
/**
* table name core_tab_trl
*            Tab页翻译
* Date:2017-05-02 15:43:44
**/
@Getter
@Setter
@Entity(name = "core_tab_trl")
public class TabTrlPo implements java.io.Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "[id]")
    @Id
    private Integer id;
    /**
    * 语言 code
    **/
    @Column(name = "[tab_id]" )
    private Integer tabId;
    /**
    * 
    **/
    @Column(name = "[lang]",length =10 )
    private String lang;
    /**
    * 名称
    **/
    @Column(name = "[name]",length =200 )
    private String name;
    /**
    * 描述
    **/
    @Column(name = "[description]",length =500 )
    private String description;
    /**
    * 帮助
    **/
    @Column(name = "[help]",length =500 )
    private String help;
}