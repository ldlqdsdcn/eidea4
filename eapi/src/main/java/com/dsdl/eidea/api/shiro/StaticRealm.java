package com.dsdl.eidea.api.shiro;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Created by 刘大磊 on 2017/4/13 9:43.
 */
public class StaticRealm extends AuthorizingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;

        String username = upToken.getUsername();
        checkNotNull(username, "Null usernames are not allowed by this realm.");

        String password = Safe.getPassword(username);
        checkNotNull(password, "No account found for user [" + username + "]");

        return
                new SimpleAuthenticationInfo(username, password.toCharArray(), getName());
    }

    private void checkNotNull(Object reference, String message) {
        if (reference == null) {
            throw new AuthenticationException(message);
        }
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(Safe.getRoles((String) principals.getPrimaryPrincipal()));
        return authorizationInfo;
    }
}
