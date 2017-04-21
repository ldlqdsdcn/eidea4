<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div  class="container-fluid" ng-controller="listCtrl">
    <div class="page-header" >
        <ol class="breadcrumb">
            <li><a href="javascript:;"><i class="icon icon-tasks"></i><eidea:label key="userSession2.title"/></a></li>
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
                    <th><input type="checkbox" name="selectAll" style="margin:0px;" ng-change="selectAll()"
                               ng-model="delFlag"></th>
                    <th><%--序号--%><eidea:label key="base.serialNumber"/></th>
                    <th><%--sessionId--%><eidea:label key="sys.userSession2.label.sessionId"/></th>
                    <th><%--loginDate--%><eidea:label key="sys.userSession2.label.loginDate"/></th>
                    <th><%--logoutDate--%><eidea:label key="sys.userSession2.label.logoutDate"/></th>
                    <th><%--remoteAddr--%><eidea:label key="sys.userSession2.label.remoteAddr"/></th>
                    <th><%--remoteHost--%><eidea:label key="sys.userSession2.label.remoteHost"/></th>
                    <th><%--sysUserId--%><eidea:label key="sys.userSession2.label.sysUserId"/></th>
                    <th><%--token--%><eidea:label key="sys.userSession2.label.token"/></th>
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
                        {{model.sessionId}}
                    </td>
                    <td>
                        {{model.loginDate|date:"yyyy-MM-dd HH:mm:ss"}}
                    </td>
                    <td>
                        {{model.logoutDate|date:"yyyy-MM-dd HH:mm:ss"}}
                    </td>
                    <td>
                        {{model.remoteAddr}}
                    </td>
                    <td>
                        {{model.remoteHost}}
                    </td>
                    <td>
                        {{model.sysUserId}}
                    </td>
                    <td>
                        {{model.token}}
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
            <div class="text-left"><eidea:message key="common.msg.result.prefix"/>{{queryParams.totalRecords}}<eidea:message key="common.msg.result.suffix"/></div>
        </div>
    </div>
</div>