package indi.liudalei.eidea.core.dao;

import indi.liudalei.eidea.core.entity.bo.ColumnMetaDataBo;
import indi.liudalei.eidea.core.entity.bo.ForeignKeyBo;
import indi.liudalei.eidea.core.entity.bo.UniqueIndexBo;
import indi.liudalei.eidea.core.entity.po.TablePo;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/5 17:11.
 */

public interface TableDao extends BaseDao<TablePo, Integer> {
    /**
     * 获取表的唯一索引
     * @param tableName 表名
     * @return 唯一索引列表
     */
    List<UniqueIndexBo> getUniqueIndex(String tableName);
    /**
     * 获取表的主键
     *
     * @param tableName 表名
     * @return 主键名
     */
    String getPrimaryKey(String tableName);
    /**
     * 获取其它表引用该表的外键
     * @param tableName
     * @return 返回外键列表
     */
    List<ForeignKeyBo> getExportedKeys(String tableName);

    /**
     * 获取该表引用其它表的外键
     * @param tableName
     * @return 返回外键列表
     */
    List<ForeignKeyBo> getImportedKeys(String tableName);

    /**
     * 获取表的所有字段信息
     * @param tableName
     * @return 返回表的所有字段信息
     */
    List<ColumnMetaDataBo> getTableColumns(String tableName);
    /**
     * 获取系统中所有的表
     * @return 该数据的所有表信息
     */
    List<String> getAllTableName();

    /**
     * 获取表注释信息
     * @param table
     * @return 返回表的注释信息
     */
    String getCommentByTableName(String table);

}
