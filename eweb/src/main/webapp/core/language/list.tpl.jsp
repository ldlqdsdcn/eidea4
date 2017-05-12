<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="listCtrl">
    <div class="page-header button-css">
        <a href="#/edit" class="btn  btn-primary btn-sm btn-hg" ng-show="canAdd" title="<eidea:label key="common.button.create"/>"><%--新建--%>
            <i class="fa fa-file-o fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.create"/></a>
        <button type="button" class="btn  btn-primary btn-sm btn-hg" id="search_but" data-toggle="modal"
                data-target="#searchModal" title="<eidea:label key="common.button.search"/>"><%--查找--%>
            <i class="fa fa-search fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.search"/>
        </button>
        <button type="button" class="btn  btn-primary btn-sm btn-hg" ng-disabled="!canDelete()"
                ng-click="deleteRecord()" ng-show="canDel" title="<eidea:label key="common.button.delete"/>"><%--删除--%>
            <i class="fa fa-trash-o fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.delete"/>
        </button>
        <div class="btn-group">
            <button type="button" class="btn btn-primary btn-sm btn-hg" data-toggle="dropdown" title="<eidea:label key="common.button.export"/>">
                <i class="fa fa-download fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.export"/>
                <span class="caret"></span>
            </button>
            <ul class="dropdown-menu" role="menu">
                <li><a href="#" class="list-group-item"><i class="fa fa-file-archive-o fa-1x" aria-hidden="true"></i>&nbsp;csv</a></li>
                <li><a href="#" class="list-group-item"><i class="fa fa-file-excel-o fa-1x" aria-hidden="true"></i>&nbsp;excel</a></li>
                <li><a href="#" class="list-group-item"><i class="fa fa-file-pdf-o fa-1x" aria-hidden="true"></i>&nbsp;pdf</a></li>
                <li><a href="#" class="list-group-item"><i class="fa fa-file-archive-o fa-1x" aria-hidden="true"></i>&nbsp;rtf</a></li>
                <li><a href="#" class="list-group-item"><i class="fa fa-file-word-o fa-1x" aria-hidden="true"></i>&nbsp;word</a></li>
                <li><a href="#" class="list-group-item"><i class="fa fa-file-archive-o fa-1x" aria-hidden="true"></i>&nbsp;xml</a></li>
            </ul>
        </div>
        <button type="button" class="btn  btn-primary btn-sm btn-hg" title="<eidea:label key="common.button.print"/>">
            <i class="fa fa-print fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.print"/>
        </button>
        <button type="button" class="btn  btn-primary btn-sm btn-hg" title="<eidea:label key="common.button.refresh"/>">
            <i class="fa fa-refresh fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.refresh"/>
        </button>
        <button type="button" class="btn  btn-primary btn-sm btn-hg" title="<eidea:label key="common.button.help"/>">
            <i class="fa fa-question fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.help"/>
        </button>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <table class="table table-hover table-striped table-condensed">
                <thead>
                <tr>
                    <th><input type="checkbox" name="selectAll" style="margin:0px;" ng-change="selectAll()"
                               ng-model="delFlag"></th>
                    <th><%--序号--%><eidea:label key="base.serialNumber"/></th>
                    <th><%--code--%><eidea:label key="language.column.code"/></th>
                    <th><%--name--%><eidea:label key="datadict.column.name"/></th>
                    <th><%--语言码--%><eidea:label key="language.language.iso"/></th>
                    <th><%--区域码--%><eidea:label key="language.country.code"/></th>
                    <th><%--remark--%><eidea:label key="base.remarks"/></th>
                    <th><%--是否有效--%><eidea:label key="base.datadict.label.isactive"/></th>
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
                        {{model.code}}
                    </td>
                    <td>
                        {{model.name}}
                    </td>
                    <td>
                        {{model.languageIso}}
                    </td>
                    <td>
                        {{model.countryCode}}
                    </td>
                    <td>
                        {{model.remark}}
                    </td>
                    <td>
                        {{model.isactive}}
                    </td>
                    <td>
                        <a class="btn btn-primary btn-xs" href="#/edit?code={{model.code}}"><%--编辑--%>
                            <i class="fa fa-pencil-square-o" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.edit"/>
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