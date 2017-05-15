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
    <title><%--语言维护--%><eidea:label key="language.title"/></title>
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
    var app = angular.module('myApp', ['ngFileUpload','ngRoute', 'ui.bootstrap', 'jcs-autoValidate'])
        .config(['$routeProvider', function ($routeProvider) {
            $routeProvider
                .when('/list', {templateUrl: '<c:url value="/core/language/list.tpl.jsp"/>'})
                .when('/edit', {templateUrl: '<c:url value="/core/language/edit.tpl.jsp"/>'})
                .otherwise({redirectTo: '/list'});
        }]);
    var version = '11.1.3';
    app.controller('listCtrl', function ($scope,$rootScope,$http) {
        $scope.modelList = [];
        $scope.delFlag = false;
        $scope.isLoading=true;
        $scope.canDel=PrivilegeService.hasPrivilege('delete');
        $scope.canAdd=PrivilegeService.hasPrivilege('add');
        $http.post("<c:url value="/core/language/list"/>",$scope.queryParams).success(function (response) {
            $scope.isLoading=false;
            if (response.success) {
                $scope.updateList(response.data);
            } else {
                bootbox.alert({
                    buttons:{
                        ok:{
                            label: '<i class="fa fa-close" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.closed"/>',
                            className: 'btn-primary'
                        }
                    },
                    message: response.message
                })
            }
        });
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
            $http.post("<c:url value="/core/language/list"/>", $scope.queryParams)
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
                                ids.push($scope.modelList[i].code);
                            }
                        }
                        $scope.queryParams.init = true;
                        var param = {"queryParams": $scope.queryParams, "ids": ids};
                        $http.post("<c:url value="/core/language/deletes"/>", param).success(function (data) {
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
    app.controller('editCtrl', function ($scope,$rootScope,$http, $routeParams, $timeout, Upload) {
        $scope.message = '';
        $scope.languageBo = {};
        $scope.canAdd = PrivilegeService.hasPrivilege('add');
        $scope.canSave = false;
        var url = "<c:url value="/core/language/create"/>";
        if ($routeParams.code != null) {
            url = "<c:url value="/core/language/get"/>" + "?code=" + $routeParams.code;
        }
        $http.get(url)
            .success(function (response) {
                if (response.success) {
                    $scope.languageBo = response.data;
                    $scope.canSave = (PrivilegeService.hasPrivilege('add') && $scope.languageBo.created) || PrivilegeService.hasPrivilege('update');
                }
                else {
                    bootbox.alert({
                        buttons: {
                            ok: {
                                label: '<i class="fa fa-close" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.closed"/>',
                                className: 'btn-primary'
                            }
                        },
                        message: response.message,
                    });
                }
            }).error(function (response) {
            bootbox.alert({
                buttons: {
                    ok: {
                        label: '<i class="fa fa-close" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.closed"/>',
                        className: 'btn-primary'
                    }
                },
                message: response,
            });
        });
        $scope.save = function () {
            var code = /^[a-z]{2}_[A-Z]{2}$/;
            if (!code.test($scope.languageBo.code)) {
                $scope.message = "<eidea:label key="language.code.pattern.error"/>";
                return false;
            }
            var code = /^[a-z]{2}$/;
            if (!code.test($scope.languageBo.languageIso)) {
                $scope.message = "<eidea:label key="language.iso.pattern.error"/>";
                return false;
            }
            var code = /^[A-Z]{2}$/;
            if (!code.test($scope.languageBo.countryCode)) {
                $scope.message = "<eidea:label key="language.country.pattern.error"/>";
                return false;
            }

            if ($scope.editForm.$valid) {
                var postUrl = '<c:url value="/core/language/saveForUpdated"/>';
                if ($scope.languageBo.created) {
                    postUrl = '<c:url value="/core/language/saveForCreated"/>';
                }
                $http.post(postUrl, $scope.languageBo).success(function (data) {
                    if (data.success) {
                        $scope.message = "<eidea:label key="success.save"/>";
                        //bootbox.alert("保存成功");
                        $scope.languageBo = data.data;
                    }
                    else {
                        $scope.message = data.message;
                    }


                });
            }
        }
        $scope.create = function () {
            $scope.message = "";
            $scope.languageBo = {};
            var url = "<c:url value="/core/language/create"/>";
            $http.get(url)
                .success(function (response) {
                    if (response.success) {
                        $scope.languageBo = response.data;
                    }
                    else {
//                            bootbox.alert(response.message);
                        bootbox.alert({
                            buttons: {
                                ok: {
                                    label: '<i class="fa fa-close" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.closed"/>',
                                    className: 'btn-primary'
                                }
                            },
                            message: response.message,
                        });
                    }
                }).error(function (response) {
//                        bootbox.alert(response);
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '<i class="fa fa-close" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.closed"/>',
                            className: 'btn-primary'
                        }
                    },
                    message: response,
                });
            });
        }

        //附件上传
        $scope.showAttachment=function () {
            $("#attachmentModal").modal('show');
            $scope.tableId=$scope.languageBo.code;
            $scope.tableId=1;
            $http.post("<c:url value="/common/attachmentUpload"/>").success(function (data) {
                if (data.success) {
                    $scope.attachmentList = data.data;
                }else {
                    $scope.message = data.message;
                }
            });
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
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '<i class="fa fa-close" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.closed"/>',
                            className: 'btn-primary'
                        }
                    },
                    message: '<eidea:message key="common.upload.select.attachment"/>',
                });return;
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
            file.upload =Upload.upload({
                //服务端接收
                url: "<c:url value="/common/attachmentUpload"/>",
                data: {'fileKeyword':$scope.commonFileBo==null?null:$scope.commonFileBo.fileKeyword,"fileAbstract":$scope.commonFileBo==null?null:$scope.commonFileBo.fileAbstract,
                    "directoryUrl":"/core","tableId":$scope.tableId,"uri":"${uri}"},
                //上传的文件
                file: file
            }).success(function (data, status, headers, config) {
                //上传成功
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '<i class="fa fa-close" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.closed"/>',
                            className: 'btn-primary'
                        }
                    },
                    message: '<eidea:message key="common.upload.success"/>',
                });
                $scope.attachmentList = data.data;
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
                        $http.post("<c:url value="/common/attachmentDelete"/>",{"id":id}).success(function (data) {
                            if (data.success) {
                                bootbox.alert({
                                    buttons: {
                                        ok: {
                                            label: '<i class="fa fa-close" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.closed"/>',
                                            className: 'btn-primary'
                                        }
                                    },
                                    message: '<eidea:message key="common.upload.delete.success"/>',
                                });
                                $scope.attachmentList = data.data;
                            }else {
                                $scope.message = data.message;
                            }
                        });
                    }
                }
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
