<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2017/1/3
  Time: 14:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/inc/taglib.jsp" %>
<html>
<head>
    <title><eidea:label key="menu.online"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file="/inc/inc_ang_js_css.jsp" %>
</head>
<body>
<div ng-app='myApp' ng-view class="content"></div>
<jsp:include page="/common/searchPage">
    <jsp:param name="uri" value="${uri}"/>
</jsp:include>
</body>

<script type="text/javascript">

    var app = angular.module('myApp', ['ngRoute', 'ui.bootstrap', 'jcs-autoValidate'])
            .config(['$routeProvider', function ($routeProvider) {
                $routeProvider
                        .when('/list', {templateUrl: '<c:url value="/base/online/list.tpl.jsp"/>'})
                        .otherwise({redirectTo: '/list'});
            }]);
    app.controller('listCtrl', function ($scope, $http) {
        $scope.allList = [];
        $scope.modelList = [];
        $scope.canDel=PrivilegeService.hasPrivilege('online:delete');
        $scope.canAdd=PrivilegeService.hasPrivilege('online:add');
        $http.get("<c:url value="/base/userOnline/list"/>")
                .success(function (response) {
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
        $scope.pageChanged = function () {
            var bgn = ($scope.bigCurrentPage - 1) * $scope.itemsPerPage;
            var end = bgn + $scope.itemsPerPage;
            $scope.modelList.length = 0;
            for (var i = bgn; i < end && i < $scope.allList.length; i++) {
                var item = $scope.allList[i];
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
    app.run([
        'bootstrap3ElementModifier',
        function (bootstrap3ElementModifier) {
            bootstrap3ElementModifier.enableValidationStateIcons(true);
        }]);
</script>
</html>

