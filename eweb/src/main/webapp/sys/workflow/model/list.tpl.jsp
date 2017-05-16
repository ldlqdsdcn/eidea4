<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div  class="container-fluid" ng-controller="listCtrl">
    <div class="page-header" >
        <ol class="breadcrumb">
            <li><a href="javascript:;"><i class="icon icon-tasks"></i><eidea:label key="leave.title"/></a></li>
        </ol>

        <a href="<c:url value="/sys/model/create"/>" class="btn  btn-primary btn-sm" ng-show="canAdd"><eidea:label key="common.button.create"/></a>
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

                    <th><%--序号--%><eidea:label key="base.serialNumber"/></th>
                    <th>ID</th>
                    <th>KEY</th>
                    <th>Name</th>
                    <th>Version</th>
                    <th>创建时间</th>
                    <th>最后更新时间</th>
                    <th>元数据</th>
                    <th><%--编辑--%><eidea:label key="common.button.edit"/></th>
                </tr>
                </thead>
                <tbody>

                <tr ng-repeat="model in modelList track by $index" ng-class-even="success">
                        <td>{{(queryParams.pageNo-1)*queryParams.pageSize+$index+1}}</td>
                    <td>
                        {{model.id}}
                    </td>
                    <td>
                        {{model.key}}
                    </td>
                    <td>
                    {{model.name}}
                </td>
                    <td>
                        {{model.version}}
                    </td>

                    <td>
                        {{model.createTime|date:"yyyy-MM-dd HH:mm:ss"}}
                    </td>
                    <td>
                        {{model.lastUpdateTime|date:"yyyy-MM-dd HH:mm:ss"}}
                    </td>
                    <td>
                        {{model.metaInfo}}
                    </td>
                    <td>
                        导出(<a href="<c:url value="/sys/model/export"/>/{{model.id}}/bpmn" target="_blank">BPMN</a>
                        |&nbsp;<a href="<c:url value="/sys/model/export"/>/{{model.id}}/json" target="_blank">JSON</a>
                        )
                         <a  href="<c:url value="/sys/workflow/modeler.jsp"/>?modelId={{model.id}}" class="btn btn-primary btn-xs"><eidea:label key="common.button.edit"/><%--编辑--%></a>

                         <button class="btn btn-danger btn-xs" ng-click="removeModel(model.id)"><eidea:label key="common.button.delete"/></button>
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