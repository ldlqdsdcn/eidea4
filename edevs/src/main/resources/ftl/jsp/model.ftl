<%--
  Created by ${user}.
  Date: ${datetime}
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/inc/taglib.jsp" %>
<html>
<head>
    <title><%--${modelName}--%><eidea:label key="${model?uncap_first}.title"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file="/inc/inc_ang_js_css.jsp" %>
</head>
<body>
<div ng-app='myApp' ng-view class="content"></div>
<jsp:include page="/common/searchPage">
    <jsp:param name="uri" value="${r'${uri}'}"/>
</jsp:include>
</body>
<script type="text/javascript">
    var app = angular.module('myApp', ['ngRoute', 'ui.bootstrap', 'jcs-autoValidate'])
            .config(['$routeProvider', function ($routeProvider) {
                $routeProvider
                        .when('/list', {templateUrl: '<c:url value="/${module}/${model?uncap_first}/list.tpl.jsp"/>'})
                        .when('/edit', {templateUrl: '<c:url value="/${module}/${model?uncap_first}/edit.tpl.jsp"/>'})
                        .otherwise({redirectTo: '/list'});
            }]);
    <#if pagingByDb>
        <#include "pagingByDBController.ftl"/>
    <#else>
        <#include "normalController.ftl"/>
    </#if>

    app.controller('editCtrl', function ($scope, $http, $routeParams) {
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

        $scope.message = '';
        $scope.${model?uncap_first}Po = {};
        $scope.canAdd=PrivilegeService.hasPrivilege('add');
        var url = "<c:url value="/${module}/${model?uncap_first}/create"/>";
        if ($routeParams.${pkProp} != null) {
            url = "<c:url value="/${module}/${model?uncap_first}/get"/>" + "?${pkProp}=" + $routeParams.${pkProp};
        }
        $http.get(url)
                .success(function (response) {
                    if (response.success) {
                        $scope.${model?uncap_first}Po = response.data;
                        $scope.canSave=(PrivilegeService.hasPrivilege('add')&&$scope.${model?uncap_first}Po.${pkProp}==null)||PrivilegeService.hasPrivilege('update');
                    }
                    else {
                        bootbox.alert(response.message);
                    }
                }).error(function (response) {
            bootbox.alert(response);
        });
        $scope.save = function () {
            if ($scope.editForm.$valid) {
                var postUrl = '<c:url value="/${module}/${model?uncap_first}/saveForUpdated"/>';
                if ($scope.${model?uncap_first}Po.${pkProp} == null) {
                    postUrl = '<c:url value="/${module}/${model?uncap_first}/saveForCreated"/>';
                }
                $http.post(postUrl, $scope.${model?uncap_first}Po).success(function (data) {
                    if (data.success) {
                        $scope.message = "<eidea:label key="base.save.success"/>";
                        $scope.${model?uncap_first}Po = data.data;
                    }
                    else {
                        $scope.message = data.message;
                        $scope.errors=data.data;
                    }
                }).error(function (data, status, headers, config) {
                    alert(JSON.stringify(data));
                });
            }
        }
        $scope.create = function () {
            $scope.message = "";
            $scope.${model?uncap_first}Po = {};
            var url = "<c:url value="/${module}/${model?uncap_first}/create"/>";
            $http.get(url)
                    .success(function (response) {
                        if (response.success) {
                            $scope.${model?uncap_first}Po = response.data;
                            $scope.canSave=(PrivilegeService.hasPrivilege('add')&&$scope.${model?uncap_first}Po.${pkProp}==null)||PrivilegeService.hasPrivilege('update');
                        }
                        else {
                            bootbox.alert(response.message);
                        }
                    }).error(function (response) {
                bootbox.alert(response);
            });
        }

    });
    app.run([
        'bootstrap3ElementModifier',
        function (bootstrap3ElementModifier) {
            bootstrap3ElementModifier.enableValidationStateIcons(true);
        }]);
</script>
</html>
