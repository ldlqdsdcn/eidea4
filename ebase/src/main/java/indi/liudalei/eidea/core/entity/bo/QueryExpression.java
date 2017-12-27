package indi.liudalei.eidea.core.entity.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by 刘大磊 on 2017/6/5 10:14.
 * eidea sql 执行表达式
 */
@Getter
@Setter
public class QueryExpression {
    private String sql;
    private List<SqlColumn> columnList;
    private List<SqlCondition> sqlConditionList;
}
