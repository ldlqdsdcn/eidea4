package indi.liudalei.eidea.base.entity.bo;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by 刘大磊 on 2017/6/2 15:22.
 */
@Getter
@Setter
public class WindowHelpBo {
    private Integer id;
    private String name;
    private String help;
    private List<TabHelpBo> tabHelpBoList;
}
