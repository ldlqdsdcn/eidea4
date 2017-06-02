<%--
  Created by IntelliJ IDEA.
  User: Bobo
  Date: 2016/12/7
  Time: 15:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/inc/taglib.jsp" %>
<html>
<head>
    <title><%--工作流--%><eidea:label key="workflow.title.workflow"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file="/inc/inc_ang_js_css.jsp" %>
    <%@include file="/common/common_header.jsp" %>
</head>
<body>
<div ng-view class="content" ng-app='myApp'></div>
</body>

<script type="text/javascript">
    var app = angular.module('myApp', ['ngFileUpload','ngRoute', 'ui.bootstrap', 'jcs-autoValidate'])
            .config(['$routeProvider', function ($routeProvider) {
                $routeProvider
                        .when('/list', {templateUrl: '<c:url value="/sys/workflow/workflow/list.tpl.jsp"/>'})
                        .when('/edit', {templateUrl: '<c:url value="/sys/workflow/workflow/edit.tpl.jsp"/>'})
                        .otherwise({redirectTo: '/list'});
            }]);
    app.controller('listCtrl', function ($rootScope,$scope,$http,$window) {
        $scope.allList = [];
        $scope.modelList = [];
        $scope.delFlag = false;
        $scope.canDel=PrivilegeService.hasPrivilege('delete');
        $scope.canAdd=PrivilegeService.hasPrivilege('add');
        $http.get("<c:url value="/sys/workflow/list"/>")
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
            $scope.queryParams.totalRecords  = $scope.allList.length;
            $scope.modelList.length = 0;
            $scope.pageChanged();
        };
        $scope.pageChanged = function (delF) {
            var bgn = ($scope.queryParams.pageNo - 1) * $scope.queryParams.pageSize;
            var end = bgn +  $scope.queryParams.pageSize;
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
                },
                callback: function (result) {
                    if (result) {
                        var ids = [];
                        for (var i = 0; i < $scope.modelList.length; i++) {
                            if ($scope.modelList[i].delFlag) {
                                ids.push($scope.modelList[i].deploymentId);
                            }
                        }
                        $http.post("<c:url value="/sys/workflow/deletes"/>", ids).success(function (data) {
                            if (data.success) {
                                bootbox.alert("<eidea:message key="common.warn.deleted.success"/>");
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
        //修改激活/挂起/转换为Model
        $scope.updateSuspended=function (message,url,id) {
            bootbox.confirm({
                message: message,
                buttons: {
                    confirm: {
                        label: '<eidea:label key="common.button.yes"/>',
                        className: 'btn-success'
                    },
                    cancel: {
                        label: '<eidea:label key="common.button.no"/>',
                        className: 'btn-danger'
                    }
                },
                callback: function (result) {
                    if(result){
                        $http.get(url+id).success(function (response) {
                            bootbox.alert({
                                buttons: {
                                    ok: {
                                        label: '<i class="fa fa-close" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.confirm"/>',
                                        className: 'btn-primary'
                                    }
                                },
                                message: response.data.message
                            });
                            if(response.data.processDefinitionList != null){
                                $scope.modelList=response.data.processDefinitionList;
                            }
                        });
                    }
                }
            });
        }
        $scope.openImage=function (id) {
            $("#imageShowModal").modal("show");
            $("#imageShowModalLabel").html('<eidea:label key="workflow.title.picture.preview"/>');
            $("#imageShow").attr("src",'<c:url value="/sys/workflow/resource/read"/>?processDefinitionId='+id+'&resourceType=image');
        }
        buttonHeader.listInit($scope,$window);
    });
    app.controller('editCtrl', function ($routeParams,$scope, $http,$window,$timeout, Upload) {
        //工作流上传
        $scope.$watch('files', function (files) {
            $scope.formUpload = false;
            if (files != null) {
                if (!angular.isArray(files)) {
                    $timeout(function () {
                        $scope.files = files = [files];
                    });
                    return;
                }
                for (var i = 0; i < $scope.files.length; i++) {
                    $scope.errorMsg = null;
                    (function (f) {
                        $scope.upload(f, true);
                    })($scope.files[i])
                    ;
                }
            }
        });
        $scope.upload = function (file) {
            file.upload =Upload.upload({
                //服务端接收
                url: "<c:url value="/sys/workflow/deploy"/>",
                //上传的文件
                file: file
            }).success(function (data, status, headers, config) {
                //上传成功
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '<i class="fa fa-close" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.confirm"/>',
                            className: 'btn-primary'
                        }
                    },
                    message: '<eidea:message key="common.upload.success"/>'
                    , callback: function() {
                            $window.location.href="<c:url value="/sys/workflow/showList"/>";
                    }
                });
//                $location.path("/list");
            }).error(function (data, status, headers, config) {
                //上传失败
                console.log('error status: ' + status);
            });
        };
    });
    app.run([
        'bootstrap3ElementModifier',
        function (bootstrap3ElementModifier) {
            bootstrap3ElementModifier.enableValidationStateIcons(true);
        }]);
</script>
</html>
