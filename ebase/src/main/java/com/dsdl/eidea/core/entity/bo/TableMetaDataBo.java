package com.dsdl.eidea.core.entity.bo;

import com.dsdl.eidea.core.def.JavaDataType;
import lombok.Data;

import java.util.List;

/**
 * Created by admin on 2016/8/18.
 */
@Data
public class TableMetaDataBo {
    /**
     * 表名
     */
    private String name;
    /**
     * 表注释
     */
    private String tableTrlName;
    /**
     * 是否出错日志
     */
    private boolean outLog;
    /**
     * model类名字
     */
    private String className;
    /**
     * 业务主键
     */
    private String busPk;
    /**
     * 备注
     */
    private String remark;
    /**
     * 主键
     */
    private String pkColumn;

    private List<ForeignKeyBo> exportedFK;

    private List<ForeignKeyBo> importedFK;

    private List<ColumnMetaDataBo> columnList;

    private List<UniqueIndexBo> uniqueKeyList;

    public String getPkClass() {
        JavaDataType[] columnDataTypes = JavaDataType.values();
        for (ColumnMetaDataBo c : columnList) {
            if (c.getColumnName().equals(pkColumn)) {
                for (JavaDataType dataType : columnDataTypes) {
                    if (c.getDataType() == dataType.getKey()) {
                        return dataType.getValue();
                    }
                }
            }
        }
        return null;
    }
}
