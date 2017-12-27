<%--
  Created by IntelliJ IDEA.
  User: 刘大磊
  Date: 2017/5/24
  Time: 11:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="indi.liudalei.eidea.core.def.FieldInputType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/inc/taglib.jsp" %>
<html>
<head>
    <title><c:out value="${windowBo.windowName}"/> </title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file="/inc/inc_ang_js_css_dynamic.jsp" %>
    <%@include file="/general/inc/header_buttons.jsp" %>
</head>
<body >
<div ng-app='myApp'  class="content">
    <div ng-controller="tabCtrl" class="nav nav-tab vertical-tab">
        <uib-tabset vertical="true" active="active" type="tabs">
            <c:forEach items="${windowBo.tabList}" var="tab" varStatus="status">
            <uib-tab index="${status.index}" heading="${tab.tabName}"  select="fieldList()"></uib-tab>
            </c:forEach>
        </uib-tabset>
    </div>
    <div ng-app='myApp'  class="tab-content vertical-tab-content" ui-view></div>
    </div>
</div>
<jsp:include page="/common/searchPage">
    <jsp:param name="uri" value="${uri}"/>
</jsp:include>
<jsp:include page="/general/window/help/${windowBo.windowId}"/>
</body>
<script type="text/javascript">
    var app = angular.module('myApp',['ngFileUpload','ngRoute','ui.router','ui.bootstrap','jcs-autoValidate'])
            .config(['$stateProvider','$urlRouterProvider',function ($stateProvider,$urlRouterProvider) {
                $urlRouterProvider.otherwise('/tab${tabBo.id}list');
                $stateProvider
                        <c:forEach items="${windowBo.tabList}" var="tab">
                        .state('tab${tab.id}list', {
                            url:'/tab${tab.id}list',
                            templateUrl: '<c:url value="/general/tab/showList/${tab.id}"/>'
                        })
                        .state('tab${tab.id}edit', {
                            url:'/tab${tab.id}edit?id',
                            templateUrl: '<c:url value="/general/tab/showForm/${tab.id}"/>'
                        })
                        </c:forEach>


            }]);
    app.controller('tabCtrl',function ($scope,$rootScope,$state) {
        <c:forEach items="${windowBo.tabList}" var="tab">
            $scope.tab${id}go=function(){
                $state.go('tab${tab.id}list');
            }
        </c:forEach>
    });

<c:forEach items="${windowBo.tabList}" var="tab">
    app.controller('tab${tab.id}listCtrl', function ($scope,$http,$rootScope,$state,$window) {
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
            $http.post("<c:url value="/general/tab/list/${tab.id}"/>", $scope.queryParams)
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

        $scope.edit=function (id) {
            $state.go('tab${tab.id}edit', {'id': id});
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
                                ids.push($scope.modelList[i].id${tab.pkFieldId});
                            }
                        }
                        $scope.queryParams.init = true;
                        var param = {"queryParams": $scope.queryParams, "ids": ids};
                        $http.post("<c:url value="/general/tab/deletes/${tab.id}"/>", param).success(function (data) {
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
        $scope.maxSize =10;
        if ($rootScope.listQueryParams != null) {
            $rootScope.queryParams = $scope.listQueryParams;
            $rootScope.queryParams.init = true;
        }
        else {
            $scope.queryParams = {
                pageSize:15,//每页显示记录数
                pageNo: 1, //当前页
                totalRecords: 0,//记录数
                init: true
            };
            $rootScope.listQueryParams = $scope.queryParams;
        }
        $scope.pageChanged();
        buttonHeader.listInit($scope,$window);

    });
    app.controller('tab${tab.id}editCtrl', function ($stateParams,$scope, $http,$window,$timeout, Upload) {
        $scope.message = '';
        $scope.model = {};
         var url = "<c:url value="/general/tab/create/${tab.id}/"/>";
        if ($stateParams.id != null) {
            url = "<c:url value="/general/tab/get/${tab.id}/"/>"+ $stateParams.id;
        }
        $http.get(url)
                .success(function (response) {
                    if (response.success) {
                        $scope.model = response.data;
                        $scope.tableId=$scope.model.id;
                    }
                    else {
                        bootbox.alert(response.message);
                    }
                }).error(function (response) {
            bootbox.alert(response);
        });
        <c:forEach items="${tab.fieldList}" var="field">
            <c:if test="${field.inputType eq FieldInputType.SELECT}">
                $http.get("<c:url value="/general/tab/getSelectList/${field.id}"/>")
                    .success(function (response) {
                        if (response.success) {
                            $scope.selectField${field.id} = response.data;
                        }
                        else
                        {
                            bootbox.alert(response.message);
                        }
                    });
            </c:if>
        </c:forEach>
        /**
         * 日期时间选择控件
         * bootstrap-datetime 24小时时间是hh
         */
        $('.bootstrap-datetime').datetimepicker({
            language:  'zh-CN',
            format: 'yyyy-mm-dd hh:ii:ss',
            weekStart: 1,
            todayBtn:  1,
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
            language:  'zh-CN',
            format: 'yyyy-mm-dd',
            autoclose: 1,
            todayBtn:  1,
            clearBtn:true
        });

        $scope.save = function () {
            $scope.message="";
            if ($scope.editForm.$valid) {
                var postUrl = '<c:url value="/general/tab/saveForUpdated/${tab.id}"/>';
                if ($scope.model.id${tab.pkFieldId} == null) {
                    postUrl = '<c:url value="/general/tab/saveForCreated/${tab.id}"/>';
                }
                $http.post(postUrl, $scope.model).success(function (data) {
                    if (data.success) {
                        $scope.errorCode=-1;
                        $scope.message = "<eidea:label key="base.save.success"/>";
                        $scope.model = data.data;
                    }
                    else {
                        $scope.message=data.message;
                        $scope.errorCode=data.errorCode;
                        if (data.errorCode == ERROR_VALIDATE_PARAM) {
                            $scope.errorMessages=data.data;
                        }
                        else {
                            $scope.errorMessages = [data.message];
                        }
                    }
                });
            }
        }
        $scope.create = function () {
            $scope.message = "";
            $scope.orgBo = {};
            var url = "<c:url value="/general/tab/create/${tab.id}"/>";
            $http.get(url)
                    .success(function (response) {
                        if (response.success) {
                            $scope.orgBo = response.data;
                        }
                        else {
                            bootbox.alert(response.message);
                        }
                    }).error(function (response) {
                bootbox.alert(response);
            });
        }
        buttonHeader.editInit($scope,$http,$window,$timeout, Upload,"/general");
    });
</c:forEach>
    app.run([
        'bootstrap3ElementModifier',
        function (bootstrap3ElementModifier) {
            bootstrap3ElementModifier.enableValidationStateIcons(true);
        }]);
</script>
</html>