package indi.liudalei.report.entity.param;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘大磊 on 2016/11/23 9:52.
 */
@Getter
@Setter
public class ReportParam implements java.io.Serializable {
    /**
     * 导出文件名
     */
    private String exportName;
    /**
     * 模板文件名
     */
    private String reportName;
    /**
     * 导出类型
     */
    private String reportType;
    /**
     * 参数列表
     */
    private List<Param> paramList = new ArrayList<>();

    /**
     * 添加参数
     * @param param 报表传递参数类
     */
    public void addParam(Param param) {
        paramList.add(param);
    }

    /**
     * type暂时先用不到
     */
    @Getter
    @Setter
    public static class Param {
        public Param(String key, String value) {
            this.key = key;
            this.value = value;
        }

        private String key;
        private String value;
        private int type;
    }
}
