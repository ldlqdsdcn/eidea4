package indi.liudalei.eidea.base.entity.bo;

import indi.liudalei.eidea.base.def.OperatorDef;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 刘大磊 on 2016/12/26 16:45.
 */
@Getter
public class UserContent implements java.io.Serializable{
    private Map<String, List<OperatorDef>> privileges = new HashMap<>();
    private List<Integer> orgIdList;
    private UserSessionBo userSessionBo;

    /**
     * token 串
     */
    private String token;
    public UserContent(Map<String, List<OperatorDef>> privileges, UserSessionBo userSessionBo, String token, List<Integer> orgIdList) {
        this.privileges = privileges;
        this.userSessionBo = userSessionBo;
        this.token = token;
        this.orgIdList=orgIdList;
    }


}
