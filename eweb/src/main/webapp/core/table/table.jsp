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
    <title><%--表信息维护--%><eidea:label key="table.title"/></title>
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
                        .when('/list', {templateUrl: '<c:url value="/core/table/list.tpl.jsp"/>'})
                        .when('/edit', {templateUrl: '<c:url value="/core/table/edit.tpl.jsp"/>'})
                        .when('/wizard', {templateUrl: '<c:url value="/core/table/wizard.tpl.jsp"/>'})
                        .otherwise({redirectTo: '/list'});
            }]);
    app.controller('listCtrl', function ($scope, $http) {
        $scope.allList = [];
        $scope.currentList = [];
        $scope.delFlag = false;
        $scope.canDel=PrivilegeService.hasPrivilege('delete');
        $scope.canAdd=PrivilegeService.hasPrivilege('add');
        $http.get("<c:url value="/core/table/list"/>")
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
            $scope.currentList.length = 0;
            $scope.pageChanged();
        };
        $scope.pageChanged = function (delF) {
            var bgn = ($scope.bigCurrentPage - 1) * $scope.itemsPerPage;
            var end = bgn + $scope.itemsPerPage;
            $scope.currentList.length = 0;
            if (delF == null) {
                delF = false;
            }
            for (var i = bgn; i < end && i < $scope.allList.length; i++) {
                var item = $scope.allList[i];
                item.delFlag = delF;
                $scope.currentList.push(item);
            }

        }
        $scope.canDelete = function () {
            for (var i = 0; i < $scope.currentList.length; i++) {
                if ($scope.currentList[i].delFlag) {
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
                        label: '<eidea:label key="common.button.checktrue"/>',
                        className: 'btn-success'
                    },
                    cancel: {
                        label: '<eidea:label key="common.button.checkfalse"/>',
                        className: 'btn-danger'
                    }
                },
                callback: function (result) {
                    if (result) {
                        var ids = [];
                        for (var i = 0; i < $scope.currentList.length; i++) {
                            if ($scope.currentList[i].delFlag) {
                                ids.push($scope.currentList[i].id);
                            }
                        }
                        $http.post("<c:url value="/core/table/deletes"/>", ids).success(function (data) {
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
        $scope.columnDataTypes = [];
        $scope.tableBo = {};
        $scope.canAdd=PrivilegeService.hasPrivilege('add');
        $scope.canSave=false;
        $http.get("<c:url value="/core/table/getJavaTypeList"/> ")
                .success(function (response) {
                    if (response.success) {
                        $scope.columnDataTypes = $.parseJSON(response.data);
                    }
                    else {
                        bootbox.alert(response.message);
                    }
                });

        var url = "<c:url value="/core/table/get"/>";
        if ($routeParams.id != null) {
            url = "<c:url value="/core/table/get"/>" + "?id=" + $routeParams.id;
        }
        $http.get(url)
                .success(function (response) {
                    if (response.success) {
                        $scope.tableBo = response.data;
                        $scope.canSave=(PrivilegeService.hasPrivilege('add')&&$scope.tableBo.id==null)||PrivilegeService.hasPrivilege('update');
                    }
                    else {
                        bootbox.alert(response.message);
                    }
                }).error(function (response) {
            bootbox.alert(response);
        });
        $scope.save = function () {
            if ($scope.tableForm.$valid) {
                var postUrl = '<c:url value="/core/table/saveForUpdated"/>';
                if ($scope.tableBo.id == null) {
                    postUrl = '<c:url value="/core/table/saveForCreated"/>';
                }
                $http.post(postUrl, $scope.tableBo).success(function (data) {
                    if (data.success) {

                        bootbox.alert("<eidea:label key="success.save"/>");
                        console.log("id=" + data.data.id);
                        $scope.tableBo = data.data;
                    }
                    else {
                        bootbox.alert(data.message);
                    }

                });
            }
        }

    });
    app.controller('wizardCtrl', function ($scope, $http, $routeParams) {
        $scope.tableInfo = {};
        $scope.columnDataTypes = [];
        $scope.canAdd=PrivilegeService.hasPrivilege('add');
        $http.get("<c:url value="/core/table/getJavaTypeList"/> ")
                .success(function (response) {
                    if (response.success) {
                        $scope.columnDataTypes = $.parseJSON(response.data);
                    }
                    else {
                        bootbox.alert(response.message);
                    }
                });
        $scope.enter = function (ev) {
            if (ev.keyCode !== 13) return;
            $http.get("<c:url value="/core/table/getTableInfo"/>?tableName=" + $scope.tableInfo.name)
                    .success(function (response) {
                        if (response.success) {
                            $scope.tableInfo = response.data;

                        }
                        else {
                            bootbox.alert(response.message, function () {
                                $("#name").focus();
                            });


                        }


                    });

        };
        $scope.submit = function () {

            if ($scope.myform.$valid) {
                $http.post("<c:url value="/core/table/saveTableInfo"/>", $scope.tableInfo).success(function (data) {
                    if (data.success) {
                        bootbox.alert("<eidea:label key="success.save"/>");
                        $scope.tableInfo = data.data;
                    }
                    else {
                        bootbox.alert(data.message);
                    }

                });
            }


        };
    });
    app.run([
        'bootstrap3ElementModifier',
        function (bootstrap3ElementModifier) {
            bootstrap3ElementModifier.enableValidationStateIcons(true);
        }]);
</script>
</html>
