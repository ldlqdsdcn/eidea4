package indi.liudalei.eidea.core.service;

import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.entity.bo.LabelBo;
import indi.liudalei.eidea.core.params.QueryParams;
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
