<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div  class="container-fluid" ng-controller="listTabCtrl">
    <div class="page-header" >
        <button type="button" class="btn btn-primary btn-sm" ng-show="canAdd" ng-click="create()"><eidea:label key="common.button.create"/></button>
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
                    <th><%--windowId--%><eidea:label key="base.tab.label.windowId"/></th>
                    <th><%--名称--%><eidea:label key="base.tab.label.name"/></th>
                    <th><%--等级--%><eidea:label key="base.tab.label.level"/></th>
                    <th><%--sortno--%><eidea:label key="base.tab.label.sortno"/></th>
                    <th><%--description--%><eidea:label key="base.tab.label.description"/></th>
                    <th><%--includedTabId--%><eidea:label key="base.tab.label.includedTabId"/></th>
                    <th><%--tableId--%><eidea:label key="base.tab.label.tableId"/></th>
                    <th><%--tableColumnId--%><eidea:label key="base.tab.label.tableColumnId"/></th>
                    <th><%--是否有效--%><eidea:label key="base.tab.label.isactive"/></th>
                    <th><%--创建时间--%><eidea:label key="base.tab.label.created"/></th>
                    <th><%--创建人--%><eidea:label key="base.tab.label.createdby"/></th>
                    <th><%--修改时间--%><eidea:label key="base.tab.label.updated"/></th>
                    <th><%--修改人--%><eidea:label key="base.tab.label.updatedby"/></th>
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
                        {{model.windowId}}
                    </td>
                    <td>
                        {{model.name}}
                    </td>
                    <td>
                        {{model.level}}
                    </td>
                    <td>
                        {{model.sortno}}
                    </td>
                    <td>
                        {{model.description}}
                    </td>
                    <td>
                        {{model.includedTabId}}
                    </td>
                    <td>
                        {{model.tableId}}
                    </td>
                    <td>
                        {{model.tableColumnId}}
                    </td>
                    <td>
                        {{model.isactive}}
                    </td>
                    <td>
                        {{model.created|date:"yyyy-MM-dd HH:mm:ss"}}
                    </td>
                    <td>
                        {{model.createdby}}
                    </td>
                    <td>
                        {{model.updated|date:"yyyy-MM-dd HH:mm:ss"}}
                    </td>
                    <td>
                        {{model.updatedby}}
                    </td>
                    <td>
                        <button class="btn btn-primary btn-xs" ng-click="edit(model.id,model.tableColumnId)"><eidea:label key="common.button.edit"/></button>
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