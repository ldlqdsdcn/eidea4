<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/12/12
  Time: 13:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" href="<c:url value="/css/bootstrap/bootstrap.min.css"/>" type="text/css" media="all"/>
<link rel="stylesheet" href="<c:url value="/css/bootstrap/bootstrap-theme.min.css"/>" type="text/css" media="all"/>
<script type='text/javascript' src='<c:url value="/js/jquery-3.1.1.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/bootstrap/bootstrap.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/js/bootbox.min.js"/>'></script>
<script type='text/javascript' src="<c:url value="/js/angular/angular.min.js"/>"></script>
<script type='text/javascript' src="<c:url value="/js/angular/angular-route.min.js"/>"></script>
<script type='text/javascript' src="<c:url value="/js/angular/jcs-auto-validate.min.js"/>"></script>
<script type='text/javascript' src="<c:url value="/js/angular/ui-bootstrap-tpls-2.2.0.min.js"/>"></script>
<script type='text/javascript' src="<c:url value="/js/angular/i18n/angular-locale_zh-cn.js"/>"></script>
<script type='text/javascript' src="<c:url value="/js/bootstrap/datetimepicker/bootstrap-datetimepicker.min.js"/>"></script>
<script type='text/javascript' src="<c:url value="/js/bootstrap/datetimepicker/locales/bootstrap-datetimepicker.zh-CN.js"/>"></script>
<script type='text/javascript' src="<c:url value="/js/bootstrap/datepicker/bootstrap-datepicker.min.js"/>"></script>
<script type='text/javascript' src="<c:url value="/js/bootstrap/datepicker/locales/bootstrap-datepicker.zh-CN.min.js"/>"></script>
<script type='text/javascript' src="<c:url value="/js/consts.js"/>"></script>
<script type='text/javascript' src="<c:url value="/js/eidea.util.js"/>"></script>

<script type='text/javascript' src="<c:url value="/js/eidea.validate.js"/>"></script>

<%--角色权限控制--%>
<script type='text/javascript' id="privileges" src="<c:url value="/js/angular/eidea.privileges.js"/>" data-privileges='${pagePrivileges}'></script>
<%--加特殊效果 css 例如 等待按钮--%>
<link rel="stylesheet" href="<c:url value="/css/font-awesome.min.css"/>" type="text/css" media="all"/>
<%--eidea项目自己的样式--%>
<link rel="stylesheet" href="<c:url value="/css/eidea.css"/>" type="text/css" media="all"/>
<%--日期控件支持--%>
<link rel="stylesheet" href="<c:url value="/css/bootstrap/datetimepicker/bootstrap-datetimepicker.min.css"/>" type="text/css" media="all"/>
<link rel="stylesheet" href="<c:url value="/css/bootstrap/datepicker/bootstrap-datepicker3.min.css"/>" type="text/css" media="all"/>
<%--上传控件--%>
<script type='text/javascript' src="<c:url value="/js/angular/ngfileupload/ng-file-upload.js"/>"></script>
<script type='text/javascript' src="<c:url value="/js/angular/ngfileupload/ng-file-upload-shim.js"/>"></script>
