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
<body>
<div ng-view class="content" ng-app='myApp'></div>
<jsp:include page="/common/searchPage">
    <jsp:param name="uri" value="${uri}"/>
</jsp:include>
</body>

<script type="text/javascript">
    var app = angular.module('myApp', ['ngFileUpload','ngRoute', 'ui.bootstrap', 'jcs-autoValidate']).config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/list', {templateUrl: '<c:url value="/base/module/list.tpl.jsp"/>'})
                      .when('/edit', {templateUrl: '<c:url value="/base/module/edit.tpl.jsp"/>'})
                      .otherwise({redirectTo: '/list'});
    }]);
    //查询模块设置的列表
    app.controller('listCtrl', function ($scope,$rootScope,$http) {
        $scope.modelList = [];
        $scope.delFlag = false;
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
            $http.post("<c:url value="/base/module/getModuleList"/>", $scope.queryParams)
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
                        $http.post("<c:url value="/base/module/deleteModuleList"/>", param).success(function (data) {
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
    //查询模块设置的新增
    app.controller('editCtrl', function ($scope, $http, $rootScope,$routeParams,$timeout, Upload) {
        $scope.menuList = [];
        $scope.menuDelFlag = false;
        $scope.directoryList = [];
        $scope.directoryDelFlag = false;
        $scope.menuIds=[];
        $scope.dirIds=[];
        $scope.canAdd=PrivilegeService.hasPrivilege('add');
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
                $scope.canSave=(PrivilegeService.hasPrivilege('add')&&$scope.moduleBo.id==null)||PrivilegeService.hasPrivilege('update');
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
        var param = {"queryParams": $scope.queryParams};
        $scope.selectDir=function () {
            $http.post("<c:url value="/base/directory/list"/>",param).success(function (data) {
                if (data.success) {
                    $scope.updateDirList(data.data);
                }else {
                    bootbox.alert(data.message);
                }
            });
        }
        $scope.updateDirList = function (data) {
            $scope.directoryList = data.data;
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

        //附件上传
        $scope.showAttachment=function () {
            if($scope.moduleBo.id != null){
                $("#attachmentModal").modal('show');
                $scope.tableId=$scope.moduleBo.id;
                $scope.directoryUrl="/base";
                $http.post("<c:url value="/common/attachmentList"/>",{"tableId":$scope.tableId,"uri":"${uri}","directoryUrl":$scope.directoryUrl}).success(function (data) {
                    if (data.success) {
                        $scope.attachmentList = data.data;
                    }else {
                        $scope.alert(data.message);
                    }
                });
            }else {
                $scope.alert("<eidea:message key="common.upload.before.save.success"/>");
            }
        }
        $scope.$watch('files', function (files) {
            $scope.formUpload = false;
            if (files != null) {
                if (!angular.isArray(files)) {
                    $timeout(function () {
                        $scope.files = files = [files];
                    });
                    return;
                }
            }
        });
        $scope.attachmentUpload=function () {
            if($scope.files==null){
                $scope.alert('<eidea:message key="common.upload.select.attachment"/>');return;
            }
            for (var i = 0; i < $scope.files.length; i++) {
                $scope.errorMsg = null;
                (function (f) {
                    $scope.upload(f, true);
                })($scope.files[i])
                ;
            }
        }
        $scope.upload = function (file) {
            $scope.canUpload=true;
            file.upload =Upload.upload({
                //服务端接收
                url: "<c:url value="/common/attachmentUpload"/>",
                data: {'fileKeyword':$scope.commonFileBo==null?null:$scope.commonFileBo.fileKeyword,"fileAbstract":$scope.commonFileBo==null?null:$scope.commonFileBo.fileAbstract,
                    "directoryUrl":$scope.directoryUrl,"tableId":$scope.tableId,"uri":"${uri}"},
                //上传的文件
                file: file
            }).success(function (data, status, headers, config) {
                //上传成功
                $scope.alert('<eidea:message key="common.upload.success"/>');
                $scope.attachmentList = data.data;
                $scope.commonFileBo=null;
                $scope.files=null;
                $scope.canUpload=false;
            }).error(function (data, status, headers, config) {
                //上传失败
                console.log('error status: ' + status);
            });
        };
        $scope.attachmentDelete=function (id) {
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
                        $http.post("<c:url value="/common/attachmentDelete"/>",{"id":id,"tableId":$scope.tableId,"uri":"${uri}","directoryUrl":$scope.directoryUrl}).success(function (data) {
                            if (data.success) {
                                $scope.alert('<eidea:message key="common.upload.delete.success"/>');
                                $scope.attachmentList = data.data;
                            }else {
                                $scope.message = data.message;
                            }
                        });
                    }
                }
            });
        }
        $scope.alert=function(message) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '<i class="fa fa-close" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.closed"/>',
                        className: 'btn-primary'
                    }
                },
                message: message,
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
