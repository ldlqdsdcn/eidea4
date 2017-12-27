package indi.liudalei.eidea.base.service.impl;

import indi.liudalei.eidea.base.entity.bo.ModuleDirectoryBo;
import indi.liudalei.eidea.base.entity.po.ModuleDirectoryPo;
import indi.liudalei.eidea.base.service.ModuleDirectoryService;
import indi.liudalei.eidea.core.dao.CommonDao;
import indi.liudalei.eidea.core.spring.annotation.DataAccess;
import com.googlecode.genericdao.search.Search;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
