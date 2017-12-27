package indi.liudalei.eidea.devs.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by 刘大磊 on 2017/4/17 13:55.
 */
@Getter
@Setter
public class GenSettings {
    /**
     * 项目跟目录
     */
    private String rootPath;
    /**
     * 业务代码输出路径 =项目跟目录+输出模块
     */
    private String outputPath;
    /**
     * controller 类和前端JSP文件输出路径
     */
    private String controllerPath;
    /**
     * 具体模块输出设置
     */
    private List<GenModelDto> genModelDtoList;
}
