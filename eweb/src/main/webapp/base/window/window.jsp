<%--
  Created by 刘大磊.
  Date: 2017-05-02 15:41:30
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/inc/taglib.jsp" %>
<html>
<head>
    <title><%--窗体--%><eidea:label key="window.title"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file="/inc/inc_ang_js_css.jsp" %>
</head>
<body>
<div ng-app='myApp'  class="content" ui-view></div>
<jsp:include page="/common/searchPage">
    <jsp:param name="uri" value="${uri}"/>
</jsp:include>
</body>
<script type="text/javascript">
    <%--var app = angular.module('myApp', ['ngRoute', 'ui.bootstrap', 'jcs-autoValidate'])--%>
        <%--.config(['$routeProvider', function ($routeProvider) {--%>
            <%--$routeProvider--%>
                <%--.when('/list', {templateUrl: '<c:url value="/base/window/list.tpl.jsp"/>'})--%>
                <%--.when('/edit', {templateUrl: '<c:url value="/base/window/edit.tpl.jsp"/>'})--%>
                <%--.otherwise({redirectTo: '/list'});--%>
        <%--}]);--%>
    var app = angular.module('myApp',['ui.router','ui.bootstrap','jcs-autoValidate'])
        .config(['$stateProvider','$urlRouterProvider',function ($stateProvider,$urlRouterProvider) {
            $urlRouterProvider.otherwise('/list');
            $stateProvider
                .state('list', {
                    url:'/list',
                    templateUrl: '<c:url value="/base/window/list.tpl.jsp"/>'
                })
                .state('windowEdit', {
                    url: '/edit?id',
                    templateUrl: '<c:url value="/base/window/edit.tpl.jsp"/>'
                })
                .state('windowEdit.windowTrlList',{
                    url:'/windowTrllist',
                    templateUrl:'<c:url value="/base/windowTrl/list.tpl.jsp"/>'
                })
                .state('windowEdit.windowTrlEdit',{
                    url:'/windowTrlEdit?windowTrlId',
                    templateUrl:'<c:url value="/base/windowTrl/edit.tpl.jsp"/>'
                })
                .state('windowEdit.tablist',{
                    url:'/tablist',
                    templateUrl:'<c:url value="/base/tab/list.tpl.jsp"/> '
                })
                .state('windowEdit.edittab',{
                    url:'/edittab?tabid',
                    templateUrl:'<c:url value="/base/tab/edit.tpl.jsp"/> '
                })
                .state('windowEdit.edittab.listTabTrl',{
                    url:'/listTabTrl',
                    templateUrl:'<c:url value="/base/tabTrl/list.tpl.jsp"/> '
                })
                .state('windowEdit.edittab.editTabTrl',{
                    url:'/editTabTrl?tabTrlId',
                    templateUrl:'<c:url value="/base/tabTrl/edit.tpl.jsp"/>'
                })
        }]);
    app.controller('listCtrl', function ($scope,$http) {
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
            $scope.pageChanged = function () {
                $http.post("<c:url value="/base/window/list"/>", $scope.queryParams)
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
    app.controller('editCtrl', function ($scope, $http,$rootScope,$stateParams) {
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
        var date = new Date();
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
                    if ($scope.windowPo.id==null){
                        $scope.windowPo.created=date;
                        $scope.windowPo.updated=date;
                    }else{
                        $scope.windowPo.updated=date;
                    }
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
    app.controller('tabCtrl',function ($scope,$rootScope,$state) {
        $scope.windowEdit=function(){
            $rootScope.windowEditShow=true;
            $rootScope.tabListshow=false;
            $rootScope.windowTrlListShow=false;
            $rootScope.tabTrlBtnShow=false;
            $rootScope.tabTrlListShow=false;
        }
        $scope.tabList=function () {
            $rootScope.windowEditShow=false;
            $rootScope.tabListshow=true;
            $rootScope.tabEditShow=true;
            $rootScope.tabTrlBtnShow=false;
            $rootScope.tabTrlListShow=false;
            $state.go('windowEdit.tablist');
        }
        $scope.windowTrlList=function () {
            $rootScope.windowEditShow=false;
            $rootScope.tabListshow=false;
            $rootScope.tabTrlBtnShow=false;
            $rootScope.windowTrlListShow=true;
            $state.go('windowEdit.windowTrlList');
        }
        $scope.tabTrlList=function () {
            $rootScope.windowEditShow=false;
            $rootScope.tabListshow=false;
            $rootScope.tabEditShow=false;
            $rootScope.windowTrlListShow=false;
            $rootScope.tabTrlListShow=true;
            $state.go('windowEdit.edittab.listTabTrl');
        }
    });
    app.controller('listWindowTrlCtrl', function ($scope, $http,$stateParams,$state) {
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
        $scope.pageChanged = function () {
            $http.post("<c:url value="/base/windowTrl/windowTrlList"/>", $stateParams.id)
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
                        $http.post("<c:url value="/base/windowTrl/deletes"/>", param).success(function (data) {
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
        $scope.editWindowTrl=function(id){
            $state.go('windowEdit.windowTrlEdit',{windowTrlId:id})
        }
        $scope.createWindowTrl=function(){
            $state.go('windowEdit.windowTrlEdit',{windowTrlId:null})
        }
    });
    app.controller('editWindowTrlCtrl', function ($scope, $http, $stateParams,$state) {
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
        $scope.windowTrlPo = {};
        $scope.canAdd = PrivilegeService.hasPrivilege('add');
        var url = "<c:url value="/base/windowTrl/create"/>";
        if ($stateParams.windowTrlId != null) {
            url = "<c:url value="/base/windowTrl/get"/>" + "?id=" + $stateParams.windowTrlId;
        }
        $http.get(url)
            .success(function (response) {
                if (response.success) {
                    $scope.windowTrlPo = response.data;
                    $scope.canSave = (PrivilegeService.hasPrivilege('add') && $scope.windowTrlPo.id == null) || PrivilegeService.hasPrivilege('update');
                }
                else {
                    bootbox.alert(response.message);
                }
            }).error(function (response) {
            bootbox.alert(response);
        });
        $scope.save = function () {
            if ($scope.editForm.$valid) {
                var postUrl = '<c:url value="/base/windowTrl/saveForUpdated"/>';
                if ($scope.windowTrlPo.id == null) {
                    postUrl = '<c:url value="/base/windowTrl/saveForCreated"/>';
                }
                $http.post(postUrl, $scope.windowTrlPo).success(function (data) {
                    if (data.success) {
                        $scope.message = "<eidea:label key="base.save.success"/>";
                        $scope.windowTrlPo = data.data;
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
            $scope.windowTrlPo = {};
            var url = "<c:url value="/base/windowTrl/create"/>";
            $http.get(url)
                .success(function (response) {
                    if (response.success) {
                        $scope.windowTrlPo = response.data;
                        $scope.canSave = (PrivilegeService.hasPrivilege('add') && $scope.windowTrlPo.id == null) || PrivilegeService.hasPrivilege('update');
                    }
                    else {
                        bootbox.alert(response.message);
                    }
                }).error(function (response) {
                bootbox.alert(response);
            });
        }
        $scope.backWindowTrlList=function () {
            $state.go('windowEdit.windowTrlList');
        }
    });
    app.controller('listTabCtrl', function ($scope, $http,$stateParams,$rootScope,$state) {
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
        $scope.pageChanged = function () {
            $http.post("<c:url value="/base/tab/tablist"/>", $stateParams.id)
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
                        $http.post("<c:url value="/base/tab/deletes"/>", param).success(function (data) {
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
        $scope.editItem=function (id) {
            $rootScope.tabTrlBtnShow=true;
          $state.go('windowEdit.edittab',{tabid:id})
        }
        $scope.createItem=function () {
            $state.go('windowEdit.edittab',{tabid:null})
        }
    });
    app.controller('editTabCtrl', function ($scope, $http,$rootScope,$stateParams,$state) {
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
        $scope.tabPo = {};
        $scope.canAdd = PrivilegeService.hasPrivilege('add');
        var url = "<c:url value="/base/tab/create"/>";
        if ($stateParams.tabid != null) {
            url = "<c:url value="/base/tab/get"/>" + "?id=" + $stateParams.tabid;
        }
        $http.get(url)
            .success(function (response) {
                if (response.success) {
                    $scope.tabPo = response.data;
                    $scope.getTableList();
                    $scope.getTableColumnList($scope.tabPo.tableId);
                    $scope.canSave = (PrivilegeService.hasPrivilege('add') && $scope.tabPo.id == null) || PrivilegeService.hasPrivilege('update');
                }
                else {
                    bootbox.alert(response.message);
                }
            }).error(function (response) {
            bootbox.alert(response);
        });
        $scope.save = function () {
            if ($scope.editForm.$valid) {
                var postUrl = '<c:url value="/base/tab/saveForUpdated"/>';
                if ($scope.tabPo.id == null) {
                    postUrl = '<c:url value="/base/tab/saveForCreated"/>';
                }
                $http.post(postUrl, $scope.tabPo).success(function (data) {
                    if (data.success) {
                        $scope.message = "<eidea:label key="base.save.success"/>";
                        $scope.tabPo = data.data;
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
            $scope.tabPo = {};
            var url = "<c:url value="/base/tab/create"/>";
            $http.get(url)
                .success(function (response) {
                    if (response.success) {
                        $scope.tabPo = response.data;
                        $scope.getTableList();
                        $scope.canSave = (PrivilegeService.hasPrivilege('add') && $scope.tabPo.id == null) || PrivilegeService.hasPrivilege('update');
                    }
                    else {
                        bootbox.alert(response.message);
                    }
                }).error(function (response) {
                bootbox.alert(response);
            });
        };
        $scope.getTableList = function () {
            $http.get("<c:url value="/base/tab/getTablePoList"/> ").success(function (data) {
                if (data.success) {
                    $scope.tablePoList = data.data.data;
                }
            }).error(function () {
                bootbox.alert(data.message);
            })
        }
        $scope.getTableColumnList = function (id) {
            $http.post("<c:url value="/base/tab/getTableColumnList"/>", id).success(function (data) {
                if (data.success) {
                    $scope.tableColumnList = data.data;
                }
            }).error(function (data) {
                bootbox.alert(data.message);
            })
        }
        $scope.backItemList=function () {
            $state.go('windowEdit.tablist',{id:$rootScope.id});
        }
    });
    app.controller('listTabTrlCtrl', function ($scope, $http,$stateParams,$state) {
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
                $scope.modelList[i].delFlag=$scope.delFlag;
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
            $http.post("<c:url value="/base/tabTrl/tabTrlList"/>", $stateParams.tabid)
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
                        $scope.queryParams.init=true;
                        var param={"queryParams":$scope.queryParams,"ids":ids};
                        $http.post("<c:url value="/base/tabTrl/deletes"/>", param).success(function (data) {
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
        $scope.editTabTrl=function (id) {
            $state.go('windowEdit.edittab.editTabTrl',{tabTrlId:id});
        }
        $scope.createTabTrl=function () {
            $state.go('windowEdit.edittab.editTabTrl',{tabTrlId:null});
        }
    });
    app.controller('editTabTrlCtrl', function ($scope, $http, $stateParams,$state) {
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
        $scope.tabTrlPo = {};
        $scope.canAdd=PrivilegeService.hasPrivilege('add');
        var url = "<c:url value="/base/tabTrl/create"/>";
        if ($stateParams.tabTrlId != null) {
            url = "<c:url value="/base/tabTrl/get"/>" + "?id=" + $stateParams.tabTrlId;
        }
        $http.get(url)
            .success(function (response) {
                if (response.success) {
                    $scope.tabTrlPo = response.data;
                    $scope.canSave=(PrivilegeService.hasPrivilege('add')&&$scope.tabTrlPo.id==null)||PrivilegeService.hasPrivilege('update');
                }
                else {
                    bootbox.alert(response.message);
                }
            }).error(function (response) {
            bootbox.alert(response);
        });
        $scope.save = function () {
            if ($scope.editForm.$valid) {
                var postUrl = '<c:url value="/base/tabTrl/saveForUpdated"/>';
                if ($scope.tabTrlPo.id == null) {
                    postUrl = '<c:url value="/base/tabTrl/saveForCreated"/>';
                }
                $http.post(postUrl, $scope.tabTrlPo).success(function (data) {
                    if (data.success) {
                        $scope.message = "<eidea:label key="base.save.success"/>";
                        $scope.tabTrlPo = data.data;
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
            $scope.tabTrlPo = {};
            var url = "<c:url value="/base/tabTrl/create"/>";
            $http.get(url)
                .success(function (response) {
                    if (response.success) {
                        $scope.tabTrlPo = response.data;
                        $scope.canSave=(PrivilegeService.hasPrivilege('add')&&$scope.tabTrlPo.id==null)||PrivilegeService.hasPrivilege('update');
                    }
                    else {
                        bootbox.alert(response.message);
                    }
                }).error(function (response) {
                bootbox.alert(response);
            });
        }
        $scope.backTabTrlList=function () {
            $state.go('windowEdit.edittab.listTabTrl');
        }
    });

    app.run([
        'bootstrap3ElementModifier',
        function (bootstrap3ElementModifier) {
            bootstrap3ElementModifier.enableValidationStateIcons(true);
        }]);
</script>
</html>
