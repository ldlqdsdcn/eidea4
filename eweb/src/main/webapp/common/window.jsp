<%--
  Created by IntelliJ IDEA.
  User: 刘大磊
  Date: 2017/5/24
  Time: 11:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/inc/taglib.jsp" %>
<html>
<head>
    <title><c:out value="${windowBo.name}"/> </title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file="/inc/inc_ang_js_css_dynamic.jsp" %>
</head>
<body>
<div ng-app='myApp'  class="content" ui-view>
    <div ng-controller="tabCtrl" class="nav nav-tab vertical-tab">
        <uib-tabset vertical="true" active="active" type="tabs">
            <c:forEach items="${windowBo.tabList}" var="tab" varStatus="status">
            <uib-tab index="${status.index}" heading="${tab.name}"  select="fieldList()"></uib-tab>
            </c:forEach>
        </uib-tabset>
    </div>
    <div ui-view class="tab-content vertical-tab-content" >

    </div>


</div>
<jsp:include page="/common/searchPage">
    <jsp:param name="uri" value="${uri}"/>
</jsp:include>
</body>
<script type="text/javascript">
    var app = angular.module('myApp',['ui.router','ui.bootstrap','jcs-autoValidate'])
            .config(['$stateProvider',function ($stateProvider,$urlRouterProvider) {
                $urlRouterProvider.otherwise('/tab${tabBo.id}list');
                $stateProvider
                        <c:forEach items="${windowBo.tabList}" var="tab">
                        .state('tab${tab.id}list', {
                            url:'/tab${tab.id}list',
                            templateUrl: '<c:url value="/common/tab/showList/${tab.id}"/>'
                        })
                        .state('tab${tab.id}edit', {
                            url:'/tab${tab.id}edit',
                            templateUrl: '<c:url value="/common/tab/showForm/${tab.id}"/>'
                        })
                        </c:forEach>


            }]);
<c:forEach items="${windowBo.tabList}" var="tab">
    app.controller('tab${tab.id}listCtrl', function ($scope,$http,$rootScope) {
        $scope.modelList = [];
        $scope.delFlag = false;
        $scope.isLoading = true;
        $scope.updateList = function (result) {
            $scope.modelList = result.data;
            $scope.queryParams.totalRecords = result.totalRecords;
            $scope.queryParams.init = false;
        };
        $scope.selectAll = function () {
            for (var i = 0; i < $scope.modelList.length; i++) {
                $scope.modelList[i].delFlag = $scope.delFlag;
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
        $scope.pageChanged = function () {
            $http.post("<c:url value="/common/tab/list/${tab.id}"/>", $scope.queryParams)
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


//批量删除
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
                }, callback: function (result) {
                    if (result) {
                        var ids = [];
                        for (var i = 0; i < $scope.modelList.length; i++) {
                            if ($scope.modelList[i].delFlag) {
                                ids.push($scope.modelList[i].id);
                            }
                        }
                        $scope.queryParams.init = true;
                        var param = {"queryParams": $scope.queryParams, "ids": ids};
                        $http.post("<c:url value="/base/window/deletes"/>", param).success(function (data) {
                            if (data.success) {
                                $scope.updateList(data.data);
                                bootbox.alert("<eidea:message key="module.deleted.success"/>");
                            } else {
                                bootbox.alert(data.message);
                            }
                        });
                    }
                }
            });
        };


//可现实分页item数量
        $scope.maxSize =${pagingSettingResult.pagingButtonSize};
        $scope.queryParams = {
            pageSize:${pagingSettingResult.perPageSize},//每页显示记录数
            pageNo: 1, //当前页
            totalRecords: 0,//记录数
            init: true
        };
        $scope.pageChanged();
    });
    app.controller('tab${tab.id}editCtrl', function ($scope, $http,$rootScope,$stateParams) {
        /**
         * 日期时间选择控件
         * bootstrap-datetime 24小时时间是hh
         */
        $('.bootstrap-datetime').datetimepicker({
            language: 'zh-CN',
            format: 'yyyy-mm-dd hh:ii:ss',
            weekStart: 1,
            todayBtn: 1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            forceParse: 0,
            showMeridian: 1,
            clearBtn: true
        });
        /**
         * 日期选择控件
         */
        $('.bootstrap-date').datepicker({
            language: 'zh-CN',
            format: 'yyyy-mm-dd',
            autoclose: 1,
            todayBtn: 1,
            clearBtn: true
        });
        $scope.message = '';
        $scope.windowPo = {};
        $scope.canAdd = PrivilegeService.hasPrivilege('add');
        $rootScope.id=$stateParams.id;
        var url = "<c:url value="/base/window/create"/>";
        if ($stateParams.id != null) {
            url = "<c:url value="/base/window/get"/>" + "?id=" + $stateParams.id;
        }
        $http.get(url)
                .success(function (response) {
                    if (response.success) {
                        $scope.windowPo = response.data;
                        $scope.getClientList();
                        $scope.getOrgList();
                        $scope.canSave = (PrivilegeService.hasPrivilege('add') && $scope.windowPo.id == null) || PrivilegeService.hasPrivilege('update');
                    }
                    else {
                        bootbox.alert(response.message);
                    }
                }).error(function (response) {
            bootbox.alert(response);
        });
        $scope.save = function () {
            if ($scope.editForm.$valid) {
                var postUrl = '<c:url value="/base/window/saveForUpdated"/>';
                if ($scope.windowPo.id == null) {
                    postUrl = '<c:url value="/base/window/saveForCreated"/>';
                }
                $http.post(postUrl, $scope.windowPo).success(function (data) {
                    if (data.success) {
                        $scope.message = "<eidea:label key="base.save.success"/>";
                        $scope.windowPo = data.data;
                    }
                    else {
                        $scope.message = data.message;
                        $scope.errors = data.data;
                    }
                }).error(function (data, status, headers, config) {
                    alert(JSON.stringify(data));
                });
            }
        }
        $scope.create = function () {
            $scope.message = "";
            $scope.windowPo = {};
            var url = "<c:url value="/base/window/create"/>";
            $http.get(url)
                    .success(function (response) {
                        if (response.success) {
                            $scope.windowPo = response.data;
                            $scope.getClientList();
                            $scope.getOrgList();
                            $scope.canSave = (PrivilegeService.hasPrivilege('add') && $scope.windowPo.id == null) || PrivilegeService.hasPrivilege('update');
                        }
                        else {
                            bootbox.alert(response.message);
                        }
                    }).error(function (response) {
                bootbox.alert(response);
            });
        }
        $scope.getClientList = function () {
            $http.get("<c:url value="/base/org/clients"/> ").success(function (data) {
                if (data.success){
                    $scope.clientList=data.data;
                }
            }).error(function (data) {
                bootbox.alert(data.message);
            })
        }
        var param = {"queryParams": $scope.queryParams};
        $scope.getOrgList = function () {
            $http.post("<c:url value="/base/org/list"/> ",param).success(function (data) {
                if (data.success){
                    $scope.orgList=data.data.data;
                }
            }).error(function (data) {
                bootbox.alert(data.message);
            })
        }
    });
</c:forEach>

    app.run([
        'bootstrap3ElementModifier',
        function (bootstrap3ElementModifier) {
            bootstrap3ElementModifier.enableValidationStateIcons(true);
        }]);
</script>
</html>