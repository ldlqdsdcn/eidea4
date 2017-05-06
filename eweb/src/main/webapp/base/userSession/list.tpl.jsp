<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div  class="container-fluid" ng-controller="listCtrl">
<div class="page-header" >
    <ol class="breadcrumb">
        <li><a href="javascript:;"><i class="icon icon-tasks"></i><eidea:label key="loginlog.label.log"/></a></li>
    </ol>
    <button type="button" class="btn  btn-primary btn-sm" id="search_but" data-toggle="modal"
            data-target="#searchModal"><eidea:label key="common.button.search"/></button>
</div>
<div class="row-fluid">
    <div class="span12">
        <table  class="table table-hover table-striped table-condensed">
            <thead>
            <tr>
                <th><eidea:label key="custom.list.index"/></th>
                <th><eidea:label key="loginlog.label.sessionId"/></th>
                <th><eidea:label key="loginlog.label.loginname"/></th>
                <th><eidea:label key="loginlog.label.logindate"/></th>
                <th><eidea:label key="loginlog.label.logoutdate"/></th>
                <th><eidea:label key="loginlog.label.ip"/></th>
                 <th><eidea:label key="loginlog.label.host"/></th>
            </tr>
            </thead>
            <tbody>

            <tr ng-repeat="model in modelList track by $index" ng-class-even="success">
                <td>{{(queryParams.pageNo-1)*queryParams.pageSize+$index+1}}</td>
                <td>
                    {{model.sessionId}}
                </td>
                 <td>
                    {{model.username}}
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
            </tr>
            <tr>
                <td colspan="6" class="text-center" ng-show="isLoading">
                    <i class='fa fa-spinner fa-pulse loading'></i>&nbsp;<eidea:message key="login.msg.logining"/>
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