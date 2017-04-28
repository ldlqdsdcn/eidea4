
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
* table name base_datadict
*            数据字典
* Date:2017-04-26 15:34:59
**/
@Getter
@Setter
@Entity(name = "base_datadict")
public class DatadictPo implements java.io.Serializable {
    @Id
    @Column(name = "id",nullable = false,unique = true,length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "code",length =20,nullable = false)
    private String code;
    @Column(name = "msgtext",length =200,nullable = false)
    private String msgtext;
    @Column(name = "isactive",length =1,nullable = false)
    private String isactive;
    @Column(name = "data_type",length =20,nullable = false)
    private String dataType;
    @Column(name = "remark",length =200)
    private String remark;
}