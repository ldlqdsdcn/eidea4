<%@ page import="com.dsdl.eidea.base.web.vo.UserResource" %>
<%@ page import="com.dsdl.eidea.core.web.def.WebConst" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eidea" uri="http://eidea.cn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <!-- Meta, title, CSS, favicons, etc. -->
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title><eidea:label key="login.title"/></title>

    <link rel="shortcut icon" href="/favicon.ico">
    <!-- Bootstrap -->
    <link href="<c:url value="/css/bootstrap/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/bootstrap/bootstrap-theme.min.css"/>"  rel="stylesheet"/>
    <!-- Font Awesome -->
    <link href="<c:url value="/css/font-awesome.min.css"/>" rel="stylesheet">
    <!-- NProgress -->
    <link href="<c:url value="/vendors/nprogress/nprogress.css"/>" rel="stylesheet">
    <!-- Animate.css -->
    <link href="<c:url value="/css/animate.min.css"/>" rel="stylesheet">
    <!-- Custom Theme Style -->
    <link href="<c:url value="/css/custom.css"/>" rel="stylesheet">
    <!--login-js-->
    <script type='text/javascript' src='<c:url value="/js/jquery-3.1.1.min.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/bootstrap/bootstrap.min.js"/>'></script>
    <script type='text/javascript' src="<c:url value="/js/angular/angular.min.js"/>"></script>
    <script type='text/javascript' src="<c:url value="/js/angular/ui-bootstrap-tpls-2.2.0.min.js"/>"></script>
    <script type='text/javascript' src="<c:url value="/js/angular/jcs-auto-validate.min.js"/>"></script>
    <script type='text/javascript' src="<c:url value="/js/md5.min.js"/>"></script>
    <!--jsencryptcipher-->
    <script src="js/crypto-js.js"></script>
    <script src="js/jsencrypt.js"></script>
    <script src="js/security/AesAndRsaUtil.js"></script>

    <!--jsencryptcipher-->


</head>

<body class="login" ng-app="loginApp">
    <div ng-controller="loginCtrl">
      <div class="login_wrapper">
        <div class="animate form login_form">
          <section class="login_content">
            <form class="form-horizontal" name="loginForm" novalidate="novalidate" ng-submit="submit();"
                  ng-submit-force="true">
                <h1><eidea:label key="login.title"/></h1>
              <div class="form-group">
                <input type="text" class="form-control" id="username" name="username" ng-model="loginBo.username" placeholder="<eidea:label key="login.userName"/>">
              </div>
              <div class="form-group has-feedback">
                <input type="password" class="form-control" id="password" name="password" ng-model="loginBo.password" placeholder="<eidea:label key="login.password"/>">
              </div>
              <div class="form-group">
                <select class="form-control" ng-model="loginBo.code" ng-change="changeLang()"
                        ng-options="option.code as option.name for option in languages">
                </select>
               </div>

              <button id="loginBtn" type="submit" class="btn btn-default btn-sm" data-loading-text="<i class='fa fa-spinner fa-pulse'></i>&nbsp;<eidea:message key="login.msg.logining"/>"><eidea:label key="login.login"/></button>
              <div style="text-align:center;color:#a94442" class="help-block has-error error-msg" ng-show="serverReturnMessage!=null">
                {{serverReturnMessage}}
              </div>
            </form>
          </section>
            <h3><eidea:message key="support.browser"/></h3>
            <div class="row">
                chrome56+  firefox53+  safari5+ opera43+
            </div>
        </div>

      </div>
    </div>
</body>
<script type="text/javascript">
    var iv = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);//密钥偏移量，用于aes加密
    var key = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);//用于生成初始key
    var app = angular.module('loginApp', ['ui.bootstrap', 'jcs-autoValidate']);
    app.controller('loginCtrl',function ($scope,$http) {
        <%
            String userName="";
            Cookie[] cookies=request.getCookies();
            for(Cookie cookie:cookies)
                {
                    System.out.println(cookie.getName()+":"+cookie.getValue());
                    if(cookie.getName().equals("userName"))
                        {
                            userName=cookie.getValue();
                            break;
                        }
                }
        %>
        $scope.loginBo={"username":"<%=userName%>"};
        $scope.loginBo.code='<%= request.getSession().getAttribute(WebConst.SESSION_RESOURCE) ==null?request.getLocale().toString():((UserResource)request.getSession().getAttribute(WebConst.SESSION_RESOURCE)).getLocale()%>';
        $http.get("<c:url value="/languages"/>").success(function (data) {
            if (data.success) {
                $scope.languages = data.data;
            }else {
                $scope.serverReturnMessage = data.message;
            }
        });
        $scope.changeLang=function () {
            $http.post("<c:url value="/common/addCookie"/>",{"username":$scope.loginBo.username}).success(function (data) {
                window.location.href = "<c:url value="/common/changeLanguage"/>?language="+$scope.loginBo.code;
            });
        }
        $scope.submit = function () {
            <%
                session.setAttribute("timestamp",System.currentTimeMillis());
            %>
            var usernameAndPassword = $scope.loginBo.username+"|"+$scope.loginBo.password;
            var aesAndRsaUtil = new AesAndRsaUtil(iv,key);
            var enkey = aesAndRsaUtil.encryptkey();
            var cipherUsernameAndPassword = aesAndRsaUtil.aesencrypt(usernameAndPassword);
            var allparam = cipherUsernameAndPassword + "|" + enkey + "|" + iv;

            $("#loginBtn").button('loading');
            if ($scope.loginForm.$valid) {
                //合成一个字符串
                //loginParam={username:$scope.loginBo.username,password:md5(cipherpassword.toString()),"code":$scope.loginBo.code,enkey:enkey,iv:iv};
                loginParam={username:$scope.loginBo.username,password:$scope.loginBo.password,"code":$scope.loginBo.code};
                $scope.serverReturnMessage = "";
                $http.post("<c:url value="/login"/>",{"allparam":allparam}).success(function (data) {
                    if (data.success) {
                        window.location.href = "<c:url value="/index.jsp"/>";
                    }else {
                        $scope.serverReturnMessage = data.message;
                    }
                    $("#loginBtn").button('reset');
                });
            }
        }
    });
</script>
</html>
