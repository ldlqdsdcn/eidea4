<%--
User: 刘大磊
Date: 2017-05-06 07:51:36
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">
    <div class="page-header">
        <ol class="breadcrumb">
            <li><a href="javascript:;"><i class="icon-fire"></i><eidea:label key="settings.title"/></a></li>
        </ol>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <br>
            <form role="form" name="editForm" novalidate  ng-submit="save()">
                <div class="form-group">
                    <label for="key" ><%--key--%><eidea:label key="base.settings.label.key"/></label>
                            <input type="text" class="form-control" id="key" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.settings.label.key" type="label"/></eidea:message>" ng-model="settingsPo.key" >

                </div>
                <div class="form-group">
                    <label for="value" ><%--键值--%><eidea:label key="base.settings.label.value"/></label>
                            <input type="text" class="form-control" id="value" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.settings.label.value" type="label"/></eidea:message>" ng-model="settingsPo.value" >

                </div>
                <div class="form-group">
                    <label for="description" ><%--description--%><eidea:label key="base.settings.label.description"/></label>
                            <input type="text" class="form-control" id="description" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.settings.label.description" type="label"/></eidea:message>" ng-model="settingsPo.description" >

                </div>


                <div class="form-group">
                    <p class="text-right">
                        <button type="reset" ng-click="create()"  class="btn btn-default btn-sm" ng-show="canAdd"><%--新建--%><eidea:label key="common.button.create"/></button>
                        <button type="submit" class="btn btn-default btn-sm" ng-show="canSave"><%--保存--%><eidea:label key="common.button.save"/></button>
                        <a href="#/list" class="btn btn-default btn-sm"><%--返回--%><eidea:label key="common.button.back"/></a>
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