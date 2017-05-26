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
    <%@include file="/inc/inc_ang_js_css.jsp" %>
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
            .config(['$stateProvider',function ($stateProvider) {
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
                            url:'/edittab?tabid&columnId',
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
                        .state('windowEdit.edittab.listField',{
                            url:'/listField',
                            templateUrl:'<c:url value="/base/field/list.tpl.jsp"/>'
                        })
                        .state('windowEdit.edittab.editField',{
                            url:'/editField?field',
                            templateUrl:'<c:url value="/base/field/edit.tpl.jsp"/> '
                        })
                        .state('windowEdit.edittab.editField.listFieldTrl',{
                            url:'/listFieldTrl',
                            templateUrl:'<c:url value="/base/fieldTrl/list.tpl.jsp"/>'
                        })
                        .state('windowEdit.edittab.editField.editFieldTrl',{
                            url:'/editField?fieldTrlId',
                            templateUrl:'<c:url value="/base/fieldTrl/edit.tpl.jsp"/> '
                        })
                        .state('windowEdit.edittab.editField.listFieldValidator',{
                            url:'/listFieldValidator',
                            templateUrl:'<c:url value="/base/fieldValidator/list.tpl.jsp"/> '
                        })
                        .state('windowEdit.edittab.editField.editFieldValidator',{
                            url:'/editFieldValidator?fieldValidatorId',
                            templateUrl:'<c:url value="/base/fieldValidator/edit.tpl.jsp"/> '
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
    app.controller('tabCtrl',function ($scope,$rootScope,$state) {
        $scope.windowEdit=function(){
            $rootScope.windowEditShow=true;
            $rootScope.windowTrlListShow=false;
            $rootScope.tabListshow=false;
            $rootScope.tabTrlListShow=false;
            $rootScope.tabTrlBtnShow=false;
            $rootScope.fieldListShow=false;
            $rootScope.fieldTrlListShow=false;
            $rootScope.fieldBtnShow=false;
            $rootScope.fieldValidatorBtnShow=false;
            $rootScope.fieldTrlBtnShow=false;
            $rootScope.fieldValidatorListShow=false;

        }
        $scope.windowTrlList=function () {
            $rootScope.windowEditShow=false;
            $rootScope.tabListshow=false;
            $rootScope.tabTrlBtnShow=false;
            $rootScope.fieldBtnShow=false;
            $rootScope.fieldTrlBtnShow=false;
            $rootScope.windowTrlListShow=true;
            $rootScope.fieldValidatorBtnShow=false
            $rootScope.fieldValidatorListShow=false;
            $rootScope.fieldListShow=false;
            $state.go('windowEdit.windowTrlList');
        }
        $scope.tabList=function () {
            $rootScope.windowEditShow=false;
            $rootScope.tabListshow=true;
            $rootScope.tabEditShow=true;
            $rootScope.tabTrlBtnShow=false;
            $rootScope.fieldBtnShow=false;
            $rootScope.fieldTrlBtnShow=false;
            $rootScope.tabTrlListShow=false;
            $rootScope.fieldValidatorBtnShow=false
            $rootScope.fieldValidatorListShow=false;
            $rootScope.fieldListShow=false;
            $state.go('windowEdit.tablist');
        }
        $scope.tabTrlList=function () {
            $rootScope.windowEditShow=false;
            $rootScope.tabListshow=false;
            $rootScope.tabEditShow=false;
            $rootScope.windowTrlListShow=false;
            $rootScope.tabTrlListShow=true;
            $rootScope.fieldListShow=false;
            $rootScope.fieldTrlBtnShow=false;
            $rootScope.fieldValidatorBtnShow=false
            $rootScope.fieldValidatorListShow=false;
            $rootScope.fieldTrlListShow=false;
            $state.go('windowEdit.edittab.listTabTrl');
        }
        $scope.fieldList=function () {
            $rootScope.windowEditShow=false;
            $rootScope.tabListshow=false;
            $rootScope.tabEditShow=false;
            $rootScope.windowTrlListShow=false;
            $rootScope.tabTrlListShow=false;
            $rootScope.fieldListShow=true;
            $rootScope.fieldTrlBtnShow=false;
            $rootScope.fieldValidatorBtnShow=false
            $rootScope.fieldValidatorListShow=false;
            $rootScope.fieldEditShow=true;
            $state.go('windowEdit.edittab.listField');
        }
        $scope.fieldTrlList=function () {
            $rootScope.windowEditShow = false;
            $rootScope.tabListshow = false;
            $rootScope.tabEditShow = false;
            $rootScope.windowTrlListShow = false;
            $rootScope.tabTrlListShow = false;
            $rootScope.fieldListShow = false;
            $rootScope.fieldEditShow=false;
            $rootScope.fieldTrlListShow=true;
            $state.go('windowEdit.edittab.editField.listFieldTrl');
        }
        $scope.fieldValidatorList=function () {
            $rootScope.windowEditShow = false;
            $rootScope.tabListshow = false;
            $rootScope.tabEditShow = false;
            $rootScope.windowTrlListShow = false;
            $rootScope.tabTrlListShow = false;
            $rootScope.fieldListShow = false;
            $rootScope.fieldEditShow=false;
            $rootScope.fieldTrlListShow=false;
            $rootScope.fieldValidatorListShow=true;
            $state.go('windowEdit.edittab.editField.listFieldValidator');
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
            var windowId=/^[0-9]+$/;
            if(!windowId.test($scope.windowTrlPo.windowId)){
                $scope.message="<eidea:label key="base.window.id.type.error"/> ";
                return false;
            }
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
        $scope.editItem=function (id,columnId) {
            $rootScope.tabTrlBtnShow=true;
            $rootScope.fieldBtnShow=true;
            $state.go('windowEdit.edittab',{tabid:id,columnId:columnId})
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
            var windowId=/^[0-9]+$/;
            if(!windowId.test($scope.tabPo.windowId)){
                $scope.message="<eidea:label key="base.window.id.type.error"/> ";
                return false;
            }
            if(!windowId.test($scope.tabPo.level)){
                $scope.message="<eidea:label key="base.tab.level.type.error"/> ";
                return false;
            }
            if(!windowId.test($scope.tabPo.sortno)){
                $scope.message="<eidea:label key="base.tab.sortno.type.error"/> ";
                return false;
            }
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
            $state.go('windowEdit.tablist',{id:$stateParams.id});
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
            var tabid=/^[0-9]+$/
            if (!tabid.test($scope.tabTrlPo.tabId)){
                $scope.message="<eidea:label key="base.window.id.type.error"/> ";
                return false;
            }
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
    app.controller('listFieldCtrl', function ($scope, $http,$stateParams,$state,$rootScope) {
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
        };
        $scope.pageChanged = function () {
            $http.post("<c:url value="/base/field/fieldList"/>", $stateParams.columnId)
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
                        $http.post("<c:url value="/base/field/deletes"/>", param).success(function (data) {
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
        $scope.editField=function (id) {
            $rootScope.fieldTrlBtnShow=true;
            $rootScope.fieldValidatorBtnShow=true;
            $state.go('windowEdit.edittab.editField',{field:id})
        }
        $scope.createField=function () {
            $state.go('windowEdit.edittab.editField',{field:null})
        }
    });
    app.controller('editFieldCtrl', function ($scope, $http, $stateParams,$state) {
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
        $scope.fieldPo = {};
        $scope.canAdd = PrivilegeService.hasPrivilege('add');
        $http.get("<c:url value="/base/field/selectInputType"/>").success(function (response) {
            if (response.success){
                var selectInputTypeList=$.parseJSON(response.data);
                $scope.inputTypeList=selectInputTypeList.fieldInputType;
            }else{
                bootbox.alert(response.message);
            }
        });
        var url = "<c:url value="/base/field/create"/>";
        if ($stateParams.field != null) {
            url = "<c:url value="/base/field/get"/>" + "?id=" + $stateParams.field;
        }
        $http.get(url)
                .success(function (response) {
                    if (response.success) {
                        $scope.fieldPo = response.data;
                        $scope.canSave = (PrivilegeService.hasPrivilege('add') && $scope.fieldPo.id == null) || PrivilegeService.hasPrivilege('update');
                    }
                    else {
                        bootbox.alert(response.message);
                    }
                }).error(function (response) {
            bootbox.alert(response);
        });
        $scope.save = function () {
            var sortno=/^[0-9]+$/;
            if(!sortno.test($scope.fieldPo.seqNo)){
                $scope.message="<eidea:label key="base.tab.sortno.type.error"/> ";
                return false;
            }
            if(!sortno.test($scope.fieldPo.displaylength)){
                $scope.message="<eidea:label key="base.field.displaylength.type.error"/> ";
                return false;
            }
            if ($scope.editForm.$valid) {
                var postUrl = '<c:url value="/base/field/saveForUpdated"/>';
                if ($scope.fieldPo.id == null) {
                    postUrl = '<c:url value="/base/field/saveForCreated"/>';
                }
                $http.post(postUrl, $scope.fieldPo).success(function (data) {
                    if (data.success) {
                        $scope.message = "<eidea:label key="base.save.success"/>";
                        $scope.fieldPo = data.data;
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
            $scope.fieldPo = {};
            var url = "<c:url value="/base/field/create"/>";
            $http.get(url)
                    .success(function (response) {
                        if (response.success) {
                            $scope.fieldPo = response.data;
                            $scope.canSave = (PrivilegeService.hasPrivilege('add') && $scope.fieldPo.id == null) || PrivilegeService.hasPrivilege('update');
                        }
                        else {
                            bootbox.alert(response.message);
                        }
                    }).error(function (response) {
                bootbox.alert(response);
            });
        }
        $scope.backFieldList=function () {
            $state.go('windowEdit.edittab.listField');
        }
    });
    app.controller('listFieldTrlCtrl', function ($scope, $http,$stateParams,$state) {
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
            $http.post("<c:url value="/base/fieldTrl/fieldTrlList"/>", $stateParams.field)
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
                        $http.post("<c:url value="/base/fieldTrl/deletes"/>", param).success(function (data) {
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
        $scope.editFieldTrl=function (id) {
            $state.go('windowEdit.edittab.editField.editFieldTrl',{fieldTrlId:id})
        }
        $scope.createFieldTrl=function () {
            $state.go('windowEdit.edittab.editField.editFieldTrl',{fieldTrlId:null})
        }
    });
    app.controller('editFieldTrlCtrl', function ($scope, $http, $stateParams,$state) {
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
        $scope.fieldTrlPo = {};
        $scope.canAdd=PrivilegeService.hasPrivilege('add');
        var url = "<c:url value="/base/fieldTrl/create"/>";
        if ($stateParams.fieldTrlId != null) {
            url = "<c:url value="/base/fieldTrl/get"/>" + "?id=" + $stateParams.fieldTrlId;
        }
        $http.get(url)
                .success(function (response) {
                    if (response.success) {
                        $scope.fieldTrlPo = response.data;
                        $scope.canSave=(PrivilegeService.hasPrivilege('add')&&$scope.fieldTrlPo.id==null)||PrivilegeService.hasPrivilege('update');
                    }
                    else {
                        bootbox.alert(response.message);
                    }
                }).error(function (response) {
            bootbox.alert(response);
        });
        $scope.save = function () {
            var field=/^[0-9]+&/
            if (!field.test($scope.fieldTrlPo.fieldId)){
                $scope.message="<eidea:label key="base.field.id.type.error"/> ";
                return false;
            }
            if ($scope.editForm.$valid) {
                var postUrl = '<c:url value="/base/fieldTrl/saveForUpdated"/>';
                if ($scope.fieldTrlPo.id == null) {
                    postUrl = '<c:url value="/base/fieldTrl/saveForCreated"/>';
                }
                $http.post(postUrl, $scope.fieldTrlPo).success(function (data) {
                    if (data.success) {
                        $scope.message = "<eidea:label key="base.save.success"/>";
                        $scope.fieldTrlPo = data.data;
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
            $scope.fieldTrlPo = {};
            var url = "<c:url value="/base/fieldTrl/create"/>";
            $http.get(url)
                    .success(function (response) {
                        if (response.success) {
                            $scope.fieldTrlPo = response.data;
                            $scope.canSave=(PrivilegeService.hasPrivilege('add')&&$scope.fieldTrlPo.id==null)||PrivilegeService.hasPrivilege('update');
                        }
                        else {
                            bootbox.alert(response.message);
                        }
                    }).error(function (response) {
                bootbox.alert(response);
            });
        }
        $scope.backFieldTrlList=function () {
            $state.go('windowEdit.edittab.editField.listFieldTrl');
        }
    });
    app.controller('listFieldValidatorCtrl', function ($scope, $http,$stateParams,$state) {
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
            $http.post("<c:url value="/base/fieldValidator/fieldValidatorList"/>", $stateParams.field)
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
                        $http.post("<c:url value="/base/fieldValidator/deletes"/>", param).success(function (data) {
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
        $scope.editFieldValidator=function (id) {
            $state.go('windowEdit.edittab.editField.editFieldValidator',{fieldValidatorId:id});
        }
        $scope.createFieldValidator=function () {
            $state.go('windowEdit.edittab.editField.editFieldValidator',{fieldValidatorId:null});
        }
    });
    app.controller('editFieldValidatorCtrl', function ($scope, $http, $stateParams,$state) {
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
        $scope.fieldValidatorPo = {};
        $scope.canAdd=PrivilegeService.hasPrivilege('add');
        var url = "<c:url value="/base/fieldValidator/create"/>";
        if ($stateParams.fieldValidatorId != null) {
            url = "<c:url value="/base/fieldValidator/get"/>" + "?id=" + $stateParams.fieldValidatorId;
        }
        $http.get(url)
                .success(function (response) {
                    if (response.success) {
                        $scope.fieldValidatorPo = response.data;
                        $scope.canSave=(PrivilegeService.hasPrivilege('add')&&$scope.fieldValidatorPo.id==null)||PrivilegeService.hasPrivilege('update');
                    }
                    else {
                        bootbox.alert(response.message);
                    }
                }).error(function (response) {
            bootbox.alert(response);
        });
        $scope.save = function () {
            $scope.message="";
            if ($scope.editForm.$valid) {
                var postUrl = '<c:url value="/base/fieldValidator/saveForUpdated"/>';
                if ($scope.fieldValidatorPo.id == null) {
                    postUrl = '<c:url value="/base/fieldValidator/saveForCreated"/>';
                }
                $http.post(postUrl, $scope.fieldValidatorPo).success(function (data) {
                    if (data.success) {
                        $scope.message = "<eidea:label key="base.save.success"/>";
                        $scope.fieldValidatorPo = data.data;
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
            $scope.fieldValidatorPo = {};
            var url = "<c:url value="/base/fieldValidator/create"/>";
            $http.get(url)
                    .success(function (response) {
                        if (response.success) {
                            $scope.fieldValidatorPo = response.data;
                            $scope.canSave=(PrivilegeService.hasPrivilege('add')&&$scope.fieldValidatorPo.id==null)||PrivilegeService.hasPrivilege('update');
                        }
                        else {
                            bootbox.alert(response.message);
                        }
                    }).error(function (response) {
                bootbox.alert(response);
            });
        }
        $scope.backFieldValidatorList=function () {
            $state.go('windowEdit.edittab.editField.listFieldValidator')
        }
    });

    app.run([
        'bootstrap3ElementModifier',
        function (bootstrap3ElementModifier) {
            bootstrap3ElementModifier.enableValidationStateIcons(true);
        }]);
</script>
</html>