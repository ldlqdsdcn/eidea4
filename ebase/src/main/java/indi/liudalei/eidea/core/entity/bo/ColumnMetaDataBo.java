package indi.liudalei.eidea.core.entity.bo;

import lombok.Data;

/**
 * Created by admin on 2016/8/23.
 * 表字段信息
 */
@Data
public class ColumnMetaDataBo implements java.io.Serializable{
    private String columnName;
    private String type;
    private int dataType;
    private int columnSize;
    private int decimalDigits;
    private Boolean nullable;
    private String remarks;
    private String columnDefault;


}
