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
    <div class="page-header" >
        <ol class="breadcrumb">
            <li><a href="javascript:;"><i class="icon icon-tasks"></i><eidea:label key="leave.title"/></a></li>
        </ol>
        <a href="#/edit" class="btn  btn-primary btn-sm" ng-show="canAdd"><eidea:label key="common.button.create"/></a>
        <button type="button" class="btn  btn-primary btn-sm" id="search_but" data-toggle="modal"
                data-target="#searchModal"><eidea:label key="common.button.search"/></button>
        <button type="button" class="btn  btn-primary btn-sm" ng-disabled="!canDelete()"
                ng-click="deleteRecord()" ng-show="canDel" ><eidea:label key="common.button.delete"/></button>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <table  class="table table-hover table-striped table-condensed">
                <thead>
                <tr>
                    <th><input type="checkbox" name="selectAll" style="margin:0px;" ng-change="selectAll()"  ng-model="delFlag"></th>
                    <th><%--序号--%><eidea:label key="base.serialNumber"/></th>
                    <th>ProcessDefinitionId</th>
                    <th>DeploymentId</th>
                    <th>名称</th>
                    <th>KEY</th>
                    <th>版本号</th>
                    <th>XML</th>
                    <th>图片</th>
                    <th>部署时间</th>
                    <th>是否挂起</th>
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

                    <td><a target="_blank" href='<c:url value="/sys/workflow/resource/read"/>?processDefinitionId={{model.id}}&resourceType=xml'>{{model.resourceName}}</a></td>
                    <td><a target="_blank" href='<c:url value="/sys/workflow/resource/read"/>?processDefinitionId={{model.id}}&resourceType=image'>{{model.resourceName}}</a></td>
                    <td>{{model.deploymentTime|date:'yyyy-MM-dd HH:mm:ss' }}</td>
                    <td>{{model.suspended}} |
                            <a ng-if="model.suspended" href="<c:url value="/sys/workflow/update/active/"/>{{model.id}}">激活</a>
                            <a ng-if="!model.suspended" href="<c:url value="/sys/workflow/update/suspend/"/>{{model.id}}">挂起</a>
                    </td>
                    <td>
                        <a href='<c:url value="/sys/workflow/process/convert-to-model/"/>/{{model.id}}'>转换为Model</a>
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