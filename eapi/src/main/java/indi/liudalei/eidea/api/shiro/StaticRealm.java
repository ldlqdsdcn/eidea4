package indi.liudalei.eidea.api.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by 刘大磊 on 2017/4/13 9:43.
 */
public class StaticRealm extends AuthorizingRealm {

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        System.out.println("登录");
        String username = upToken.getUsername();
        checkNotNull(username, "Null usernames are not allowed by this realm.");

        String password = Safe.getPassword(username);
        checkNotNull(password, "No account found for user [" + username + "]");
        WebDelegatingSubject subject=(WebDelegatingSubject)SecurityUtils.getSubject();
        HttpServletRequest request=(HttpServletRequest) subject.getServletRequest();
        System.out.println(subject.getClass().getName());
        System.out.println("路径:"+request.getServletPath());
        return
                new SimpleAuthenticationInfo(username, password, getName());
    }

    private void checkNotNull(Object reference, String message) {
        if (reference == null) {
            throw new AuthenticationException(message);
        }
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("授权");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(Safe.getRoles((String) principals.getPrimaryPrincipal()));
        WebDelegatingSubject subject=(WebDelegatingSubject)SecurityUtils.getSubject();
        System.out.println(subject.getClass().getName());
        return authorizationInfo;
    }
}
