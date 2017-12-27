package indi.liudalei.eidea.core.entity.dto;

import lombok.Data;

@Data
public class SearchColumnDto {
    private Integer columnId;
    private String columnName;
    private int dataType;
    private String opearType;
    private String value;
}