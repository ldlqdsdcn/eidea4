<%--
  Created by IntelliJ IDEA.
  User: 刘大磊
  Date: 2016/12/7
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/inc/taglib.jsp" %>
<html>
<head>
    <title>查询条件维护</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file="/inc/inc_ang_js_css.jsp" %>
</head>
<body>
<div ng-app='myApp' ng-view class="container-fluid"></div>
<jsp:include page="/common/searchPage">
    <jsp:param name="uri" value="${uri}"/>
</jsp:include>
</body>

<script type="text/javascript">
    var app = angular.module('myApp', ['ngRoute', 'ui.bootstrap', 'jcs-autoValidate'])
            .config(['$routeProvider', function ($routeProvider) {
                $routeProvider
                        .when('/list', {templateUrl: '<c:url value="/core/search/list.tpl.jsp"/>'})
                        .when('/edit', {templateUrl: '<c:url value="/core/search/edit.tpl.jsp"/>'})
                        .otherwise({redirectTo: '/list'});
            }]);
    app.controller('listCtrl', function ($scope, $http) {
        $scope.allList = [];
        $scope.currentList = [];
        $scope.delFlag = false;
        $scope.isLoading = true;
        $scope.canDel=PrivilegeService.hasPrivilege('search:delete');
        $scope.canAdd=PrivilegeService.hasPrivilege('search:add');
        $http.get("<c:url value="/core/search/list"/>")
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
            $scope.currentList.length = 0;
            $scope.pageChanged();
        };
        $scope.pageChanged = function (delF) {
            var bgn = ($scope.bigCurrentPage - 1) * $scope.itemsPerPage;
            var end = bgn + $scope.itemsPerPage;
            $scope.currentList.length = 0;
            if (delF == null) {
                delF = false;
            }
            for (var i = bgn; i < end && i < $scope.allList.length; i++) {
                var item = $scope.allList[i];
                item.delFlag = delF;
                $scope.currentList.push(item);
            }
            $scope.isLoading = false;
        }
        $scope.canDelete = function () {
            for (var i = 0; i < $scope.currentList.length; i++) {
                if ($scope.currentList[i].delFlag) {
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
                        for (var i = 0; i < $scope.currentList.length; i++) {
                            if ($scope.currentList[i].delFlag) {
                                ids.push($scope.currentList[i].id);
                            }
                        }
                        $http.post("<c:url value="/core/search/deletes"/>", ids).success(function (data) {
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
        $scope.showTypeList = [];
        $scope.relOper = [];
        $scope.searchPageFieldInput = [];
        $scope.searchDataType = [];
        $scope.searchBo = {};
        $scope.delF = false;
        $scope.canAdd=PrivilegeService.hasPrivilege('search:add');
        $scope.canSave=false;
        $http.get("<c:url value="/core/search/getSelectList"/> ")
                .success(function (response) {
                    if (response.success) {
                        var selectList = $.parseJSON(response.data);
                        $scope.showTypeList = selectList.searchPageType;
                        $scope.relOper = selectList.relOper;
                        $scope.searchPageFieldInput = selectList.searchPageFieldInput;
                        $scope.searchDataType = selectList.searchDataType;
                    }
                    else {
                        bootbox.alert(response.message);
                    }
                });

        var url = "<c:url value="/core/search/get"/>";
        if ($routeParams.id != null) {
            url = "<c:url value="/core/search/get"/>" + "?id=" + $routeParams.id;
        }
        $http.get(url)
                .success(function (response) {
                    if (response.success) {
                        $scope.searchBo = response.data;
                        $scope.canSave=(PrivilegeService.hasPrivilege('search:add')&&$scope.searchBo.id==null)||PrivilegeService.hasPrivilege('search:update');
                    }
                    else {
                        bootbox.alert(response.message);
                    }
                }).error(function (response) {
            bootbox.alert(response);
        });
        $scope.save = function () {
            if ($scope.searchForm.$valid) {
                var postUrl = '<c:url value="/core/search/saveForUpdated"/>';
                if ($scope.searchBo.id == null) {
                    postUrl = '<c:url value="/core/search/saveForCreated"/>';
                }
                $http.post(postUrl, $scope.searchBo).success(function (data) {
                    if (data.success) {

                        bootbox.alert("<eidea:label key="base.save.success"/>");
                        console.log("id=" + data.data.id);
                        $scope.searchBo = data.data;
                    }
                    else {
                        bootbox.alert(data.message);
                    }

                });
            }
        }
        $scope.addOneColumn = function () {
            $http.get("<c:url value="/core/search/addOneColumn"/>")
                    .success(function (response) {
                        if (response.success) {
                            $scope.searchBo.searchColumnBoList.push(response.data);
                        }
                        else {
                            bootbox.alert(response.message);
                        }
                    }).error(function (response) {
                bootbox.alert(response);
            });

        }
        $scope.selectAll = function () {
            //$scope.delFlag=!$scope.delFlag;
            if ($scope.searchBo.searchColumnBoList != null) {
                for (var i = 0; i < $scope.searchBo.searchColumnBoList.length; i++) {
                    var item = $scope.searchBo.searchColumnBoList[i];
                    item.delFlag = $scope.delFlag;
                }
            }

        }
        $scope.deleteColumns = function () {
            if ($scope.searchBo.searchColumnBoList != null) {
                for (var i = $scope.searchBo.searchColumnBoList.length - 1; i >= 0; i--) {
                    var item = $scope.searchBo.searchColumnBoList[i];
                    if (item.delFlag) {
                        $scope.searchBo.searchColumnBoList.splice(i, 1);
                    }
                }
            }
        }
        $scope.canDelete = function () {
            if ($scope.searchBo.searchColumnBoList != null)
                for (var i = 0; i < $scope.searchBo.searchColumnBoList.length; i++) {
                    if ($scope.searchBo.searchColumnBoList[i].delFlag) {
                        return true;
                    }
                }
            return false;
        }
    });
    app.run([
        'bootstrap3ElementModifier',
        function (bootstrap3ElementModifier) {
            bootstrap3ElementModifier.enableValidationStateIcons(true);
        }]);
</script>
</html>
