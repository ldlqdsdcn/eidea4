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
    <title><%--实体设置--%><eidea:label key="client.title"/></title>
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
                        .when('/list', {templateUrl: '<c:url value="/base/client/list.tpl.jsp"/>'})
                        .when('/edit', {templateUrl: '<c:url value="/base/client/edit.tpl.jsp"/>'})
                        .otherwise({redirectTo: '/list'});
            }]);
    app.controller('listCtrl', function ($scope,$rootScope,$http) {
        $scope.modelList = [];
        $scope.delFlag = false;
        $scope.isLoading = true;
        $scope.canDel=PrivilegeService.hasPrivilege('delete');
        $scope.canAdd=PrivilegeService.hasPrivilege('add');
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
            $http.post("<c:url value="/base/client/list"/>", $scope.queryParams)
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
                        $http.post("<c:url value="/base/client/deletes"/>", param).success(function (data) {
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
    app.controller('editCtrl', function ($scope,$rootScope, $http, $routeParams) {
        $scope.canAdd=PrivilegeService.hasPrivilege('add');
        var url = "<c:url value="/base/client/create"/>";
        if ($routeParams.id != null) {
            url = "<c:url value="/base/client/get"/>" + "?id=" + $routeParams.id;
        }
        $http.get(url)
                .success(function (response) {
                    if (response.success) {
                        $scope.clientBo = response.data;
                        $scope.canSave=(PrivilegeService.hasPrivilege('add')&&$scope.clientBo.id==null)||PrivilegeService.hasPrivilege('update');
                    }
                    else {
                        bootbox.alert(response.message);
                    }
                }).error(function (response) {
            bootbox.alert(response);
        });
        $scope.save = function () {
            var reg=/^[a-zA-Z0-9]+$/;
            if (!reg.test($scope.clientBo.no)){
                $scope.message="<eidea:label key="client.msg.client_no_error"/>";
                return false;
            }
            $scope.message="";
            if ($scope.editForm.$valid) {
                var postUrl = '<c:url value="/base/client/saveForUpdated"/>';
                if ($scope.clientBo.id == null) {
                    postUrl = '<c:url value="/base/client/saveForCreated"/>';
                }
                $http.post(postUrl, $scope.clientBo).success(function (data) {
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
        $scope.create = function () {
            var url = "<c:url value="/base/client/create"/>";
            $http.get(url)
                    .success(function (response) {
                        if (response.success) {
                            $scope.clientBo = response.data;
                            $scope.canSave=(PrivilegeService.hasPrivilege('add')&&$scope.clientBo.id==null)||PrivilegeService.hasPrivilege('update');
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
