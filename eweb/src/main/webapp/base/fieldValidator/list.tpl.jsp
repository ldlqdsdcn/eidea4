<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div  class="container-fluid" ng-controller="listFieldValidatorCtrl">
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
                    <th><%--fieldId--%><eidea:label key="base.fieldValidator.label.fieldId"/></th>
                    <th><%--validateType--%><eidea:label key="base.fieldValidator.label.validateType"/></th>
                    <th><%--maxValue--%><eidea:label key="base.fieldValidator.label.maxValue"/></th>
                    <th><%--minValue--%><eidea:label key="base.fieldValidator.label.minValue"/></th>
                    <th><%--maxDate--%><eidea:label key="base.fieldValidator.label.maxDate"/></th>
                    <th><%--minDate--%><eidea:label key="base.fieldValidator.label.minDate"/></th>
                    <th><%--maxLength--%><eidea:label key="base.fieldValidator.label.maxLength"/></th>
                    <th><%--minLength--%><eidea:label key="base.fieldValidator.label.minLength"/></th>
                    <th><%--validatorPatten--%><eidea:label key="base.fieldValidator.label.validatorPatten"/></th>
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
                        {{model.fieldId}}
                    </td>
                    <td>
                        {{model.validateType}}
                    </td>
                    <td>
                        {{model.maxValue}}
                    </td>
                    <td>
                        {{model.minValue}}
                    </td>
                    <td>
                        {{model.maxDate|date:"yyyy-MM-dd HH:mm:ss"}}
                    </td>
                    <td>
                        {{model.minDate|date:"yyyy-MM-dd HH:mm:ss"}}
                    </td>
                    <td>
                        {{model.maxLength}}
                    </td>
                    <td>
                        {{model.minLength}}
                    </td>
                    <td>
                        {{model.validatorPatten}}
                    </td>
                    <td>
                        <button type="button" class="btn btn-primary btn-xs" ng-click="edit(model.id)"><eidea:label key="common.button.edit"/></button>
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