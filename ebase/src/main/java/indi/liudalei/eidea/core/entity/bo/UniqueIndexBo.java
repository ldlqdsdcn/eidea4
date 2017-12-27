package indi.liudalei.eidea.core.entity.bo;

import lombok.Data;

/**
 * Created by admin on 2016/8/23.
 */
@Data
public class UniqueIndexBo implements java.io.Serializable{
    private String indexColumnName;
    private String indexName;
}
