package indi.liudalei.eidea.core.entity.bo;

/**
 * Created by admin on 2016/8/30.
 */

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class SearchColumnBo {

    private Integer id;
    private String labelKey;
    @NotNull(message = "查询列序号不允许为空")
    private Integer seqNo;
    @NotNull(message = "查询列名称不允许为空")
    private String name;
    @NotNull(message = "查询列属性称不允许为空")
    private String propertyName;
    @NotNull(message = "查询列数据类型不允许为空")
    private Integer dataType;
    @NotNull(message = "查询列显示类型不允许为空")
    private Integer showType;
    @NotNull(message = "查询列连接符不允许为空")
    private String relOper;
    private String fkTable;
    private String fkKeyColumn;
    private String fkLabelColumn;
    private String coditions;
    private String remark;
    private String newline;
    private List<KeyValue> relOperList;
}
