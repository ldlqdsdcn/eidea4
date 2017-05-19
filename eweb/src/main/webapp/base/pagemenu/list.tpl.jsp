<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div  class="container-fluid" ng-controller="listCtrl">
    <jsp:include page="/common/common_list_button.jsp"/>
    <div class="row-fluid">
        <div class="span12">
            <table  class="table table-hover table-striped table-condensed">
                <thead>
                <tr>
                    <th><input type="hidden" name="selectAll" style="margin:0px;" ng-change="selectAll()"
                               ng-model="delFlag"></th>
                    <th><eidea:label key="custom.list.index"/></th>
                    <th><eidea:label key="dymenuForm.label.sequence"/></th>
                    <th><eidea:label key="dymenuForm.label.menutype"/></th>
                    <th><eidea:label key="dymenuForm.label.icon"/></th>
                    <th><eidea:label key="dymenu.label.menuname"/></th>
                    <th><eidea:label key="dymenu.label.chainedaddress"/></th>
                    <th><eidea:label key="base.remarks"/></th>
                    <th><eidea:label key="base.whetherEffective"/></th>
                    <th><eidea:label key="common.button.edit"/></th>
                </tr>
                </thead>
                <tbody>

                <tr ng-repeat="model in modelList track by $index" ng-class-even="success">
                    <td>
                        <input type="checkbox" ng-model="model.delFlag">
                    </td>
                    <td>{{(queryParams.pageNo-1)*queryParams.pageSize+$index+1}}</td>
                    <td>{{model.seqNo}}</td>
                    <td>
                        <span ng-if="model.menuType == 1"><eidea:label key="dymenu.label.menufolde"/></span>
                        <span ng-if="model.menuType == 2"><eidea:label key="dymenus.label.type.hyperlink"/></span>
                    </td>
                    <td>
                        {{model.icon}}
                    </td>
                    <td>
                        {{model.name}}
                    </td>
                    <td>
                        {{model.url}}
                    </td>
                    <td>
                        {{model.remark}}
                    </td>
                    <td>
                        {{model.isactive}}
                    </td>
                    <td>
                        <a class="btn btn-primary btn-xs" href="#/edit?id={{model.id}}">
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