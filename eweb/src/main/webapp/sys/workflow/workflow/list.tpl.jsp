<%--
  Created by IntelliJ IDEA.
  User: 刘大磊
  Date: 2017/5/17
  Time: 9:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div  class="container-fluid" ng-controller="listCtrl">
    <jsp:include page="/common/common_list_button.jsp"/>
    <div class="row-fluid">
        <div class="span12">
            <table  class="table table-hover table-striped table-condensed">
                <thead>
                <tr>
                    <th><input type="checkbox" name="selectAll" style="margin:0px;" ng-change="selectAll()"  ng-model="delFlag"></th>
                    <th><%--序号--%><eidea:label key="base.serialNumber"/></th>
                    <th><%--ProcessDefinitionId--%><eidea:label key="workflow.column.id"/></th>
                    <th><%--DeploymentId--%><eidea:label key="workflow.column.deploymentId"/></th>
                    <th><%--名称--%><eidea:label key="workflow.column.name"/></th>
                    <th><%--KEY--%><eidea:label key="workflow.column.key"/></th>
                    <th><%--版本号--%><eidea:label key="workflow.column.version"/></th>
                    <th><%--XML--%><eidea:label key="workflow.column.resourceName.xml"/></th>
                    <th><%--图片--%><eidea:label key="workflow.column.resourceName.image"/></th>
                    <th><%--部署时间--%><eidea:label key="workflow.column.deploymentTime"/></th>
                    <th><%--是否挂起--%><eidea:label key="workflow.column.suspended"/></th>
                    <th><%--编辑--%><eidea:label key="common.button.edit"/></th>
                </tr>
                </thead>
                <tbody>

                <tr ng-repeat="model in modelList track by $index" ng-class-even="success">
                    <td>
                        <input type="checkbox" ng-model="model.delFlag">
                    </td>
                    <td>{{(queryParams.pageNo-1)*queryParams.pageSize+$index+1}}</td>
                    <td>{{model.id }}</td>
                    <td>{{model.deploymentId }}</td>
                    <td>{{model.name }}</td>
                    <td>{{model.key }}</td>
                    <td>{{model.version }}</td>

                    <td><a href='<c:url value="/sys/workflow/resource/read"/>?processDefinitionId={{model.id}}&resourceType=xml'>{{model.resourceName}}</a></td>
                    <td><a href='javascript:void(0);' ng-click="openImage(model.id)">{{model.resourceName}}</a></td>
                    <td>{{model.deploymentTime|date:'yyyy-MM-dd HH:mm:ss' }}</td>
                    <td>{{model.suspended}} |
                        <a ng-if="model.suspended" href="javascript:void(0);" ng-click="updateSuspended('<eidea:message key="workflow.label.if.activation"/>','<c:url value="/sys/workflow/update/active/"/>',model.id)"><%--激活--%><eidea:label key="workflow.label.activation"/></a>
                        <a ng-if="!model.suspended" href="javascript:void(0);" ng-click="updateSuspended('<eidea:message key="workflow.label.if.hangUp"/>','<c:url value="/sys/workflow/update/suspend/"/>',model.id)"><%--挂起--%><eidea:label key="workflow.label.hangUp"/></a>
                    </td>
                    <td>
                        <a href='javascript:void(0);' ng-click="updateSuspended('<eidea:message key="workflow.label.if.convertToModel"/>','<c:url value="/sys/workflow/process/convert-to-model/"/>/',model.id)"><%--转换为Model--%><eidea:label key="workflow.label.convertToModel"/></a>
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