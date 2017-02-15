.<%@page contentType="text/html;charset=UTF-8" isErrorPage="true"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@page import="org.slf4j.Logger,org.slf4j.LoggerFactory"%>
<%@page import="java.io.*"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
	response.setStatus(200);
	Throwable ex = null;
	if (exception != null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");

	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(ex.getMessage(), ex);
%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>500 - 系统内部错误</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
</head>

<body>
	<div style="overflow: auto">
		<h2>500 - 系统发生内部错误</h2>
		<br />
		<div>错误详细信息：</div>
		<div class="alert alert-error">
			<%=ex.getClass().getName() + "(" + ex.getLocalizedMessage() + ")"%><br />
			<%
				StringWriter sw = new StringWriter();
				PrintWriter pw = new PrintWriter(sw);
				ex.printStackTrace(pw);
				out.print(sw);
			%>
		</div>
		<a class="btn btn-primary" href="${ctx}">返回首页</a>
	</div>
</body>
</html>
