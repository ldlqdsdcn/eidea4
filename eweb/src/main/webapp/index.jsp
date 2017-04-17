<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="eidea" uri="http://eidea.cn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.dsdl.eidea.base.entity.bo.UserBo" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title><eidea:label key="index.title"/></title>

    <!-- Bootstrap core CSS -->

    <link rel="stylesheet" href="<c:url value="/css/bootstrap/bootstrap.min.css"/>" type="text/css" media="all"/>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap/bootstrap-theme.min.css"/>" type="text/css" media="all"/>
    <link rel="stylesheet" href="<c:url value="/vendors/bootstrap-progressbar/css/bootstrap-progressbar-3.3.4.min.css"/>" type="text/css" media="all"/>
    <script type='text/javascript' src='<c:url value="/js/jquery-3.1.1.min.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/bootstrap/bootstrap.min.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/bootbox.min.js"/>'></script>
    <script type='text/javascript' src='<c:url value="/js/bootstrap/bootstrap-datetimepicker.min.js"/>'></script>
    <script type='text/javascript' src="<c:url value="/js/angular/angular.min.js"/>"></script>
    <script type='text/javascript' src="<c:url value="/js/angular/angular-route.min.js"/>"></script>
    <script type='text/javascript' src="<c:url value="/js/angular/jcs-auto-validate.min.js"/>"></script>
    <script type='text/javascript' src="<c:url value="/js/angular/ui-bootstrap-tpls-2.2.0.min.js"/>"></script>
    <script type='text/javascript' src="<c:url value="/js/consts.js"/>"></script>
    <script type='text/javascript' src="<c:url value="/js/eidea.util.js"/>"></script>

    <script type='text/javascript' src="<c:url value="/vendors/bootstrap-progressbar/bootstrap-progressbar.min.js"/>"></script>
    <link href="<c:url value="/css/font-awesome.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/css/animate.min.css"/>" rel="stylesheet">
    <script type='text/javascript' src="<c:url value="/js/md5.min.js"/>"></script>
    <!--sidebar-->
    <link href="<c:url value="/js/bootstrap/sidebar/jquery.mCustomScrollbar.min.css"/>" rel="stylesheet">
    <script type='text/javascript' src='<c:url value="/js/bootstrap/sidebar/jquery.mCustomScrollbar.js"/>'></script>
    <!--sidebar-->

    <!-- Theme styling -->

    <link href="<c:url value="/css/custom.css"/>" rel="stylesheet">
    <!--[if lt IE 9]>
    <script type="text/javascript" src='<c:url value="/js/ie8-responsive-file-warning.js"/>'></script>
    <![endif]-->

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src='<c:url value="/js/html5shiv.min.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/js/respond.min.js"/>' type="text/javascript"></script>
    <![endif]-->
    <script type="text/javascript">

        function resizeIframe(obj) {
            obj.style.height = obj.contentWindow.document.body.scrollHeight + 'px';
        }
        function gotoUrl(url) {
            document.getElementById('content').src=url;
        }
    </script>
</head>
<%UserBo user=(UserBo)session.getAttribute("loginUser"); %>
<body class="nav-md" ng-app="loginApp">
<div class="container body">
    <div class="main_container">
        <div class="col-md-3 left_col menu_fixed">
            <div class="left_col scroll-view">
                <div class="navbar nav_title" style="border: 0;">
                    <a href="<c:url value="/"/> " class="site_title"><i class="fa fa-paw"></i> <span><eidea:label key="index.enterprise_thinking"/></span></a>
                </div>

                <div class="clearfix"></div>

                <!-- menu profile quick info -->
                <div class="profile clearfix">
                    <div class="profile_pic">
                        <img src="<c:url value="/img/header.png"/>" alt="..." class="img-circle profile_img">
                    </div>
                    <div class="profile_info">
                        <span>Welcome,</span>
                        <h2> <p><%=user.getName()%></p></h2>
                    </div>
                </div>
                <!-- /menu profile quick info -->

                <br>

                <!-- sidebar menu -->
                <div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
                    <div class="menu_section">
                        ${sessionScope.menuString}
                    </div>
                </div>
                <!-- sidebar menu -->

                <!-- /menu footer buttons -->
                <div class="sidebar-footer hidden-small">
                    <a data-toggle="tooltip" data-placement="top" title="" data-original-title="Settings">
                        <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
                    </a>
                    <a data-toggle="tooltip" data-placement="top" title="" data-original-title="FullScreen">
                        <span class="glyphicon glyphicon-fullscreen" aria-hidden="true"></span>
                    </a>
                    <a data-toggle="tooltip" data-placement="top" title="" data-original-title="Lock">
                        <span class="glyphicon glyphicon-eye-close" aria-hidden="true"></span>
                    </a>
                    <a data-toggle="tooltip" data-placement="top" title="" data-original-title="Logout">
                        <span class="glyphicon glyphicon-off" aria-hidden="true"></span>
                    </a>
                </div>
                <!-- /menu footer buttons -->
            </div>
        </div>
        <!--left menu end-->
        <!--top navigation-->
        <div class="top_nav" ng-controller="switchLanguageCtrl">
            <div class="nav_menu">
                <nav>
                    <div class="nav toggle">
                        <a id="menu_toggle"><i class="fa fa-bars"></i></a>
                    </div>

                    <ul class="nav navbar-nav navbar-right">
                        <li class="">
                            <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                <img src="<c:url value="/img/header.png"/>" alt="">
                                <span class=" fa fa-angle-down"></span>
                            </a>
                            <ul class="dropdown-menu dropdown-usermenu pull-right">
                                <li><a href="javascript:void(0);" data-toggle="modal" data-target="#changePasswordModal"><eidea:label key="index.change_password"/></a></li>
                                <li><a href="javascript:gotoUrl('<c:url value="/common/profile.jsp"/>');"><eidea:label key="index.profile"/></a></li>
                                <li>
                                    <a href="javascript:;">
                                        <span class="badge bg-red pull-right"><eidea:label key="index.proportion"/></span>
                                        <span><eidea:label key="index.settings"/></span>
                                    </a>
                                </li>
                                <li><a href="javascript:;"><eidea:label key="index.help"/></a></li>
                                <li><a href="<c:url value="/logout"/>"><i class="fa fa-sign-out pull-right"></i><eidea:label key="index.log_out"/></a></li>
                            </ul>
                        </li>

                        <li role="presentation" class="dropdown">
                            <a href="javascript:;" class="dropdown-toggle info-number" data-toggle="dropdown" aria-expanded="false">
                                <i class="fa fa-envelope-o"></i>
                                <span class="badge bg-green"><eidea:label key="index.email_size"/></span>
                            </a>
                            <ul id="menu1" class="dropdown-menu list-unstyled msg_list" role="menu">
                                <li>
                                    <a>
                                        <span class="image"><img src="<c:url value="/img/header.png"/>" alt="Profile Image"></span>
                                        <span>
                                          <span><eidea:label key="index.email_name"/></span>
                                          <span class="time"><eidea:label key="index.email_time"/></span>
                                        </span>
                                        <span class="message"><eidea:label key="index.email_message"/></span>
                                    </a>
                                </li>
                                <li>
                                    <div class="text-center">
                                        <a>
                                            <strong><eidea:label key="index.email_see_all"/></strong>
                                            <i class="fa fa-angle-right"></i>
                                        </a>
                                    </div>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a href="javascript:;" class="user-profile dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                {{defaultLanguageName}}
                            </a>
                            <ul class="dropdown-menu dropdown-usermenu pull-right">
                                <li ng-repeat="language in languages">
                                    <a ng-click="swithLanguage(language.code)">{{language.name}}</a>
                                </li>
                            </ul>

                        </li>
                    </ul>
                </nav>
            </div>
            <!--用户修改密码-->
            <%@ include file="/common/change_password.jsp" %>
            <!--用户修改密码-->
        </div>
        <!--top navigation-->
        <!--page content-->
        <div class="right_col" role="main">
            <!--我是主要内容 start-->
            <iframe id="content" name="content" src="<c:url value="/default.jsp"/>" width="100%" height="100%"  frameborder="0"></iframe>
            <!--我是主要内容 end-->
        </div>
        <!--page content-->
    </div>
</div>
<script src='<c:url value="/js/custom.js"/>' type="text/javascript"></script>
<script type="text/javascript">
    //$("#content").load("<c:url value="/core/language/showList"/>");
    var app = angular.module('loginApp', ['ui.bootstrap', 'jcs-autoValidate']);
    app.controller('switchLanguageCtrl',function ($scope,$http) {
        //获取语言列表
        $http.get("<c:url value="/languages"/>").success(function (data) {
            if (data.success) {
                $scope.languages = data.data;
                var languageCode="<%=(request.getSession().getAttribute("language")==null)?request.getLocale().toString():request.getSession().getAttribute("language")%>";
                $.each($scope.languages,function (index,value) {
                    if(value.code == languageCode){
                        $scope.defaultLanguageName=value.name;
                    }
                })
            }else {
                $scope.serverReturnMessage = data.message;
            }
        });
        //切换语种
        $scope.swithLanguage=function (languageCode) {
            $.each($scope.languages,function (index,value) {
                if(value.code == languageCode){
                    $scope.defaultLanguageName=value.name;
                }
            })
            window.parent.location.href = "<c:url value="/common/changeLanguageCode"/>?language="+languageCode;
        }
    });
</script>
</body>
</html>
