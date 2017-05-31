<%--
  Created by IntelliJ IDEA.
  User: 刘大磊
  Date: 2017/5/15
  Time: 16:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/inc/taglib.jsp" %>
<html>
<head>
    <title><%--用户Session--%><eidea:label key="leave.title"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file="/inc/inc_ang_js_css.jsp" %>
    <%@include file="/common/common_header.jsp" %>
</head>
<body>
<div ng-app='myApp' ng-view class="content"></div>
</body>
<script type="text/javascript">
    var app = angular.module('myApp', ['ngFileUpload','ngRoute', 'ui.bootstrap', 'jcs-autoValidate'])
            .config(['$routeProvider', function ($routeProvider) {
                $routeProvider
                        .when('/list', {templateUrl: '<c:url value="/sys/workflow/model/list.tpl.jsp"/>'})
                        .when('/edit',{templateUrl:'<c:url value="/sys/workflow/model/create.tpl.jsp"/>'})
                        .otherwise({redirectTo: '/list'});
            }]);
    app.controller('listCtrl', function ($rootScope,$scope,$http,$window) {
        $scope.allList = [];
        $scope.modelList = [];
        $scope.delFlag = false;
        $scope.canDel=PrivilegeService.hasPrivilege('delete');
        $scope.canAdd=PrivilegeService.hasPrivilege('add');
        $http.get("<c:url value="/sys/model/list"/>")
                .success(function (response) {
                    if (response.success) {
                        $scope.updateList(response.data);
                    }
                    else {
                        bootbox.alert(response.message);
                    }

                });
        $scope.deploy=function(modelId)
        {
            $http.get("<c:url value="/sys/model/deploy/"/>"+modelId)
                    .success(function (response) {
                        if(response.success){
                            bootbox.alert(response.data);
                        }else{
                            bootbox.alert(response.message);
                        }
                    });
        }
        $scope.updateList = function (data) {
            $scope.allList = data;
            $scope.queryParams.totalRecords  = $scope.allList.length;
            $scope.modelList.length = 0;
            $scope.pageChanged();
        };
        $scope.pageChanged = function (delF) {
            var bgn = ($scope.queryParams.pageNo - 1) * $scope.queryParams.pageSize;
            var end = bgn +  $scope.queryParams.pageSize;
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
        $scope.canDelete = function () {
            for (var i = 0; i < $scope.modelList.length; i++) {
                if ($scope.modelList[i].delFlag) {
                    return true;
                }
            }
            return false;
        }
        $scope.selectAll = function () {
            $scope.pageChanged($scope.delFlag);
        }
        $scope.deleteRecord = function () {
            bootbox.confirm({
                message: "<eidea:message key="common.warn.confirm.deletion"/>",
                buttons: {
                    confirm: {
                        label: '<eidea:label key="common.button.yes"/>',
                        className: 'btn-success'
                    },
                    cancel: {
                        label: '<eidea:label key="common.button.no"/>',
                        className: 'btn-danger'
                    }
                },
                callback: function (result) {
                    if (result) {
                        var ids = [];
                        for (var i = 0; i < $scope.modelList.length; i++) {
                            if ($scope.modelList[i].delFlag) {
                                ids.push($scope.modelList[i].id);
                            }
                        }
                        $http.post("<c:url value="/sys/model/delete"/>",ids).success(function (data) {
                            if (data.success) {
                                bootbox.alert("<eidea:message key="common.warn.deleted.success"/>");
                                $scope.updateList(data.data);
                            }
                            else {
                                bootbox.alert(data.message);
                            }

                        });
                    }
                }
            });
        };
        $scope.removeModel=function(modelId)
        {
            bootbox.confirm({
                message: "<eidea:message key="common.warn.confirm.deletion"/>",
                buttons: {
                    confirm: {
                        label: '<eidea:label key="common.button.yes"/>',
                        className: 'btn-success'
                    },
                    cancel: {
                        label: '<eidea:label key="common.button.no"/>',
                        className: 'btn-danger'
                    }
                },
                callback: function (result) {
                    if (result) {
                        var ids = [];
                        ids.push(modelId);
                        $http.post("<c:url value="/sys/model/delete/"/>",ids).success(function (data) {
                            if (data.success) {
                                bootbox.alert("<eidea:message key="common.warn.deleted.success"/>");
                                $scope.updateList(data.data);
                            }
                            else {
                                bootbox.alert(data.message);
                            }

                        });
                    }
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

        buttonHeader.listInit($scope,$window);
    });
    app.controller("createCtrl",function($routeParams,$scope, $http,$window,$timeout, Upload){
        $scope.canAdd=PrivilegeService.hasPrivilege('add');
        $scope.canSave=PrivilegeService.hasPrivilege('add');
        $scope.model={"name":"","key":"","description":""};
        $scope.create=function () {
            $scope.model={};
        }
        $scope.saveNext=function () {
            window.location.href="<c:url value="/sys/model/create"/>?name="+$scope.model.name+"&key="+$scope.model.key+"&description="+$scope.model.description;
        }
        buttonHeader.editInit($scope,$http,$window,$timeout, Upload,"/sys");
    });
    app.run([
        'bootstrap3ElementModifier',
        function (bootstrap3ElementModifier) {
            bootstrap3ElementModifier.enableValidationStateIcons(true);
        }]);
</script>
</html>