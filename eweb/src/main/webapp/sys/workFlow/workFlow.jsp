<%--
  Created by IntelliJ IDEA.
  User: Bobo
  Date: 2016/12/7
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/inc/taglib.jsp" %>
<html>
<head>
    <title>工作流</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file="/inc/inc_ang_js_css.jsp" %>
</head>
<body>
<div ng-view class="content" ng-app='myApp'></div>
</body>

<script type="text/javascript">
    var app = angular.module('myApp', ['ngFileUpload','ngRoute', 'ui.bootstrap', 'jcs-autoValidate'])
            .config(['$routeProvider', function ($routeProvider) {
                $routeProvider
                        .when('/edit', {templateUrl: '<c:url value="/sys/workFlow/edit.tpl.jsp"/>'})
                        .otherwise({redirectTo: '/edit'});
            }]);
    var version = '11.1.3';
    app.controller('editCtrl', function ($scope,$rootScope,$http, $routeParams, $timeout, Upload) {
        //工作流上传
        $scope.$watch('files', function (files) {
            $scope.formUpload = false;
            if (files != null) {
                if (!angular.isArray(files)) {
                    $timeout(function () {
                        $scope.files = files = [files];
                    });
                    return;
                }
                for (var i = 0; i < $scope.files.length; i++) {
                    $scope.errorMsg = null;
                    (function (f) {
                        $scope.upload(f, true);
                    })($scope.files[i])
                    ;
                }
            }
        });
        $scope.upload = function (file) {
            file.upload =Upload.upload({
                //服务端接收
                url: "<c:url value="/sys/workflow/upload"/>",
                //上传的文件
                file: file
            }).success(function (data, status, headers, config) {
                //上传成功
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '<i class="fa fa-close" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.closed"/>',
                            className: 'btn-primary'
                        }
                    },
                    message: '<eidea:message key="common.upload.success"/>',
                });
            }).error(function (data, status, headers, config) {
                //上传失败
                console.log('error status: ' + status);
            });
        };
    });
    app.run([
        'bootstrap3ElementModifier',
        function (bootstrap3ElementModifier) {
            bootstrap3ElementModifier.enableValidationStateIcons(true);
        }]);
</script>
</html>
