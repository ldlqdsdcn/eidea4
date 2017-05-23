<%@ taglib prefix="ediea" uri="http://eidea.cn" %>
<%--
User: 刘大磊
Date: 2017-05-02 15:41:30
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">
    <div ng-controller="tabCtrl" class="nav nav-tab vertical-tab">
        <uib-tabset vertical="true" active="active" type="tabs">
            <uib-tab index="0" heading="<ediea:label key="window.title"/>" select="windowEdit()"></uib-tab>
            <uib-tab index="1" heading="<ediea:label key="windowTrl.title"/>" select="windowTrlList()"></uib-tab>
            <uib-tab index="2" heading="<ediea:label key="tab.title"/>" select="tabList()"></uib-tab>
            <uib-tab index="3" heading="<eidea:label key="tabTrl.title"/> " select="tabTrlList()"
                     disable="!tabTrlBtnShow"></uib-tab>
            <uib-tab index="4" heading="<eidea:label key="field.title"/> " select="fieldList()"
                     disable="!fieldBtnShow"></uib-tab>
            <uib-tab index="5" heading="<eidea:label key="fieldTrl.title"/> " select="fieldTrlList()"
                     disable="!fieldTrlBtnShow"></uib-tab>
            <uib-tab index="6" heading="<eidea:label key="fieldValidator.title"/> " select="fieldValidatorList()"
                     disable="!fieldValidatorBtnShow"></uib-tab>
        </uib-tabset>
    </div>
    <div ui-view class="tab-content vertical-tab-content"
         ng-show="tabListshow||windowTrlListShow||tabTrlListShow||fieldListShow||fieldTrlListShow||fieldValidatorListShow"></div>
    <div class="tab-content vertical-tab-content" ng-show="windowEditShow">
        <form role="form" name="editForm" novalidate ng-submit="save()"
              class="form-horizontal form-label-left input_mask">
            <jsp:include page="/common/common_edit_button.jsp"/>
            <table class="table v-table-borderless">
                <tr>
                    <td class="control-label"><eidea:label key="base.window.label.name"/></td>
                    <td class="form-group">
                        <input type="text" id="name" class="form-control"
                               placeholder="<eidea:message key="common.please.input"><eidea:param value="base.window.label.name" type="label"/></eidea:message>"
                               ng-model="windowPo.name" required ng-minlength="2" ng-maxlength="50">
                    </td>
                    <td class="control-label"><eidea:label key="base.window.label.isactive"/></td>
                    <td class="form-group"><input class="form-group" id="isactive" type="checkbox" ng-true-value="'Y'"
                                                  ng-false-value="'N'" ng-model="windowPo.isactive"></td>
                </tr>
                <tr>
                    <td class="control-label"><eidea:label key="base.window.label.description"/></td>
                    <td class="form-group" colspan="3"><input type="text" class="form-control" id="description"
                                                  placeholder="<eidea:message key="common.please.input"><eidea:param value="base.window.label.description" type="label"/></eidea:message>"
                                                  ng-model="windowPo.description" ng-maxlength="200">
                    </td>
                </tr>
                <tr>
                    <td class="control-label"><eidea:label key="base.window.label.clientId"/></td>
                    <td class="form-group"><select class="form-control" id="clientId" ng-model="windowPo.clientId"
                                                   ng-options="clientPo.id as clientPo.name for clientPo in clientList"
                                                   required></select>
                    </td>
                    <td class="control-label"><eidea:label key="base.window.label.orgId"/></td>
                    <td class="form-group"><select class="form-control" id="orgId" ng-model="windowPo.orgId"
                                                   ng-options="orgPo.id as orgPo.name for orgPo in orgList"
                                                   required></select>
                    </td>
                </tr>
                <tr>
                    <td class="control-label"><eidea:label key="base.window.label.createdby"/></td>
                    <td class="form-group"><input type="text" class="form-control" id="createdby"
                                                  placeholder="<eidea:message key="common.please.input"><eidea:param value="base.window.label.createdby" type="label"/></eidea:message>"
                                                  ng-model="windowPo.createdby" required ng-minlength="1"
                                                  ng-maxlength="11">
                    </td>
                    <td class="control-label"><eidea:label key="base.window.label.created"/></td>
                    <td class="input-group date bootstrap-datetime"><input type="text" class="form-control" id="created"
                                                  placeholder="<eidea:message key="common.please.input"><eidea:param value="base.window.label.created" type="label"/></eidea:message>"
                                                  ng-model="windowPo.created"
                                                  uib-datepicker-popup="yyyy-MM-dd HH:mm:ss" required>
                        <span class="input-group-addon"><span
                                class="glyphicon glyphicon-calendar"></span></span>
                    </td>
                </tr>
                <tr>
                    <td class="control-label"><eidea:label key="base.window.label.updatedby"/></td>
                    <td class="form-group "><input type="text" class="form-control" id="updatedby"
                                                  placeholder="<eidea:message key="common.please.input"><eidea:param value="base.window.label.updatedby" type="label"/></eidea:message>"
                                                  ng-model="windowPo.updatedby" ng-minlength="1" ng-maxlength="11"
                                                  required>
                    </td>
                    <td class="control-label"><eidea:label key="base.window.label.updated"/></td>
                    <td class="input-group date bootstrap-datetime"><input type="text" class="form-control" id="updated"
                                                  placeholder="<eidea:message key="common.please.input"><eidea:param value="base.window.label.updated" type="label"/></eidea:message>"
                                                  ng-model="windowPo.updated"
                                                  uib-datepicker-popup="yyyy-MM-dd HH:mm:ss" required>
                        <span class="input-group-addon"><span
                                class="glyphicon glyphicon-calendar"></span></span>
                    </td>
                </tr>
                <tr>
                    <td class="control-label"><eidea:label key="base.window.label.requestMapping"/></td>
                    <td colspan="3"class="form-group"><input type="text" class="form-control" id="requestMapping"
                                                  placeholder="<eidea:message key="common.please.input"><eidea:param value="base.window.label.requestMapping" type="label"/></eidea:message>"
                                                  ng-model="windowPo.requestMapping" ng-maxlength="200">
                    </td>
                </tr>
            </table>
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
        </form>
    </div>
</div>
<script type='text/javascript' src="<c:url value="/js/ondrag-start.js"/>"></script>