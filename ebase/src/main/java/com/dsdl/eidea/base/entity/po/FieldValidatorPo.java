
/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.base.entity.po;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
* table name core_field_validator
*            字段验证
* Date:2017-05-02 15:49:09
**/
@Getter
@Setter
@Entity(name = "core_field_validator")
@org.hibernate.annotations.Cache(usage= CacheConcurrencyStrategy.READ_ONLY)
public class FieldValidatorPo implements java.io.Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "[id]")
    @Id
    private Integer id;
    /**
    * 字段id
    **/
    @Column(name = "[field_id]",nullable = false,length = 11)
    @NotNull(message = "error.field.id.not.null")
    private Integer fieldId;
    /**
    * 验证类型
    **/
    @Column(name = "[validate_type]",nullable = false,length = 11)
    @NotNull(message = "error.validatetype.not.null")
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
    @Column(name = "[max_length]",length = 11)
    private Integer maxLength;
    /**
    * 最小长度
    **/
    @Column(name = "[min_length]",length = 11)
    private Integer minLength;
    /**
    * 验证正则表达式
    **/
    @Column(name = "[validator_patten]",length =500)
    @Length(max = 500,message = "error.validatorPattern.length")
    private String validatorPatten;
}