package com.dsdl.eidea.base.entity.bo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by 刘大磊 on 2017/4/8.
 */
@Getter
@Setter
public class PermissionBo {
    private String repository;
    private String operator;

    public String getPermission() {
        return repository + ":" + operator;
    }
    public String toString() {
        return getPermission();
    }
}
