package indi.liudalei.eidea.core.entity.bo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by 刘大磊 on 2016/12/7 10:00.
 */
@Getter
@Setter
public class TableColumnBo implements java.io.Serializable{
    private Integer id;
    private String name;
    private String columnName;
    private Integer dataType;
    private String canShow;
    private String propertyName;
    private String outLog;
    private String remark;
    private Integer fkTableId;
    private Integer fkColumnId;
    private String isUnique;
    private Integer columnSize;
    private Integer digits;
    private String nullable;
}
