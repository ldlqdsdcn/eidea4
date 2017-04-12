package com.dsdl.eidea.security;


import com.dsdl.eidea.base.entity.bo.PermissionBo;
import com.dsdl.eidea.base.entity.bo.UserBo;
import com.dsdl.eidea.base.service.SpringContextHolder;
import com.dsdl.eidea.base.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.h2.engine.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by 刘大磊 on 2017/4/8 9:39.
 */
@Slf4j
public class EideaRealm extends AuthorizingRealm {

    private UserService userService;

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        if(userService==null)
        {
            userService= SpringContextHolder.getBean(UserService.class);
        }
        log.debug("------------------------------>doGetAuthorizationInfo-11");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        String username = (String) principalCollection.getPrimaryPrincipal();
        UserBo user = userService.getUserByUsername(username);
        List<PermissionBo> permissionBoList = userService.getUserPermissionList(user.getId());
        Set<String> permissionList = new HashSet<>();
        permissionBoList.forEach(e -> {
            permissionList.add(e.getPermission());
        });
        authorizationInfo.setStringPermissions(permissionList);
        return authorizationInfo;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        if(userService==null)
        {
            userService= SpringContextHolder.getBean(UserService.class);
        }
        UserBo user = userService.getUserByUsername((String) authenticationToken.getPrincipal());
        //账号不存在
        if (user == null) {
            throw new UnknownAccountException();
        }
        if (user.getIsactive().equals("N")) {
            throw new LockedAccountException();
        }
        AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(), "wms");
        log.debug("authenticationToken.getPrincipal()=" + authenticationToken.getPrincipal() + "");
        log.debug("------------------------------>doGetAuthenticationInfo22");
        return authenticationInfo;
    }
}
