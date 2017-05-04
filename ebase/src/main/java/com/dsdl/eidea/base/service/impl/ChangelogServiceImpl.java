package com.dsdl.eidea.base.service.impl;

import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.dsdl.eidea.core.spring.annotation.DataAccess;
import com.dsdl.eidea.base.entity.bo.ChangelogBo;
import com.dsdl.eidea.base.entity.po.ChangelogPo;
import com.dsdl.eidea.base.entity.po.UserPo;
import com.dsdl.eidea.base.service.ChangelogService;
import com.dsdl.eidea.core.dao.CommonDao;
import com.dsdl.eidea.core.dao.TableDao;
import com.dsdl.eidea.core.entity.bo.TableColumnBo;
import com.dsdl.eidea.core.entity.po.TableColumnPo;
import com.dsdl.eidea.core.entity.po.TablePo;
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


}
