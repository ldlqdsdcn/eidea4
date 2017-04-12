package com.dsdl.eidea.base.web.util;

import com.dsdl.eidea.base.def.OperatorDef;
import com.dsdl.eidea.base.entity.bo.UserContent;
import com.dsdl.eidea.base.service.SpringContextHolder;
import com.dsdl.eidea.base.service.UserService;
import com.dsdl.eidea.base.util.JwtUtil;
import com.dsdl.eidea.base.web.vo.VerifiedResult;
import com.dsdl.eidea.core.web.def.WebConst;
import com.dsdl.eidea.util.StringUtil;

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

    private SecurityHelper() {

    }

    public static SecurityHelper getSecurityHelper() {
        return securityHelper;
    }

    public VerifiedResult verifiedResult(HttpServletRequest request, OperatorDef[] operatorDefs) {
        UserContent userContent = (UserContent) request.getSession().getAttribute(WebConst.SESSION_USERCONTENT);
        VerifiedResult verifiedResult = new VerifiedResult();
        if (userContent == null) {
            String token = request.getHeader(WebConst.HEADER_TOKEN);
            if (StringUtil.isNotEmpty(token)) {
                if (JwtUtil.validateToken(token)) {
                    userContent = SpringContextHolder.getBean(UserService.class).getUserContent(token);
                } else {
                    verifiedResult.setCanAccessed(false);
                    verifiedResult.setMessage("你需要登录");
                    return verifiedResult;
                }
            } else {
                verifiedResult.setCanAccessed(false);
                verifiedResult.setMessage("你需要登录");
                return verifiedResult;
            }

        }
        Map<String, List<OperatorDef>> privilegesMap = userContent.getPrivileges();

        Set<String> privilegeKeySet = privilegesMap.keySet();
        userContent.getPrivileges();
        String requestURL = request.getServletPath();
        for (String key : privilegeKeySet) {
            if (requestURL.startsWith(key)) {
                StringBuilder sb = new StringBuilder("[");

                List<OperatorDef> operatorDefList = privilegesMap.get(key);
                for (int i = 0; i < operatorDefList.size(); i++) {
                    if (i > 0) {
                        sb.append(",");
                    }
                    sb.append("\"").append(operatorDefList.get(i).getKey()).append("\"");
                }
                sb.append("]");
                request.setAttribute(WebConst.PAGE_PRIVILEGES, sb.toString());
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
        UserContent userContent = (UserContent) request.getSession().getAttribute(WebConst.SESSION_USERCONTENT);
        if (userContent == null) {
            String token = request.getHeader(WebConst.HEADER_TOKEN);
            UserService userService = SpringContextHolder.getBean(UserService.class);
            userContent = userService.getUserContent(token);
        }


        return userContent.getOrgIdList();
    }


    private boolean containsOperator(List<OperatorDef> privilegesOperatorList, OperatorDef[] hasOperatorArray) {
        for (OperatorDef operatorDef : privilegesOperatorList) {
            for (OperatorDef hasOper : hasOperatorArray) {
                if (operatorDef == hasOper) {
                    return true;
                }
            }
        }
        return false;
    }

}
