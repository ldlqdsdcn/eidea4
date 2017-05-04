package com.dsdl.eidea.base.service.impl;

import com.dsdl.eidea.base.entity.po.OrgPo;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.dsdl.eidea.core.spring.annotation.DataAccess;
import com.dsdl.eidea.base.entity.bo.ClientBo;
import com.dsdl.eidea.base.entity.po.ClientPo;
import com.dsdl.eidea.base.service.ClientService;
import com.dsdl.eidea.core.dao.CommonDao;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * Created by 刘大磊 on 2016/12/12 17:38 9:48.
 */
@Service
public class ClientServiceImpl implements ClientService {
    @DataAccess(entity = ClientPo.class)
    private CommonDao<ClientPo, Integer> clientDao;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public PaginationResult<ClientBo> getClientList(Search search, QueryParams queryParams) {
        search.setFirstResult(queryParams.getFirstResult());
        search.setMaxResults(queryParams.getPageSize());
        PaginationResult<ClientBo> paginationResult = null;
        if (queryParams.isInit()){
            SearchResult<ClientPo> searchResult =  clientDao.searchAndCount(search);
            List<ClientBo> clientBoList = modelMapper.map(searchResult.getResult(),new TypeToken<List<ClientBo>>(){
            }.getType());
            paginationResult = PaginationResult.pagination(clientBoList,searchResult.getTotalCount(),queryParams.getPageNo(),queryParams.getPageSize());
        } else {
            List<ClientPo> clientPoList = clientDao.search(search);
            List<ClientBo> clientBoList= modelMapper.map(clientPoList,new TypeToken<List<ClientBo>>(){}.getType());
            paginationResult=PaginationResult.pagination(clientBoList,queryParams.getTotalRecords(),queryParams.getPageNo(),queryParams.getPageSize());
        }
        return paginationResult;
    }

    public boolean findExistClient(String no) {
        Search search = new Search();
        search.addFilterEqual("no", no);
        List<ClientPo> clientPoList = clientDao.search(search);
        if (clientPoList.size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean findExistClientName(String clientName) {
        Search search = new Search();
        search.addFilterEqual("name", clientName);
        List<ClientPo> clientPoList = clientDao.search(search);
        if (clientPoList != null && clientPoList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    public ClientBo findExistClientByName(String clientName){
        Search search = new Search();
        search.addFilterEqual("name",clientName);
        ClientPo clientPo = clientDao.searchUnique(search);
        ClientBo clientBo = modelMapper.map(clientPo,ClientBo.class);
        return clientBo;
    }

    public ClientBo getClientBo(Integer id) {
        ClientPo clientPo = clientDao.find(id);
        return modelMapper.map(clientPo, ClientBo.class);
    }

    public void save(ClientBo clientBo) {
        ClientPo clientPo = modelMapper.map(clientBo, ClientPo.class);
        clientDao.save(clientPo);
        clientBo.setId(clientPo.getId());
    }

    public void deletes(Integer[] ids) {
        clientDao.removeByIds(ids);
    }

    public List<ClientBo> getClientListForActivated() {
        Search search = new Search();
        search.addFilterEqual("isactive", "Y");
        return modelMapper.map(clientDao.search(search), new TypeToken<List<ClientBo>>() {
        }.getType());
    }

    @Override
    public boolean getHasRolesByClientId(Integer id) {
        ClientPo clientPo=clientDao.find(id);
        Set<OrgPo> orgPos=clientPo.getSysOrgs();
        if (orgPos.size()>0){
            return true;
        }
        return false;
    }
}
