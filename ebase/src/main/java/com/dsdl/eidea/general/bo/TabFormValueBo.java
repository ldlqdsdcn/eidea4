package com.dsdl.eidea.general.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by 刘大磊 on 2017/5/27 8:54.
 * form界面 Tab类
 */
@Getter
@Setter
public class TabFormValueBo {
    /**
     * form界面显示的字段
     */
    List<FieldValueBo> fieldValueBoList;
}
