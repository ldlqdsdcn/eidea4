
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
* table name base_datadict_type
*            数据字典类型
* Date:2017-04-26 15:34:16
**/
@Getter
@Setter
@Entity(name = "base_datadict_type")
public class DatadictTypePo implements java.io.Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "[id]")
    @Id
    private Integer id;
    /**
    * 主键值
    **/
    @Column(name = "[value]",length =50 )
    private String value;
    /**
    * 名称
    **/
    @Column(name = "[name]",length =200 )
    private String name;
    /**
    * 备注
    **/
    @Column(name = "[remark]",length =200 )
    private String remark;
}