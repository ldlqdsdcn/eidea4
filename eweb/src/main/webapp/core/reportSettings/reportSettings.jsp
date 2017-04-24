<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/inc/taglib.jsp" %>
<html>
<head>
    <title>消息设置</title>
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
                        .when('/list', {templateUrl: '<c:url value="/core/reportSettings/list.tpl.jsp"/>'})
                        .when('/edit', {templateUrl: '<c:url value="/core/reportSettings/edit.tpl.jsp"/>'})
                        .otherwise({redirectTo: '/list'});
            }]);
    app.controller('listCtrl', function ($scope, $http) {
        $scope.allList = [];
        $scope.modelList = [];
        $scope.delFlag = false;
        $scope.canDel = PrivilegeService.hasPrivilege('delete');
        $scope.canAdd = PrivilegeService.hasPrivilege('add');
        $http.get("<c:url value="/core/reportSettings/list"/>")
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
                if(item.init!='Y')
                {
                    item.delFlag = delF;

                }
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
                                ids.push($scope.modelList[i].key);
                            }
                        }
                        $http.post("<c:url value="/core/reportSettings/deletes"/>", ids).success(function (data) {
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
        $scope.message= '';
        $scope.messageBo = {};
        $scope.canAdd = PrivilegeService.hasPrivilege('add');
        $scope.canSave = PrivilegeService.hasPrivilege('update');
        var url = "<c:url value="/core/reportSettings/create"/>";
        if ($routeParams.key != null) {
            url = "<c:url value="/core/reportSettings/get"/>" + "?key=" + $routeParams.key;
        }
        $http.get(url)
                .success(function (response) {
                    if (response.success) {
                        $scope.reportBo = response.data;
                        $scope.canSave = (PrivilegeService.hasPrivilege('add') && $scope.reportBo.created) || PrivilegeService.hasPrivilege('update');
                    }
                    else {
                        bootbox.alert(response.message);
                    }
                }).error(function (response) {
            bootbox.alert(response);
        });
        $scope.save = function () {
            if ($scope.editForm.$valid) {
                var postUrl = '<c:url value="/core/reportSettings/saveForUpdated"/>';
                if ($scope.reportBo.created) {
                    postUrl = '<c:url value="/core/reportSettings/saveForCreated"/>';
                }
                $http.post(postUrl, $scope.reportBo).success(function (data) {
                    if (data.success) {
                        $scope.message = "<eidea:label key="base.save.success"/>";
                        $scope.reportBo = data.data;
                    }
                    else {
                        $scope.message = data.message;
                    }
                });
            }
        }
        $scope.create = function () {
            $scope.message = "";
            $scope.reportBo = {};
            var url = "<c:url value="/core/reportSettings/create"/>";
            $http.get(url)
                    .success(function (response) {
                        if (response.success) {
                            $scope.reportBo = response.data;
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
