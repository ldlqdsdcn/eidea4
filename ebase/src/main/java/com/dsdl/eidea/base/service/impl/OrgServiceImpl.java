package com.dsdl.eidea.base.service.impl;

import com.dsdl.eidea.base.dao.ClientDao;
import com.dsdl.eidea.base.dao.OrgDao;
import com.dsdl.eidea.base.entity.bo.ClientBo;
import com.dsdl.eidea.base.entity.bo.OrgBo;
import com.dsdl.eidea.base.entity.po.ClientPo;
import com.dsdl.eidea.base.entity.po.OrgPo;
import com.dsdl.eidea.base.service.OrgService;
import com.googlecode.genericdao.search.Search;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 刘大磊 on 2016/12/12 17:38.
 */
@Service
public class OrgServiceImpl implements OrgService {
    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private OrgDao orgDao;
    @Autowired
    private ClientDao clientDao;

    public List<OrgBo> findOrgList(Search search) {
        List<OrgPo> orgPoList = orgDao.search(search);
        List<OrgBo> orgBoList = convert(orgPoList);
        return orgBoList;
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

        orgDao.save(orgPo);
        orgBo.setId(orgPo.getId());
    }

    @Override
    public void deletes(Integer[] ids) {

        orgDao.removeByIds(ids);
    }

    private List<OrgBo> convert(List<OrgPo> orgPoList) {
        return orgPoList.stream().map(e -> {
            OrgBo orgBo = modelMapper.map(e, OrgBo.class);
            orgBo.setClient(modelMapper.map(e.getSysClient(), ClientBo.class));
            return orgBo;
        }).collect(Collectors.toList());
    }
}
