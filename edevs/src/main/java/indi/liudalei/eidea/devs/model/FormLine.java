package indi.liudalei.eidea.devs.model;

import lombok.Data;

import java.util.List;

/**
 * Created by 刘大磊 on 2017/1/16 9:39.
 * 编辑界面，一行显示的字段
 */
@Data
public class FormLine {
    /**
     * 标签
     */
    private String label;
    /**
     * model 对应的具体model名
     */
    private String model;
    /**
     * module 模块名称
     */
    private String module;
    /**
     * 是否翻译
     */
    private boolean trl;

    private List<JspModelProp> propertyList;
}
