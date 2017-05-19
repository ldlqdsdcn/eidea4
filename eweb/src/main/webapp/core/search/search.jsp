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
    <title>查询条件维护</title>
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
    var app = angular.module('myApp', ['ngFileUpload','ngRoute', 'ui.bootstrap', 'jcs-autoValidate'])
            .config(['$routeProvider', function ($routeProvider) {
                $routeProvider
                        .when('/list', {templateUrl: '<c:url value="/core/search/list.tpl.jsp"/>'})
                        .when('/edit', {templateUrl: '<c:url value="/core/search/edit.tpl.jsp"/>'})
                        .otherwise({redirectTo: '/list'});
            }]);
    app.controller('listCtrl', function ($scope,$rootScope,$http) {
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
            $http.post("<c:url value="/core/search/list"/>", $scope.queryParams)
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
                        $http.post("<c:url value="/core/search/deletes"/>", param).success(function (data) {
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
    app.controller('editCtrl', function ($scope,$rootScope,$http, $routeParams,$timeout, Upload) {
        $scope.showTypeList = [];
        $scope.relOper = [];
        $scope.searchPageFieldInput = [];
        $scope.searchDataType = [];
        $scope.searchBo = {};
        $scope.delF = false;
        $scope.canAdd=PrivilegeService.hasPrivilege('add');
        $scope.canSave=false;
        $http.get("<c:url value="/core/search/getSelectList"/> ")
                .success(function (response) {
                    if (response.success) {
                        var selectList = $.parseJSON(response.data);
                        $scope.showTypeList = selectList.searchPageType;
                        $scope.relOper = selectList.relOper;
                        $scope.searchPageFieldInput = selectList.searchPageFieldInput;
                        $scope.searchDataType = selectList.searchDataType;
                    }
                    else {
                        bootbox.alert(response.message);
                    }
                });

        if ($routeParams.id != null) {
            var url = "<c:url value="/core/search/get"/>" + "?id=" + $routeParams.id;
            $http.get(url)
                    .success(function (response) {
                        if (response.success) {
                            $scope.searchBo = response.data;
                            $scope.canSave=(PrivilegeService.hasPrivilege('add')&&$scope.searchBo.id==null)||PrivilegeService.hasPrivilege('update');
                        }
                        else {
                            bootbox.alert(response.message);
                        }
                    }).error(function (response) {
                bootbox.alert(response);
            });
        }
        $scope.create = function () {
            var url = "<c:url value="/core/search/get"/>";
            $http.get(url)
                    .success(function (response) {
                        if (response.success) {
                            $scope.searchBo = response.data;
                        }
                        else {
                            bootbox.alert(response.message);
                        }
                    }).error(function (response) {
                bootbox.alert(response);
            });
        }

        $scope.save = function () {
            if ($scope.searchForm.$valid) {
                var postUrl = '<c:url value="/core/search/saveForUpdated"/>';
                if ($scope.searchBo.id == null) {
                    postUrl = '<c:url value="/core/search/saveForCreated"/>';
                }
                $http.post(postUrl, $scope.searchBo).success(function (data) {
                    if (data.success) {
                        bootbox.alert("<eidea:label key="base.save.success"/>");
                        console.log("id=" + data.data.id);
                        $scope.searchBo = data.data;
                    }
                    else {
                        bootbox.alert(data.message);
                    }

                });
            }
        }
        $scope.addOneColumn = function () {
            $http.get("<c:url value="/core/search/addOneColumn"/>")
                    .success(function (response) {
                        if (response.success) {
                            $scope.searchBo.searchColumnBoList.push(response.data);
                        }
                        else {
                            bootbox.alert(response.message);
                        }
                    }).error(function (response) {
                bootbox.alert(response);
            });

        }
        $scope.selectAll = function () {
            //$scope.delFlag=!$scope.delFlag;
            if ($scope.searchBo.searchColumnBoList != null) {
                for (var i = 0; i < $scope.searchBo.searchColumnBoList.length; i++) {
                    var item = $scope.searchBo.searchColumnBoList[i];
                    item.delFlag = $scope.delFlag;
                }
            }

        }
        $scope.deleteColumns = function () {
            if ($scope.searchBo.searchColumnBoList != null) {
                for (var i = $scope.searchBo.searchColumnBoList.length - 1; i >= 0; i--) {
                    var item = $scope.searchBo.searchColumnBoList[i];
                    if (item.delFlag) {
                        $scope.searchBo.searchColumnBoList.splice(i, 1);
                    }
                }
            }
        }
        $scope.canDelete = function () {
            if ($scope.searchBo.searchColumnBoList != null)
                for (var i = 0; i < $scope.searchBo.searchColumnBoList.length; i++) {
                    if ($scope.searchBo.searchColumnBoList[i].delFlag) {
                        return true;
                    }
                }
            return false;
        }
        //附件上传
        $scope.showAttachment=function () {
            if($scope.searchBo.id != null){
                $("#attachmentModal").modal('show');
                $scope.tableId=$scope.searchBo.id;
                $scope.directoryUrl="/core";
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
