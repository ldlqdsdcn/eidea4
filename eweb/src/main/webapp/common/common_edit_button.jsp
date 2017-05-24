<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="page-header button-css">
    <button type="reset" ng-click="create()" class="btn btn-primary btn-sm btn-hg" ng-show="canAdd"
            title="<eidea:label key="common.button.create"/>"><%--新建--%>
        <i class="fa fa-file-o fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.create"/>
    </button>
    <button type="submit" class="btn btn-primary btn-sm btn-hg" ng-show="canSave"
            title="<eidea:label key="common.button.save"/>"><%--保存--%>
        <i class="fa fa-save fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.save"/>
    </button>
    <a href="#/list" class="btn btn-primary btn-sm btn-hg"
       title="<eidea:label key="common.button.back"/>"><%--返回--%>
        <i class="fa fa-mail-reply fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label
                key="common.button.back"/></a>
    <button type="button" class="btn  btn-primary btn-sm btn-hg"
            title="<eidea:label key="common.button.home.page"/>">
        <i class="fa fa-step-backward fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label
            key="common.label.firstpage"/>
    </button>
    <button type="button" class="btn  btn-primary btn-sm btn-hg"
            title="<eidea:label key="common.button.previous.page"/>">
        <i class="fa fa-caret-left fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label
            key="common.label.previouspage"/>
    </button>
    <button type="button" class="btn  btn-primary btn-sm btn-hg"
            title="<eidea:label key="common.button.next.page"/>">
        <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label
            key="common.label.nextpage"/>
    </button>
    <button type="button" class="btn  btn-primary btn-sm btn-hg"
            title="<eidea:label key="common.button.last.page"/>">
        <i class="fa fa-step-forward fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label
            key="common.label.lastpage"/>
    </button>
    <button type="button" class="btn  btn-primary btn-sm btn-hg" title="<eidea:label key="common.button.copy"/>">
        <i class="fa fa-copy fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.copy"/>
    </button>
    <button type="button" class="btn  btn-primary btn-sm btn-hg"
            title="<eidea:label key="common.button.auditing"/>">
        <i class="fa fa-gavel fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label
            key="common.button.auditing"/>
    </button>
    <div class="btn-group">
        <button type="button" class="btn btn-primary btn-sm btn-hg" data-toggle="dropdown"
                title="<eidea:label key="common.button.export"/>">
            <i class="fa fa-download fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label
                key="common.button.export"/>
            <span class="caret"></span>
        </button>
        <ul class="dropdown-menu" role="menu">
            <li><a href="#" class="list-group-item"><i class="fa fa-file-archive-o fa-1x"
                                                       aria-hidden="true"></i>&nbsp;csv</a></li>
            <li><a href="#" class="list-group-item"><i class="fa fa-file-excel-o fa-1x"
                                                       aria-hidden="true"></i>&nbsp;excel</a></li>
            <li><a href="#" class="list-group-item"><i class="fa fa-file-pdf-o fa-1x"
                                                       aria-hidden="true"></i>&nbsp;pdf</a></li>
            <li><a href="#" class="list-group-item"><i class="fa fa-file-archive-o fa-1x"
                                                       aria-hidden="true"></i>&nbsp;rtf</a></li>
            <li><a href="#" class="list-group-item"><i class="fa fa-file-word-o fa-1x"
                                                       aria-hidden="true"></i>&nbsp;word</a></li>
            <li><a href="#" class="list-group-item"><i class="fa fa-file-archive-o fa-1x"
                                                       aria-hidden="true"></i>&nbsp;xml</a></li>
        </ul>
    </div>
    <button type="button" class="btn  btn-primary btn-sm btn-hg" title="<eidea:label key="common.button.print"/>">
        <i class="fa fa-print fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.print"/>
    </button>
    <button type="button" class="btn  btn-primary btn-sm btn-hg" ng-click="commonEditHeader('attachment')" title="<eidea:label key="common.button.attachment"/>">
        <i class="fa fa-ils fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label
            key="common.button.attachment"/>
    </button>
    <button type="button" class="btn  btn-primary btn-sm btn-hg"
            title="<eidea:label key="common.button.refresh"/>" ng-click="commonEditHeader('refresh')">
        <i class="fa fa-refresh fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label
            key="common.button.refresh"/>
    </button>
    <button type="button" class="btn  btn-primary btn-sm btn-hg"
            title="<eidea:label key="common.button.operation.log"/>" ng-click="commonEditHeader('log')">
        <i class="fa fa-file-text-o fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label
            key="common.button.operation.log"/>
    </button>
</div>