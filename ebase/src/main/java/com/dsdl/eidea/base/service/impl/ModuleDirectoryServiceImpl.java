package com.dsdl.eidea.base.service.impl;

import com.dsdl.eidea.base.entity.bo.ModuleBo;
import com.dsdl.eidea.base.entity.bo.ModuleDirectoryBo;
import com.dsdl.eidea.base.entity.po.ModuleDirectoryPo;
import com.dsdl.eidea.base.service.ModuleDirectoryService;
import com.dsdl.eidea.core.dao.CommonDao;
import com.dsdl.eidea.core.spring.annotation.DataAccess;
import com.googlecode.genericdao.search.Search;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Bobo on 2017/5/11.
 */
@Service("moduleDirectoryService")
public class ModuleDirectoryServiceImpl implements ModuleDirectoryService{

    @DataAccess(entity = ModuleDirectoryPo.class )
    private CommonDao<ModuleDirectoryPo,Integer> moduleDirectoryDao;

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public List<ModuleDirectoryBo> getModuleDirectoryList(int directoryId) {
        Search search=new Search();
        search.addFilterEqual("sysDirectory.id",directoryId);
        search.addSortAsc("id");
        List<ModuleDirectoryPo> moduleDirectoryAllList=moduleDirectoryDao.search(search);
        return modelMapper.map(moduleDirectoryAllList,new TypeToken<List<ModuleDirectoryBo>>(){}.getType());
    }
}
