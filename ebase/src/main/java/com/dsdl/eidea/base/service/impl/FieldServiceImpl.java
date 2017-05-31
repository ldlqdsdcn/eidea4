/**
 * 版权所有 刘大磊 2013-07-01
 * 作者：刘大磊
 * 电话：13336390671
 * email:ldlqdsd@126.com
 */
package com.dsdl.eidea.base.service.impl;

import com.dsdl.eidea.base.entity.bo.FieldInListPageBo;
import com.dsdl.eidea.base.entity.bo.FieldValueBo;
import com.dsdl.eidea.base.entity.po.*;
import com.dsdl.eidea.base.exception.ServiceException;
import com.dsdl.eidea.base.service.FieldService;
import com.dsdl.eidea.core.dao.CommonDao;
import com.dsdl.eidea.core.def.FieldInputType;
import com.dsdl.eidea.core.def.JavaDataType;
import com.dsdl.eidea.core.dto.PaginationResult;
import com.dsdl.eidea.core.entity.po.TableColumnPo;
import com.dsdl.eidea.core.entity.po.TablePo;
import com.dsdl.eidea.core.params.QueryParams;
import com.dsdl.eidea.core.spring.annotation.DataAccess;
import com.dsdl.eidea.general.bo.FieldStructureBo;
import com.dsdl.eidea.general.bo.TabFormStructureBo;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 刘大磊 2017-05-04 13:22:23
 */
@Slf4j
@Service("fieldService")
public class FieldServiceImpl implements FieldService {
    private static String SELECT_KEY = " select ";
    private static String FROM_KEY = " from ";
    private static String WHERE_KEY = " where ";
    private static String LIMIT_KEY = " limit %d,%d ";
    private static String COLUMN_SPLIT_KEY = ",";
    @DataAccess(entity = FieldPo.class)
    private CommonDao<FieldPo, Integer> fieldDao;
    @DataAccess(entity = FieldTrlPo.class)
    private CommonDao<FieldTrlPo, Integer> fieldTrlDao;
    @DataAccess(entity = TabPo.class)
    private CommonDao<TabPo, Integer> tabDao;
    @DataAccess(entity = TablePo.class)
    private CommonDao<TablePo, Integer> tableDao;
    @DataAccess(entity = TableColumnPo.class)
    private CommonDao<TableColumnPo, Integer> tableColumnDao;
    @DataAccess(entity = FieldValidatorPo.class)
    private CommonDao<FieldValidatorPo, Integer> fieldValidatorDao;
    @DataAccess(entity = ElementLinkedPo.class)
    private CommonDao<ElementLinkedPo, Integer> elementLinkedDao;
    @DataAccess(entity = ElementCheckboxPo.class)
    private CommonDao<ElementCheckboxPo,Integer> elementCheckboxDao;
    @Autowired
    private DataSource dataSource;

    public PaginationResult<FieldPo> getFieldListByPaging(Search search, QueryParams queryParams) {
        search.setFirstResult(queryParams.getFirstResult());
        search.setMaxResults(queryParams.getPageSize());
        PaginationResult<FieldPo> paginationResult = null;
        if (queryParams.isInit()) {
            SearchResult<FieldPo> searchResult = fieldDao.searchAndCount(search);
            paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
        } else {
            List<FieldPo> fieldPoList = fieldDao.search(search);
            paginationResult = PaginationResult.pagination(fieldPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
        }
        return paginationResult;
    }

    public PaginationResult<FieldPo> getFieldListByTabId(Search search, Integer tabId) {
        QueryParams queryParams = new QueryParams();
        search.setFirstResult(queryParams.getFirstResult());
        search.setMaxResults(queryParams.getPageSize());
        search.addFilterEqual("columnId", tabId);
        PaginationResult<FieldPo> paginationResult = null;
        if (queryParams.isInit()) {
            SearchResult<FieldPo> searchResult = fieldDao.searchAndCount(search);
            paginationResult = PaginationResult.pagination(searchResult.getResult(), searchResult.getTotalCount(), queryParams.getPageNo(), queryParams.getPageSize());
        } else {
            List<FieldPo> fieldPoList = fieldDao.search(search);
            paginationResult = PaginationResult.pagination(fieldPoList, queryParams.getTotalRecords(), queryParams.getPageNo(), queryParams.getPageSize());
        }
        return paginationResult;
    }

    public boolean findExistFieldByName(String name) {
        Search search = new Search();
        search.addFilterEqual("name", name);

        List<FieldPo> fieldPoList = fieldDao.search(search);
        if (fieldPoList.size() > 0 && fieldPoList != null) {
            return true;
        } else {
            return false;
        }
    }

    public FieldPo getField(Integer id) {
        return fieldDao.find(id);
    }

    public void saveField(FieldPo field) {
        fieldDao.save(field);
    }

    public void deletes(Integer[] ids) {
        fieldDao.removeByIds(ids);
    }

    @Override
    public List<FieldInListPageBo> getListPageFiledList(Integer tabId, String lang) {
        List<FieldInListPageBo> fieldInListPageBoList = new ArrayList<>();
        Search search = new Search();
        search.addFilterEqual("tabId", tabId);
        search.addSortAsc("seqnogrid");
        search.addSortAsc("seqNo");
        List<FieldPo> fieldPoList = fieldDao.search(search);
        for (FieldPo fieldPo : fieldPoList) {
            FieldInListPageBo fieldInListPageBo = new FieldInListPageBo();
            fieldInListPageBo.setId(fieldPo.getId());
            fieldInListPageBo.setFieldPo(fieldPo);
            Search trlSearch = new Search();
            trlSearch.addFilterEqual("lang", lang);
            trlSearch.addFilterEqual("fieldId", fieldPo.getId());
            FieldTrlPo fieldTrlPo = fieldTrlDao.searchUnique(trlSearch);
            TableColumnPo tableColumnPo = tableColumnDao.find(fieldPo.getColumnId());
            JavaDataType dataType = JavaDataType.getJavaDataTypeByKey(tableColumnPo.getDataType());
            FieldInputType fieldInputType = FieldInputType.getFieldInputTypeByKey(fieldPo.getInputType());
            fieldInListPageBo.setDataType(dataType);
            fieldInListPageBo.setFieldInputType(fieldInputType);
            if (fieldTrlPo != null) {
                fieldInListPageBo.setName(fieldTrlPo.getName());
            } else {
                fieldInListPageBo.setName(fieldPo.getName());
            }
            if (dataType == JavaDataType.DATE) {
                if (fieldInputType == FieldInputType.DATEPICKER) {
                    fieldInListPageBo.setPattern("|date:\"yyyy-MM-dd\"");
                } else if (fieldInputType == FieldInputType.DATETIMEPICKER) {
                    fieldInListPageBo.setPattern("|date:\"yyyy-MM-dd HH:mm:ss\"");
                }
            }
            fieldInListPageBoList.add(fieldInListPageBo);
        }
        return fieldInListPageBoList;
    }

    @Override
    public TabFormStructureBo getFormPageFieldList(Integer tabId, String lang) {
        TabFormStructureBo tabFormStructureBo = new TabFormStructureBo();
        List<FieldStructureBo> fieldStructureBoList = new ArrayList<>();
        Search search = new Search();
        search.addSortAsc("seqnogrid");
        search.addSortAsc("seqNo");
        search.addFilterEqual("tabId", tabId);
        List<FieldPo> fieldPoList = fieldDao.search(search);
        for (FieldPo fieldPo : fieldPoList) {
            FieldStructureBo fieldStructureBo = new FieldStructureBo();
            Search fieldTrlSearch = new Search();
            fieldTrlSearch.addFilterEqual("fieldId", fieldPo.getId());
            fieldTrlSearch.addFilterEqual("lang", lang);
            FieldTrlPo fieldTrlPo = fieldTrlDao.searchUnique(fieldTrlSearch);
            if (fieldTrlPo == null) {
                fieldTrlPo = new FieldTrlPo();
                fieldTrlPo.setName(fieldPo.getName());
                fieldTrlPo.setDescription(fieldPo.getDescription());
                fieldTrlPo.setHelp("");
            }
            FieldInputType fieldInputType = FieldInputType.getFieldInputTypeByKey(fieldPo.getInputType());
            fieldStructureBo.setFieldInputType(fieldInputType);
            fieldStructureBo.setFieldPo(fieldPo);
            fieldStructureBo.setFieldTrlPo(fieldTrlPo);
            Search fieldValidatorSearch = new Search();
            fieldValidatorSearch.addFilterEqual("fieldId", fieldPo.getId());
            List<FieldValidatorPo> fieldValidatorPoList = fieldValidatorDao.search(fieldValidatorSearch);
            fieldStructureBo.setFieldValidatorPoList(fieldValidatorPoList);

            if(FieldInputType.CHECKBOX==fieldInputType)
            {
                Search checkSearch=new Search();
                checkSearch.addFilterEqual("elementId",fieldPo.getElementId());
                ElementCheckboxPo elementCheckboxPo=elementCheckboxDao.searchUnique(checkSearch);
                fieldStructureBo.setFalseValue(elementCheckboxPo.getUncheckedValue());
                fieldStructureBo.setTrueValue(elementCheckboxPo.getCheckedValue());
            }

            fieldStructureBoList.add(fieldStructureBo);
        }
        tabFormStructureBo.setFieldStructureBoList(fieldStructureBoList);
        return tabFormStructureBo;
    }

    @Override
    public PaginationResult<Map<String, Object>> getDataList(Integer tabId, QueryParams queryParams) {

        TabPo tabPo = tabDao.find(tabId);
        TablePo tablePo = tableDao.find(tabPo.getTableId());
        String tableName = tablePo.getTableName();

        StringBuilder stringBuilder = new StringBuilder();
        boolean isBegin = true;
        List<FieldColumn> fieldColumnList = new ArrayList<>();
        Search search = new Search();
        search.addSortAsc("seqnogrid");
        search.addSortAsc("seqNo");
        search.addFilterEqual("tabId", tabId);
        List<FieldPo> fieldPoList = fieldDao.search(search);
        for (FieldPo fieldPo : fieldPoList) {
            TableColumnPo tableColumnPo = tableColumnDao.find(fieldPo.getColumnId());
            FieldColumn fieldColumn = new FieldColumn();
            fieldColumn.fieldId = fieldPo.getId();
            fieldColumn.fieldPo = fieldPo;
            fieldColumn.columnName = tableColumnPo.getColumnName();
            fieldColumn.tableColumnPo = tableColumnPo;
            fieldColumnList.add(fieldColumn);
            if (isBegin) {
                isBegin = false;
            } else {
                stringBuilder.append(COLUMN_SPLIT_KEY);
            }
            stringBuilder.append(tableColumnPo.getColumnName());

        }
        String sql = SELECT_KEY + stringBuilder.toString() + FROM_KEY + tableName + String.format(LIMIT_KEY, queryParams.getFirstResult(), queryParams.getPageSize());
        String countSql = SELECT_KEY + " count(*) " + FROM_KEY + tableName;
        int count = 0;
        try {
            Connection countConnection = dataSource.getConnection();
            Statement countStatement = countConnection.createStatement();
            ResultSet countResultSet = countStatement.executeQuery(countSql);
            while (countResultSet.next()) {
                count = countResultSet.getInt(1);
            }
            countResultSet.close();
            countStatement.close();
            countConnection.close();
        } catch (Exception e) {
            throw new ServiceException("获取统计数量出错", e);
        }
        List<List<FieldValueBo>> resultList = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                List<FieldValueBo> oneRecord = getOneRecord(resultSet, fieldColumnList);
                resultList.add(oneRecord);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            throw new ServiceException("查询列表信息出错", e);
        }
        List<Map<String, Object>> resultMapList = new ArrayList<>();
        for (List<FieldValueBo> fieldValueBoList : resultList) {
            Map<String, Object> recordMap = new HashMap<>();
            for (FieldValueBo valueBo : fieldValueBoList) {
                Object value = null;
                switch (valueBo.getFieldPo().getShowType()) {
                    case LINKED:
                        try {
                            value = getLinkedValue(valueBo);
                        } catch (SQLException e) {
                            log.error("获取关联的数据出错", e);
                            throw new ServiceException("sql语句错误", e);
                        }
                        break;
                    default:
                        value = valueBo.getValue();
                        break;

                }

                recordMap.put("id" + valueBo.getFieldPo().getId(), value);

            }
            resultMapList.add(recordMap);
        }
        PaginationResult paginationResult = PaginationResult.pagination(resultMapList, count, queryParams.getPageNo(), queryParams.getPageSize());
        return paginationResult;
    }

    private Object getLinkedValue(FieldValueBo fieldValueBo) throws SQLException {
        Search search = new Search();
        search.addFilterEqual("elementId", fieldValueBo.getFieldPo().getElementId());
        ElementLinkedPo elementLinkedPo = elementLinkedDao.searchUnique(search);
        TablePo tablePo = tableDao.find(elementLinkedPo.getLinkedTableId());
        TableColumnPo columnKeyPo = tableColumnDao.find(elementLinkedPo.getLinkedColumnId());
        TableColumnPo columnValuePo = tableColumnDao.find(elementLinkedPo.getLinkedColumnValue());
        String key = columnKeyPo.getColumnName();
        String value = columnValuePo.getColumnName();
        String sql = SELECT_KEY + value + FROM_KEY + tablePo.getTableName() + WHERE_KEY + key + "=" + fieldValueBo.getValue();
        Object resultValue = null;
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                JavaDataType javaDataType = JavaDataType.getJavaDataTypeByKey(columnValuePo.getDataType());
                resultValue = getValue(resultSet, javaDataType, value);
            }
            resultSet.close();
            statement.close();
            connection.close();

        } catch (Exception e) {
            log.error("获取关联值出错", e);
            throw new ServiceException("获取关联值出错", e);
        }
        return resultValue;
    }

    private Object getValue(ResultSet resultSet, JavaDataType javaDataType, String columnName) throws SQLException {
        Object value = null;
        switch (javaDataType) {
            case DATE:
                value = resultSet.getTimestamp(columnName);
                break;
            case BYTES:
                value = resultSet.getBytes(columnName);
                break;
            case INT:
                value = resultSet.getInt(columnName);
                break;
            case DECIMAL:
                value = resultSet.getBigDecimal(columnName);
                break;
            case DOUBLE:
                value = resultSet.getDouble(columnName);
                break;
            case FLOAT:
                value = resultSet.getFloat(columnName);
                break;
            case LONG:
                value = resultSet.getLong(columnName);
                break;
            case STRING:
                value = resultSet.getString(columnName);
                break;
            case OTHER:
                value = resultSet.getObject(columnName);
        }
        return value;
    }

    private List<FieldValueBo> getOneRecord(ResultSet resultSet, List<FieldColumn> fieldColumnList) throws SQLException {
        List<FieldValueBo> resultList = new ArrayList<>();
        for (FieldColumn fieldColumn : fieldColumnList) {
            /**
             * 如果是普通输入类型
             */
            //TODO暂时先不考虑数据类型
            JavaDataType javaDataType = JavaDataType.getJavaDataTypeByKey(fieldColumn.tableColumnPo.getDataType());
            Object value = getValue(resultSet, javaDataType, fieldColumn.columnName);
            FieldValueBo fieldValueBo = new FieldValueBo();
            fieldValueBo.setFieldPo(fieldColumn.fieldPo);
            fieldValueBo.setValue(value);
            resultList.add(fieldValueBo);
        }
        return resultList;
    }

    @Override
    public Map<String, Object> getDataForm(Integer tabId, Integer recordId) {
        TabPo tabPo = tabDao.find(tabId);
        TablePo tablePo = tableDao.find(tabPo.getTableId());
        TableColumnPo tableColumnPo = tableColumnDao.find(tabPo.getTableColumnId());

        Search search = new Search();
        search.addSortAsc("seqNo");
        search.addFilterEqual("tabId", tabId);
        StringBuilder stringBuilder = new StringBuilder();
        List<FieldPo> fieldPoList = fieldDao.search(search);
        List<FieldColumn> fieldColumnList = new ArrayList<>();
        boolean isBegin = true;
        for (FieldPo fieldPo : fieldPoList) {
            FieldColumn fieldColumn = new FieldColumn();
            TableColumnPo columnPo = tableColumnDao.find(fieldPo.getColumnId());
            fieldColumn.fieldId = fieldPo.getId();
            fieldColumn.fieldPo = fieldPo;
            fieldColumn.columnName = columnPo.getColumnName();
            fieldColumn.tableColumnPo = columnPo;
            fieldColumnList.add(fieldColumn);
            if (isBegin) {
                isBegin = false;
            } else {
                stringBuilder.append(COLUMN_SPLIT_KEY);
            }
            stringBuilder.append(fieldColumn.columnName);
        }
        List<FieldValueBo> resultList = null;
        String sql = SELECT_KEY + stringBuilder.toString() + FROM_KEY + tablePo.getTableName() + WHERE_KEY + tableColumnPo.getColumnName() + "=" + recordId;
        try {
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                resultList = getOneRecord(resultSet, fieldColumnList);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            throw new ServiceException("查询列表信息出错", e);
        }
        Map<String, Object> resultMap = new HashMap<>();
        for (FieldValueBo valueBo : resultList) {
            Object value = null;
            switch (valueBo.getFieldPo().getShowType()) {
                case LINKED:
                    try {
                        value = getLinkedValue(valueBo);
                    } catch (SQLException e) {
                        log.error("获取关联的数据出错", e);
                        throw new ServiceException("sql语句错误", e);
                    }
                    break;
                default:
                    value = valueBo.getValue();
                    break;

            }

            resultMap.put("id" + valueBo.getFieldPo().getId(), value);

        }
        return resultMap;
    }

    @Override
    public Map<String, String> saveForUpdated(Map<String, String> result) {
        return null;
    }

    class FieldColumn {
        private Integer fieldId;
        private FieldPo fieldPo;
        private String columnName;
        private String value;
        private TableColumnPo tableColumnPo;
    }
}
