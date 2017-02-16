package com.dsdl.eidea.core.service;

import com.dsdl.eidea.core.entity.bo.TableBo;
import com.dsdl.eidea.core.entity.bo.TableMetaDataBo;
import com.googlecode.genericdao.search.ISearch;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/6 14:48.
 */
public interface TableService {
    TableMetaDataBo getTableDescription(String tableName);

    List<TableBo> findList(ISearch search);

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
}
