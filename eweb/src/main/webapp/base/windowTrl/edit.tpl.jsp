<%--
User: 刘大磊
Date: 2017-05-02 15:42:28
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editWindowTrlCtrl">
    <div class="row-fluid">
        <div class="span12">
            <br>
            <form role="form" name="editForm" novalidate ng-submit="save()">
                <div class="form-group">
                    <label for="windowId"><%--windowId--%><eidea:label key="base.windowTrl.label.windowId"/></label>
                    <input type="text" class="form-control" id="windowId"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.windowTrl.label.windowId" type="label"/></eidea:message>"
                           ng-model="windowTrlPo.windowId" required ng-minLength="1" ng-maxLength="11">

                </div>
                <div class="form-group">
                    <label for="lang"><%--lang--%><eidea:label key="base.windowTrl.label.lang"/></label>
                    <input type="text" class="form-control" id="lang"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.windowTrl.label.lang" type="label"/></eidea:message>"
                           ng-model="windowTrlPo.lang" required ng-minLength="1" ng-maxLength="10">

                </div>
                <div class="form-group">
                    <label for="name"><%--名称--%><eidea:label key="base.windowTrl.label.name"/></label>
                    <input type="text" class="form-control" id="name"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.windowTrl.label.name" type="label"/></eidea:message>"
                           ng-model="windowTrlPo.name" ng-maxLength="100">

                </div>
                <div class="form-group">
                    <label for="description"><%--description--%><eidea:label
                            key="base.windowTrl.label.description"/></label>
                    <input type="text" class="form-control" id="description"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.windowTrl.label.description" type="label"/></eidea:message>"
                           ng-model="windowTrlPo.description" ng-maxLength="500">

                </div>
                <div class="form-group">
                    <label for="help"><%--帮助--%><eidea:label key="base.windowTrl.label.help"/></label>
                    <input type="text" class="form-control" id="help"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.windowTrl.label.help" type="label"/></eidea:message>"
                           ng-model="windowTrlPo.help" ng-maxLength="500">

                </div>


                <div class="form-group">
                    <p class="text-right">
                        <button type="reset" ng-click="create()" class="btn btn-default btn-sm" ng-show="canAdd"><%--新建--%>
                        <eidea:label key="common.button.create"/></button>
                        <button type="submit" class="btn btn-default btn-sm" ng-show="canSave"><%--保存--%><eidea:label
                                key="common.button.save"/></button>
                        <button type="button" ng-click="backWindowTrlList()" class="btn btn-default btn-sm"><eidea:label key="common.button.back"/></button>
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