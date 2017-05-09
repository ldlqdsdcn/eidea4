<%--
User: 刘大磊
Date: 2017-04-26 15:34:17
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">

    <ul class="nav nav-tab vertical-tab" role="tablist" id="vtab">
        <li role="presentation" class="active">
            <a href="#tab-home" role="tab" data-toggle="tab"><eidea:label key="datadict.title"/></a>
        </li>
        <li role="presentation">
            <a href="#tab-1" role="tab" data-toggle="tab"><eidea:label key="datadict.detail"/></a>
        </li>
    </ul>
    <div class="tab-content vertical-tab-content">
        <div role="tabpanel" class="tab-pane active" id="tab-home">
            <form role="form" name="editForm" novalidate ng-submit="save()">
                <jsp:include page="/common/common_edit_button.jsp"/>
                <div class="row-fluid">
                    <div class="span12">
                        <br>
                        <div class="form-group">
                            <label for="value"><%--键值--%><eidea:label key="base.datadictType.label.value"/></label>
                            <input type="text" class="form-control" id="value"
                                   placeholder="<eidea:message key="common.please.input"><eidea:param value="base.datadictType.label.value" type="label"/></eidea:message>"
                                   ng-model="datadictTypePo.value"
                                   required ng-minlength="2" ng-maxlength="50">

                        </div>
                        <div class="form-group">
                            <label for="name"><%--名称--%><eidea:label key="base.datadictType.label.name"/></label>
                            <input type="text" class="form-control" id="name"
                                   placeholder="<eidea:message key="common.please.input"><eidea:param value="base.datadictType.label.name" type="label"/></eidea:message>"
                                   ng-model="datadictTypePo.name" required ng-minlength="2" ng-maxlength="200">

                        </div>
                        <div class="form-group">
                            <label for="remark"><%--备注--%><eidea:label
                                    key="base.datadictType.label.remark"/></label>
                            <input type="text" class="form-control" id="remark"
                                   placeholder="<eidea:message key="common.please.input"><eidea:param value="base.datadictType.label.remark" type="label"/></eidea:message>"
                                   ng-model="datadictTypePo.remark" ng-maxlength="200">

                        </div>
                        <div class="form-group">
                            <label for="isactive"><%--是否有效--%>
                                <eidea:label key="base.datadictType.label.isactive"/>
                                <input type="checkbox" class="form-control" id="isactive"
                                       ng-model="datadictTypePo.isactive" ng-true-value="'Y'"
                                       ng-false-value="'N'"></label>

                        </div>
                        <div class="form-group">
                            <p class="text-center" style="color: red">
                                {{message}}
                            </p>
                            <p>
                        <span ng-repeat="error in errors track by $index">
                            {{error.message}}
                        </span>
                            </p>
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <div role="tabpanel" class="tab-pane" id="tab-1">
            <jsp:include page="../datadict/list.tpl.jsp"/>
        </div>
    </div>
</div>
</div>
