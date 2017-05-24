package com.dsdl.eidea.core.service.impl;

import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.params.QueryParams;
import com.dsdl.eidea.core.spring.annotation.DataAccess;
import com.dsdl.eidea.core.dao.CommonDao;
import com.dsdl.eidea.core.dao.TableDao;
import com.dsdl.eidea.core.def.JdbcType;
import com.dsdl.eidea.core.entity.bo.*;
import com.dsdl.eidea.core.entity.po.TableColumnPo;
import com.dsdl.eidea.core.entity.po.TablePo;
import com.dsdl.eidea.core.service.TableService;
import com.dsdl.eidea.util.StringUtil;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 刘大磊 on 2016/12/6 14:48.
 */
@Service
public class TableServiceImpl implements TableService {
    private static final Logger log = Logger.getLogger(TableServiceImpl.class);
    @Autowired
    private TableDao tableDao;
    @DataAccess(entity = TableColumnPo.class)
    private CommonDao<TableColumnPo, Integer> tableColumnDao;
    ModelMapper modelMapper;

    public TableServiceImpl() {
        modelMapper = new ModelMapper();
        modelMapper.addMappings(languageBoPropertyMap);
    }

    private PropertyMap<TablePo, TableBo> languageBoPropertyMap = new PropertyMap<TablePo, TableBo>() {
        @Override
        protected void configure() {
            skip().setTableColumnBoList(null);
        }
    };

    @Override
    public TableMetaDataBo getTableDescription(String tableName) {
        TableMetaDataBo tableMetaDataDto = null;
        log.debug(tableName);
        tableMetaDataDto = new TableMetaDataBo();
        tableMetaDataDto.setName(tableName);
        String comment = tableDao.getCommentByTableName(tableName);
        tableMetaDataDto.setRemark(comment);
        String primaryKey = tableDao.getPrimaryKey(tableName);
        tableMetaDataDto.setPkColumn(primaryKey);
        List<UniqueIndexBo> uniqueIndexList = tableDao.getUniqueIndex(tableName);
        tableMetaDataDto.setUniqueKeyList(uniqueIndexList);
        log.debug("primaryKey=" + primaryKey);
        tableMetaDataDto.setExportedFK(tableDao.getExportedKeys(tableName));
        tableMetaDataDto.setImportedFK(tableDao.getImportedKeys(tableName));
        List<ColumnMetaDataBo> columns = tableDao.getTableColumns(tableName);
        JdbcType[] columnDataTypes = JdbcType.values();
        for (ColumnMetaDataBo dto : columns) {
            for (JdbcType type : columnDataTypes) {
                if ("DOUBLE".equals(dto.getType())) {
                    log.debug("DOUBLE------>" + dto.getDataType());
                }
                if (type.getDesc().equals(dto.getType()) || type.getKey() == dto.getDataType()) {
                    dto.setDataType(type.getType());
                    break;
                }
            }
            if ("DOUBLE".equals(dto.getType())) {
                log.debug("DOUBLE------>" + dto.getDataType());
            }
        }
        tableMetaDataDto.setColumnList(columns);
        log.debug("columns=" + columns);
        return tableMetaDataDto;
    }

    public PaginationResult<TableBo> findList(Search search, QueryParams queryParams) {
        search.setFirstResult(queryParams.getFirstResult());
        search.setMaxResults(queryParams.getPageSize());
        PaginationResult<TableBo> paginationResult = null;
        if (queryParams.isInit()){
            SearchResult<TablePo> searchResult = tableDao.searchAndCount(search);
            List<TableBo> list = modelMapper.map(searchResult.getResult(),new TypeToken<List<TableBo>>(){}.getType());
            paginationResult = PaginationResult.pagination(list,searchResult.getTotalCount(),queryParams.getPageNo(),queryParams.getPageSize());
        }else{
            List<TablePo> tablePoList = tableDao.search(search);
            List<TableBo> tableBoList = modelMapper.map(tablePoList,new TypeToken<List<TableBo>>(){}.getType());
            paginationResult = PaginationResult.pagination(tableBoList,queryParams.getTotalRecords(),queryParams.getPageNo(),queryParams.getPageSize());
        }
        return paginationResult;
    }

    @Override
    public boolean findExistTableByName(String tableName) {
        Search search = new Search();
        search.addFilterEqual("tableName", tableName);
        List<TablePo> tablePoList = tableDao.search(search);
        if (tablePoList!=null&&tablePoList.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public TableBo getTableBo(Integer id) {
        TablePo tablePo = tableDao.find(id);

        List<TableColumnPo> tableColumnPoList = tablePo.getCoreTableColumns();
        TableBo tableBo = modelMapper.map(tablePo, TableBo.class);
        List<TableColumnBo> tableColumnBoList = modelMapper.map(tableColumnPoList, new TypeToken<List<TableColumnBo>>() {
        }.getType());
        tableBo.setTableColumnBoList(tableColumnBoList);
        return tableBo;
    }

    @Override
    public TableBo saveTableBo(TableBo tableBo) {
        ModelMapper modelMapper = new ModelMapper();
        TablePo tablePo = modelMapper.map(tableBo, TablePo.class);
        List<TableColumnPo> tableColumnPoList = modelMapper.map(tableBo.getTableColumnBoList(), new TypeToken<List<TableColumnPo>>() {
        }.getType());
        tableColumnPoList.forEach(e -> {
            e.setTablePo(tablePo);
        });
        tablePo.setCoreTableColumns(tableColumnPoList);
        tableDao.save(tablePo);
        return modelMapper.map(tablePo, TableBo.class);
    }

    public void deleteTables(Integer[] ids) {
        tableDao.removeByIds(ids);
    }

    @Override
    public void saveTableInfoByWizard(TableMetaDataBo tableInfo) {
        Map<String, String> param = new HashMap<>();
        param.put("name", tableInfo.getName().trim());
        Search search = new Search();
        search.addFilterEqual("tableName", tableInfo.getName());
        Integer tableCount = tableDao.count(search);
        if (tableCount > 0) {
            throw new RuntimeException("该表已经存在，不能重复创建");
        }
        TableMetaDataBo tmdd = new TableMetaDataBo();
        tmdd.setExportedFK(tableInfo.getExportedFK());
        tmdd.setImportedFK(tableInfo.getImportedFK());
        tmdd.setUniqueKeyList(tableInfo.getUniqueKeyList());

        Gson gson = new Gson();
        TablePo table = new TablePo();
        table.setTableName(tableInfo.getName());
        table.setPoClass(tableInfo.getClassName());
        table.setName(tableInfo.getTableTrlName());
        table.setBuPk(tableInfo.getBusPk());
        table.setExternJson(gson.toJson(tmdd));
        table.setRemark(tableInfo.getRemark());
        table.setIsactive("N");
        table.setOutLog(tableInfo.isOutLog() ? "Y" : "N");
        List<TableColumnPo> tableColumnPoList = new ArrayList<>();
        List<ColumnMetaDataBo> columnMetaDataDtoList = tableInfo.getColumnList();
        for (ColumnMetaDataBo columnMetaDataDto : columnMetaDataDtoList) {
            TableColumnPo tableColumn = new TableColumnPo();
            tableColumn.setTablePo(table);
            tableColumn.setColumnName(columnMetaDataDto.getColumnName());
            tableColumn.setCanShow("Y");
            tableColumn.setName(columnMetaDataDto.getRemarks());
            tableColumn.setColumnSize(columnMetaDataDto.getColumnSize());
            tableColumn.setDigits(columnMetaDataDto.getDecimalDigits());
            tableColumn.setDataType(columnMetaDataDto.getDataType());
            tableColumn.setOutLog("Y");
            tableColumn.setPropertyName(StringUtil.fieldToProperty(columnMetaDataDto.getColumnName()));
            tableColumn.setNullable(columnMetaDataDto.getNullable() ? "Y" : "N");
            List<UniqueIndexBo> uniqueKeyList = tableInfo.getUniqueKeyList();
            tableColumn.setIsUnique("N");
            for (UniqueIndexBo uk : uniqueKeyList) {
                if (uk.getIndexColumnName().equals(tableColumn.getColumnName())) {
                    tableColumn.setIsUnique("Y");
                }
            }
            tableColumnPoList.add(tableColumn);

        }
        table.setCoreTableColumns(tableColumnPoList);
        tableDao.save(table);
    }

    public List<TableBo> getTableBoListForOutLog() {
        Search search = new Search();
        search.addFilterEqual("isactive", "Y");
        search.addFilterEqual("outLog", "Y");
        List<TablePo> tablePoList = tableDao.search(search);
        List<TableBo> tableBoList = modelMapper.map(tablePoList, new TypeToken<List<TableBo>>() {
        }.getType());
        for (TableBo tableBo : tableBoList) {
            search = new Search();
            search.addFilterEqual("tablePo.id", tableBo.getId());
            search.addFilterEqual("outLog", "Y");
            List<TableColumnPo> tableColumnPoList = tableColumnDao.search(search);
            List<TableColumnBo> tableColumnBoList = modelMapper.map(tableColumnPoList, new TypeToken<List<TableColumnBo>>() {
            }.getType());
            tableBo.setTableColumnBoList(tableColumnBoList);
        }
        return tableBoList;
    }

    @Override
    public List<TableBo> findAllTableBoList(Search search) {
        List<TablePo> tablePoList = tableDao.search(search);
        return modelMapper.map(tablePoList, new TypeToken<List<TableBo>>() {}.getType());
    }
}
