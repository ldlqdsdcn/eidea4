package com.dsdl.eidea.core.entity.po;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by 刘大磊 on 2016/12/22 14:04.
 * 为了便于记录日志，所有的Po类，都集成BasePo，在页面编辑的时候都设置上 updatedby 默认值
 * 如果默认值为空，则为系统管理员
 */
@Getter
@Setter
public class BasePo implements java.io.Serializable{
    private Integer operator;
}
