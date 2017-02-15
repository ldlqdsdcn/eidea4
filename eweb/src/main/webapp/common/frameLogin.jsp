<%@ include file="/inc/taglib.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<head>
<script type="text/javascript">
if(window.parent.parent!=null)
window.parent.parent.location="<c:url value='/login.jsp'/>";
else{
if(window.parent!=null)
window.parent.location="<c:url value='/login.jsp'/>";
else window.location="<c:url value='/login.jsp'/>";
}
</script>
</head>
<body></body>
</html>
