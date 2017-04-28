
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
    @Id
    @Column(name = "id",nullable = false,unique = true,length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "value",length =50,nullable = false,unique = true)
    private String value;
    @Column(name = "name",length =200,nullable = false)
    private String name;
    @Column(name = "remark",length =200 )
    private String remark;
    @Column(name = "isactive",nullable = false,length = 1)
    private String isactive;
}