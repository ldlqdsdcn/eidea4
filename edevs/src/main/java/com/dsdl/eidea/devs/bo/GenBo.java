package com.dsdl.eidea.devs.bo;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.List;

/**
 * Created by 刘大磊 on 2017/1/11 14:09.
 */
@Getter
@Setter
public class GenBo {
    /**
     * 表名
     */
    private String tableName;
    /**
     * po class name
     */
    private String poClass;
    /**
     * 模块名
     */
    private String module;
    /**
     * 备注
     */
    private String remark;
    /**
     * 输出模块位置
     */
    private String outputModule;
    /**
     * 输出路径
     */
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
     * 是否生成Bo类
     */
    private boolean generateBoClass;
    /**
     * 是否翻译文件
     */
    private boolean trl;
    /**
     * 基础包
     */
    private String basePackage;
    /**
     * 包含的模块
     */
    private List<GenBo> includeModelList;

    public String getBaseSourcePath() {
        return basePackage.replace(".", "/");
    }
}
