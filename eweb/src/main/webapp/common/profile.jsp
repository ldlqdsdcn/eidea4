<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/12/13
  Time: 10:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/inc/taglib.jsp" %>
<html>
<head>
    <title><%--个人设置--%><eidea:label key="index.profile"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file="/inc/inc_ang_js_css.jsp" %>
</head>
<body>
<div ng-app='myApp' ng-view class="content">
    <div class="container-fluid" ng-controller="editCtrl">
        <form role="form" name="editForm" novalidate  ng-submit="save()">
            <div class="page-header button-css">
                <button type="submit" class="btn btn-primary btn-sm"><%--保存--%><eidea:label key="common.button.save"/></button>
            </div>
            <div class="row-fluid">
                <div class="span12">
                    <br>
                        <div class="form-group">
                            <label for="code"><%--登录名--%><eidea:label key="login.username.login"/></label>
                            <input type="text" class="form-control" id="code" name="code" ng-model="userProfileVo.user.username"  required ng-minlength="2" ng-maxlength="10" disabled>
                        </div>
                        <div class="form-group">
                            <label for="name"><%--姓名--%><eidea:label key="user.username"/></label>
                            <input type="text" class="form-control" id="name" ng-model="userProfileVo.user.name" required ng-minlength="2" ng-maxlength="30">
                        </div>
                        <div class="form-group">
                            <label for="token"><%--token--%><eidea:label key="personal.settings.label.token"/></label>
                            <textarea id="token" class="form-control" style="height: 150px;">
                                {{userProfileVo.userContent.token}}
                            </textarea>
                        </div>
                        <div class="form-group">
                            <p class="text-center" style="color: red">
                                {{message}}
                            </p>
                            <p>
                            <span ng-repeat="error in errors track by $index">
                                {{error.message}}
                            </span>
                            </p>
                        </div>
                </div>
            </div>
        </form>
    </div>
</div>

</body>
<script type="text/javascript">
    var app = angular.module('myApp', [ 'ui.bootstrap', 'jcs-autoValidate']);
    app.controller('editCtrl', function ($scope, $http) {
        $scope.message = '';
        $scope.userContent = {};
        $http.get( '<c:url value="/common/userCenter/getProfile"/>')
                .success(function (response) {
                    if (response.success) {
                        $scope.userProfileVo = response.data;
                    }
                    else {
                        bootbox.alert(response.message);
                    }
                }).error(function (response) {
            bootbox.alert(response);
        });
        $scope.save = function () {
            if ($scope.editForm.$valid) {
                var postUrl = '<c:url value="/common/userCenter/updateProfile"/>';
                $http.post(postUrl, $scope.userProfileVo.user).success(function (data) {
                    if (data.success) {
                        $scope.message = "<eidea:label key="base.save.success"/>";
                        $scope.clientBo = data.data;
                    }
                    else {
                        $scope.message = data.message;
                        $scope.errors=data.data;
                    }
                }).error(function (data, status, headers, config) {
                    // never reached even for 400/500 status codes
                    alert(JSON.stringify(data));
                });
            }
        }
    });
    app.run([
        'bootstrap3ElementModifier',
        function (bootstrap3ElementModifier) {
            bootstrap3ElementModifier.enableValidationStateIcons(true);
        }]);
</script>
</html>
