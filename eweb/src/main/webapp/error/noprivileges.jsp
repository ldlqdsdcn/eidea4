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
<html>
<head>
</head>
<body style="background: white">
<div align="center">
    <img src="<%=request.getContextPath() %>/img/noview.jpg"><br><br>
    <font size="4" color="red"><b>您没有权限执行该操作</b></font>
</div>
</body>
</html>
<%
    } else {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("utf-8");
        Gson gson = new Gson();
        out.print(gson.toJson(ApiResult.fail(ErrorCodes.NO_PRIVILEGES.getCode(), "你没有操作权限")));
    }%>