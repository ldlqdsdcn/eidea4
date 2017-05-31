<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div  class="container-fluid" ng-controller="listFieldTrlCtrl">
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
                    <th><%--lang--%><eidea:label key="base.fieldTrl.label.lang"/></th>
                    <th><%--fieldId--%><eidea:label key="base.fieldTrl.label.fieldId"/></th>
                    <th><%--名称--%><eidea:label key="base.fieldTrl.label.name"/></th>
                    <th><%--description--%><eidea:label key="base.fieldTrl.label.description"/></th>
                    <th><%--帮助--%><eidea:label key="base.fieldTrl.label.help"/></th>
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
                        {{model.lang}}
                    </td>
                    <td>
                        {{model.fieldId}}
                    </td>
                    <td>
                        {{model.name}}
                    </td>
                    <td>
                        {{model.description}}
                    </td>
                    <td>
                        {{model.help}}
                    </td>
                    <td>
                        <button type="button" class="btn btn-primary btn-xs" ng-click="edit(model.id)"><eidea:label key="common.button.edit"></eidea:label></button>
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