
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
* table name core_settings
*            系统设置
* Date:2017-05-06 07:51:35
**/
@Getter
@Setter
@Entity(name = "core_settings")
public class SettingsPo implements java.io.Serializable {
    @Column(name = "[key]",length =50,unique = true, nullable = false )
    @Id
    private String key;
    /**
    * 值
    **/
    @Column(name = "[value]",length =500 )
    private String value;
    /**
    * 描述
    **/
    @Column(name = "[description]",length =500 )
    private String description;
}