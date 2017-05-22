<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div  class="container-fluid" ng-controller="listCtrl">
    <div class="page-header" >
    </div>
    <div class="row-fluid">
        <div class="span12">
            <table  class="table table-hover table-striped table-condensed">
                <thead>
                <tr>
                    <th><eidea:label key="custom.list.index"/></th>
                    <th>sessionId</th>
                    <th><eidea:label key="user.user.name"/></th>
                    <th><eidea:label key="online.label.logindate"/></th>
                    <th>remoteAddr</th>
                    <th>remoteHost </th>
                </tr>
                </thead>
                <tbody>

                <tr ng-repeat="module in modelList track by $index" ng-class-even="success">
                    <td>{{(bigCurrentPage-1)*itemsPerPage+$index+1}}</td>
                    <td>
                        {{module.sessionId}}
                    </td>
                    <td>
                        {{module.username}}
                    </td>
                    <td>
                        {{module.loginDate|date:"yyyy-MM-dd HH:mm:ss"}}
                    </td>
                    <td>
                        {{module.remoteAddr}}
                    </td>
                    <td>
                        {{module.remoteHost}}
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