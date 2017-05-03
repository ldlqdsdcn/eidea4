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
    <title><eidea:label key="menu.changelog"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file="/inc/inc_ang_js_css.jsp" %>
</head>
<body ng-app='myApp'>
<div ng-view class="content"></div>
</body>

<script type="text/javascript">
    var app = angular.module('myApp', ['ngRoute', 'ui.bootstrap', 'jcs-autoValidate'])
            .config(['$routeProvider', function ($routeProvider) {
                $routeProvider
                        .when('/list', {templateUrl: '<c:url value="/base/changelog/list.tpl.jsp"/>'})
                        .when('/edit', {templateUrl: '<c:url value="/base/changelog/edit.tpl.jsp"/>'})
                        .otherwise({redirectTo: '/list'});
            }]);
    app.controller('listCtrl', function ($scope,$rootScope, $http) {
        $scope.modelList = [];
        $scope.delFlag = false;
        $scope.isLoading=true;
        $http.post("<c:url value="/base/changelog/list"/>",$scope.queryParams)
                .success(function (response) {
                    $scope.isLoading=false;
                   if (response.success) {
                        $scope.updateList(response.data);
                    }
                    else {
                        bootbox.alert(response.message);
                    }

                });
        $scope.updateList = function (result) {
            $scope.modelList = result.data;
            $scope.queryParams.totalRecords = result.totalRecords;
            $scope.queryParams.init = false;
        };
        $scope.pageChanged = function () {
            $http.post("<c:url value="/base/changelog/list"/>", $scope.queryParams)
                .success(function (response) {
                    $scope.isLoading = false;
                    if (response.success) {
                        $scope.updateList(response.data);
                    }
                    else {
                        bootbox.alert(response.message);
                    }

                });
        }
//可现实分页item数量
        $scope.maxSize =${pagingSettingResult.pagingButtonSize};
        if ($rootScope.listQueryParams != null) {
            $rootScope.queryParams = $scope.listQueryParams;
            $rootScope.queryParams.init = true;
        }
        else {
            $scope.queryParams = {
                pageSize:${pagingSettingResult.perPageSize},//每页显示记录数
                pageNo: 1, //当前页
                totalRecords: 0,//记录数
                init: true
            };
            $rootScope.listQueryParams = $scope.queryParams;
        }

        $scope.pageChanged();

    });
    app.controller('editCtrl', function($scope,$rootScope, $http, $routeParams) {
           $scope.changelogVo={};
           $scope.headerList=[];
           $scope.bodyList=[];

           $http.get("<c:url value="/base/changelog/get"/>" + "?id=" + $routeParams.id).success(function (response) {
                       if (response.success) {
                           $scope.changelogVo=response.data;
                           $scope.showSelf();
                       }
                       else {
                           bootbox.alert(response.message);
                       }

                   });

    $scope.showSelf =function () {
        $scope.headerList= $scope.changelogVo.header;
        $scope.bodyList=$scope.changelogVo.bodyList;
   }
        $scope.showAll = function () {
            var url = "<c:url value="/base/changelog/showAllChanges"/>?tableName=" + $scope.changelogVo.changelogBo.name+"&pk="+ $scope.changelogVo.changelogBo.pk;
            $http.get(url)
                    .success(function (response) {
                        if (response.success) {
                            var changelogVoAll = response.data;
                            $scope.headerList = changelogVoAll.header;
                            $scope.bodyList = changelogVoAll.bodyList;
                        }
                        else {
                            bootbox.alert(response.message);
                        }
                    }).error(function (response) {
                bootbox.alert(response);
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
