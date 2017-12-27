package indi.liudalei.eidea.devs.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/6 13:40.
 */
@Getter
@Setter
public class TableInfo {
    /**
     * 表名
     */
    private String tableName;
    /**
     * po类名字
     */
    private String poName;
    /**
     * 表注释
     */
    private String tableRemark;
    /**
     * 主键
     */
    private ColumnInfo pkColumn;
    /**
     * 备注
     */
    private String remark;
    /**
     * 其它字段
     */
    private List<ColumnInfo> columnInfoList;
}
