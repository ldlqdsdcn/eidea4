<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div  class="container-fluid" ng-controller="listCtrl">
    <div class="page-header" >
        <ol class="breadcrumb">
            <li><a href="javascript:;"><i class="icon icon-tasks"></i><eidea:label key="commonFile.title"/></a></li>
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
                    <th><%--filename--%><eidea:label key="base.commonFile.label.filename"/></th>
                    <th><%--fileType--%><eidea:label key="base.commonFile.label.fileType"/></th>
                    <th><%--path--%><eidea:label key="base.commonFile.label.path"/></th>
                    <th><%--extension--%><eidea:label key="base.commonFile.label.extension"/></th>
                    <th><%--fileSize--%><eidea:label key="base.commonFile.label.fileSize"/></th>
                    <th><%--fileCreated--%><eidea:label key="base.commonFile.label.fileCreated"/></th>
                    <th><%--fileUpdated--%><eidea:label key="base.commonFile.label.fileUpdated"/></th>
                    <th><%--fileIsreadonly--%><eidea:label key="base.commonFile.label.fileIsreadonly"/></th>
                    <th><%--fileIshidden--%><eidea:label key="base.commonFile.label.fileIshidden"/></th>
                    <th><%--创建时间--%><eidea:label key="base.commonFile.label.created"/></th>
                    <th><%--commonFileSettingId--%><eidea:label key="base.commonFile.label.commonFileSettingId"/></th>
                    <th><%--fileAbstract--%><eidea:label key="base.commonFile.label.fileAbstract"/></th>
                    <th><%--fileKeyword--%><eidea:label key="base.commonFile.label.fileKeyword"/></th>
                    <th><%--fileMode--%><eidea:label key="base.commonFile.label.fileMode"/></th>
                    <th><%--fileBlob--%><eidea:label key="base.commonFile.label.fileBlob"/></th>
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
                        {{model.filename}}
                    </td>
                    <td>
                        {{model.fileType}}
                    </td>
                    <td>
                        {{model.path}}
                    </td>
                    <td>
                        {{model.extension}}
                    </td>
                    <td>
                        {{model.fileSize}}
                    </td>
                    <td>
                        {{model.fileCreated|date:"yyyy-MM-dd HH:mm:ss"}}
                    </td>
                    <td>
                        {{model.fileUpdated|date:"yyyy-MM-dd HH:mm:ss"}}
                    </td>
                    <td>
                        {{model.fileIsreadonly}}
                    </td>
                    <td>
                        {{model.fileIshidden}}
                    </td>
                    <td>
                        {{model.created|date:"yyyy-MM-dd HH:mm:ss"}}
                    </td>
                    <td>
                        {{model.commonFileSettingId}}
                    </td>
                    <td>
                        {{model.fileAbstract}}
                    </td>
                    <td>
                        {{model.fileKeyword}}
                    </td>
                    <td>
                        {{model.fileMode}}
                    </td>
                    <td>
                        {{model.fileBlob}}
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