/**
* 版权所有 刘大磊 2013-07-01
* 作者：刘大磊
* 电话：13336390671
* email:ldlqdsd@126.com
*/
package com.dsdl.eidea.base.service.impl;

import com.dsdl.eidea.base.entity.bo.FieldBo;
import com.dsdl.eidea.base.entity.bo.FieldInListPageBo;
import com.dsdl.eidea.base.entity.po.FieldTrlPo;
import com.dsdl.eidea.base.entity.po.TabPo;
import com.dsdl.eidea.base.exception.ServiceException;
import com.dsdl.eidea.core.def.FieldInputType;
import com.dsdl.eidea.core.entity.po.TableColumnPo;
import com.dsdl.eidea.core.entity.po.TablePo;
import com.dsdl.eidea.core.spring.annotation.DataAccess;
import com.googlecode.genericdao.search.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dsdl.eidea.base.entity.po.FieldPo;
import com.dsdl.eidea.base.service.FieldService;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.Search;
import com.dsdl.eidea.core.dao.CommonDao;

import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘大磊 2017-05-04 13:22:23
 */
@Service("fieldService")
public class FieldServiceImpl  implements	FieldService {
	private static String SELECT_KEY=" select ";
	private static String FROM_KEY=" from ";
	private static String WHERE_KEY=" where ";
	private static String LIMIT_KEY=" limit(%d,%d) ";
	private static String COLUMN_SPLIT_KEY=",";
	@DataAccess(entity =FieldPo.class)
	private CommonDao<FieldPo,Integer> fieldDao;
	@DataAccess(entity = FieldTrlPo.class)
	private CommonDao<FieldTrlPo,Integer> fieldTrlDao;
	@DataAccess(entity = TabPo.class)
	private CommonDao<TabPo,Integer> tabDao;
	@DataAccess(entity = TablePo.class)
	private CommonDao<TablePo,Integer> tableDao;
	@DataAccess(entity = TableColumnPo.class)
	private CommonDao<TableColumnPo,Integer> tableColumnDao;
	@Autowired
	private DataSource dataSource;
	public PaginationResult<FieldPo> getFieldListByPaging(Search search,QueryParams queryParams)
    {
		search.setFirstResult(queryParams.getFirstResult());
		search.setMaxResults(queryParams.getPageSize());
		PaginationResult<FieldPo> paginationResult = null;
		if (queryParams.isInit()) {
		SearchResult<FieldPo> searchResult = fieldDao.searchAndCount(search);
		paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		else
		{
		List<FieldPo> fieldPoList = fieldDao.search(search);
		paginationResult = PaginationResult.pagination(fieldPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
		}
    	return paginationResult;
    }
	public PaginationResult<FieldPo> getFieldListByColumnId(Search search,Integer columnId)
	{
		QueryParams queryParams = new QueryParams();
		search.setFirstResult(queryParams.getFirstResult());
		search.setMaxResults(queryParams.getPageSize());
		search.addFilterEqual("columnId",columnId);
		PaginationResult<FieldPo> paginationResult = null;
		if (queryParams.isInit()) {
			SearchResult<FieldPo> searchResult = fieldDao.searchAndCount(search);
			paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		else
		{
			List<FieldPo> fieldPoList = fieldDao.search(search);
			paginationResult = PaginationResult.pagination(fieldPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
		}
		return paginationResult;
	}
	public boolean findExistFieldByName(String name){
		Search search = new Search();
		search.addFilterEqual("name",name);

		List<FieldPo> fieldPoList = fieldDao.search(search);
		if (fieldPoList.size()>0&&fieldPoList!=null){
			return true;
		}else{
			return false;
		}
	}
    public FieldPo getField(Integer id)
	{
		return fieldDao.find(id);
	}
    public void saveField(FieldPo field)
	{
		fieldDao.save(field);
	}
    public void deletes(Integer[] ids)
	{
		fieldDao.removeByIds(ids);
	}

	@Override
	public List<FieldInListPageBo> getListPageFiledList(Integer tabId,String lang) {
		List<FieldInListPageBo> fieldInListPageBoList=new ArrayList<>();
		Search search=new Search();
		search.addFilterEqual("isdisplaygrid","Y");
		search.addSortAsc("seqnogrid");
		search.addSortAsc("seqNo");
		List<FieldPo> fieldPoList=fieldDao.search(search);
		for(FieldPo fieldPo:fieldPoList)
		{
			FieldInListPageBo fieldInListPageBo=new FieldInListPageBo();
			fieldInListPageBo.setId(fieldPo.getId());
			Search trlSearch=new Search();
			trlSearch.addFilterEqual("lang",lang);
			trlSearch.addFilterEqual("tabId",fieldPo.getId());
			FieldTrlPo fieldTrlPo=fieldTrlDao.searchUnique(trlSearch);
			if (fieldTrlPo!=null)
			{
				fieldInListPageBo.setName(fieldTrlPo.getName());
				fieldInListPageBoList.add(fieldInListPageBo);
			}
		}
		return fieldInListPageBoList;
	}

	@Override
	public List<FieldBo> getFormPageFieldList(Integer tabId,String lang) {
		return null;
	}
	@Override
	public List<Map<String, String>> getDataList(Integer tabId, int bgn, int size) {
		Search search=new Search();
		search.addFilterEqual("isdisplaygrid","Y");
		search.addSortAsc("seqnogrid");
		search.addSortAsc("seqNo");
		TabPo tabPo=tabDao.find(tabId);
		TablePo tablePo=tableDao.find(tabPo.getTableId());
		String tableName=tablePo.getTableName();

		List<FieldPo> fieldPoList=fieldDao.search(search);
		StringBuilder stringBuilder=new StringBuilder();
		boolean isBegin=true;
		List<FieldColumn> fieldColumnList=new ArrayList<>();
		for(FieldPo fieldPo:fieldPoList)
		{
			TableColumnPo tableColumnPo=tableColumnDao.find(fieldPo.getColumnId());

			FieldColumn fieldColumn=new FieldColumn();
			fieldColumn.fieldId=fieldPo.getId();
			fieldColumn.fieldPo=fieldPo;
			fieldColumn.columnName=tableColumnPo.getColumnName();
			fieldColumnList.add(fieldColumn);
			if(isBegin)
			{
				isBegin=false;
			}
			else
			{
				stringBuilder.append(COLUMN_SPLIT_KEY);
			}
			stringBuilder.append(tableColumnPo.getColumnName());

		}
		String sql=SELECT_KEY+stringBuilder.toString()+FROM_KEY+tableName+String.format(LIMIT_KEY,bgn,size);
		List<Map<String,String>> resultList=new ArrayList<>();
		try
		{
			Connection connection=dataSource.getConnection();
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sql);

			while (resultSet.next())
			{

				Map<String, String> resultMap=new HashMap<>();
				for(FieldColumn fieldColumn:fieldColumnList)
				{
					/**
					 * 如果是普通输入类型
					 */
					//TODO暂时先不考虑数据类型
					String value= resultSet.getString(fieldColumn.columnName);
					resultMap.put("id"+fieldColumn.fieldId,value);
					resultList.add(resultMap);
				}
			}


		}
		catch (Exception e)
		{
			throw new ServiceException("查询列表信息出错",e);
		}

		return resultList;
	}
	class FieldColumn{
		private Integer fieldId;
		private FieldPo fieldPo;
		private String columnName;
		private String value;
	}
}
