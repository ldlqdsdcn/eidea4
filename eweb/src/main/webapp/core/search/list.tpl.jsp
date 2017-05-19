<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div  class="container-fluid" ng-controller="listCtrl">
    <jsp:include page="/common/common_list_button.jsp"/>
    <div class="row-fluid">
        <div class="span12">
            <table class="table table-hover table-striped">
                <thead>
                <tr>
                    <th><input type="checkbox" name="selectAll" style="margin:0px;" ng-change="selectAll()"
                               ng-model="delFlag"></th>
                    <th><%--序号--%><eidea:label key="base.serialNumber"/></th>
                    <th><%--名称--%><eidea:label key="datadict.column.name"/></th>
                    <th><%--查询页类型--%><eidea:label key="search.page.type"/></th>
                    <th><%--标识符--%><eidea:label key="base.identifier"/></th>
                    <th><%--是否有效--%><eidea:label key="base.whetherEffective"/></th>
                    <th>remark</th>
                    <th><eidea:label key="common.button.edit"/><%--编辑--%></th>
                </tr>
                </thead>
                <tbody>
                <tr ng-repeat="model in modelList track by $index" ng-class-even="success">
                    <td>
                        <input type="checkbox" ng-model="model.delFlag">
                    </td>
                    <td>{{(queryParams.pageNo-1)*queryParams.pageSize+$index+1}}</td>
                    <td>
                        {{model.name}}
                    </td>
                    <td>
                        {{model.showTypeStr}}
                    </td>
                    <td>
                        {{model.uri}}
                    </td>
                    <td>
                        {{model.isactive}}
                    </td>
                    <td>
                        {{model.remark}}
                    </td>
                    <td>
                        <a class="btn btn-primary btn-xs" href="#/edit?id={{model.id}}">
                            <i class="fa fa-pencil-square-o" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.edit"/><%--编辑--%>
                        </a>
                    </td>
                </tr>
                <tr ng-show="$scope.isLoading">
                    <td colspan="9">
                        <i class='fa fa-spinner fa-pulse loading'></i>&nbsp;<eidea:message key="load.please.wait"/>
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
