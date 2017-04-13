<%@ page import="com.google.gson.Gson" %>
<%@ page import="com.dsdl.eidea.core.web.result.ApiResult" %>
<%@ page import="com.dsdl.eidea.core.web.result.def.ErrorCodes" %>
<% String headerKey = "x-requested-with";
    String headerContent = "XMLHttpRequest";
    String angularKey = "accept";
    String angularContent = "application/json, text/plain, */*";
    if (!headerContent.equals(request.getHeader(headerKey)) && !angularContent.equals(request.getHeader(angularKey))) {
        response.setContentType("text/html; charset=UTF-8");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <head>
        <script type="text/javascript">
            if (window.parent.parent != null)
                window.parent.parent.location = "<c:url value='/login.jsp'/>";
            else {
                if (window.parent != null)
                    window.parent.location = "<c:url value='/login.jsp'/>";
                else window.location = "<c:url value='/login.jsp'/>";
            }
        </script>
    </head>
<body></body>
</html>
<%
    } else {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        Gson gson = new Gson();
        out.print(gson.toJson(ApiResult.fail(ErrorCodes.NO_LOGIN.getCode(), "你需要登录")));
    }%>