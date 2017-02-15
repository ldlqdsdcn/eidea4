package com.dsdl.eidea.common.web.vo;

import com.dsdl.eidea.core.entity.bo.CommonSearchResult;
import lombok.Data;

import java.util.List;

/**
 * @author dalei.liu 2016/8/29 22:53.
 */
@Data
public class SearchColumnVo implements java.io.Serializable{
    private Integer columnId;
    private String columnName;
    private String columnLabel;
    private int dataType;
    private String opearType;
    private String value;
    private String newline;
    private Integer showType;
    private Integer seqNo;
    private List<String> relOpearList;
    private List<CommonSearchResult> commonSearchResultList;

}
