<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%----%>
<%@ include file="/inc/taglib.jsp" %>
<div class="page-header button-css">
    <button ng-click="edit()" class="btn  btn-primary btn-sm btn-hg" title="<eidea:label key="common.button.create"/>"><%--新建--%>
        <i class="fa fa-file-o fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.create"/></button>
    <button type="button" class="btn  btn-primary btn-sm btn-hg" id="search_but" data-toggle="modal"
            data-target="#searchModal" title="<eidea:label key="common.button.search"/>"><%--查找--%>
        <i class="fa fa-search fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.search"/>
    </button>
    <button type="button" class="btn  btn-primary btn-sm btn-hg" ng-click="deleteRecord()" title="<eidea:label key="common.button.delete"/>">
        <%--删除--%>
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
    <button type="button" class="btn  btn-primary btn-sm btn-hg" title="<eidea:label key="common.button.refresh"/>" ng-click="commonListHeader('refresh')">
        <i class="fa fa-refresh fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.refresh"/>
    </button>
    <button type="button" class="btn  btn-primary btn-sm btn-hg" title="<eidea:label key="common.button.help"/>">
        <i class="fa fa-question fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.help"/>
    </button>
</div>