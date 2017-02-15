package com.dsdl.eidea.core.dao.hibernate;

import com.dsdl.eidea.core.dao.TableDao;
import com.dsdl.eidea.core.dao.exception.DataBaseException;
import com.dsdl.eidea.core.entity.bo.ColumnMetaDataBo;
import com.dsdl.eidea.core.entity.bo.ForeignKeyBo;
import com.dsdl.eidea.core.entity.bo.UniqueIndexBo;
import com.dsdl.eidea.core.entity.po.TablePo;
import com.google.gson.JsonObject;
import org.apache.log4j.Logger;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.stereotype.Repository;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 刘大磊 on 2016/12/5 17:26.
 */
@Repository
public class TableDaoHibernate extends BaseDaoHibernate<TablePo, Integer> implements TableDao {
    private static final Logger logger = Logger.getLogger(TableDaoHibernate.class);
    private static final String INDEX_NAME = "INDEX_NAME";
    private static final String INDEX_COLUMN_NAME = "COLUMN_NAME";
    private static final String PRIMARY_KEY_KEY_SEQ = "KEY_SEQ";

    private static final String EXPORT_KEY_PK_TABLE_NAME = "PKTABLE_NAME";
    private static final String EXPORT_KEY_PK_COLUMN_NAME = "PKCOLUMN_NAME";
    private static final String EXPORT_KEY_FK_TABLE_NAME = "FKTABLE_NAME";
    private static final String EXPORT_KEY_FK_COLUMN_NAME = "FKCOLUMN_NAME";
    private static final String EXPORT_KEY_FK_NAME = "FK_NAME";
    private static final String EXPORT_KEY_PK_NAME = "PK_NAME";

    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String TYPE_NAME = "TYPE_NAME";
    private static final String DATA_TYPE = "DATA_TYPE";
    private static final String COLUMN_SIZE = "COLUMN_SIZE";
    private static final String DECIMAL_DIGITS = "DECIMAL_DIGITS";
    private static final String NULLABLE = "NULLABLE";
    private static final String REMARKS = "REMARKS";
    private static final String COLUMN_DEF = "COLUMN_DEF";

    public List<UniqueIndexBo> getUniqueIndex(String tableName) {
        List<UniqueIndexBo> list = new ArrayList<>();
        Connection conn = openConnection();
        try {
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            ResultSet rs = databaseMetaData.getIndexInfo(null, null, tableName, true, true);
            while (rs.next()) {
                String indexName = rs.getString(INDEX_COLUMN_NAME);
                String columnName = rs.getString(INDEX_NAME);
                UniqueIndexBo uniqueIndexBo = new UniqueIndexBo();
                uniqueIndexBo.setIndexColumnName(columnName);
                uniqueIndexBo.setIndexName(indexName);
                list.add(uniqueIndexBo);
            }
        } catch (Exception e) {
            throw new DataBaseException("获取 Index失败", e);
        }
        closeConnection(conn);
        return list;
    }

    public String getPrimaryKey(String tableName) {
        String primaryKey = "";
        JsonObject jsonObject = new JsonObject();
        Connection conn = openConnection();
        try {
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            ResultSet rs = databaseMetaData.getPrimaryKeys(null, null, tableName);
            while (rs.next()) {
                String indexName = rs.getString(INDEX_COLUMN_NAME);
                String keySeq = rs.getString(PRIMARY_KEY_KEY_SEQ);
                jsonObject.addProperty(INDEX_COLUMN_NAME, indexName);
                jsonObject.addProperty(PRIMARY_KEY_KEY_SEQ, keySeq);
                primaryKey = indexName;
            }
        } catch (Exception e) {
            throw new DataBaseException("获取 PrimaryKey失败", e);
        }
        closeConnection(conn);
        return primaryKey;
    }

    public List<ForeignKeyBo> getExportedKeys(String tableName) {
        List<ForeignKeyBo> list = new ArrayList<>();
        Connection conn = openConnection();
        try {
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            ResultSet rs = databaseMetaData.getExportedKeys(null, null, tableName);
            while (rs.next()) {
                ForeignKeyBo foreignKey = getForeignKeyByResultSet(rs);
                list.add(foreignKey);
            }
        } catch (SQLException e) {
            throw new DataBaseException("获取 外部引用键值失败", e);
        }
        closeConnection(conn);
        return list;
    }

    private ForeignKeyBo getForeignKeyByResultSet(ResultSet rs) {
        ForeignKeyBo foreignKey = new ForeignKeyBo();
        try {
            String fkColumn = rs.getString(EXPORT_KEY_FK_COLUMN_NAME);
            String fkTable = rs.getString(EXPORT_KEY_FK_TABLE_NAME);
            String pkColumn = rs.getString(EXPORT_KEY_PK_COLUMN_NAME);
            String pkTable = rs.getString(EXPORT_KEY_PK_TABLE_NAME);
            String fkName = rs.getString(EXPORT_KEY_FK_NAME);
            String pkName = rs.getString(EXPORT_KEY_PK_NAME);
            foreignKey.setFkColumnName(fkColumn);
            foreignKey.setFkTableName(fkTable);
            foreignKey.setPkColumnName(pkColumn);
            foreignKey.setPkTableName(pkTable);
            foreignKey.setFkName(fkName);
            foreignKey.setPkName(pkName);
        } catch (SQLException e) {
            throw new DataBaseException("获取 外部键值失败", e);
        }
        return foreignKey;
    }

    public List<ForeignKeyBo> getImportedKeys(String tableName) {
        List<ForeignKeyBo> list = new ArrayList<>();
        Connection conn = openConnection();
        try {

            DatabaseMetaData databaseMetaData = conn.getMetaData();
            ResultSet rs = databaseMetaData.getImportedKeys(null, null, tableName);
            while (rs.next()) {
                ForeignKeyBo foreignKey = getForeignKeyByResultSet(rs);
                list.add(foreignKey);
            }
        } catch (SQLException e) {
            throw new DataBaseException("获取 引用外部键值失败", e);
        }
        closeConnection(conn);
        return list;
    }

    public List<ColumnMetaDataBo> getTableColumns(String tableName) {
        List<ColumnMetaDataBo> list = new ArrayList<>();
        Connection conn = openConnection();

        try {
            //System.out.println("获取 index");
            DatabaseMetaData databaseMetaData = conn.getMetaData();
            String columnName;
            String columnType;
            ResultSet colRet = databaseMetaData.getColumns(null, "%", tableName, "%");
            while (colRet.next()) {
                ColumnMetaDataBo columnMetaDataDto = new ColumnMetaDataBo();
                columnName = colRet.getString(COLUMN_NAME);
                columnType = colRet.getString(TYPE_NAME);
                Integer dataType = colRet.getInt(DATA_TYPE);
                int column_size = colRet.getInt(COLUMN_SIZE);
                int digits = colRet.getInt(DECIMAL_DIGITS);
                int nullable = colRet.getInt(NULLABLE);
                String remarks = colRet.getString(REMARKS);
                String colDef = colRet.getString(COLUMN_DEF);
                columnMetaDataDto.setDataType(dataType);
                columnMetaDataDto.setColumnName(columnName);
                columnMetaDataDto.setType(columnType);
                columnMetaDataDto.setColumnSize(column_size);
                columnMetaDataDto.setDecimalDigits(digits);
                columnMetaDataDto.setNullable(nullable == 1);
                columnMetaDataDto.setRemarks(remarks);
                columnMetaDataDto.setColumnDefault(colDef);
                list.add(columnMetaDataDto);
                // System.out.println(columnMetaDataDto.toString());
//                for(int i=1;i<resultSetMetaData.getColumnCount();i++)
//                {
//                    System.out.print(resultSetMetaData.getColumnName(i)+" "+resultSetMetaData.getColumnLabel(i)+" "+colRet.getObject(i)+"   ");
//                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new DataBaseException("获取数据库列信息异常", e);
        }
        closeConnection(conn);
        return list;
    }

    public List<String> getAllTableName() {
        List<String> tables = new ArrayList<>();
        Connection conn = openConnection();
        try {
            DatabaseMetaData dbMetaData = conn.getMetaData();
            //可为:"TABLE",   "VIEW",   "SYSTEM   TABLE",
            //"GLOBAL   TEMPORARY",   "LOCAL   TEMPORARY",   "ALIAS",   "SYNONYM"
            String[] types = {"TABLE"};
            ResultSet tabs = dbMetaData.getTables(null, null, null, types/*只要表就好了*/);
            while (tabs.next()) {
                //只要表名这一列
                tables.add(tabs.getString("TABLE_NAME"));
            }
            tabs.close();

        } catch (SQLException e) {
            logger.error("获取所有表目录异常", e);
            throw new DataBaseException("获取数据库列信息异常", e);
        }
        logger.debug(tables);
        closeConnection(conn);
        return tables;
    }

    public String getCommentByTableName(String table) {
        try {
            Map<String, String> map = new HashMap<>();
            String comment = null;
            Connection conn = openConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + table);
            if (rs != null && rs.next()) {
                String create = rs.getString(2);
                comment = parse(create);
                map.put(table, comment);
            }
            rs.close();
            stmt.close();
            conn.close();
            return comment;
        }
        catch (Exception e)
        {
            new DataBaseException("获取表备注信息出错",e);
        }
        return null;
    }
    private static String parse(String all) {
        String comment = null;
        int index = all.indexOf("COMMENT='");
        if (index < 0) {
            return "";
        }
        comment = all.substring(index + 9);
        comment = comment.substring(0, comment.length() - 1);
        try {
            comment = new String(comment.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return comment;
    }
    private Connection openConnection() {
        Connection conn = null;
        try {
            conn = SessionFactoryUtils.getDataSource(getSessionFactory()).getConnection();
        } catch (SQLException e) {
            throw new DataBaseException("打开connection异常", e);
        }
        return conn;
    }

    private void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DataBaseException("关闭connection异常", e);
            }
        }
    }
}
