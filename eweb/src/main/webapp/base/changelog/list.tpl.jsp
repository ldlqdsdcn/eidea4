<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div  class="container-fluid" ng-controller="listCtrl">
<div class="page-header" >
    <ol class="breadcrumb">
        <li><a href="javascript:;"><i class="icon icon-tasks"></i> <eidea:label key="menu.changelog"/></a></li>
    </ol>
</div>
<div class="row-fluid">
    <div class="span12">
        <table  class="table table-hover table-striped table-condensed">
            <thead>
            <tr>
                <th><eidea:label key="custom.list.index"/></th>
                <th><eidea:label key="searchcolumn.column.tableName"/></th>
                <th><eidea:label key="changelog.business.name"/></th>
                <th><eidea:label key="changelog.primarykey"/></th>
                 <th><eidea:label key="table.column.buskey"/></th>
                 <th><eidea:label key="changelog.actiontypes"/></th>
                 <th><eidea:label key="user.user.name"/></th>
                 <th><eidea:label key="changelog.operatetime"/></th>
                 <th><eidea:label key="officeTestExam.button.view"/></th>
            </tr>
            </thead>
            <tbody>

            <tr ng-repeat="model in modelList track by $index" ng-class-even="success">
                <td>{{(bigCurrentPage-1)*itemsPerPage+$index+1}}</td>
                <td>
                    {{model.name}}
                </td>
                 <td>
                    {{model.tableName}}
                </td>
                <td>
                    {{model.pk}}
                </td>
                 <td>
                    {{model.buPk}}
                </td>
                 <td>
                    {{model.operateType}}
                </td>
                 <td>
                    {{model.sysUser}}
                </td>
                 <td>
                    {{model.inDate}}
                </td>
                <td>
                	<a class="btn btn-primary btn-xs" href="#/edit?id={{model.id}}"><eidea:label key="common.button.view"/></a>
                </td>
            </tr>
            <tr>
                <td colspan="6" class="text-center" ng-show="isLoading">
                    <i class='fa fa-spinner fa-pulse loading'></i>&nbsp;<eidea:message key="login.msg.logining"/>
                </td>
            </tr>
            </tbody>
        </table>
        <ul uib-pagination boundary-links="true" total-items="bigTotalItems" ng-model="bigCurrentPage"
            max-size="maxSize" first-text="<eidea:label key="common.label.firstpage"/>" previous-text="<eidea:label key="common.label.previouspage"/>" next-text="<eidea:label key="common.label.nextpage"/>" last-text="<eidea:label key="common.label.lastpage"/>"
            class="pagination-sm" boundary-link-numbers="true" rotate="false" items-per-page="itemsPerPage"
            ng-change="pageChanged()"></ul>
    </div>
</div>
</div>