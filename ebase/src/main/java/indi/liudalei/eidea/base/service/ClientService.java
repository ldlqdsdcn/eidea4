package indi.liudalei.eidea.base.service;

import indi.liudalei.eidea.base.entity.bo.ClientBo;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/12 17:37.
 */
public interface ClientService {
    PaginationResult<ClientBo> getClientList(Search search, QueryParams queryParams);

    /**
     * judgement no is repeat
     * @param no
     * @return
     */
    boolean findExistClient(String no);


    boolean findExistClientName(String clientName);

    ClientBo findExistClientByName(String clientName);

    ClientBo getClientBo(Integer id);

    void save(ClientBo clientBo);

    void deletes(Integer[] ids);

    List<ClientBo> getClientListForActivated();

    boolean getHasRolesByClientId(Integer id);
}
