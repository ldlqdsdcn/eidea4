package com.dsdl.eidea.base.service;

import com.dsdl.eidea.base.entity.bo.UserBo;
import com.dsdl.eidea.base.entity.bo.UserContent;
import com.dsdl.eidea.base.entity.bo.UserSessionBo;
import com.googlecode.genericdao.search.Search;

import java.util.List;
import java.util.Map;

/**
 * Created by Bobo on 2016/12/17 14:01.
 */
public interface UserService {
    /**
     * getUserList:用户列表
     * @param search
     * @return
     */
    List<UserBo> getUserList(Search search);
    /**
     * deleteUserList:用户批量删除
     * @param ids
     */
    void deleteUserList(Integer[] ids);

    /**
     * saveUser:用户保存
     * @param userBo
     */
    void saveUser(UserBo userBo);

    /**
     * getUser:查询用户
     * @param id
     * @return
     */
    UserBo getUser(Integer id);
    UserBo getUserLogin(String username,String password);
    /**
     * findExistByUsername:判断url是否存在
     * @param username
     * @return
     */
    boolean findExistByUsername(String username);

    /**
     * getExistUserName:判断登录名称是否存在
     * @param userBo
     * @return
     */
    boolean getExistUserName(UserBo userBo);

    /**
     * 获取登录权限
     * @param userId
     * @return
     */
    Map<String, List<String>> getUserPrivileges(Integer userId);


    UserSessionBo saveUserSessionBo(UserSessionBo userSessionBo);

    /**
     * 获取数据访问权限
     * @param userId
     * @return
     */
    List<Integer> getCanAccessOrgList(Integer userId);

    /**
     * 根据token获取用户权限
     * @param token
     * @return
     */
    UserContent getUserContent(String token);
}
