package indi.liudalei.eidea.core.service;

import indi.liudalei.eidea.core.dto.PaginationResult;
import indi.liudalei.eidea.core.entity.bo.TableBo;
import indi.liudalei.eidea.core.entity.bo.TableMetaDataBo;
import indi.liudalei.eidea.core.params.QueryParams;
import com.googlecode.genericdao.search.Search;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/6 14:48.
 */
public interface TableService {
    TableMetaDataBo getTableDescription(String tableName);

    PaginationResult<TableBo> findList(Search search, QueryParams queryParams);


    boolean findExistTableByName(String tableName);

    TableBo getTableBo(Integer id);

    TableBo saveTableBo(TableBo tableBo);

    void deleteTables(Integer[] ids);

    void saveTableInfoByWizard(TableMetaDataBo tableInfo);

    /**
     * 获取输出日志的tableBo类
     *
     * @return
     */
    List<TableBo> getTableBoListForOutLog();

    /**
     * findAllTableBoList:查询所有的table表
     * @return
     */
    List<TableBo> findAllTableBoList(Search search);
}
