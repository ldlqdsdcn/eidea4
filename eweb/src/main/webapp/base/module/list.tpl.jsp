<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div  class="container-fluid" ng-controller="listCtrl">
    <jsp:include page="/common/common_list_button.jsp"/>
    <div class="row-fluid">
        <div class="span12">
            <table  class="table table-hover table-striped table-condensed">
                <thead>
                <tr>
                    <th><input type="checkbox" name="selectAll" style="margin:0px;" ng-change="selectAll()"
                               ng-model="delFlag"></th>
                    <th><eidea:label key="custom.list.index"/></th>
                    <th><eidea:label key="module.column.name"/></th>
                    <th><eidea:label key="base.remarks"/></th>
                    <th><eidea:label key="base.whetherEffective"/></th>
                    <th><eidea:label key="common.button.edit"/></th>
                </tr>
                </thead>
                <tbody>

                <tr ng-repeat="module in modelList track by $index" ng-class-even="success">
                    <td>
                        <input type="checkbox" ng-model="module.delFlag">
                    </td>
                    <td>{{(queryParams.pageNo-1)*queryParams.pageSize+$index+1}}</td>
                    <td>
                        {{module.name}}
                    </td>
                    <td>
                        {{module.remark}}
                    </td>
                    <td>
                        {{module.isactive}}
                    </td>
                    <td>
                        <a class="btn btn-primary btn-xs" href="#/edit?id={{module.id}}">
                            <i class="fa fa-pencil-square-o" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.edit"/>
                        </a>
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
<script type='text/javascript' src="<c:url value="/js/ondrag-start.js"/>"></script>