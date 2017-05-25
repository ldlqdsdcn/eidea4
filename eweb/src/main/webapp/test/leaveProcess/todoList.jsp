<%--
  Created by IntelliJ IDEA.
  User: 刘大磊
  Date: 2017/5/23
  Time: 9:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/inc/taglib.jsp" %>
<html>
<head>
    <title><%--用户Session--%><eidea:label key="leave.title"/></title>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@include file="/inc/inc_ang_js_css.jsp" %>
    <%@include file="/common/common_header.jsp" %>
</head>
<body>
<div ng-app='myApp' ng-view class="content">
    <div  class="container-fluid" ng-controller="listCtrl">
        <jsp:include page="/common/common_list_button.jsp"/>
        <div class="row-fluid">
            <div class="span12">
                <table  class="table table-hover table-striped table-condensed">
                    <thead>
                    <tr>
                        <th><input type="checkbox" name="selectAll" style="margin:0px;" ng-change="selectAll()"  ng-model="delFlag"></th>
                        <th><%--序号--%><eidea:label key="base.serialNumber"/></th>
                        <th><%--标题--%><eidea:label key="test.leave.label.title"/></th>
                        <th><%--content--%><eidea:label key="test.leave.label.content"/></th>
                        <th><%--开始时间--%><eidea:label key="test.leave.label.bgnTime"/></th>
                        <th><%--结束时间--%><eidea:label key="test.leave.label.endTime"/></th>
                        <th><%--leaveUserId--%><eidea:label key="test.leave.label.leaveUserId"/></th>
                        <th><%--状态--%><eidea:label key="test.leave.label.state"/></th>
                        <th><%--任务创建时间--%><eidea:label key="test.leave.label.task.creation.time"/></th>
                        <th><%--suspended--%><eidea:label key="test.leave.label.suspended"/></th>
                        <th><%--assignee--%><eidea:label key="test.leave.label.assignee"/></th>

                        <th><%--编辑--%><eidea:label key="common.button.edit"/></th>
                    </tr>
                    </thead>
                    <tbody>

                    <tr ng-repeat="model in modelList track by $index" ng-class-even="success">
                        <td>
                            <input type="checkbox" ng-model="model.delFlag">
                        </td>
                        <td>{{(queryParams.pageNo-1)*queryParams.pageSize+$index+1}}</td>
                        <td>
                            {{model.title}}
                        </td>
                        <td>
                            {{model.content}}
                        </td>
                        <td>
                            {{model.bgnTime|date:"yyyy-MM-dd HH:mm:ss"}}
                        </td>
                        <td>
                            {{model.endTime|date:"yyyy-MM-dd HH:mm:ss"}}
                        </td>
                        <td>
                            {{model.userName}}
                        </td>
                        <td>
                            <%--<a target="_blank" href='<c:url value="/common/activiti/show/trace/"/>{{model.processInstanceId}}'  title="<eidea:label key="leave.title.click.view.flow.chart"/>">{{model.taskName }}</a>--%>
                                <a href='javascript:void(0);'  title="<eidea:label key="leave.title.click.view.flow.chart"/>" ng-click="openImage(model.processInstanceId)">{{model.taskName }}</a>
                        </td>
                        <td>{{model.taskCreateTime }}</td>
                        <td>{{model.suspended ? "<eidea:label key="leave.label.suspend"/>" : "<eidea:label key="leave.label.normal"/>" }}；<b title='<eidea:label key="leave.label.process.version"/>'>V: {{model.version }}</b></td>
                        <td>{{model.assignee }}</td>
                        <td ng-show="model.assignee!=null&&model.assignee!=''">

                            <button class="btn btn-primary btn-sm" ng-click="approve(model.taskId)"><%--同意--%>
                                <i class="fa fa-check fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.agree"/>
                            </button>
                            <button class="btn btn-primary btn-sm" ng-click="reject(model.taskId)"><%--拒绝--%>
                                <i class="fa fa-close fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.refuse"/>
                            </button>
                        </td>
                        <td ng-show="model.assignee==null||model.assignee==''">
                            <button class="btn btn-primary btn-sm" ng-click="claim(model.taskId)"><%--签收--%>
                                <i class="fa fa-pencil fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.sign"/>
                            </button>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <ul uib-pagination boundary-links="true" total-items="queryParams.totalRecords" ng-model="queryParams.pageNo"
                    max-size="maxSize" first-text="<eidea:label key="common.label.firstpage"/>" previous-text="<eidea:label key="common.label.previouspage"/>" next-text="<eidea:label key="common.label.nextpage"/>" last-text="<eidea:label key="common.label.lastpage"/>"
                    class="pagination-sm" boundary-link-numbers="true" rotate="false" items-per-page="queryParams.pageSize"
                    ng-change="pageChanged()"></ul>
                <div class="text-left ng-binding padding_total_banner"><eidea:message key="common.msg.result.prefix"/><span>{{queryParams.totalRecords}}</span><eidea:message key="common.msg.result.suffix"/></div>
            </div>
        </div>
    </div>
    <jsp:include page="/common/common_upload.jsp"/>
</div>
<jsp:include page="/common/searchPage">
    <jsp:param name="uri" value="${uri}"/>
</jsp:include>
</body>
<script type="text/javascript">
    var app = angular.module('myApp', ['ngFileUpload','ui.bootstrap', 'jcs-autoValidate']);
    app.controller('listCtrl', function ($rootScope,$scope,$http,$window) {
        $scope.allList = [];
        $scope.modelList = [];
        $scope.delFlag = false;
        $scope.canDel=PrivilegeService.hasPrivilege('delete');
        $scope.canAdd=PrivilegeService.hasPrivilege('add');
        $http.get("<c:url value="/test/leaveProcess/todoList"/>")
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
        $scope.approve=function(taskId)
        {
            bootbox.confirm({
                message: "<eidea:message key="leave.process.confirm.consent.audit"/>",
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
                        $http.post("<c:url value="/test/leaveProcess/complete/"/>"+taskId+"/"+true).success(function (data) {
                            if (data.success) {
                                bootbox.alert({"message":"<eidea:message key="leave.msg.approval"/>","callback": function() {

                                    window.location.href="<c:url value="/test/leaveProcess/showTodoList"/>";
                                }});
                            }
                            else {
                                bootbox.alert(data.message);
                            }

                        });
                    }
                }
            });
        };
        $scope.reject=function(taskId)
        {
            bootbox.confirm({
                message: "<eidea:message key="leave.process.confirm.refusal.audit"/>",
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
                        $http.post("<c:url value="/test/leaveProcess/complete/"/>"+taskId+"/"+false).success(function (data) {
                            if (data.success) {
                                bootbox.alert({"message":"<eidea:message key="leave.msg.approval.reject"/>","callback": function() {
                                    window.location.href="<c:url value="/test/leaveProcess/showTodoList"/>";
                                }});
                            }
                            else {
                                bootbox.alert(data.message);
                            }

                        });
                    }
                }
            });
        }
        $scope.claim=function(taskId)
        {
            bootbox.confirm({
                message: "<eidea:message key="leave.process.confirm.confirm.sign"/>",
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
                        $http.post("<c:url value="/test/leaveProcess/taskClaim/"/>"+taskId+"").success(function (data) {
                            if (data.success) {
                                bootbox.alert({"message":"<eidea:message key="leave.msg.sign.success"/>","callback": function() {
                                    window.location.href="<c:url value="/test/leaveProcess/showTodoList"/>";
                                }});
                            }
                            else {
                                bootbox.alert(data.message);
                            }
                        });
                    }
                }
            });
        };
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
                                ids.push($scope.modelList[i].id);
                            }
                        }
                        $http.post("<c:url value="/test/leave/deletes"/>", ids).success(function (data) {
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
        $scope.openImage=function (id) {
            $("#imageShowModal").modal("show");
            $("#imageShow").attr("src",'<c:url value="/common/activiti/process/trace/auto/"/>'+id);
        }
        buttonHeader.listInit($scope,$window);
    });
</script>
</html>
