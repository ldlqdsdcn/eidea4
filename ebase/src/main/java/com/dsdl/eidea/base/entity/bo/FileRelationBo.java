package com.dsdl.eidea.base.entity.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by admin on 2017/5/17.
 */
@Getter
@Setter
public class FileRelationBo {
    private Integer id;
    private Integer fileId;
    private String tableName;
    private Integer tableId;
    private Date created;
}
