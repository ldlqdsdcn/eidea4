<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div  class="container-fluid" ng-controller="listCtrl">
    <jsp:include page="/common/common_list_button.jsp"/>
    <div class="row-fluid">
        <div class="span12">
            <table  class="table table-hover table-striped table-condensed">
                <thead>
                <tr>
                    <th><input type="checkbox" name="selectAll" style="margin:0px;" ng-change="selectAll()"  ng-model="delFlag"></th>
                    <th><%--序号--%><eidea:label key="base.serialNumber"/></th>
                    <th><%--名称--%><eidea:label key="base.fileSetting.label.name"/></th>
                    <th><%--rootDirectory--%><eidea:label key="base.fileSetting.label.rootDirectory"/></th>
                    <th><%--fileSize--%><eidea:label key="base.fileSetting.label.fileSize"/></th>
                  <%--  <th>&lt;%&ndash;fileTypes&ndash;%&gt;<eidea:label key="base.fileSetting.label.fileTypes"/></th>
                    <th>&lt;%&ndash;storageMode&ndash;%&gt;<eidea:label key="base.fileSetting.label.storageMode"/></th>
                    <th>&lt;%&ndash;ftpcommectionId&ndash;%&gt;<eidea:label key="base.fileSetting.label.ftpcommectionId"/></th>--%>
                    <th><%--创建时间--%><eidea:label key="base.fileSetting.label.created"/></th>
                    <th><%--moduleId--%><eidea:label key="base.fileSetting.label.moduleId"/></th>
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
                        {{model.name}}
                    </td>
                    <td>
                        {{model.rootDirectory}}
                    </td>
                    <td>
                        {{model.fileSize}}
                    </td>
                  <%--  <td>
                        {{model.fileTypes}}
                    </td>
                    <td>
                        {{model.storageMode}}
                    </td>
                    <td>
                        {{model.ftpcommectionId}}
                    </td>--%>
                    <td>
                        {{model.created|date:"yyyy-MM-dd HH:mm:ss"}}
                    </td>
                    <td>
                        {{model.moduleId}}
                    </td>
                    <td>
                        <a class="btn btn-primary btn-xs" href="#/edit?id={{model.id}}">
                            <i class="fa fa-pencil-square-o" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.edit"/><%--编辑--%>
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