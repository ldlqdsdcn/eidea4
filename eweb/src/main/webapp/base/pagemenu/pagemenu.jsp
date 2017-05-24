<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/12/15
  Time: 8:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/inc/taglib.jsp" %>
<html>
<head>
    <title><eidea:label key="dymenus.title"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file="/inc/inc_ang_js_css.jsp" %>
    <%@include file="/common/common_header.jsp" %>
</head>
<body>
<div ng-app='myApp' ng-view class="content"></div>
<jsp:include page="/common/searchPage">
    <jsp:param name="uri" value="${uri}"/>
</jsp:include>
</body>

<script type="text/javascript">
    var app = angular.module('myApp', ['ngFileUpload','ngRoute', 'ui.bootstrap', 'jcs-autoValidate'])
            .config(['$routeProvider', function ($routeProvider) {
                $routeProvider
                        .when('/list', {templateUrl: '<c:url value="/base/pagemenu/list.tpl.jsp"/>'})
                        .when('/edit', {templateUrl: '<c:url value="/base/pagemenu/edit.tpl.jsp"/>'})
                        .otherwise({redirectTo: '/list'});
            }]);
    /*查询出所有的菜单，在页面显示*/
    app.controller('listCtrl', function ($rootScope,$scope,$http,$window) {
        $scope.modelList = [];
        $scope.delFlag = false;
        $scope.isLoading=true;
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
            $http.post("<c:url value="/base/pagemenu/list"/>", $scope.queryParams)
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
                        $http.post("<c:url value="/base/pagemenu/deletes"/>", param).success(function (data) {
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

        buttonHeader.listInit($scope,$window);
    });
    app.controller('editCtrl', function ($routeParams,$scope, $http,$window,$timeout, Upload) {
        $scope.message = '';
        $scope.pageMenuBo = {};
        $scope.canAdd=PrivilegeService.hasPrivilege('add');
        $scope.canSave=false;
        $scope.menuTypeList={"<eidea:label key="dymenu.label.menufolde"/>":1,"<eidea:label key="dymenuForm.label.type.hyperlink"/>":2};
        var url = "<c:url value="/base/pagemenu/create"/>";
        if ($routeParams.id != null) {
            url = "<c:url value="/base/pagemenu/get"/>" + "?id=" + $routeParams.id;
        }
        $http.get(url).success(function (response) {
            if (response.success) {
                $scope.pageMenuBo = response.data;
                $scope.tableId=$scope.pageMenuBo.id;
                $scope.getMenuTypeList($routeParams.id);
                $scope.canSave=(PrivilegeService.hasPrivilege('add')&&$scope.pageMenuBo.id==null)||PrivilegeService.hasPrivilege('update');
            }else {
                bootbox.alert(response.message);
            }
        }).error(function (response) {
            bootbox.alert(response);
        });
        //保存
        $scope.save = function () {
            if ($scope.editForm.$valid) {
                $scope.message = '';
                var postUrl = '<c:url value="/base/pagemenu/saveForUpdated"/>';
                if ($scope.pageMenuBo.id==null) {
                    postUrl = '<c:url value="/base/pagemenu/saveForCreated"/>';
                }

                $http.post(postUrl, $scope.pageMenuBo).success(function (data) {
                    if (data.success) {
                        $scope.message = "<eidea:label key="base.save.success"/>";
                        $scope.pageMenuBo = data.data;
                    }else {
                        $scope.message = data.message;
                    }
                });
            }
        }
        //再次新建
        $scope.create=function () {
            $scope.message = "";
            $scope.pageMenuBo={};
            $http.get("<c:url value="/base/pagemenu/create"/>").success(function (data) {
                if (data.success) {
                    $scope.pageMenuBo = data.data;
                    $scope.pageMenuBo.menuType=1;
                    $scope.getMenuTypeList();
                }else {
                    bootbox.alert(data.message);
                }
            }).error(function (data) {
                bootbox.alert(data);
            })
        };
        //获取父菜单列表
        $scope.getMenuTypeList=function (id) {
            $http.post("<c:url value="/base/pagemenu/getListMenuType"/>").success(function (data) {
                if(data.success){
                    $scope.pageMenuBoList=data.data;
                    if(id == null){
                        $scope.pageMenuBo.menuType=1;
                        if(data.data[0] != null && data.data[0].id != null){
                            $scope.pageMenuBo.parentMenuId=data.data[0].id;
                        }
                    }
                }else{
                    bootbox.alert(response.message);
                }
            })
        };

        buttonHeader.editInit($scope,$http,$window,$timeout, Upload,"/base");
    });
    app.run([
        'bootstrap3ElementModifier',
        function (bootstrap3ElementModifier) {
            bootstrap3ElementModifier.enableValidationStateIcons(true);
        }]);
</script>
</html>

