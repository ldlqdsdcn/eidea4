<%--
  Created by 刘大磊.
  Date: 2017-04-26 15:54:01
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/inc/taglib.jsp" %>
<html>
<head>
    <title><%--数据字典类型--%><eidea:label key="datadictType.title"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file="/inc/inc_ang_js_css.jsp" %>
</head>
<body>
<div ng-app='myApp' ui-view class="content"></div>
<jsp:include page="/common/searchPage">
    <jsp:param name="uri" value="${uri}"/>
</jsp:include>
</body>
<script type="text/javascript">
    var app = angular.module('myApp', ['ui.router', 'ngFileUpload', 'ui.bootstrap', 'jcs-autoValidate'])
        .config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            $urlRouterProvider.otherwise('/list');
            $stateProvider
                .state('list', {
                    url: '/list',
                    templateUrl: '<c:url value="/base/datadictType/list.tpl.jsp"/>'
                })
                .state('edit', {
                    url: '/edit?id&dataType',
                    templateUrl: '<c:url value="/base/datadictType/edit.tpl.jsp"/>'
                })
                .state('edit.datadictList', {
                    url: '/datadictList',
                    templateUrl: '<c:url value="/base/datadict/list.tpl.jsp"/> '
                })
                .state('edit.datadictEdit', {
                    url: '/datadictEdit?datadictId',
                    templateUrl: '<c:url value="/base/datadict/edit.tpl.jsp"/> '
                })
        }]);
    app.controller('listCtrl', function ($scope, $http) {
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
            $http.post("<c:url value="/base/datadictType/list"/>", $scope.queryParams)
                .success(function (response) {
                    $scope.isLoading = false;
                    if (response.success) {
                        $scope.updateList(response.data);
                    }
                    else {
                        bootbox.alert(response.message);
                    }
                }).error(function (response) {
                bootbox.alert(response);
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
                        $http.post('<c:url value="/base/datadictType/deletes"/>', param).success(function (data) {
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
    app.controller('editCtrl', function ($scope,$http, $stateParams, $timeout, Upload) {
        $scope.message = '';
        $scope.datadictTypePo = {};
        $scope.canAdd = PrivilegeService.hasPrivilege('add');
        var url = "<c:url value="/base/datadictType/create"/>";
        if ($stateParams.id != null) {
            url = "<c:url value="/base/datadictType/get"/>" + "?id=" + $stateParams.id;
        }
        $http.get(url)
            .success(function (response) {
                if (response.success) {
                    $scope.datadictTypePo = response.data;
                    $scope.canSave = (PrivilegeService.hasPrivilege('add') && $scope.datadictTypePo.id == null) || PrivilegeService.hasPrivilege('update');
                }
                else {
                    bootbox.alert(response.message);
                }
            }).error(function (response) {
            bootbox.alert(response);
        });
        $scope.save = function () {
            $scope.message = "";
            if ($scope.editForm.$valid) {
                var postUrl = '<c:url value="/base/datadictType/saveForUpdated"/>';
                if ($scope.datadictTypePo.id == null) {
                    postUrl = '<c:url value="/base/datadictType/saveForCreated"/>';
                }
                $http.post(postUrl, $scope.datadictTypePo).success(function (data) {
                    if (data.success) {
                        $scope.message = "<eidea:label key="base.save.success"/>";
                        $scope.datadictTypePo = data.data;

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
            $scope.datadictTypePo = {};
            var url = "<c:url value="/base/datadictType/create"/>";
            $http.get(url)
                .success(function (response) {
                    if (response.success) {
                        $scope.datadictTypePo = response.data;
                        $scope.canSave = (PrivilegeService.hasPrivilege('add') && $scope.datadictTypePo.id == null) || PrivilegeService.hasPrivilege('update');
                    }
                    else {
                        bootbox.alert(response.message);
                    }
                }).error(function (response) {
                bootbox.alert(response);
            });
        }
        //附件上传
        $scope.showAttachment = function () {
            if ($scope.datadictTypePo.id != null) {
                $("#attachmentModal").modal('show');
                $scope.tableId = $scope.datadictTypePo.id;
                $scope.directoryUrl = "/base";
                $http.post("<c:url value="/common/attachmentList"/>", {
                    "tableId": $scope.tableId,
                    "uri": "${uri}",
                    "directoryUrl": $scope.directoryUrl
                }).success(function (data) {
                    if (data.success) {
                        $scope.attachmentList = data.data;
                    } else {
                        $scope.alert(data.message);
                    }
                });
            } else {
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
        $scope.attachmentUpload = function () {
            if ($scope.files == null) {
                $scope.alert('<eidea:message key="common.upload.select.attachment"/>');
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
        $scope.upload = function (file) {
            $scope.canUpload = true;
            file.upload = Upload.upload({
                //服务端接收
                url: "<c:url value="/common/attachmentUpload"/>",
                data: {
                    'fileKeyword': $scope.commonFileBo == null ? null : $scope.commonFileBo.fileKeyword,
                    "fileAbstract": $scope.commonFileBo == null ? null : $scope.commonFileBo.fileAbstract,
                    "directoryUrl": $scope.directoryUrl,
                    "tableId": $scope.tableId,
                    "uri": "${uri}"
                },
                //上传的文件
                file: file
            }).success(function (data, status, headers, config) {
                //上传成功
                $scope.alert('<eidea:message key="common.upload.success"/>');
                $scope.attachmentList = data.data;
                $scope.commonFileBo = null;
                $scope.files = null;
                $scope.canUpload = false;
            }).error(function (data, status, headers, config) {
                //上传失败
                console.log('error status: ' + status);
            });
        };
        $scope.attachmentDelete = function (id) {
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
                        $http.post("<c:url value="/common/attachmentDelete"/>", {
                            "id": id,
                            "tableId": $scope.tableId,
                            "uri": "${uri}",
                            "directoryUrl": $scope.directoryUrl
                        }).success(function (data) {
                            if (data.success) {
                                $scope.alert('<eidea:message key="common.upload.delete.success"/>');
                                $scope.attachmentList = data.data;
                            } else {
                                $scope.message = data.message;
                            }
                        });
                    }
                }
            });
        }
        $scope.alert = function (message) {
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
    app.controller('tabCtrl', function ($scope, $rootScope, $state) {
        $scope.showDatadict = function () {
            $rootScope.datadictShow = true;
            $rootScope.detailShow = false;
        }
        $scope.showDetail = function () {
            $rootScope.datadictShow = false;
            $rootScope.detailShow = true;
            $state.go('edit.datadictList');
        }
    });
    app.controller('listDetailCtrl', function ($scope, $http, $state,$stateParams) {
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
            $http.post("<c:url value="/base/datadict/detaillist"/>", $stateParams.dataType)
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
                        $http.post("<c:url value="/base/datadict/deletes"/>", param).success(function (data) {
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
       $scope.editDetail=function (id) {
           $state.go('edit.datadictEdit',{datadictId:id})
       }
    });
    app.controller('editDetailCtrl', function ($scope, $http, $stateParams, $state) {
        $scope.message = '';
        $scope.datadictPo = {};
        $scope.canAdd = PrivilegeService.hasPrivilege('add');
        var url = "<c:url value="/base/datadict/create"/>";
        if ($stateParams.datadictId != null) {
            url = "<c:url value="/base/datadict/get"/>" + "?id=" + $stateParams.datadictId;
        }
        $http.get(url)
            .success(function (response) {
                if (response.success) {
                    $scope.datadictPo = response.data;
                    $scope.getDatadictTypeList();
                    $scope.canSave = (PrivilegeService.hasPrivilege('add') && $scope.datadictPo.id == null) || PrivilegeService.hasPrivilege('update');
                }
                else {
                    bootbox.alert(response.message);
                }
            }).error(function (response) {
            bootbox.alert(response);
        });
        $scope.save = function () {
            if ($scope.editForm.$valid) {
                var postUrl = '<c:url value="/base/datadict/saveForUpdated"/>';
                if ($scope.datadictPo.id == null) {
                    postUrl = '<c:url value="/base/datadict/saveForCreated"/>';
                }
                $http.post(postUrl, $scope.datadictPo).success(function (data) {
                    if (data.success) {
                        $scope.message = "<eidea:label key="base.save.success"/>";
                        $scope.datadictPo = data.data;
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
            $scope.datadictPo = {};
            var url = "<c:url value="/base/datadict/create"/>";
            $http.get(url)
                .success(function (response) {
                    if (response.success) {
                        $scope.datadictPo = response.data;
                        $scope.getDatadictTypeList();
                        $scope.canSave = (PrivilegeService.hasPrivilege('add') && $scope.datadictPo.id == null) || PrivilegeService.hasPrivilege('update');
                    }
                    else {
                        bootbox.alert(response.message);
                    }
                }).error(function (response) {
                bootbox.alert(response);
            });
        };
//获取DatadictType列表
        $scope.getDatadictTypeList = function () {
            $http.post("<c:url value="/base/datadict/getDatadictTypeList"/> ").success(function (data) {
                if (data.success) {
                    $scope.datadictTypeList = data.data;
                }
            }).error(function (data) {
                bootbox.alert(data.message);
            })
        };
        $scope.backDetailList = function () {
            $state.go('edit.datadictList');
        }

    });
    app.run([
        'bootstrap3ElementModifier',
        function (bootstrap3ElementModifier) {
            bootstrap3ElementModifier.enableValidationStateIcons(true);
        }]);
</script>
</html>
