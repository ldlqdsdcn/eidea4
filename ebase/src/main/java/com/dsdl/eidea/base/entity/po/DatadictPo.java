
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "[id]")
    @Id
    private Integer id;
    /**
    * 
    **/
    @Column(name = "[code]",length =20 )
    private String code;
    /**
    * 
    **/
    @Column(name = "[msgtext]",length =200 )
    private String msgtext;
    /**
    * 
    **/
    @Column(name = "[isactive]",length =1 )
    private String isactive;
    /**
    * 
    **/
    @Column(name = "[data_type]",length =20 )
    private String dataType;
    /**
    * 
    **/
    @Column(name = "[remark]",length =200 )
    private String remark;
}