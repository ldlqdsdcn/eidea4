package indi.liudalei.eidea.core.entity.bo;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by 刘大磊 on 2017/6/5 10:09.
 * sql语句条件
 */
@Getter
@Setter
public class SqlCondition {
    /**
     * 下标
     */
    private int index;
    /**
     * 列名
     */
    private String columnName;

}
