/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.base.service.impl;

import com.dsdl.eidea.base.entity.bo.ModuleBo;
import com.dsdl.eidea.base.service.ModuleService;
import com.dsdl.eidea.core.spring.annotation.DataAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dsdl.eidea.base.entity.po.FileSettingPo;
import com.dsdl.eidea.base.service.FileSettingService;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.Search;
import com.dsdl.eidea.core.dao.CommonDao;
import java.util.List;
/**
 * @author 刘大磊 2017-05-02 13:07:50
 */
@Service("fileSettingService")
public class FileSettingServiceImpl  implements	FileSettingService {
	@DataAccess(entity =FileSettingPo.class)
	private CommonDao<FileSettingPo,Integer> fileSettingDao;
	@Autowired
	private ModuleService moduleService;
	public PaginationResult<FileSettingPo> getFileSettingListByPaging(Search search,QueryParams queryParams)
    {
		search.setFirstResult(queryParams.getFirstResult());
		search.setMaxResults(queryParams.getPageSize());
		PaginationResult<FileSettingPo> paginationResult = null;
		if (queryParams.isInit()) {
		SearchResult<FileSettingPo> searchResult = fileSettingDao.searchAndCount(search);
		paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		else
		{
		List<FileSettingPo> fileSettingPoList = fileSettingDao.search(search);
		paginationResult = PaginationResult.pagination(fileSettingPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
		}
    	return paginationResult;
    }

    public FileSettingPo getFileSetting(Integer id)
	{
		return fileSettingDao.find(id);
	}
    public void saveFileSetting(FileSettingPo fileSetting)
	{
		fileSettingDao.save(fileSetting);
	}
    public void deletes(Integer[] ids)
	{
		fileSettingDao.removeByIds(ids);
	}

	@Override
	public List<FileSettingPo> getFileSettingList(Search search) {
		return fileSettingDao.search(search);
	}

	public FileSettingPo getFileSettingsByRequestPath(String path)
	{
		ModuleBo moduleBo=moduleService.getModuleBoByPath(path);
		Search search=new Search();
		search.addFilterEqual("moduleId",moduleBo.getId());
		FileSettingPo fileSettingPo=fileSettingDao.searchUnique(search);
		return fileSettingPo;
	}
}
