package com.dsdl.eidea.base.service.impl;

import com.dsdl.eidea.core.spring.annotation.DataAccess;
import com.dsdl.eidea.base.entity.bo.DirectoryBo;
import com.dsdl.eidea.base.entity.po.DirectoryPo;
import com.dsdl.eidea.base.service.DirectoryService;
import com.dsdl.eidea.core.dao.CommonDao;
import com.googlecode.genericdao.search.Search;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by admin on 2016/12/19.
 */
@Service
public class DirectoryServiceImpl implements DirectoryService {

    @DataAccess(entity = DirectoryPo.class)
    private CommonDao<DirectoryPo,Integer> directoryDao;
    private ModelMapper modelMapper = new ModelMapper();
    @Override
    public List<DirectoryBo> findDirectory(Search search) {
        List<DirectoryPo> listDirectory= directoryDao.search(search);
        List<DirectoryBo> directoryBoList = modelMapper.map(listDirectory, new TypeToken<List<DirectoryBo>>() {
        }.getType());
        return directoryBoList;
    }

    @Override
    public void deleteDirectoryById(Integer[] ids) {
        directoryDao.removeByIds(ids);
    }

    @Override
    public void save(DirectoryBo directoryBo) {
        DirectoryPo  directoryPo = modelMapper.map(directoryBo,DirectoryPo.class);
        directoryDao.save(directoryPo);
        directoryBo.setId(directoryPo.getId());

    }

    @Override
    public boolean findExistId(Integer id) {
        DirectoryPo pp=  directoryDao.find(id);
        if (pp != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public DirectoryBo getDirectoryBo(Integer id) {
        DirectoryPo pdirectoryPo=  directoryDao.find(id);
        DirectoryBo directoryBo=modelMapper.map(pdirectoryPo,DirectoryBo.class);
        return directoryBo;
    }
}
