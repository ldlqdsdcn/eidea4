package indi.liudalei.eidea.devs.model;

import lombok.Data;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/8/26.
 * 代码生成工具，数据库表和po对象映射关系
 */
@Data
public class GenModelDto {
    /**
     * 表名
     */
    private String tableName;
    /**
     * model类名
     */
    private String modelName;
    /**
     * 模块
     */
    private String module;
    /**
     * 类名称
     */
    private String name;
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
    private String basePackage="com.dsdl.eidea";
    private List<GenModelDto> includeModelList;
    public GenModelDto()
    {

    }
    /**
     *
     * @param tableName 表名
     * @param modelName 对应model 名
     * @param module 模块关键字
     * @param name 模块的名称
     */
    public GenModelDto(String tableName, String modelName, String module, String name)
    {
      this(tableName,modelName,module,name,null);
    }
    /**
     *
     * @param tableName 表名
     * @param modelName 对应model 名
     * @param module 模块关键字
     * @param name 类的名称
     * @param includeModelList 包含的模块
     */
    private GenModelDto(String tableName, String modelName, String module, String name, List<GenModelDto> includeModelList)
    {
        this.tableName=tableName;
        this.modelName=modelName;
        this.module=module;
        this.name=name;
        this.includeModelList=includeModelList;
        generateWeb=true;
        generateService=true;
    }
}
