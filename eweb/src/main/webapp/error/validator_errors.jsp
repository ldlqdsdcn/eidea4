<%--
  Created by IntelliJ IDEA.
  User: 刘大磊
  Date: 2017/9/15
  Time: 17:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
</head>
<body style="background: white">
<div align="center">

    <img src="<%=request.getContextPath() %>/image/noview.jpg"><br><br>
    <font size="4" color="red"><b>验证错误</b></font>
    <br>
    <%=request.getAttribute("errors")%>
</div>
</body>
</html>

