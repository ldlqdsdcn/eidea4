<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/inc/taglib.jsp" %>
<html>
<head>
    <title>访问目录</title>
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
                        .when('/list', {templateUrl: '<c:url value="/base/directory/list.tpl.jsp"/>'})
                        .when('/edit', {templateUrl: '<c:url value="/base/directory/edit.tpl.jsp"/>'})
                        .otherwise({redirectTo: '/list'});
            }]);
    app.controller('listCtrl', function ($scope, $http) {
        $scope.allList = [];
        $scope.modelList = [];
        $scope.delFlag = false;
        $scope.canDel=PrivilegeService.hasPrivilege('directory:delete');
        $scope.canAdd=PrivilegeService.hasPrivilege('directory:add');
        $http.get("<c:url value="/base/directory/list"/>")
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
                message: "<eidea:message key="modile.deleteselect.check"/>",
                buttons: {
                    confirm: {
                        label: '<eidea:label key="module.name.checktrue"/>',
                        className: 'btn-success'
                    },
                    cancel: {
                        label: '<eidea:label key="module.name.checkfalse"/>',
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
                        $http.post("<c:url value="/base/directory/deletes"/>", ids).success(function (data) {
                            if (data.success) {
                                bootbox.alert("<eidea:message key="module.deleted.success"/>");
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
        //可现实分页item数量
        $scope.maxSize =${pagingSettingResult.pagingButtonSize};
        //每页现实记录数
        $scope.itemsPerPage =${pagingSettingResult.perPageSize};
        //当前页
        $scope.bigCurrentPage = 1;
        //记录数
        $scope.bigTotalItems = 0;
    });
    app.controller('editCtrl', function ($scope, $http, $routeParams) {
        $scope.message = '';
        $scope.directoryBo = {};
        $scope.canAdd=PrivilegeService.hasPrivilege('directory:add');
        $scope.canSave=PrivilegeService.hasPrivilege('directory:update');;
        if ($routeParams.id != null) {
            url = "<c:url value="/base/directory/get"/>" + "?id=" + $routeParams.id;
            $http.get(url)
                    .success(function (response) {
                        if (response.success) {
                            $scope.directoryBo = response.data;
                            $scope.canSave=(PrivilegeService.hasPrivilege('directory:add')&&$scope.directoryBo.id==null)||PrivilegeService.hasPrivilege('directory:update');
                        }
                        else {
                            bootbox.alert(response.message);
                        }
                    }).error(function (response) {
                bootbox.alert(response);
            });
        }
        $scope.save = function () {
            if ($scope.editForm.$valid) {
                var postUrl = '<c:url value="/base/directory/saveForUpdated"/>';
                if ($scope.directoryBo.id==null) {
                    postUrl = '<c:url value="/base/directory/saveForCreated"/>';
                }

                $http.post(postUrl, $scope.directoryBo).success(function (data) {
                    if (data.success) {
                        $scope.message = "<eidea:label key="base.save.success"/>";
                        //bootbox.alert("保存成功");
                        $scope.directoryBo = data.data;
                    }
                    else {
                        $scope.message = data.message;
                    }
                });
            }
        }
        $scope.create = function () {
            $scope.message = "";
            $scope.directoryBo = {};
            var url = "<c:url value="/base/directory/create"/>";
            $http.get(url)
                    .success(function (response) {
                        if (response.success) {
                            $scope.directoryBo = response.data;
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
