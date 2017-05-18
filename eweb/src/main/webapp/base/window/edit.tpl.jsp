<%@ taglib prefix="ediea" uri="http://eidea.cn" %>
<%--
User: 刘大磊
Date: 2017-05-02 15:41:30
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">
    <div ng-controller="tabCtrl">
    <uib-tabset class="nav nav-tab vertical-tab" vertical="true" active="active">
        <uib-tab index="0" heading="<ediea:label key="window.title"/>" select="windowEdit()"></uib-tab>
        <uib-tab index="1" heading="<ediea:label key="windowTrl.title"/>" select="windowTrlList()"></uib-tab>
        <uib-tab index="2" heading="<ediea:label key="tab.title"/>"select="tabList()"></uib-tab>
        <uib-tab index="3" heading="<eidea:label key="tabTrl.title"/> " select="tabTrlList()" ng-show="tabTrlBtnShow"></uib-tab>
        <uib-tab index="4" heading="<eidea:label key="field.title"/> " select="fieldList()" ng-show="fieldBtnShow"></uib-tab>
        <uib-tab index="5" heading="<eidea:label key="fieldTrl.title"/> "></uib-tab>
    </uib-tabset>
    </div>
    <div ui-view class="tab-content vertical-tab-content" ng-show="tabListshow||windowTrlListShow||tabTrlListShow||fieldListShow"></div>
    <div class="tab-content vertical-tab-content" ng-show="windowEditShow">
            <form role="form" name="editForm" novalidate ng-submit="save()">
                <jsp:include page="/common/common_edit_button.jsp"/>
                <div class="row-fluid">
                    <div class="span12">
                        <br>
                        <div class="form-group">
                            <label for="name"><%--名称--%><eidea:label key="base.window.label.name"/></label>
                            <input type="text" class="form-control" id="name"
                                   placeholder="<eidea:message key="common.please.input"><eidea:param value="base.window.label.name" type="label"/></eidea:message>"
                                   ng-model="windowPo.name" required ng-minlength="2" ng-maxlength="50">

                        </div>
                        <div class="form-group">
                            <label for="isactive"><%--是否有效--%><eidea:label
                                    key="base.window.label.isactive"/></label>
                            <input id="isactive" type="checkbox" ng-true-value="'Y'" ng-false-value="'N'"
                                   ng-model="windowPo.isactive">
                        </div>
                        <div class="form-group">
                            <label for="description"><%--description--%><eidea:label
                                    key="base.window.label.description"/></label>
                            <input type="text" class="form-control" id="description"
                                   placeholder="<eidea:message key="common.please.input"><eidea:param value="base.window.label.description" type="label"/></eidea:message>"
                                   ng-model="windowPo.description" ng-maxlength="200">

                        </div>
                        <div class="form-group">
                            <label for="clientId"><%--实体--%><eidea:label key="base.window.label.clientId"/></label>
                            <select id="clientId" class="form-control" ng-model="windowPo.clientId"
                                    ng-options="clientPo.id as clientPo.name for clientPo in clientList"
                                    required></select>

                        </div>
                        <div class="form-group">
                            <label for="orgId"><%--组织--%><eidea:label key="base.window.label.orgId"/></label>
                            <select id="orgId" class="form-control" ng-model="windowPo.orgId"
                                    ng-options="orgPo.id as orgPo.name for orgPo in orgList" required></select>

                        </div>
                        <div class="form-group">
                            <label for="createdby"><%--创建人--%><eidea:label
                                    key="base.window.label.createdby"/></label>
                            <input type="text" class="form-control" id="createdby"
                                   placeholder="<eidea:message key="common.please.input"><eidea:param value="base.window.label.createdby" type="label"/></eidea:message>"
                                   ng-model="windowPo.createdby" required ng-minlength="1" ng-maxlength="11"
                                   ng-disabled="windowPo.id!=null">

                        </div>
                        <div class="form-group">
                            <label for="created"><%--创建时间--%><eidea:label key="base.window.label.created"/></label>
                            <div class="input-group date bootstrap-datetime">
                                <input type="text" class="form-control" id="created"
                                       placeholder="<eidea:message key="common.please.input"><eidea:param value="base.window.label.created" type="label"/></eidea:message>"
                                       ng-model="windowPo.created"
                                       uib-datepicker-popup="yyyy-MM-dd HH:mm:ss" ng-disabled="windowPo.id!=null" required>
                                <span class="input-group-addon"><span
                                        class="glyphicon glyphicon-calendar"></span></span>
                            </div>

                        </div>
                        <div class="form-group">
                            <label for="updated"><%--修改时间--%><eidea:label key="base.window.label.updated"/></label>
                            <div class="input-group date bootstrap-datetime">
                                <input type="text" class="form-control" id="updated"
                                       placeholder="<eidea:message key="common.please.input"><eidea:param value="base.window.label.updated" type="label"/></eidea:message>"
                                       ng-model="windowPo.updated"
                                       uib-datepicker-popup="yyyy-MM-dd HH:mm:ss" ng-disabled="windowPo.id==null" required>
                                <span class="input-group-addon"><span
                                        class="glyphicon glyphicon-calendar"></span></span>
                            </div>

                        </div>
                        <div class="form-group">
                            <label for="updatedby"><%--修改人--%><eidea:label
                                    key="base.window.label.updatedby"/></label>
                            <input type="text" class="form-control" id="updatedby"
                                   placeholder="<eidea:message key="common.please.input"><eidea:param value="base.window.label.updatedby" type="label"/></eidea:message>"
                                   ng-model="windowPo.updatedby" ng-minlength="1" ng-maxlength="11" required>

                        </div>
                        <div class="form-group">
                            <label for="requestMapping"><%--requestMapping--%><eidea:label
                                    key="base.window.label.requestMapping"/></label>
                            <input type="text" class="form-control" id="requestMapping"
                                   placeholder="<eidea:message key="common.please.input"><eidea:param value="base.window.label.requestMapping" type="label"/></eidea:message>"
                                   ng-model="windowPo.requestMapping" ng-maxlength="200">
                        </div>
                        <div class="form-group">
                            <p class="text-right">
                                <button type="reset" ng-click="create()" class="btn btn-default btn-sm"
                                        ng-show="canAdd"><%--新建--%><eidea:label
                                        key="common.button.create"/></button>
                                <button type="submit" class="btn btn-default btn-sm" ng-show="canSave"><%--保存--%>
                                    <eidea:label key="common.button.save"/></button>
                                <a href="#/list" class="btn btn-default btn-sm"><%--返回--%><eidea:label
                                        key="common.button.back"/></a>
                            </p>
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

</div>
<script type='text/javascript' src="<c:url value="/js/ondrag-start.js"/>"></script>