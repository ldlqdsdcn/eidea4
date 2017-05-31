<%--
User: 刘大磊
Date: 2017-05-02 15:43:44
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editTabTrlCtrl">
    <div class="row-fluid">
        <div class="span12">
            <br>
            <form role="form" name="editForm" novalidate ng-submit="save()">
                <div class="form-group">
                    <label for="tabId"><%--tabId--%><eidea:label key="base.tabTrl.label.tabId"/></label>
                    <input type="text" class="form-control" id="tabId"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tabTrl.label.tabId" type="label"/></eidea:message>"
                           ng-model="tabTrlPo.tabId" required ng-minLength="1" ng-maxLength="11">

                </div>
                <div class="form-group">
                    <label for="lang"><%--lang--%><eidea:label key="base.tabTrl.label.lang"/></label>
                    <input type="text" class="form-control" id="lang"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tabTrl.label.lang" type="label"/></eidea:message>"
                           ng-model="tabTrlPo.lang" ng-minLength="1" ng-maxLength="10" required>

                </div>
                <div class="form-group">
                    <label for="name"><%--名称--%><eidea:label key="base.tabTrl.label.name"/></label>
                    <input type="text" class="form-control" id="name"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tabTrl.label.name" type="label"/></eidea:message>"
                           ng-model="tabTrlPo.name" ng-maxLength="200">

                </div>
                <div class="form-group">
                    <label for="description"><%--description--%><eidea:label
                            key="base.tabTrl.label.description"/></label>
                    <input type="text" class="form-control" id="description"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tabTrl.label.description" type="label"/></eidea:message>"
                           ng-model="tabTrlPo.description" ng-maxLength="500">

                </div>
                <div class="form-group">
                    <label for="help"><%--帮助--%><eidea:label key="base.tabTrl.label.help"/></label>
                    <input type="text" class="form-control" id="help"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tabTrl.label.help" type="label"/></eidea:message>"
                           ng-model="tabTrlPo.help" ng-maxLength="500">

                </div>


                <div class="form-group">
                    <p class="text-right">
                        <button type="reset" ng-click="create()" class="btn btn-default btn-sm" ng-show="canAdd"><%--新建--%>
                        <eidea:label key="common.button.create"/></button>
                        <button type="submit" class="btn btn-default btn-sm" ng-show="canSave"><%--保存--%><eidea:label
                                key="common.button.save"/></button>
                        <button type="button" class="btn btn-default btn-sm" ng-click="back()"><eidea:label key="common.button.back"/></button>
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
            </form>
        </div>
    </div>
</div>