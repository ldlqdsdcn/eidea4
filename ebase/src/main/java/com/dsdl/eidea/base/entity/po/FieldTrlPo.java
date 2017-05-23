
/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.base.entity.po;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

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
    @Column(name = "[id]",nullable = false,unique = true)
    @Id
    private Integer id;
    /**
    * 
    **/
    @Column(name = "[lang]",length =10,nullable = false)
    @Length(min=1,max = 10,message = "tab.error.lang.length")
    @NotBlank(message = "error.lang.not.null")
    private String lang;
    /**
    * 
    **/
    @Column(name = "[field_id]",nullable = false,length = 11)
    @NotNull(message = "error.field.id.not.null")
    private Integer fieldId;
    /**
    * 
    **/
    @Column(name = "[name]",length =200,nullable = false)
    @Length(min = 1,max = 200,message = "error.field.name.length")
    @NotBlank(message = "pagemenu.name.check")
    private String name;
    /**
    * 
    **/
    @Column(name = "[description]",length =500)
    @Length(max = 500,message = "tab.error.description.length")
    private String description;
    /**
    * 
    **/
    @Column(name = "[help]",length =3000)
    @Length(max = 3000,message = "error.help.length")
    private String help;
}