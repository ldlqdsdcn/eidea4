package com.dsdl.eidea.common.web.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/20 17:00.
 */
@Getter
@Setter
public class SearchConditionParam {
    private String uri;
    private List<SearchColumnVo> searchColumnVoList;
}
