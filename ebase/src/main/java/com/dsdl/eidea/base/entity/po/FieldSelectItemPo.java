
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
* table name core_field_select_item
*            字段下拉选择
* Date:2017-05-03 17:51:03
**/
@Getter
@Setter
@Entity(name = "core_field_select_item")
public class FieldSelectItemPo implements java.io.Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "[id]")
    @Id
    private Integer id;
    /**
    * 字段id
    **/
    @Column(name = "[field_id]" )
    private Integer fieldId;
    /**
    * 选择list id
    **/
    @Column(name = "[select_item_id]" )
    private Integer selectItemId;
}