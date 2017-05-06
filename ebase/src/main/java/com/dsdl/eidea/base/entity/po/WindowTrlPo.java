
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
* table name core_window_trl
*            窗体信息
* Date:2017-05-02 15:42:27
**/
@Getter
@Setter
@Entity(name = "core_window_trl")
public class WindowTrlPo implements java.io.Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "[id]")
    @Id
    private Integer id;
    /**
    * 
    **/
    @Column(name = "[window_id]" )
    private Integer windowId;
    /**
    * 
    **/
    @Column(name = "[lang]",length =10 )
    private String lang;
    /**
    * 
    **/
    @Column(name = "[name]",length =100 )
    private String name;
    /**
    * 
    **/
    @Column(name = "[description]",length =500 )
    private String description;
    /**
    * 
    **/
    @Column(name = "[help]",length =65535 )
    private String help;
}