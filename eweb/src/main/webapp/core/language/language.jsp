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
    <title><%--语言维护--%><eidea:label key="language.title"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file="/inc/inc_ang_js_css.jsp" %>
</head>
<body>
<div ng-view class="content" ng-app='myApp'></div>
<jsp:include page="/common/searchPage">
    <jsp:param name="uri" value="${uri}"/>
</jsp:include>
</body>

<script type="text/javascript">
    var app = angular.module('myApp', ['ngRoute', 'ui.bootstrap', 'jcs-autoValidate'])
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/list', {templateUrl: '<c:url value="/core/language/list.tpl.jsp"/>'})
                .when('/edit', {templateUrl: '<c:url value="/core/language/edit.tpl.jsp"/>'})
                .otherwise({redirectTo: '/list'});
        }]);
    app.controller('listCtrl', function ($scope, $http) {
        $scope.allList = [];
        $scope.modelList = [];
        $scope.delFlag = false;
        $scope.canDel = PrivilegeService.hasPrivilege('delete');
        $scope.canAdd = PrivilegeService.hasPrivilege('add');
        $http.get("<c:url value="/core/language/list"/>")
            .success(function (response) {
                if (response.success) {
                    $scope.updateList(response.data);
                }
                else {
//                        bootbox.alert(response.message);
                    bootbox.alert({
                        buttons: {
                            ok: {
                                label: '<i class="fa fa-close" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.closed"/>',
                                className: 'btn-primary'
                            }
                        },
                        message: response.message,
                    });
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
                        label: '<i class="fa fa-check" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.checktrue"/>',
                        className: 'btn-primary'
                    },
                    cancel: {
                        label: '<i class="fa fa-close" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.checkfalse"/>',
                        className: 'btn-primary'
                    }
                },
                callback: function (result) {
                    if (result) {
                        var ids = [];
                        for (var i = 0; i < $scope.modelList.length; i++) {
                            if ($scope.modelList[i].delFlag) {
                                ids.push($scope.modelList[i].code);
                            }
                        }
                        $http.post("<c:url value="/core/language/deletes"/>", ids).success(function (data) {
                            if (data.success) {
                                <%--bootbox.alert("<eidea:message key="module.deleted.success"/>");--%>
                                bootbox.alert({
                                    buttons: {
                                        ok: {
                                            label: '<i class="fa fa-close" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.closed"/>',
                                            className: 'btn-primary'
                                        }
                                    },
                                    message: '<eidea:message key="module.deleted.success"/>',
                                });
                                $scope.updateList(data.data);
                            }
                            else {
//                                bootbox.alert(data.message);
                                bootbox.alert({
                                    buttons: {
                                        ok: {
                                            label: '<i class="fa fa-close" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.closed"/>',
                                            className: 'btn-primary'
                                        }
                                    },
                                    message: data.message,
                                });
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
        $scope.languageBo = {};
        $scope.canAdd = PrivilegeService.hasPrivilege('add');
        $scope.canSave = false;
        var url = "<c:url value="/core/language/create"/>";
        if ($routeParams.code != null) {
            url = "<c:url value="/core/language/get"/>" + "?code=" + $routeParams.code;
        }
        $http.get(url)
            .success(function (response) {
                if (response.success) {
                    $scope.languageBo = response.data;
                    $scope.canSave = (PrivilegeService.hasPrivilege('add') && $scope.languageBo.created) || PrivilegeService.hasPrivilege('update');
                }
                else {
                    bootbox.alert({
                        buttons: {
                            ok: {
                                label: '<i class="fa fa-close" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.closed"/>',
                                className: 'btn-primary'
                            }
                        },
                        message: response.message,
                    });
                }
            }).error(function (response) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '<i class="fa fa-close" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.closed"/>',
                        className: 'btn-primary'
                    }
                },
                message: response,
            });
        });
        $scope.save = function () {
            var code = /^[a-z]{2}_[A-Z]{2}$/;
            if (!code.test($scope.languageBo.code)) {
                $scope.message = "<eidea:label key="language.code.pattern.error"/>";
                return false;
            }
            var code = /^[a-z]{2}$/;
            if (!code.test($scope.languageBo.languageIso)) {
                $scope.message = "<eidea:label key="language.iso.pattern.error"/>";
                return false;
            }
            var code = /^[A-Z]{2}$/;
            if (!code.test($scope.languageBo.countryCode)) {
                $scope.message = "<eidea:label key="language.country.pattern.error"/>";
                return false;
            }

            if ($scope.editForm.$valid) {
                var postUrl = '<c:url value="/core/language/saveForUpdated"/>';
                if ($scope.languageBo.created) {
                    postUrl = '<c:url value="/core/language/saveForCreated"/>';
                }
                $http.post(postUrl, $scope.languageBo).success(function (data) {
                    if (data.success) {
                        $scope.message = "<eidea:label key="success.save"/>";
                        //bootbox.alert("保存成功");
                        $scope.languageBo = data.data;
                    }
                    else {
                        $scope.message = data.message;
                    }


                });
            }
        }
        $scope.create = function () {
            $scope.message = "";
            $scope.languageBo = {};
            var url = "<c:url value="/core/language/create"/>";
            $http.get(url)
                .success(function (response) {
                    if (response.success) {
                        $scope.languageBo = response.data;
                    }
                    else {
//                            bootbox.alert(response.message);
                        bootbox.alert({
                            buttons: {
                                ok: {
                                    label: '<i class="fa fa-close" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.closed"/>',
                                    className: 'btn-primary'
                                }
                            },
                            message: response.message,
                        });
                    }
                }).error(function (response) {
//                        bootbox.alert(response);
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '<i class="fa fa-close" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.closed"/>',
                            className: 'btn-primary'
                        }
                    },
                    message: response,
                });
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
