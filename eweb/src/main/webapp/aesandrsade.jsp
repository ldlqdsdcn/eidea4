<%@ page import="cryptography.AesUtil" %>
<%@ page import="cryptography.RsaUtil" %>

<%--
  Created by IntelliJ IDEA.
  User: joseph
  Date: 2017/4/28
  Time: 9:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<html>
<head>
    <title>contentDecrypt</title>
</head>
<body>
<%
    String iv = request.getParameter("iv");
    String content = request.getParameter("entext");
    String enc = request.getParameter("enc");
    AesUtil aesUtil = new AesUtil();
    RsaUtil rsaUtil = new RsaUtil();
    String dec = rsaUtil.rsaDecode(enc);
    String decodeContent = aesUtil.aesDecode(dec ,iv , content);
%>


还原出的密文为<%=decodeContent%>
</body>
</html>