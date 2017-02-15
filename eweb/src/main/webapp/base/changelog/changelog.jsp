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
    app.controller('listCtrl', function ($scope, $http) {
        $scope.allList = [];
        $scope.modelList = [];
        $scope.delFlag = false;
        $scope.isLoading=true;
        $http.get("<c:url value="/base/changelog/list"/>")
                .success(function (response) {
                    $scope.isLoading=false;
                   if (response.success) {
                        $scope.updateList(response.data);
                    }
                    else {
                        bootbox.alert(response.message);
                    }

                });
        $scope.updateList = function (data) {
            $scope.allList = data;
            $scope.bigTotalItems = $scope.allList.length;
            $scope.modelList.length = 0;
            $scope.pageChanged();
        };
        $scope.pageChanged = function (delF) {
            var bgn = ($scope.bigCurrentPage - 1) * $scope.itemsPerPage;
            var end = bgn + $scope.itemsPerPage;
            $scope.modelList.length = 0;
            if (delF == null) {
                delF = false;
            }
            for (var i = bgn; i < end && i < $scope.allList.length; i++) {
                var item = $scope.allList[i];
                item.delFlag = delF;
                $scope.modelList.push(item);

            }
        }
      
        //可现实分页item数量
        $scope.maxSize =${pagingSettingResult.pagingButtonSize};
        //每页现实记录数
        $scope.itemsPerPage =${pagingSettingResult.perPageSize};
        //当前页
        $scope.bigCurrentPage = 1;
        //记录数
        $scope.bigTotalItems = 0;
    });
    app.controller('editCtrl', function($scope, $http, $routeParams) {
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
            var url = "<c:url value="/base/changelog/showAllChanges"/>?tableName=" + $scope.changelogVo.changelogBo.name;
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
