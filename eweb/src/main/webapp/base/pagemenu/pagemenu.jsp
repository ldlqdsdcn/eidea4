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
                        .when('/list', {templateUrl: '<c:url value="/base/pagemenu/list.tpl.jsp"/>'})
                        .when('/edit', {templateUrl: '<c:url value="/base/pagemenu/edit.tpl.jsp"/>'})
                        .otherwise({redirectTo: '/list'});
            }]);
    /*查询出所有的菜单，在页面显示*/
    app.controller('listCtrl', function ($scope, $http) {
        $scope.allList = [];
        $scope.pageMenuBoList = [];
        $scope.delFlag = false;
        $scope.canDel=PrivilegeService.hasPrivilege('delete');
        $scope.canAdd=PrivilegeService.hasPrivilege('add');
        $http.post("<c:url value="/base/pagemenu/list"/> ").success(function (data) {
          if(data.success){
              $scope.updateList(data.data);
          }else{
              bootbox.alert(response.message);
          }
        });
        $scope.updateList = function (data) {
            $scope.allList = data;
            $scope.bigTotalItems = $scope.allList.length;
            $scope.pageMenuBoList.length = 0;
            $scope.pageChanged();
        };
        $scope.pageChanged = function (delF) {
            var bgn = ($scope.bigCurrentPage - 1) * $scope.itemsPerPage;
            var end = bgn + $scope.itemsPerPage;
            $scope.pageMenuBoList.length = 0;
            if (delF == null) {
                delF = false;
            }
            for (var i = bgn; i < end && i < $scope.allList.length; i++) {
                var item = $scope.allList[i];
                item.delFlag = delF;
                $scope.pageMenuBoList.push(item);

            }
        }
        $scope.canDelete = function () {
            for (var i = 0; i < $scope.pageMenuBoList.length; i++) {
                if ($scope.pageMenuBoList[i].delFlag) {
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
                    },cancel: {
                        label: '<eidea:label key="module.name.checkfalse"/>',
                        className: 'btn-danger'
                    }
                },callback: function (result) {
                    if (result) {
                        var ids = [];
                        for (var i = 0; i < $scope.pageMenuBoList.length; i++) {
                            if ($scope.pageMenuBoList[i].delFlag) {
                                ids.push($scope.pageMenuBoList[i].id);
                            }
                        }
                        $http.post("<c:url value="/base/pagemenu/deletes"/>", ids).success(function (data) {
                            if (data.success) {
                                bootbox.alert("<eidea:message key="module.deleted.success"/>");
                                $scope.updateList(data.data);
                            }else {
                                bootbox.alert(data.message);
                            }
                        });
                    }
                }
            });
        };
        $scope.maxSize =${pagingSettingResult.pagingButtonSize};
        //每页现实记录数
        $scope.itemsPerPage =${pagingSettingResult.perPageSize};
        //当前页
        $scope.bigCurrentPage = 1;
        //记录数
        $scope.bigTotalItems = 0;
    });
    app.controller('editCtrl', function ($scope, $http,$routeParams) {
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
    });
    app.run([
        'bootstrap3ElementModifier',
        function (bootstrap3ElementModifier) {
            bootstrap3ElementModifier.enableValidationStateIcons(true);
        }]);
</script>
</html>

