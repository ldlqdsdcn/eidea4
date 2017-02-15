package com.dsdl.eidea.devs.strategy;

import com.dsdl.eidea.core.def.JavaDataType;
import com.dsdl.eidea.core.entity.bo.ColumnMetaDataBo;
import com.dsdl.eidea.core.entity.bo.TableColumnBo;
import com.dsdl.eidea.core.entity.bo.TableMetaDataBo;
import com.dsdl.eidea.devs.model.GenModelDto;
import com.dsdl.eidea.devs.util.FreeMarkerHelper;
import com.dsdl.eidea.util.DateTimeHelper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 刘大磊 on 2017/1/11 17:17.
 */
public class DaoInterfaceGenerateStrategy {
    private String[] modelList;

    private String modulename;

    private GenModelDto model;

    private String aClass;

    /**
     *
     */
    public DaoInterfaceGenerateStrategy(String modulename, String[] modelList,GenModelDto model) {
        this.modulename = modulename;

        this.modelList = modelList;

        this.model=model;
    }

    public DaoInterfaceGenerateStrategy(String core, String[] strings, GenModelDto genModelDto, TableMetaDataBo tableMetaDataBo) {
        this.modulename = core;
        this.modelList = strings;
        this.model=genModelDto;
        JavaDataType[] columnDataTypes = JavaDataType.values();
        for(ColumnMetaDataBo c:tableMetaDataBo.getColumnList())
        {
            if(c.getColumnName().equals(tableMetaDataBo.getPkColumn()))
            {
                for (JavaDataType dataType : columnDataTypes) {
                    if (c.getDataType() == dataType.getKey()) {
                        aClass=dataType.getValue();
                    }
                }
            }
        }

        if(aClass==null)
        {
            aClass="java.io.Serializable";
        }

    }

    public void generateDaoInterface() {


        Map<String,Object> root = new HashMap();

        Date date = new Date();
        String interfacepackage = model.getBasePackage()+"." + modulename + ".dao";
        if(interfacepackage.equals("com.dsdl.eidea.core.dao"))
        {
            root.put("needImportBaseDao",false);
        }
        else
        {
            root.put("needImportBaseDao",true);
        }
        String modelpackage =  model.getBasePackage()+"." + modulename + ".entity.po";
        String datetime = DateTimeHelper.formatDateTime(date);
        root.put("packagename", interfacepackage);
        root.put("modelpackage", modelpackage);
        root.put("pkClass",aClass);
        root.put("datetime", datetime);
        //gc.setTime(date);
        try {
            for (String model : modelList) {

                root.put("modelname", model);
                FreeMarkerHelper.getInstance().outFile("dao/daoInterface.ftl", root, this.model.getOutputPath().getAbsolutePath()+"/src/main/java/" + interfacepackage.replace(".", "/") + "/" + model + "Dao.java");

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void generateDaoClass() {
        Map root = new HashMap();
        Date date = new Date();
        String classpackage = model.getBasePackage()+"." + modulename + ".dao.hibernate";
        String modelpackage = model.getBasePackage()+"." + modulename + ".entity.po";
        String datetime = DateTimeHelper.formatDateTime(date);
        root.put("packagename", classpackage);
        root.put("modelpackage", modelpackage);
        root.put("datetime", datetime);
        root.put("pkClass",aClass);
        if(classpackage.equals("com.dsdl.eidea.core.dao.hibernate"))
        {
            root.put("needImportBaseDao",false);
        }
        else
        {
            root.put("needImportBaseDao",true);
        }
        //gc.setTime(date);
        try {
            for (String model : modelList) {
                //toUpperCase
                root.put("interfacefullname", this.model.getBasePackage()+"." + modulename + ".dao." + model + "Dao");

                root.put("modelname", model);
                String bgnChar = model.substring(0, 1);
                root.put("repositoryname", bgnChar.toLowerCase() + model.substring(1) + "Dao");
                root.put("mappername", "com.dsdl." + modulename + ".mybatis.sql." + model + "Mapper");
                FreeMarkerHelper.getInstance().outFile("dao/daoclass.ftl", root, this.model.getOutputPath().getAbsolutePath()+"/src/main/java/" + classpackage.replace(".", "/") + "/" + model + "DaoHibernate.java");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
