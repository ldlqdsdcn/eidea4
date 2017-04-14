package com.dsdl.eidea.web.security.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 刘大磊 on 2017/4/14 12:55.
 */
@Getter
public class UserPrivilegesContent {
    private Map<String, List<String>> privileges = new HashMap<>();
    private List<Integer> orgIdList;

    /**
     * token 串
     */
    private String token;
    public UserPrivilegesContent(Map<String, List<String>> privileges, String token,List<Integer> orgIdList) {
        this.privileges = privileges;
        this.token = token;
        this.orgIdList=orgIdList;
    }
}
