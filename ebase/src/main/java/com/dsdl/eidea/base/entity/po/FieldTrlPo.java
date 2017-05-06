
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
* table name core_field_trl
*            字段翻译
* Date:2017-05-02 15:46:44
**/
@Getter
@Setter
@Entity(name = "core_field_trl")
public class FieldTrlPo implements java.io.Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "[id]")
    @Id
    private Integer id;
    /**
    * 
    **/
    @Column(name = "[lang]",length =10 )
    private String lang;
    /**
    * 
    **/
    @Column(name = "[field_id]" )
    private Integer fieldId;
    /**
    * 
    **/
    @Column(name = "[name]",length =200 )
    private String name;
    /**
    * 
    **/
    @Column(name = "[description]",length =500 )
    private String description;
    /**
    * 
    **/
    @Column(name = "[help]",length =3000 )
    private String help;
}