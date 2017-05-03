package com.dsdl.eidea.core.service;

import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.entity.bo.LabelBo;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;

import java.util.List;

public interface LabelService {
    void save(LabelBo labelBo);

    boolean findExistClient(String no);

    PaginationResult<LabelBo> getLabelList(Search search, QueryParams queryParams);

    void deletes(String[] codes);

    LabelBo getLabelBo(String key);

    List<LabelBo> findLanguageListForActivated(String[] notInCodes);

}
