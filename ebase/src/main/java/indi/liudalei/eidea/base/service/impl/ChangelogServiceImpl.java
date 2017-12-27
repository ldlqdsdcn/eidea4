package indi.liudalei.eidea.base.service.impl;

import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.params.QueryParams;
import indi.liudalei.eidea.core.spring.annotation.DataAccess;
import indi.liudalei.eidea.base.entity.bo.ChangelogBo;
import indi.liudalei.eidea.base.entity.po.ChangelogPo;
import indi.liudalei.eidea.base.entity.po.UserPo;
import indi.liudalei.eidea.base.service.ChangelogService;
import indi.liudalei.eidea.core.dao.CommonDao;
import indi.liudalei.eidea.core.dao.TableDao;
import indi.liudalei.eidea.core.entity.bo.TableColumnBo;
import indi.liudalei.eidea.core.entity.po.TableColumnPo;
import indi.liudalei.eidea.core.entity.po.TablePo;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class ChangelogServiceImpl implements ChangelogService {
	@DataAccess(entity = ChangelogPo.class)
	private CommonDao<ChangelogPo,Integer> changelogDao;
	@DataAccess(entity = UserPo.class)
	private CommonDao<UserPo,Integer> userDao;
	@Autowired
	private TableDao tableDao;
	private final ModelMapper modelMapper = new ModelMapper();
	@Override
	public PaginationResult<ChangelogBo> getChangelogList(Search search, QueryParams queryParams) {
		search.setFirstResult(queryParams.getFirstResult());
		search.setMaxResults(queryParams.getPageSize());
		PaginationResult<ChangelogBo> paginationResult = null;
		if (queryParams.isInit()){
			SearchResult<ChangelogPo> searchResult = changelogDao.searchAndCount(search);
			List<ChangelogBo> ChangelogBoList = new ArrayList<ChangelogBo>();
			for(ChangelogPo change:searchResult.getResult()){
				ChangelogBo changelogBo= modelMapper.map(change, ChangelogBo.class);
				changelogBo.setTableName(change.getTablePo().getName());
				changelogBo.setSysUser(change.getUserPo().getName());
				changelogBo.setName(change.getTablePo().getTableName());
				ChangelogBoList.add(changelogBo);
			}
			paginationResult = PaginationResult.pagination(ChangelogBoList,searchResult.getTotalCount(),queryParams.getPageNo(),queryParams.getPageSize());
		}else{
			List<ChangelogPo> changelogPoList = changelogDao.search(search);
			List<ChangelogBo> ChangelogBoList = new ArrayList<ChangelogBo>();
			for(ChangelogPo change:changelogPoList){
				ChangelogBo changelogBo= modelMapper.map(change, ChangelogBo.class);
				changelogBo.setTableName(change.getTablePo().getName());
				changelogBo.setSysUser(change.getUserPo().getName());
				changelogBo.setName(change.getTablePo().getTableName());
				ChangelogBoList.add(changelogBo);
			}
			paginationResult = PaginationResult.pagination(ChangelogBoList,queryParams.getTotalRecords(),queryParams.getPageNo(),queryParams.getPageSize());
		}
		return paginationResult;
	}
	@Override
	public ChangelogBo getChangelogBo(Integer id) {

		ChangelogPo changelogPo= changelogDao.find(id);
		ChangelogBo changelogBo=modelMapper.map(changelogPo, ChangelogBo.class);
		changelogBo.setTableName(changelogPo.getTablePo().getName());
		changelogBo.setSysUser(changelogPo.getUserPo().getName());
		changelogBo.setName(changelogPo.getTablePo().getTableName());
		changelogBo.setPoClass(changelogPo.getTablePo().getPoClass());
		
		return changelogBo;
	}

	@Override
	public List<TableColumnBo> getChangelogHeader(String tableName) {
		Search search=new Search();
		search.addFilterEqual("tableName",tableName);
		TablePo tablePo=tableDao.searchUnique(search);
		List<TableColumnPo> columnList=tablePo.getCoreTableColumns().stream().filter((x) -> {
			return "Y".equals(x.getOutLog());
		}).collect(Collectors.toList());
		List<TableColumnBo> list=modelMapper.map(columnList,new TypeToken<List<TableColumnBo>>(){}.getType());
		return list;
	}

	@Override
	public List<ChangelogBo> getChangeLogModelList(Search search) {
		List<ChangelogPo> changelogPoList=changelogDao.search(search);
		List<ChangelogBo> changelogBoList=modelMapper.map(changelogPoList,new TypeToken<List<ChangelogBo>>(){}.getType());
		for(int i=0;i<changelogBoList.size();i++){
			changelogBoList.get(i).setTableName(changelogPoList.get(0).getTablePo().getName());
			changelogBoList.get(i).setSysUser(changelogPoList.get(0).getUserPo().getName());
			changelogBoList.get(i).setName(changelogPoList.get(0).getTablePo().getTableName());
			changelogBoList.get(i).setPoClass(changelogPoList.get(0).getTablePo().getPoClass());
		}
		return changelogBoList;
	}


}
