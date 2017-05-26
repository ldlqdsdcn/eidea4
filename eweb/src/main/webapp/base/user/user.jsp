<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/12/13
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/inc/taglib.jsp" %>
<html>
<head>
    <title>用户信息</title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file="/inc/inc_ang_js_css.jsp" %>
    <script type='text/javascript' src="<c:url value="/js/md5.min.js"/>"></script>
    <%@include file="/common/common_header.jsp" %>
</head>
<body>
<div ng-app='myApp' ng-view class="container-fluid"></div>
<jsp:include page="/common/searchPage">
    <jsp:param name="uri" value="${uri}"/>
</jsp:include>
</body>

<script type="text/javascript">
    var app = angular.module('myApp', ['ngFileUpload','ngRoute', 'ui.bootstrap', 'jcs-autoValidate']).config(['$routeProvider', function ($routeProvider) {
        $routeProvider.when('/list', {templateUrl: '<c:url value="/base/user/list.tpl.jsp"/>'})
                .when('/edit', {templateUrl: '<c:url value="/base/user/edit.tpl.jsp"/>'})
                .otherwise({redirectTo: '/list'});
    }]);
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
            $http.post("<c:url value="/base/user/getUserList"/>", $scope.queryParams)
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
                        $http.post("<c:url value="/base/user/deleteUserList"/>", param).success(function (data) {
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
        $scope.canAdd=PrivilegeService.hasPrivilege('add');
        $scope.canSave=false;
        //用户编辑
        var url = "<c:url value="/base/user/create"/>";
        if ($routeParams.id != null) {
            url = "<c:url value="/base/user/getUser"/>" + "?id=" + $routeParams.id;
        }
        $http.post(url).success(function (data) {
            if (data.success) {
                $scope.userBo = data.data;
                $scope.tableId=$scope.userBo.id;
                if ($routeParams.id == null) {
                    $scope.userBo.init='N';
                }
                $scope.roleIds = data.data.roleIds;
                $scope.repassword = data.data.password;
                $scope.getRole();
                $scope.getClient($routeParams.id);
                $scope.getOrg($routeParams.id);
                $scope.canSave=(PrivilegeService.hasPrivilege('add')&&$scope.userBo.id==null)||PrivilegeService.hasPrivilege('update');
            } else {
                bootbox.alert(data.message);
            }
        }).error(function (data) {
            bootbox.alert(data);
        })
        //验证登录名称
        var userName = true;
        $scope.getExistUserName = function () {
            $http.post("<c:url value="/base/user/getExistUserName"/>", $scope.userBo).success(function (data) {
                if (data.success) {
                    if (!data.data) {
                        userName = true;
                        $scope.message = "";
                    } else {
                        $scope.message = "<eidea:label key="login.name.already.exists"/>";
                        /*登录名称已存在*/
                        userName = false;
                        return false;
                    }
                } else {
                    bootbox.alert(data.message);
                }
            }).error(function (data) {
                bootbox.alert(data);
            })
        }
        //用户保存
        $scope.save = function () {
            if (userName == false) {
                return false;
            }
            if ($scope.userBo.password == $scope.repassword) {
                $scope.message = "";
            } else {
                $scope.message = "<eidea:label key="password.confirm.password.inconsistent"/>";
                /*密码和确认密码不一致*/
                return false;
            }
         /*   var reg = /^1[3|4|5|7|8][0-9]{9}$/;
            if(!reg.test($scope.userBo.telephone)){
                $scope.message = "<eidea:label key="telephone.confirm.error"/>";
                return false;
            }*/
            var roleIds = [];
            for (var i = 0; i < $scope.roleList.length; i++) {
                if ($scope.roleList[i].roleDelFlag) {
                    roleIds.push($scope.roleList[i].id);
                }
            }
            $scope.userBo.roleIds = roleIds;
            if ($scope.editForm.$valid) {
                var postUrl = '<c:url value="/base/user/saveUserForUpdated"/>';
                if ($scope.userBo.id == null) {
                    postUrl = '<c:url value="/base/user/saveUserForCreated"/>';
                    $scope.userBo.password=md5($scope.userBo.password);
                }
                $http.post(postUrl, $scope.userBo).success(function (data) {
                    if (data.success) {
                        $scope.errorCode = -1;
                        $scope.message = "<eidea:label key="base.save.success"/>";
                        $scope.userBo = data.data;
                        $scope.repassword = data.data.password;
                    } else {
                        $scope.message = data.message;
                        $scope.errorCode = data.errorCode;
                        if (data.errorCode == ERROR_VALIDATE_PARAM) {
                            $scope.errorMessages = data.data;
                        }
                        else {
                            $scope.errorMessages = [data.message];
                        }
                    }
                });
            }
            $scope.roleChecked = [];
        }
        //再次新建
        $scope.create = function () {
            $http.post("<c:url value="/base/user/create"/>").success(function (data) {
                if (data.success) {
                    $scope.userBo = data.data;
                    $scope.roleIds = [];
                    $scope.getRole();
                    $scope.getClient();
                    $scope.getOrg();
                } else {
                    bootbox.alert(data.message);
                }
            }).error(function (data) {
                bootbox.alert(data);
            })
        }
        var param = {"queryParams": $scope.queryParams};
        //查询实体
        $scope.getClient = function (id) {
            $http.post("<c:url value="/base/client/list"/>",param).success(function (data) {
                if (data.success) {
                    $scope.clientList = data.data.data;
                    if (id == null) {
                        if (data.data[0] != null && data.data[0].id != null) {
                            $scope.userBo.clientId = data.data[0].id;
                        }
                    }
                } else {
                    bootbox.alert(data.message);
                }
            });
        }
        //查询组织
        $scope.getOrg = function (id) {
            $http.post("<c:url value="/base/org/list"/>",param).success(function (data) {
                if (data.success) {
                    $scope.orgList = data.data.data;
                    if (id == null) {
                        if (data.data[0] != null && data.data[0].id != null) {
                            $scope.userBo.orgId = data.data[0].id;
                        }
                    }
                } else {
                    bootbox.alert(data.message);
                }
            });
        }
        //查询角色
        $scope.roleDelFlag = false;
        $scope.roleList = [];
        $scope.roleIds = [];

        $scope.getRole = function () {
            $http.post("<c:url value="/base/role/list"/>",param).success(function (data) {
                if (data.success) {
                    $scope.updateRoleList(data.data);
                } else {
                    bootbox.alert(data.message);
                }
            });
        }
        $scope.updateRoleList = function (data) {
            $scope.roleList = data.data;
            for (var i = 0; i < $scope.roleList.length; i++) {
                if ($scope.roleIds != null && $scope.roleList.length > 0) {
                    for (var j = 0; j < $scope.roleList.length; j++) {
                        if ($scope.roleList[i].id == $scope.roleIds[j]) {
                            $scope.roleList[i].roleDelFlag = true;
                        }
                    }
                }
            }
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

