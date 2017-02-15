
<#include "../inc/class_header.ftl"/>
package com.dsdl.eidea.${module}.entity.bo;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
* Created by 刘大磊 on ${datetime}
*/
@Getter
@Setter
public class ${tableInfo.poName}Bo {
private Integer id;
@NotBlank(message="编号不允许为空")
@Length(min=4,max=10,message="common.create.success")
private String no;
@NotBlank(message="名称不能为空")
@Length(min=2,max=30,message="名字必须由2~30个字符组成")
private String name;
@NotBlank(message="是否有效不允许为空")
@Length(min = 1,max = 1,message="是否有效长度必须为1")
private String isactive;
@Length(max = 200,message="备注长度不能大于200")
private String remark;
}
