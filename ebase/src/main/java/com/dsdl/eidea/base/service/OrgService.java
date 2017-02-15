package com.dsdl.eidea.base.service;

import com.dsdl.eidea.base.entity.bo.ClientBo;
import com.dsdl.eidea.base.entity.bo.OrgBo;
import com.googlecode.genericdao.search.Search;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/12 17:37.
 */
public interface OrgService {
    List<OrgBo> findOrgList(Search search);
    /**
     * judgement no is repeat
     * @param no
     * @return
     */
    boolean findExistOrg(String no);

    OrgBo getOrgBo(Integer id);

    void save(OrgBo orgBo);

    void deletes(Integer[] ids);
}
