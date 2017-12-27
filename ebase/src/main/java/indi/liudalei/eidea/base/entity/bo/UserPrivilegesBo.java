package indi.liudalei.eidea.base.entity.bo;

import indi.liudalei.eidea.base.def.OperatorDef;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by 刘大磊 on 2016/12/26 17:19.
 */
@Getter
@Setter
public class UserPrivilegesBo {
    private Integer moduleId;
    private String moduleName;
    private List<String> directoryList;
    private List<OperatorDef> operatorDefList;
}
