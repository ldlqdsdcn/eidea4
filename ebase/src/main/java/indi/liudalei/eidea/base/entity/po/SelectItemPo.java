
/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package indi.liudalei.eidea.base.entity.po;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
/**
* table name core_select_item
*            select下拉列表
* Date:2017-05-03 17:49:28
**/
@Getter
@Setter
@Entity(name = "core_select_item")
public class SelectItemPo implements java.io.Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "[id]")
    @Id
    private Integer id;
    /**
    * 字段id
    **/
    @Column(name = "[name]",length =200 )
    private String name;
    /**
    * select 元素 option value
    **/
    @Column(name = "[key]",length =50 )
    private String key;
    /**
    * select 元素的 option 显示值
    **/
    @Column(name = "[value]",length =50 )
    private String value;
    /**
    * sql 语句 或者是 hsql查询语句
    **/
    @Column(name = "[sql]",length =3000 )
    private String sql;
    /**
    * 类型 HSQL or SQL
    **/
    @Column(name = "[type]",length =10 )
    private String type;
}