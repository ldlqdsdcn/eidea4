package com.dsdl.eidea.devs.model;

import lombok.Data;

import java.io.File;
import java.util.List;

/**
 * Created by admin on 2016/8/26.
 */
@Data
public class GenModelDto {
    private String tableName;
    private String modelName;
    private String module;
    private String remark;
    private String outputModule;

    private File outputPath;
    /**
     * 是否数据库分页
     */
    private boolean pagingByDb;
    /**
     * 是否生成web代码
     */
    private boolean generateWeb;
    /**
     * 是否生成serviceCode
     */
    private boolean generateService;
    /**
     * 是否翻译文件
     */
    private boolean trl;
    /**
     * 基础包名
     */
    private String basePackage;
    private List<GenModelDto> includeModelList;
    public GenModelDto()
    {

    }
    /**
     *
     * @param tableName 表名
     * @param modelName 对应model 名
     * @param module 模块关键字
     * @param remark 模块的名称
     */
    public GenModelDto(String tableName, String modelName, String module, String remark)
    {
      this(tableName,modelName,module,remark,null);
    }
    /**
     *
     * @param tableName 表名
     * @param modelName 对应model 名
     * @param module 模块关键字
     * @param remark 模块的名称
     * @param includeModelList 包含的模块
     */
    private GenModelDto(String tableName, String modelName, String module, String remark, List<GenModelDto> includeModelList)
    {
        this.tableName=tableName;
        this.modelName=modelName;
        this.module=module;
        this.remark=remark;
        this.includeModelList=includeModelList;
        generateWeb=true;
        generateService=true;
    }
}
