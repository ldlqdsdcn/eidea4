package com.dsdl.eidea.base.service;

import com.dsdl.eidea.base.entity.bo.ClientBo;
import com.googlecode.genericdao.search.ISearch;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/12 17:37.
 */
public interface ClientService {
    List<ClientBo> getClientList(ISearch search);

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
