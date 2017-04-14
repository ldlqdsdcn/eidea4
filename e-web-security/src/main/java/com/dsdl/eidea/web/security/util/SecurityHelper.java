package com.dsdl.eidea.web.security.util;

import com.dsdl.eidea.web.security.def.OperatorDef;
import com.dsdl.eidea.base.entity.bo.UserContent;
import com.dsdl.eidea.base.service.SpringContextHolder;
import com.dsdl.eidea.base.service.UserService;
import com.dsdl.eidea.web.security.def.SecurityWebConst;
import com.dsdl.eidea.web.security.jwt.JwtUtil;
import com.dsdl.eidea.web.security.model.UserPrivilegesContent;
import com.dsdl.eidea.web.security.model.VerifiedResult;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.util.StringUtil;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by 刘大磊 on 2016/12/27 16:52.
 * 通过主键，验证用户权限
 */
public class SecurityHelper {
    private static final SecurityHelper securityHelper = new SecurityHelper();
    /**
     *
     */
    private EhCacheCacheManager ehCacheCacheManager;
    private SecurityHelper() {

    }

    public static SecurityHelper getSecurityHelper() {
        return securityHelper;
    }

    public VerifiedResult verifiedResult(HttpServletRequest request, OperatorDef[] operatorDefs) {
        UserPrivilegesContent userContent = (UserPrivilegesContent) request.getSession().getAttribute(SecurityWebConst.SESSION_USERCONTENT);
        VerifiedResult verifiedResult = new VerifiedResult();
        if (userContent == null) {
            String token=request.getHeader(SecurityWebConst.HEADER_TOKEN);
            if(StringUtil.isNotEmpty(token))
            {
                if(JwtUtil.validateToken(token))
                {

                    userContent= (UserPrivilegesContent) ehCacheCacheManager.getCache("userTokenContent").get(token);
                }
                else
                {
                    verifiedResult.setCanAccessed(false);
                    verifiedResult.setMessage("你需要登录");
                    return verifiedResult;
                }
            }
            else
            {
                verifiedResult.setCanAccessed(false);
                verifiedResult.setMessage("你需要登录");
                return verifiedResult;
            }

        }
        Map<String, List<String>> privilegesMap = userContent.getPrivileges();

        Set<String> privilegeKeySet = privilegesMap.keySet();
        userContent.getPrivileges();
        String requestURL = request.getServletPath();
        for (String key : privilegeKeySet) {
            if (requestURL.startsWith(key)) {
                StringBuilder sb = new StringBuilder("[");

                List<String> operatorDefList = privilegesMap.get(key);
                for (int i = 0; i < operatorDefList.size(); i++) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append("\"").append(operatorDefList.get(i)).append("\"");
                }
                sb.append("]");
                request.setAttribute(SecurityWebConst.PAGE_PRIVILEGES, sb.toString());
                if (!containsOperator(operatorDefList, operatorDefs)) {
                    verifiedResult.setCanAccessed(false);
                    verifiedResult.setMessage("你没有相关的操作权限");
                    return verifiedResult;
                } else {
                    verifiedResult.setCanAccessed(true);
                    verifiedResult.setMessage("");
                    return verifiedResult;
                }
            }
        }
        return verifiedResult;
    }

    public boolean hasPrivilege(HttpServletRequest request, OperatorDef operatorDefs) {
        UserContent userContent = (UserContent) request.getSession().getAttribute(WebConst.SESSION_USERCONTENT);
        if (userContent == null) {
            return false;
        }
        Map<String, List<OperatorDef>> privilegesMap = userContent.getPrivileges();
        Set<String> privilegeKeySet = privilegesMap.keySet();
        String requestURL = request.getServletPath();
        for (String key : privilegeKeySet) {
            if (requestURL.startsWith(key)) {
                List<OperatorDef> operatorDefList = privilegesMap.get(key);

                if (operatorDefList.contains(operatorDefs)) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public List<Integer> getAccessOrgList(HttpServletRequest request) {
        UserPrivilegesContent userContent = (UserPrivilegesContent) request.getSession().getAttribute(SecurityWebConst.SESSION_USERCONTENT);
        if (userContent == null) {
            String token = request.getHeader(SecurityWebConst.HEADER_TOKEN);

             userContent = (UserPrivilegesContent) request.getSession().getAttribute(SecurityWebConst.SESSION_USERCONTENT);
        }
        return userContent.getOrgIdList();
    }

    private boolean containsOperator(List<String> privilegesOperatorList, String[] hasOperatorArray) {
        for (String operatorDef : privilegesOperatorList) {
            for (String hasOper : hasOperatorArray) {
                if (operatorDef.equals(hasOper) ) {
                    return true;
                }
            }
        }
        return false;
    }

}
