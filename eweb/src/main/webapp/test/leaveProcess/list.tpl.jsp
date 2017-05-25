<%--
  Created by IntelliJ IDEA.
  User: 刘大磊
  Date: 2017/5/18
  Time: 13:40
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
                    <th><%--标题--%><eidea:label key="test.leave.label.title"/></th>
                    <th><%--content--%><eidea:label key="test.leave.label.content"/></th>
                    <th><%--开始时间--%><eidea:label key="test.leave.label.bgnTime"/></th>
                    <th><%--结束时间--%><eidea:label key="test.leave.label.endTime"/></th>
                    <th><%--leaveUserId--%><eidea:label key="test.leave.label.leaveUserId"/></th>
                    <th>&nbsp;</th>
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
                        <%--<a target="_blank" href='<c:url value="/common/activiti/show/trace/"/>{{model.processInstanceId}}'  title="点击查看流程图">{{model.taskName }}</a>--%>
                        <a href='javascript:void (0);'  title="<eidea:label key="leave.title.click.view.flow.chart"/>" ng-click="openImage(model.processInstanceId)">{{model.taskName }}</a>
                    </td>
                    <td>{{model.taskCreateTime }}</td>
                    <td>{{model.suspended ? "<eidea:label key="leave.label.suspend"/>" : "<eidea:label key="leave.label.normal"/>" }}；<b title='<eidea:label key="leave.label.process.version"/>'>V: {{model.version }}</b></td>
                    <td>{{model.assignee }}</td>
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
