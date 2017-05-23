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
                    <th><%--ID--%><eidea:label key="workflow.model.column.id"/></th>
                    <th><%--KEY--%><eidea:label key="workflow.model.column.key"/></th>
                    <th><%--Name--%><eidea:label key="workflow.model.column.name"/></th>
                    <th><%--Version--%><eidea:label key="workflow.model.column.version"/></th>
                    <th><%--创建时间--%><eidea:label key="workflow.model.column.createtime"/></th>
                    <th><%--最后更新时间--%><eidea:label key="workflow.model.column.lastupdatetime"/></th>
                    <th><%--元数据--%><eidea:label key="workflow.model.column.metadata"/></th>
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
                        <%--导出--%><eidea:label key="workflow.model.label.expert"/>(<a href="<c:url value="/sys/model/export"/>/{{model.id}}/bpmn">
                            <%--BPMN--%><eidea:label key="workflow.model.label.bpmn"/></a>
                        |&nbsp;<a href="<c:url value="/sys/model/export"/>/{{model.id}}/json">
                            <%--JSON--%><eidea:label key="workflow.model.label.json"/></a>
                        )
                         <a  href="<c:url value="/sys/workflow/modeler.jsp"/>?modelId={{model.id}}" class="btn btn-primary btn-xs">
                             <i class="fa fa-pencil-square-o" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.edit"/><%--编辑--%>
                         </a>

                         <%--<button class="btn btn-danger btn-xs" ng-click="removeModel(model.id)"><eidea:label key="common.button.delete"/></button>--%>
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