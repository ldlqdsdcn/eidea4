/**
 * 版权所有 刘大磊 2013-07-01
 * 作者：刘大磊
 * 电话：13336390671
 * email:ldlqdsd@126.com
 */
package indi.liudalei.eidea.base.service.impl;

import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
import indi.liudalei.eidea.base.def.BoolChar;
import indi.liudalei.eidea.base.entity.bo.FieldInListPageBo;
import indi.liudalei.eidea.base.entity.bo.FieldValueBo;
import indi.liudalei.eidea.base.entity.bo.SelectItemBo;
import indi.liudalei.eidea.base.entity.bo.UserBo;
import indi.liudalei.eidea.base.entity.po.*;
import indi.liudalei.eidea.base.exception.ServiceException;
import indi.liudalei.eidea.base.service.FieldService;
import indi.liudalei.eidea.core.dao.CommonDao;
import indi.liudalei.eidea.core.def.*;
import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.entity.bo.QueryExpression;
import indi.liudalei.eidea.core.entity.bo.SqlColumn;
import indi.liudalei.eidea.core.entity.bo.SqlCondition;
import indi.liudalei.eidea.core.entity.po.TableColumnPo;
import indi.liudalei.eidea.core.entity.po.TablePo;
import indi.liudalei.eidea.core.params.QueryParams;
import indi.liudalei.eidea.core.spring.annotation.DataAccess;
import indi.liudalei.eidea.core.util.SQLUtil;
import indi.liudalei.eidea.general.bo.FieldStructureBo;
import indi.liudalei.eidea.general.bo.TabFormStructureBo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.Date;

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
    private static String UPDATE_KEY = " update ";
    private static String DELETE_KEY = " delete ";
    private static String SET_KEY = " set ";
    private static String SQL_EQUAL_KEY = "=";
    private static String INSERT_KEY = " insert into ";
    private static String LEFT_BRACKETS_KEY = "(";
    private static String RIGHT_BRACKETS_KEY = ")";
    private static String VALUES_KEY = "values";
    private static String ANGULARJS_DATE_FILTER = "|date:\"yyyy-MM-dd\"";
    private static String ANGULARJS_DATETIME_FILTER = "|date:\"yyyy-MM-dd HH:mm:ss\"";
    private static String FIELD_TAB_ID = "tabId";
    private static String FIELD_SEQ_NO = "seqNo";
    private static String FIELD_SEQNOGRID = "seqnogrid";
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
    private CommonDao<ElementCheckboxPo, Integer> elementCheckboxDao;
    @DataAccess(entity = ElementSelectPo.class)
    private CommonDao<ElementSelectPo, Integer> elementSelectDao;
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
        search.addFilterEqual("tabId", tabId);
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
        search.addFilterEqual(FIELD_TAB_ID, tabId);
        search.addSortAsc(FIELD_SEQNOGRID);
        search.addSortAsc(FIELD_SEQ_NO);
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
            FieldInputType fieldInputType = fieldPo.getInputType();
            fieldInListPageBo.setDataType(dataType);
            fieldInListPageBo.setFieldInputType(fieldInputType);
            if (fieldTrlPo != null) {
                fieldInListPageBo.setName(fieldTrlPo.getName());
            } else {
                fieldInListPageBo.setName(fieldPo.getName());
            }
            if (dataType == JavaDataType.DATE) {
                if (fieldInputType == FieldInputType.DATEPICKER) {
                    fieldInListPageBo.setPattern(ANGULARJS_DATE_FILTER);
                } else if (fieldInputType == FieldInputType.DATETIMEPICKER) {
                    fieldInListPageBo.setPattern(ANGULARJS_DATETIME_FILTER);
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
        search.addSortAsc(FIELD_SEQNOGRID);
        search.addSortAsc(FIELD_SEQ_NO);
        search.addFilterEqual(FIELD_TAB_ID, tabId);
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
            FieldInputType fieldInputType = FieldInputType.getFieldInputTypeByKey(fieldPo.getInputType().getKey());
            fieldStructureBo.setFieldInputType(fieldInputType);
            fieldStructureBo.setFieldPo(fieldPo);
            fieldStructureBo.setFieldTrlPo(fieldTrlPo);
            Search fieldValidatorSearch = new Search();
            fieldValidatorSearch.addFilterEqual("fieldId", fieldPo.getId());
            List<FieldValidatorPo> fieldValidatorPoList = fieldValidatorDao.search(fieldValidatorSearch);
            fieldStructureBo.setFieldValidatorPoList(fieldValidatorPoList);

            if (FieldInputType.CHECKBOX == fieldInputType) {
                Search checkSearch = new Search();
                checkSearch.addFilterEqual("elementId", fieldPo.getElementId());
                ElementCheckboxPo elementCheckboxPo = elementCheckboxDao.searchUnique(checkSearch);
                fieldStructureBo.setFalseValue(elementCheckboxPo.getUncheckedValue());
                fieldStructureBo.setTrueValue(elementCheckboxPo.getCheckedValue());
            }

            fieldStructureBoList.add(fieldStructureBo);
        }
        for(FieldStructureBo fs:fieldStructureBoList)
        {
            if (FieldInputType.SELECT == fs.getFieldPo().getInputType()) {
                Search selectSearch = new Search();
                selectSearch.addFilterEqual("elementId", fs.getFieldPo().getElementId());
                ElementSelectPo elementSelectPo = elementSelectDao.searchUnique(selectSearch);
                String sql = elementSelectPo.getSql();
                JavaDataType javaDataType = JavaDataType.getJavaDataTypeByKey(fs.getFieldPo().getTableColumnPo().getDataType());
                SqlColumn keyColumn = new SqlColumn();
                keyColumn.setIndex(0);
                keyColumn.setColumnName("item_id");
                keyColumn.setDataType(javaDataType);
                SqlColumn labelColumn = new SqlColumn();
                labelColumn.setIndex(1);
                labelColumn.setColumnName("item_value");
                labelColumn.setDataType(javaDataType);
                List<SqlColumn> sqlColumnList = new ArrayList<>();
                sqlColumnList.add(keyColumn);
                sqlColumnList.add(labelColumn);
                QueryExpression queryExpression = SQLUtil.buildQueryExpression(sql, sqlColumnList);
                List<SqlCondition> sqlConditionList=queryExpression.getSqlConditionList();
                if(sqlConditionList!=null)
                {
                    for(SqlCondition sqlCondition:sqlConditionList)
                    {
                        String columnName= sqlCondition.getColumnName();
                        for(FieldStructureBo fpo:fieldStructureBoList)
                        {
                            if(columnName.equals(fpo.getFieldPo().getTableColumnPo().getColumnName()))
                            {
                                //FIXME 需要根据事件类型添加事件
                                fpo.addEvent(EventType.ONCHANGE);
                            }
                        }
                    }
                }


            }
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
                recordMap.put("id" + valueBo.getFieldPo().getId(), valueBo.getValue());
                switch (valueBo.getFieldPo().getShowType()) {
                    case LINKED:
                        value = getLinkedValue(valueBo);
                        recordMap.put("idLinked" + valueBo.getFieldPo().getId(), value);
                        break;
                    default:
                        break;
                }
            }
            resultMapList.add(recordMap);
        }
        PaginationResult paginationResult = PaginationResult.pagination(resultMapList, count, queryParams.getPageNo(), queryParams.getPageSize());
        return paginationResult;
    }

    private Object getLinkedValue(FieldValueBo fieldValueBo) {
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

    private Object getValue(ResultSet resultSet, JavaDataType javaDataType, int index) throws SQLException {
        Object value = null;
        switch (javaDataType) {
            case DATE:
                value = resultSet.getTimestamp(index);
                break;
            case BYTES:
                value = resultSet.getBytes(index);
                break;
            case INT:
                value = resultSet.getInt(index);
                break;
            case DECIMAL:
                value = resultSet.getBigDecimal(index);
                break;
            case DOUBLE:
                value = resultSet.getDouble(index);
                break;
            case FLOAT:
                value = resultSet.getFloat(index);
                break;
            case LONG:
                value = resultSet.getLong(index);
                break;
            case STRING:
                value = resultSet.getString(index);
                break;
            case OTHER:
                value = resultSet.getObject(index);
        }
        return value;
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
    public Map<String, Object> getDataForm(Integer tabId, Serializable recordId) {
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
            resultMap.put("id" + valueBo.getFieldPo().getId(), valueBo.getValue());
            switch (valueBo.getFieldPo().getShowType()) {
                case LINKED:
                    resultMap.put("idLinked" + valueBo.getFieldPo().getId(), getLinkedValue(valueBo));
                    break;
                default:
                    break;
            }

        }
        return resultMap;
    }

    @Override
    public Map<String, Object> saveForUpdated(Integer tabId, Map<String, Object> param, UserBo userBo) {
        TabPo tabPo = tabDao.find(tabId);
        TablePo tablePo = tableDao.find(tabPo.getTableId());
        String tableName = tablePo.getTableName();
        Map<String, FieldPo> tableColumnPoMap = new HashMap<>();
        Search search = new Search();
        search.addFilterEqual("tabId", tabId);
        List<FieldPo> tableColumnPoList = fieldDao.search(search);
        for (FieldPo fieldPo : tableColumnPoList) {
            if (fieldPo.getAutowiredValue() != null) {
                Object value = autowiredValue(fieldPo.getAutowiredValue(), userBo);
                param.put("id" + fieldPo.getId(), value);
            }
        }
        Set<String> keys = param.keySet();
        List<FieldColumn> values = new ArrayList<>();
        StringBuilder updateSqlBuilder = new StringBuilder();
        updateSqlBuilder.append(UPDATE_KEY);
        updateSqlBuilder.append(tableName);
        updateSqlBuilder.append(SET_KEY);
        boolean isBgn = true;
        Serializable pkValue = null;
        TableColumnPo pkTablePo = tableColumnDao.find(tabPo.getTableColumnId());
        for (String key : keys) {
            if (key.contains("Linked")) {
                continue;
            }
            String idKey = key.replace("id", "");
            Integer fieldId = Integer.parseInt(idKey);
            FieldPo fieldPo = fieldDao.find(fieldId);
            if (fieldPo.getColumnId().equals(tabPo.getTableColumnId())) {
                pkValue = (Serializable) param.get(key);
                continue;
            }
            if (fieldPo.getIsupdated() == BoolChar.FALSE) {
                continue;
            }
            if (isBgn) {
                isBgn = false;
            } else {

                updateSqlBuilder.append(COLUMN_SPLIT_KEY);
            }

            TableColumnPo tableColumnPo = fieldPo.getTableColumnPo();
            updateSqlBuilder.append(tableColumnPo.getColumnName())
                    .append(SQL_EQUAL_KEY);
            updateSqlBuilder.append("?");
            tableColumnPoMap.put(key, fieldPo);
            FieldColumn fieldColumn = new FieldColumn();
            fieldColumn.fieldPo = fieldPo;
            fieldColumn.value = param.get(key);
            values.add(fieldColumn);
        }
        updateSqlBuilder.append(WHERE_KEY);
        updateSqlBuilder.append(pkTablePo.getColumnName())
                .append(SQL_EQUAL_KEY).append("?");
        String updateSql = updateSqlBuilder.toString();
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            throw new ServiceException("获取conn出错", e);
        }
        log.debug(updateSql);
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = conn.prepareStatement(updateSql);
        } catch (SQLException e) {
            throw new ServiceException("获取PreparedStatement出错", e);
        }

        for (int i = 0; i < values.size(); i++) {
            FieldColumn fieldColumn = values.get(i);
            TableColumnPo tableColumnPo = fieldColumn.fieldPo.getTableColumnPo();
            JavaDataType dataType = JavaDataType.getJavaDataTypeByKey(tableColumnPo.getDataType());
            setPreparedStatement(i + 1, fieldColumn.value, preparedStatement, dataType);
        }
        JavaDataType dataType = JavaDataType.getJavaDataTypeByKey(pkTablePo.getDataType());
        setPreparedStatement(values.size() + 1, pkValue, preparedStatement, dataType);
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error(updateSql, e);
            throw new ServiceException("执行更新操作失败", e);
        }
        return getDataForm(tabId, pkValue);
    }

    private void setPreparedStatement(int index, Object value, PreparedStatement preparedStatement, JavaDataType dataType) {
        try {
            switch (dataType) {
                case INT:
                    if (value instanceof String) {
                        value = Integer.parseInt((String) value);
                    }
                    if (value == null) {
                        preparedStatement.setObject(index, null);
                    } else {
                        preparedStatement.setInt(index, (int) value);
                    }


                    break;
                case DECIMAL:
                    if (value instanceof String) {
                        value = new BigDecimal((String) value);
                    }
                    preparedStatement.setBigDecimal(index, (BigDecimal) value);
                    break;
                case LONG:
                    if (value instanceof String) {
                        value = Long.parseLong((String) value);
                    }
                    preparedStatement.setLong(index, (long) value);
                    break;
                case STRING:
                    preparedStatement.setString(index, (String) value);
                    break;
                case DATE:
                    if (value instanceof Long) {
                        value = new Date((Long) value);
                    }
                    Date date = (Date) value;
                    Timestamp timestamp = new Timestamp(date.getTime());
                    preparedStatement.setTimestamp(index, timestamp);
                    break;
                case DOUBLE:
                    if (value instanceof String) {
                        value = Double.parseDouble((String) value);
                    }
                    preparedStatement.setDouble(index, (double) value);
                    break;
                case FLOAT:
                    if (value instanceof String) {
                        value = Float.parseFloat((String) value);
                    }
                    preparedStatement.setDouble(index, (float) value);
                    break;
                case BYTES:
                    preparedStatement.setBinaryStream(index, (InputStream) value);
                    break;
                case OTHER:
                    preparedStatement.setString(index, value.toString());
                    break;
            }
        } catch (SQLException e) {
            log.error("执行更新操作失败", e);
            throw new ServiceException("执行更新操作失败", e);
        }
    }

    private Object autowiredValue(String expression, UserBo userBo) {
        Object value = null;
        switch (expression) {
            case EideaConst.EXPRESS_CLIENT_ID:
                value = userBo.getClientId();
                break;
            case EideaConst.EXPRESS_ORG_ID:
                value = userBo.getOrgId();
                break;
            case EideaConst.EXPRESS_USER_ID:
                value = userBo.getId();
                break;
            case EideaConst.EXPRESS_CURRENT_DATE:
            case EideaConst.EXPRESS_CURRENT_TIME:
                value = new Date();
                break;

        }
        return value;
    }

    public Map<String, Object> saveForCreated(Integer tabId, Map<String, Object> param, UserBo userBo) {
        TabPo tabPo = tabDao.find(tabId);
        TablePo tablePo = tableDao.find(tabPo.getTableId());
        String tableName = tablePo.getTableName();
        //TableColumnPo pkColumn=
        Search search = new Search();
        search.addFilterEqual("tabId", tabId);
        List<FieldPo> tableColumnPoList = fieldDao.search(search);
        for (FieldPo fieldPo : tableColumnPoList) {
            if (fieldPo.getDefaultvalue() != null) {
                param.put("id" + fieldPo.getId(), autowiredValue(fieldPo.getDefaultvalue(), userBo));
            }
        }
        StringBuilder insertSQL = new StringBuilder();
        List<FieldColumn> values = new ArrayList<>();
        insertSQL.append(INSERT_KEY);
        insertSQL.append(tableName)
                .append(LEFT_BRACKETS_KEY);

        Set<String> keys = param.keySet();
        boolean begin = true;
        for (String key : keys) {
            if (key.contains("idLinked")) {
                continue;
            }
            String idKey = key.replace("id", "");
            Integer fieldId = Integer.parseInt(idKey);

            FieldPo fieldPo = fieldDao.find(fieldId);
            if (fieldPo.getIsadded() == BoolChar.FALSE) {
                continue;
            }
            if (begin) {
                begin = false;
            } else {
                insertSQL.append(COLUMN_SPLIT_KEY);
            }
            FieldColumn fieldColumn = new FieldColumn();
            fieldColumn.fieldPo = fieldPo;
            fieldColumn.value = param.get(key);
            values.add(fieldColumn);
            insertSQL.append(fieldPo.getTableColumnPo().getColumnName());
        }
        insertSQL.append(RIGHT_BRACKETS_KEY);
        insertSQL.append(VALUES_KEY).append(LEFT_BRACKETS_KEY);
        for (int i = 0; i < values.size(); i++) {
            if (i != 0) {
                insertSQL.append(COLUMN_SPLIT_KEY);
            }
            insertSQL.append("?");
        }
        insertSQL.append(RIGHT_BRACKETS_KEY);
        String sqlString = insertSQL.toString();
        TableColumnPo pkTableColumnPo = tableColumnDao.find(tabPo.getTableColumnId());
        JavaDataType pkDataType = JavaDataType.getJavaDataTypeByKey(pkTableColumnPo.getDataType());
        Object primaryKey = null;
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < values.size(); i++) {
                int index = i + 1;
                Object value = values.get(i).value;
                JavaDataType dataType = JavaDataType.getJavaDataTypeByKey(values.get(i).fieldPo.getTableColumnPo().getDataType());
                setPreparedStatement(index, value, preparedStatement, dataType);
            }

            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {

                primaryKey = getValue(rs, pkDataType, 1);
                ;
            }
            preparedStatement.close();
            conn.close();

        } catch (Exception e) {
            log.error(sqlString);
            e.printStackTrace();
            throw new ServiceException("插入数据异常", e);
        }
        return getDataForm(tabId, (Serializable) primaryKey);
    }

    public Map<String, Object> getNewObject(Integer tabId, String lang, UserBo userBo) {
        TabFormStructureBo tabFormStructureBo = getFormPageFieldList(tabId, lang);
        List<FieldStructureBo> fieldStructureBoList = tabFormStructureBo.getFieldStructureBoList();
        Map<String, Object> result = new HashMap<>();
        for (FieldStructureBo fieldStructureBo : fieldStructureBoList) {
            String defaultValue = fieldStructureBo.getFieldPo().getDefaultvalue();
            if (defaultValue == null) {
                result.put("id" + fieldStructureBo.getFieldPo().getId(), null);
            } else {
                Object value = null;
                switch (defaultValue) {
                    case EideaConst.EXPRESS_CLIENT_ID: {
                        value = userBo.getClientId();
                        break;
                    }
                    case EideaConst.EXPRESS_ORG_ID: {
                        value = userBo.getOrgId();
                        break;
                    }
                    case EideaConst.EXPRESS_USER_ID: {
                        value = userBo.getId();
                        break;
                    }
                    case EideaConst.EXPRESS_CURRENT_DATE:
                    case EideaConst.EXPRESS_CURRENT_TIME: {
                        value = new Date();
                        break;
                    }
                    default: {
                        value = null;
                    }
                }
                result.put("id" + fieldStructureBo.getFieldPo().getId(), value);
                if (value != null) {
                    if (fieldStructureBo.getFieldPo().getShowType() == FieldShowType.LINKED) {
                        FieldValueBo fieldValueBo = new FieldValueBo();
                        fieldValueBo.setFieldPo(fieldStructureBo.getFieldPo());
                        fieldValueBo.setValue(value);
                        Object linkedValue = getLinkedValue(fieldValueBo);
                        result.put("idLinked" + fieldStructureBo.getFieldPo().getId(), linkedValue);
                    }
                }
            }


        }
        return result;
    }

    public void deleteList(Integer tabId, Object[] ids) {
        TabPo tabPo = tabDao.find(tabId);
        TablePo tablePo = tableDao.find(tabPo.getTableId());
        String tableName = tablePo.getTableName();
        TableColumnPo tableColumnPo = tableColumnDao.find(tabPo.getTableColumnId());
        String columnName = tableColumnPo.getColumnName();
        StringBuilder deleteSqlBuilder = new StringBuilder();
        deleteSqlBuilder.append(DELETE_KEY)
                .append(FROM_KEY)
                .append(tableName)
                .append(WHERE_KEY)
                .append(columnName)
                .append(SQL_EQUAL_KEY)
                .append("?");
        String deleteSql = deleteSqlBuilder.toString();
        try {
            Connection conn = dataSource.getConnection();
            for (Object id : ids) {
                PreparedStatement statement = conn.prepareStatement(deleteSql);
                statement.setObject(1, id);
                statement.executeUpdate();
                statement.close();
            }
            conn.close();

        } catch (Exception e) {
            throw new ServiceException("删除记录出错" + deleteSqlBuilder.toString(), e);
        }


    }

    public List<SelectItemBo> getSelectItemList(Integer fieldId) {
        FieldPo fieldPo = fieldDao.find(fieldId);
        Search search = new Search();
        search.addFilterEqual("elementId", fieldPo.getElementId());
        JavaDataType javaDataType = JavaDataType.getJavaDataTypeByKey(fieldPo.getTableColumnPo().getDataType());
        if (fieldPo.getElementId() == null) {
            throw new ServiceException("field id=" + fieldId + "的字段，element id不能为空");
        }
        ElementSelectPo elementSelectPo = elementSelectDao.searchUnique(search);
        if (elementSelectPo == null) {
            throw new ServiceException("找不到 element id 为" + fieldPo.getElementId() + "的 ElementSelect 记录");
        }
        String sql = elementSelectPo.getSql();
        List<SelectItemBo> selectItemBoList = new ArrayList<>();
        try {
            Connection conn = dataSource.getConnection();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            while (rs.next()) {
                Serializable key = (Serializable) getValue(rs, javaDataType, 1);
                String value = rs.getString(2);
                SelectItemBo selectItemBo = new SelectItemBo();
                selectItemBo.setKey(key);
                selectItemBo.setValue(value);
                selectItemBoList.add(selectItemBo);
            }
        } catch (Exception e) {
            throw new ServiceException("获取select list 执行sql出错", e);
        }
        return selectItemBoList;
    }

    class FieldColumn {
        private FieldPo fieldPo;
        private String columnName;
        private Object value;
        private TableColumnPo tableColumnPo;
    }

}
