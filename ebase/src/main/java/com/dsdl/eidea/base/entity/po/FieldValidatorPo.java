
/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.base.entity.po;

import java.util.Date;
import java.math.BigDecimal;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
/**
* table name core_field_validator
*            字段验证
* Date:2017-05-02 15:49:09
**/
@Getter
@Setter
@Entity(name = "core_field_validator")
public class FieldValidatorPo implements java.io.Serializable {
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
    * 验证类型
    **/
    @Column(name = "[validate_type]" )
    private Integer validateType;
    /**
    * 最大值
    **/
    @Column(name = "[max_value]",length =10 )
    private BigDecimal maxValue;
    /**
    * 最小值
    **/
    @Column(name = "[min_value]",length =10 )
    private BigDecimal minValue;
    /**
    * 最大日期
    **/
    @Column(name = "[max_date]",length =19 )
    private Date maxDate;
    /**
    * 最小日期
    **/
    @Column(name = "[min_date]",length =19 )
    private Date minDate;
    /**
    * 最大长度
    **/
    @Column(name = "[max_length]" )
    private Integer maxLength;
    /**
    * 最小长度
    **/
    @Column(name = "[min_length]" )
    private Integer minLength;
    /**
    * 验证正则表达式
    **/
    @Column(name = "[validator_patten]",length =500 )
    private String validatorPatten;
}