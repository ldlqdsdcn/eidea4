<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div  class="container-fluid" ng-controller="listCtrl">
    <div class="page-header" >
        <ol class="breadcrumb">
            <li><a href="javascript:;"><i class="icon icon-tasks"></i><eidea:label key="field.title"/></a></li>
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
                    <th><%--seqNo--%><eidea:label key="base.field.label.seqNo"/></th>
                    <th><%--名称--%><eidea:label key="base.field.label.name"/></th>
                    <th><%--fieldName--%><eidea:label key="base.field.label.fieldName"/></th>
                    <th><%--columnId--%><eidea:label key="base.field.label.columnId"/></th>
                    <th><%--required--%><eidea:label key="base.field.label.required"/></th>
                    <th><%--是否有效--%><eidea:label key="base.field.label.isactive"/></th>
                    <th><%--创建时间--%><eidea:label key="base.field.label.created"/></th>
                    <th><%--创建人--%><eidea:label key="base.field.label.createdby"/></th>
                    <th><%--修改时间--%><eidea:label key="base.field.label.updated"/></th>
                    <th><%--修改人--%><eidea:label key="base.field.label.updatedby"/></th>
                    <th><%--description--%><eidea:label key="base.field.label.description"/></th>
                    <th><%--inputType--%><eidea:label key="base.field.label.inputType"/></th>
                    <th><%--isDisplayed--%><eidea:label key="base.field.label.isDisplayed"/></th>
                    <th><%--displayedlogic--%><eidea:label key="base.field.label.displayedlogic"/></th>
                    <th><%--displaylength--%><eidea:label key="base.field.label.displaylength"/></th>
                    <th><%--isreadonly--%><eidea:label key="base.field.label.isreadonly"/></th>
                    <th><%--issameline--%><eidea:label key="base.field.label.issameline"/></th>
                    <th><%--isencrypted--%><eidea:label key="base.field.label.isencrypted"/></th>
                    <th><%--defaultvalue--%><eidea:label key="base.field.label.defaultvalue"/></th>
                    <th><%--isdisplaygrid--%><eidea:label key="base.field.label.isdisplaygrid"/></th>
                    <th><%--seqnogrid--%><eidea:label key="base.field.label.seqnogrid"/></th>
                    <th><%--isallowcopy--%><eidea:label key="base.field.label.isallowcopy"/></th>
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
                        {{model.seqNo}}
                    </td>
                    <td>
                        {{model.name}}
                    </td>
                    <td>
                        {{model.fieldName}}
                    </td>
                    <td>
                        {{model.columnId}}
                    </td>
                    <td>
                        {{model.required}}
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
                        {{model.description}}
                    </td>
                    <td>
                        {{model.inputType}}
                    </td>
                    <td>
                        {{model.isDisplayed}}
                    </td>
                    <td>
                        {{model.displayedlogic}}
                    </td>
                    <td>
                        {{model.displaylength}}
                    </td>
                    <td>
                        {{model.isreadonly}}
                    </td>
                    <td>
                        {{model.issameline}}
                    </td>
                    <td>
                        {{model.isencrypted}}
                    </td>
                    <td>
                        {{model.defaultvalue}}
                    </td>
                    <td>
                        {{model.isdisplaygrid}}
                    </td>
                    <td>
                        {{model.seqnogrid}}
                    </td>
                    <td>
                        {{model.isallowcopy}}
                    </td>
                    <td>
                        <a class="btn btn-primary btn-xs" href="#/edit?id={{model.id}}"><eidea:label key="common.button.edit"/><%--编辑--%></a>
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