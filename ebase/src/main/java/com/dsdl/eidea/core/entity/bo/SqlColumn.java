package com.dsdl.eidea.core.entity.bo;

import com.dsdl.eidea.core.def.JavaDataType;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by 刘大磊 on 2017/6/5 10:15.
 */
@Getter
@Setter
public class SqlColumn {
    private int index;
    private String columnName;
    private JavaDataType dataType;
}
