package indi.liudalei.eidea.base.service;

import indi.liudalei.eidea.base.entity.bo.OrgBo;
import indi.liudalei.eidea.base.entity.po.OrgPo;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;

/**
 * Created by 刘大磊 on 2016/12/12 17:37.
 */
public interface OrgService {
    PaginationResult<OrgBo> findOrgList(Search search, QueryParams queryParams);
    /**
     * judgement no is repeat
     * @param no
     * @return
     */
    boolean findExistOrg(String no);

    OrgBo getOrgBo(Integer id);

    void save(OrgBo orgBo);

    void deletes(Integer[] ids);

    OrgPo getOrg(Integer orgId);

    OrgPo getOrgByNo(String no);
}
