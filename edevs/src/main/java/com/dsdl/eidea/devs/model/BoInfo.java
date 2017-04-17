package com.dsdl.eidea.devs.model;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by 刘大磊 on 2017/1/11 10:24.
 * Bo类信息
 */
@Getter
@Setter
public class BoInfo {
    private ColumnInfo columnInfo;
    /**
     * 验证长度
     */
    private boolean validateLength;
    /**
     * 验证是否为空
     */
    private boolean validateNull;
    /**
     * 验证是否为空字符串
     */
    private boolean validateNotEmpty;
}
