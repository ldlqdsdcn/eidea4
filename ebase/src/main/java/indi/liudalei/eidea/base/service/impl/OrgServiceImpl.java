package indi.liudalei.eidea.base.service.impl;

import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.QueryParams;
import indi.liudalei.eidea.core.spring.annotation.DataAccess;
import indi.liudalei.eidea.base.entity.bo.ClientBo;
import indi.liudalei.eidea.base.entity.bo.OrgBo;
import indi.liudalei.eidea.base.entity.po.ClientPo;
import indi.liudalei.eidea.base.entity.po.OrgPo;
import indi.liudalei.eidea.base.service.OrgService;
import indi.liudalei.eidea.core.dao.CommonDao;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 刘大磊 on 2016/12/12 17:38.
 */
@Service
@Slf4j
public class OrgServiceImpl implements OrgService {
    private ModelMapper modelMapper = new ModelMapper();
    @DataAccess(entity = OrgPo.class)
    private CommonDao<OrgPo,Integer> orgDao;
    @DataAccess(entity = ClientPo.class)
    private CommonDao<ClientPo,Integer> clientDao;
    public PaginationResult<OrgBo> findOrgList(Search search, QueryParams queryParams) {
        search.setFirstResult(queryParams.getFirstResult());
        search.setMaxResults(queryParams.getPageSize());
        PaginationResult<OrgBo> paginationResult = null;
        if (queryParams.isInit()){
            SearchResult<OrgPo> searchResult = orgDao.searchAndCount(search);
            List<OrgBo> list = convert(searchResult.getResult());
            paginationResult = PaginationResult.pagination(list,searchResult.getTotalCount(),queryParams.getPageNo(),queryParams.getPageSize());
        }else{
            List<OrgPo> orgPoList = orgDao.search(search);
            List<OrgBo> orgBoList = modelMapper.map(orgPoList,new TypeToken<List<ClientBo>>(){}.getType());
            paginationResult = PaginationResult.pagination(orgBoList,queryParams.getTotalRecords(),queryParams.getPageNo(),queryParams.getPageSize());
        }
        return paginationResult;
    }

    @Override
    public boolean findExistOrg(String no) {
        Search search = new Search();
        search.addFilterEqual("no", no);
        int count = orgDao.count(search);
        if (count > 0)
            return true;
        return false;
    }

    @Override
    public OrgBo getOrgBo(Integer id) {
        OrgPo orgPo = orgDao.find(id);
        log.warn("orgPo.getClass="+orgPo.getClass().getName());
        ClientBo clientBo = modelMapper.map(orgPo.getSysClient(), ClientBo.class);
        OrgBo orgBo = modelMapper.map(orgPo, OrgBo.class);
        orgBo.setClient(clientBo);
        return orgBo;
    }

    @Override
    public void save(OrgBo orgBo) {
        ClientPo clientPo = clientDao.find(orgBo.getClient().getId());
        OrgPo orgPo = modelMapper.map(orgBo, OrgPo.class);
        orgPo.setSysClient(clientPo);

        orgDao.saveForLog(orgPo);
        orgBo.setId(orgPo.getId());
    }

    @Override
    public void deletes(Integer[] ids) {

        orgDao.removeByIdsForLog(ids);
    }

    @Override
    public OrgPo getOrg(Integer orgId) {

        OrgPo orgPo=orgDao.find(orgId);
//        orgPo.getSysClient();
//        orgPo.getSysClient().getSysOrgs();
        Hibernate.initialize(orgPo.getSysClient());
        Hibernate.initialize(orgPo.getSysClient().getSysOrgs());
        return orgPo;
    }

    @Override
    public OrgPo getOrgByNo(String no){
        Search search = new Search();
        search.addFilterEqual("no",no);
        return orgDao.searchUnique(search);
    }
    private List<OrgBo> convert(List<OrgPo> orgPoList) {
        return orgPoList.stream().map(e -> {
            OrgBo orgBo = modelMapper.map(e, OrgBo.class);
            orgBo.setClient(modelMapper.map(e.getSysClient(), ClientBo.class));
            return orgBo;
        }).collect(Collectors.toList());
    }
}
