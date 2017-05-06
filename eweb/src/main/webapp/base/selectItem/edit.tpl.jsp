<%--
User: 刘大磊
Date: 2017-05-03 17:49:28
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">
    <div class="page-header">
        <ol class="breadcrumb">
            <li><a href="javascript:;"><i class="icon-fire"></i><eidea:label key="selectItem.title"/></a></li>
        </ol>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <br>
            <form role="form" name="editForm" novalidate  ng-submit="save()">
                <div class="form-group">
                    <label for="name" ><%--名称--%><eidea:label key="base.selectItem.label.name"/></label>
                            <input type="text" class="form-control" id="name" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.selectItem.label.name" type="label"/></eidea:message>" ng-model="selectItemPo.name" >

                </div>
                <div class="form-group">
                    <label for="key" ><%--key--%><eidea:label key="base.selectItem.label.key"/></label>
                            <input type="text" class="form-control" id="key" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.selectItem.label.key" type="label"/></eidea:message>" ng-model="selectItemPo.key" >

                </div>
                <div class="form-group">
                    <label for="value" ><%--键值--%><eidea:label key="base.selectItem.label.value"/></label>
                            <input type="text" class="form-control" id="value" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.selectItem.label.value" type="label"/></eidea:message>" ng-model="selectItemPo.value" >

                </div>
                <div class="form-group">
                    <label for="sql" ><%--sql--%><eidea:label key="base.selectItem.label.sql"/></label>
                            <input type="text" class="form-control" id="sql" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.selectItem.label.sql" type="label"/></eidea:message>" ng-model="selectItemPo.sql" >

                </div>
                <div class="form-group">
                    <label for="type" ><%--type--%><eidea:label key="base.selectItem.label.type"/></label>
                            <input type="text" class="form-control" id="type" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.selectItem.label.type" type="label"/></eidea:message>" ng-model="selectItemPo.type" >

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