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
    <title><eidea:label key="menu.module"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file="/inc/inc_ang_js_css.jsp" %>
</head>
<body ng-app='myApp'>
<div ng-view class="content"></div>
</body>

<script type="text/javascript">
    var app = angular.module('myApp', ['ngRoute', 'ui.bootstrap', 'jcs-autoValidate']).config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/list', {templateUrl: '<c:url value="/base/module/list.tpl.jsp"/>'})
                      .when('/edit', {templateUrl: '<c:url value="/base/module/edit.tpl.jsp"/>'})
                      .otherwise({redirectTo: '/list'});
    }]);
    //查询模块设置的列表
    app.controller('listCtrl', function ($scope, $http) {
        $scope.allList = [];
        $scope.modelList = [];
        $scope.delFlag = false;
        $scope.canDel=PrivilegeService.hasPrivilege('module:delete');
        $scope.canAdd=PrivilegeService.hasPrivilege('module:add');
        //模块查询
        $http.post("<c:url value="/base/module/getModuleList"/>").success(function (data) {
            if (data.success) {
                $scope.updateList(data.data);
            }else {
                bootbox.alert(data.message);
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
                message: "<eidea:label key="modile.deleteselect.check"/>",
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
                        $http.post("<c:url value="/base/module/deleteModuleList"/>", ids).success(function (data) {
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
    //查询模块设置的新增
    app.controller('editCtrl', function ($scope, $http, $routeParams) {
        $scope.menuList = [];
        $scope.menuDelFlag = false;
        $scope.directoryList = [];
        $scope.directoryDelFlag = false;
        $scope.menuIds=[];
        $scope.dirIds=[];
        $scope.canAdd=PrivilegeService.hasPrivilege('module:add');
        $scope.canSave=false;
        var url = "<c:url value="/base/module/create"/>";
        if ($routeParams.id != null) {
            url = "<c:url value="/base/module/getModule"/>" + "?id=" + $routeParams.id;
        }
        $http.post(url).success(function (data) {
            if (data.success) {
                $scope.moduleBo = data.data;
                $scope.menuIds=data.data.menuIds;
                $scope.dirIds=data.data.dirIds;
                $scope.selectMenu();
                $scope.canSave=(PrivilegeService.hasPrivilege('module:add')&&$scope.moduleBo.id==null)||PrivilegeService.hasPrivilege('module:update');
            }else {
                bootbox.alert(data.message);
            }
        }).error(function (data) {
            bootbox.alert(data);
        })
        //模块设置保存
        $scope.save=function () {
            var menuIds = [];
            for (var i = 0; i < $scope.menuList.length; i++) {
                if ($scope.menuList[i].menuDelFlag) {
                    menuIds.push($scope.menuList[i].id);
                }
            }
            var dirIds = [];
            for (var i = 0; i < $scope.directoryList.length; i++) {
                if ($scope.directoryList[i].directoryDelFlag) {
                    dirIds.push($scope.directoryList[i].id);
                }
            }
            $scope.moduleBo.menuIds=menuIds;
            $scope.moduleBo.dirIds=dirIds;
            if ($scope.editForm.$valid) {
                var postUrl = '<c:url value="/base/module/saveModuleForUpdated"/>';
                if ($scope.moduleBo.id == null) {
                    postUrl = '<c:url value="/base/module/saveModuleForCreated"/>';
                }

                $http.post(postUrl,$scope.moduleBo).success(function (data) {
                    if (data.success) {
                        $scope.errorCode=-1;
                        $scope.message = "<eidea:label key="success.save"/>";
                        $scope.moduleBo = data.data;
                        $scope.menuIds=data.data.menuIds;
                        $scope.dirIds=data.data.dirIds;
                        if(parentpath == 'page'){
                            $scope.selectDir();
                        }else{
                            $scope.selectMenu();
                        }
                    }else {
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
            $scope.menuList = [];
            $scope.directoryList = [];
        }
        //再次新建
        $scope.create =function () {
            $http.post("<c:url value="/base/module/create"/>").success(function (data) {
                if (data.success) {
                    $scope.moduleBo = data.data;
                    $scope.menuIds=[];
                    $scope.dirIds=[];
                    $scope.selectDir();
                    $scope.selectMenu();
                }else {
                    bootbox.alert(data.message);
                }
            }).error(function (data) {
                bootbox.alert(data);
            })
        };
        //菜单设置
        $scope.selectMenu=function () {
            $http.post("<c:url value="/base/pagemenu/getModuleMenuList"/>").success(function (data) {
                if(data.success){
                    $scope.updateMenuList(data.data);
                }else{
                    bootbox.alert(data.message);
                }
            });
        }
        $scope.updateMenuList = function (data) {
            $scope.menuList = data;
            for (var i = 0; i < $scope.menuList.length; i++) {
                if($scope.menuIds != null && $scope.menuIds.length > 0){
                    for(var j = 0; j <$scope.menuIds.length; j++){
                        if($scope.menuList[i].id == $scope.menuIds[j]){
                            $scope.menuList[i].menuDelFlag = true;
                        }
                    }
                }
            }
        };
        $scope.menuPageChanged = function (delF) {
            if (delF == null) {
                delF = false;
            }
            for (var i = 0; i < $scope.menuList.length; i++) {
                var item = $scope.menuList[i];
                item.menuDelFlag = delF;
            }
        }
        $scope.selectMenuAll = function () {
            $scope.menuPageChanged($scope.menuDelFlag);
        }
        //访问目录设置
        $scope.selectDir=function () {
            $http.get("<c:url value="/base/directory/list"/>").success(function (data) {
                if (data.success) {
                    $scope.updateDirList(data.data);
                }else {
                    bootbox.alert(data.message);
                }
            });
        }
        $scope.updateDirList = function (data) {
            $scope.directoryList = data;
            for (var i = 0; i < $scope.directoryList.length; i++) {
                if($scope.dirIds != null && $scope.dirIds.length > 0){
                    for(var j = 0; j <$scope.dirIds.length; j++){
                        if($scope.directoryList[i].id == $scope.dirIds[j]){
                            $scope.directoryList[i].directoryDelFlag = true;
                        }
                    }
                }
            }
        };
        $scope.directoryPageChanged = function (delF) {
            if (delF == null) {
                delF = false;
            }
            for (var i = 0; i < $scope.directoryList.length; i++) {
                var item = $scope.directoryList[i];
                item.directoryDelFlag = delF;
            }
        }
        $scope.selectDirectoryMenuAll = function () {
            $scope.directoryPageChanged($scope.directoryDelFlag);
        }
        //tab页
        var parentpath='menu';
        $scope.tab=function (path) {
            if(path == 'page'){
                $scope.selectDir();
            }else{
                $scope.selectMenu();
            }
            document.getElementById(parentpath).className='tab-pane fade';
            document.getElementById(path).className='tab-pane fade in active ng-pristine ng-untouched ng-valid ng-empty';
            parentpath=path;
        }
    });
    app.run([
        'bootstrap3ElementModifier',
        function (bootstrap3ElementModifier) {
            bootstrap3ElementModifier.enableValidationStateIcons(true);
        }]);
</script>
</html>
