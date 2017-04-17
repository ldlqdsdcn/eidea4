package com.dsdl.eidea.devs.strategy;


import com.dsdl.eidea.core.entity.bo.ColumnMetaDataBo;
import com.dsdl.eidea.core.entity.bo.TableMetaDataBo;
import com.dsdl.eidea.core.def.JavaDataType;
import com.dsdl.eidea.devs.cons.IntelliKeyWord;
import com.dsdl.eidea.devs.model.ColumnInfo;
import com.dsdl.eidea.devs.model.GenModelDto;
import com.dsdl.eidea.devs.model.TableInfo;
import com.dsdl.eidea.devs.util.FreeMarkerHelper;
import com.dsdl.eidea.util.CommonConverter;
import com.dsdl.eidea.util.DateTimeHelper;
import com.dsdl.eidea.util.StringUtil;

import java.util.*;

/**
 * Created by admin on 2016/8/26.
 */
public class PoGenerateStrategy {
    public PoGenerateStrategy(TableMetaDataBo tableMetaDataBo, GenModelDto genModelDto) {
        this.tableMetaDataBo = tableMetaDataBo;
        this.genModelDto = genModelDto;
    }

    private TableMetaDataBo tableMetaDataBo;
    private GenModelDto genModelDto;

    /**
     *
     * @param outPutModulePath 输出业务逻辑代码模块的目录
     */
    public void generateModel(String outPutModulePath) {
        TableInfo tableInfo=new TableInfo();
        tableInfo.setTableName(tableMetaDataBo.getName());
        String poName=IntelliKeyWord.removePrefix(tableMetaDataBo.getName());
        poName= StringUtil.upperFirstChar(poName);
        poName=StringUtil.fieldToProperty(poName);


        tableInfo.setPoName(poName);
        if(genModelDto.getModelName()==null)
        {
            genModelDto.setModelName(poName);
        }

        Map<String, Object> param = new HashMap<>();
        param.put("tableInfo",tableInfo);
        param.put("module", genModelDto.getModule());
        param.put("model", genModelDto.getModelName());
        param.put("tableName", tableMetaDataBo.getName());
        param.put("basePackage",genModelDto.getBasePackage());
        param.put("tableRemark",tableMetaDataBo.getRemark()==null?"":tableMetaDataBo.getRemark());
        Date date = new Date();
        String datetime = DateTimeHelper.formatDateTime(date);
        param.put("datetime", datetime);
        param.put("hasDate", false);
        param.put("hasDecimal", false);
        List<ColumnInfo> columnInfoList = new ArrayList<>();
        JavaDataType[] columnDataTypes = JavaDataType.values();
        ColumnInfo pkColumnInfo=null;
        for (ColumnMetaDataBo cmd : tableMetaDataBo.getColumnList()) {
            ColumnInfo columnInfo = CommonConverter.convertObject(cmd, ColumnInfo.class);
            for (JavaDataType dataType : columnDataTypes) {
                if (columnInfo.getDataType() == dataType.getKey()) {
                    columnInfo.setPropertyType(dataType.getValue());
                    if (columnInfo.getDataType() == JavaDataType.DATE.getKey()) {
                        param.put("hasDate", true);
                    }
                    if (columnInfo.getDataType() == JavaDataType.DECIMAL.getKey()) {
                        param.put("hasDecimal", true);
                    }
                }
            }

            columnInfo.setPropertyName(StringUtil.fieldToProperty(cmd.getColumnName()));
            System.out.println("columnInfo.getPropertyName()=" + columnInfo.getPropertyName() + " type:" + columnInfo.getType());
            columnInfo.setKeyWord(IntelliKeyWord.isKeyWord(columnInfo.getColumnName()));

            if(columnInfo.getColumnName().equals(tableMetaDataBo.getPkColumn()))
            {
                pkColumnInfo=columnInfo;
            }
            else
            {
                columnInfoList.add(columnInfo);
            }
        }
        tableInfo.setColumnInfoList(columnInfoList);
        tableInfo.setRemark(tableMetaDataBo.getRemark());
        tableInfo.setPkColumn(pkColumnInfo);
        FreeMarkerHelper.getInstance().outFile("po/hibernate_po.ftl", param, outPutModulePath + "/src/main/java/"+genModelDto.getBasePackage().replace(".","/")+"/" + genModelDto.getModule() + "/entity/po/" + StringUtil.upperFirstChar(genModelDto.getModelName()) + "Po.java");

    }
}
