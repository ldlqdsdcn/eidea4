package indi.liudalei.eidea.base.entity.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by 刘大磊 on 2017/5/3 16:32.
 */
@Getter
@Setter
public class WindowBo {
    /**
     * 窗体Id
     */
    private Integer windowId;
    /**
     * 窗体名称
     */
    private String windowName;
    /**
     * 窗体描述
     */
    private String description;
    /**
     * 窗体帮助信息
     */
    private String help;
    /**
     * tab页
     */
    private List<TabBo> tabList;
}
