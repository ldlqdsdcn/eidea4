/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package indi.liudalei.eidea.base.service.impl;

import indi.liudalei.eidea.base.entity.bo.CommonFileBo;
import indi.liudalei.eidea.base.entity.po.FileRelationPo;
import indi.liudalei.eidea.core.spring.annotation.DataAccess;
import indi.liudalei.eidea.util.StringUtil;
import indi.liudalei.eidea.base.entity.po.CommonFilePo;
import indi.liudalei.eidea.base.service.CommonFileService;
import indi.liudalei.eidea.core.dao.CommonDao;
import indi.liudalei.eidea.core.dto.PaginationResult;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;
import indi.liudalei.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.Search;

import java.util.List;
/**
 * @author 刘大磊 2017-05-02 13:09:39
 */
@Service("commonFileService")
public class CommonFileServiceImpl  implements CommonFileService {
	@DataAccess(entity =CommonFilePo.class)
	private CommonDao<CommonFilePo,Integer> commonFileDao;
	@DataAccess(entity =FileRelationPo.class)
	private CommonDao<FileRelationPo,Integer> commonFileRelationDao;
	private ModelMapper modelMapper = new ModelMapper();

	public PaginationResult<CommonFilePo> getCommonFileListByPaging(Search search, QueryParams queryParams)
    {
		search.setFirstResult(queryParams.getFirstResult());
		search.setMaxResults(queryParams.getPageSize());
		PaginationResult<CommonFilePo> paginationResult = null;
		if (queryParams.isInit()) {
		SearchResult<CommonFilePo> searchResult = commonFileDao.searchAndCount(search);
		paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		else
		{
		List<CommonFilePo> commonFilePoList = commonFileDao.search(search);
		paginationResult = PaginationResult.pagination(commonFilePoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
		}
    	return paginationResult;
    }

    public CommonFilePo getCommonFile(Integer id)
	{
		return commonFileDao.find(id);
	}
    public void saveCommonFile(CommonFilePo commonFile)
	{
		commonFileDao.saveForLog(commonFile);
	}
    public void deletes(Integer[] ids)
	{
		commonFileDao.removeByIdsForLog(ids);
	}

	@Override
	public List<CommonFileBo> getAttachmentList(Search search) {
		List<CommonFilePo> commonFilePoList = commonFileDao.search(search);
		return modelMapper.map(commonFilePoList,new TypeToken<List<CommonFileBo>>(){}.getType());
	}

	@Override
	public void saveAttachmentUpload(CommonFileBo commonFileBo) {
		CommonFilePo commonFilePo=new CommonFilePo();
		if(StringUtil.isNotEmpty(commonFileBo.getFileKeyword()) && !commonFileBo.getFileKeyword().equals("null")){
			commonFilePo.setFileKeyword(commonFileBo.getFileKeyword());
		}
		if(StringUtil.isNotEmpty(commonFileBo.getFileAbstract()) && !commonFileBo.getFileAbstract().equals("null")){
			commonFilePo.setFileAbstract(commonFileBo.getFileAbstract());
		}
		commonFilePo.setFilename(commonFileBo.getFileName());
		commonFilePo.setExtension(commonFileBo.getExtension());
		commonFilePo.setPath(commonFileBo.getPath());
		commonFilePo.setFileSize(commonFileBo.getFileSize());
		commonFilePo.setFileCreated(commonFileBo.getFileCreated());
		commonFilePo.setFileIsreadonly(commonFileBo.getFileIsreadonly());
		commonFilePo.setFileIshidden(commonFileBo.getFileIshidden());
		commonFilePo.setCreated(commonFileBo.getCreated());
		commonFilePo.setCommonFileSettingId(commonFileBo.getCommonFileSettingId());
		commonFileDao.saveForLog(commonFilePo);
		FileRelationPo fileRelationPo=new FileRelationPo();
		fileRelationPo.setTableName(commonFileBo.getUri());
		fileRelationPo.setFileId(commonFilePo.getId());
		fileRelationPo.setTableId(Integer.parseInt(commonFileBo.getTableId()));
		fileRelationPo.setCreated(commonFileBo.getCreated());
		commonFileRelationDao.saveForLog(fileRelationPo);
	}

	@Override
	public void deleteAttachment(CommonFileBo commonFileBo) {
		Search search=new Search();
		search.addFilterEqual("fileId",commonFileBo.getId());
		List<FileRelationPo> FileRelationPoList=commonFileRelationDao.search(search);
		FileRelationPoList.forEach(fileRelationPo -> {
			commonFileRelationDao.removeByIdForLog(fileRelationPo.getId());
		});
		commonFileDao.removeByIdForLog(commonFileBo.getId());
	}
}
