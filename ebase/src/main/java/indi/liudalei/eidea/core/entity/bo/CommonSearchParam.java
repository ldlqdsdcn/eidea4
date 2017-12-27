package indi.liudalei.eidea.core.entity.bo;

import lombok.Data;

/**
 * @author dalei.liu 2016/8/29 20:29.
 */
@Data
public class CommonSearchParam {
    private String keyValue;
    private String labelValue;
    private String tableName;
    private String conditions;

}
