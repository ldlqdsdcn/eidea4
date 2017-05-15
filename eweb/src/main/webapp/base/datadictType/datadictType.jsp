<%--
  Created by 刘大磊.
  Date: 2017-04-26 15:54:01
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/inc/taglib.jsp" %>
<html>
<head>
    <title><%--数据字典类型--%><eidea:label key="datadictType.title"/></title>
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
                .when('/list', {templateUrl: '<c:url value="/base/datadictType/list.tpl.jsp"/>'})
                .when('/edit', {templateUrl: '<c:url value="/base/datadictType/edit.tpl.jsp"/>'})
                .when('/editDetail', {templateUrl: '<c:url value="/base/datadict/edit.tpl.jsp"/>'})
                .otherwise({redirectTo: '/list'});
        }]);
    app.controller('listCtrl', function ($scope, $http,$routeParams) {
        $scope.modelList = [];
        $scope.delFlag = false;
        $scope.isLoading = true;
        $scope.canDel = PrivilegeService.hasPrivilege('delete');
        $scope.canAdd = PrivilegeService.hasPrivilege('add');
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
            if ($routeParams.id == null) {
                $scope.pageChanged = function () {
                    $http.post("<c:url value="/base/datadictType/list"/>", $scope.queryParams)
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
            } else {
                $scope.pageChanged = function () {
                    var url = "<c:url value="/base/datadictType/get"/>" + "?id=" + $routeParams.id;
                    $http.get(url).success(function (response) {
                        if (response.success) {
                            $http.post("<c:url value="/base/datadict/detaillist"/>", response.data.value)
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
                    }).error(function (response) {
                        bootbox.alert(response);
                    });
                }
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

                        if ($routeParams.id == null) {
                            url = "<c:url value="/base/datadictType/deletes"/>";
                        } else {
                            url = "<c:url value="/base/datadict/deletes"/>";
                        }
                        $http.post(url, param).success(function (data) {
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
    app.controller('editCtrl', function ($scope, $rootScope, $http, $routeParams) {
        $rootScope.show = true;
        $scope.message = '';
        $scope.datadictTypePo = {};
        $scope.canAdd = PrivilegeService.hasPrivilege('add');
        var url = "<c:url value="/base/datadictType/create"/>";
        if ($routeParams.id != null) {
            url = "<c:url value="/base/datadictType/get"/>" + "?id=" + $routeParams.id;
        }
        $http.get(url)
            .success(function (response) {
                if (response.success) {
                    $scope.datadictTypePo = response.data;
                    $scope.canSave = (PrivilegeService.hasPrivilege('add') && $scope.datadictTypePo.id == null) || PrivilegeService.hasPrivilege('update');
                }
                else {
                    bootbox.alert(response.message);
                }
            }).error(function (response) {
            bootbox.alert(response);
        });
        $scope.save = function () {
            if ($scope.editForm.$valid) {
                $scope.message="";
                var postUrl = '<c:url value="/base/datadictType/saveForUpdated"/>';
                if ($scope.datadictTypePo.id == null) {
                    postUrl = '<c:url value="/base/datadictType/saveForCreated"/>';
                }
                $http.post(postUrl, $scope.datadictTypePo).success(function (data) {
                    if (data.success) {
                        $scope.message = "<eidea:label key="base.save.success"/>";
                        $scope.datadictTypePo = data.data;

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
            $scope.datadictTypePo = {};
            var url = "<c:url value="/base/datadictType/create"/>";
            $http.get(url)
                .success(function (response) {
                    if (response.success) {
                        $scope.datadictTypePo = response.data;
                        $scope.canSave = (PrivilegeService.hasPrivilege('add') && $scope.datadictTypePo.id == null) || PrivilegeService.hasPrivilege('update');
                    }
                    else {
                        bootbox.alert(response.message);
                    }
                }).error(function (response) {
                bootbox.alert(response);
            });
        }
    });
    app.controller('tabCtrl', function ($scope, $rootScope) {
        $scope.showDatadict = function () {
            $rootScope.show = true;
        }
        $scope.showDetail = function () {
            $rootScope.show = false;
        }
    });
    //编辑Item
    app.controller('editDetailCtrl', function ($scope, $http, $routeParams) {
        $scope.message = '';
        $scope.datadictPo = {};
        $scope.canAdd = PrivilegeService.hasPrivilege('add');
        var url = "<c:url value="/base/datadict/create"/>";
        if ($routeParams.id != null) {
            url = "<c:url value="/base/datadict/get"/>" + "?id=" + $routeParams.id;
        }
        $http.get(url)
            .success(function (response) {
                if (response.success) {
                    $scope.datadictPo = response.data;
                    $scope.getDatadictTypeList();
                    $scope.canSave = (PrivilegeService.hasPrivilege('add') && $scope.datadictPo.id == null) || PrivilegeService.hasPrivilege('update');
                }
                else {
                    bootbox.alert(response.message);
                }
            }).error(function (response) {
            bootbox.alert(response);
        });
        $scope.save = function () {
            if ($scope.editForm.$valid) {
                var postUrl = '<c:url value="/base/datadict/saveForUpdated"/>';
                if ($scope.datadictPo.id == null) {
                    postUrl = '<c:url value="/base/datadict/saveForCreated"/>';
                }
                $http.post(postUrl, $scope.datadictPo).success(function (data) {
                    if (data.success) {
                        $scope.message = "<eidea:label key="base.save.success"/>";
                        $scope.datadictPo = data.data;
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
            $scope.datadictPo = {};
            var url = "<c:url value="/base/datadict/create"/>";
            $http.get(url)
                .success(function (response) {
                    if (response.success) {
                        $scope.datadictPo = response.data;
                        $scope.getDatadictTypeList();
                        $scope.canSave = (PrivilegeService.hasPrivilege('add') && $scope.datadictPo.id == null) || PrivilegeService.hasPrivilege('update');
                    }
                    else {
                        bootbox.alert(response.message);
                    }
                }).error(function (response) {
                bootbox.alert(response);
            });
        };
//获取DatadictType列表
        $scope.getDatadictTypeList = function () {
            $http.post("<c:url value="/base/datadict/getDatadictTypeList"/> ").success(function (data) {
                if (data.success) {
                    $scope.datadictTypeList = data.data;
                }
            }).error(function (data) {
                bootbox.alert(data.message);
            })
        };

    });
    app.run([
        'bootstrap3ElementModifier',
        function (bootstrap3ElementModifier) {
            bootstrap3ElementModifier.enableValidationStateIcons(true);
        }]);
</script>
</html>
