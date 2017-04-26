<%--
User: 刘大磊
Date: 2017-04-26 15:34:17
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">
    <div class="page-header">
        <ol class="breadcrumb">
            <li><a href="javascript:;"><i class="icon-fire"></i><eidea:label key="datadictType.title"/></a></li>
        </ol>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <br>
            <form role="form" name="editForm" novalidate  ng-submit="save()">
                <div class="form-group">
                    <label for="value" ><%--键值--%><eidea:label key="base.datadictType.label.value"/></label>
                            <input type="text" class="form-control" id="value" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.datadictType.label.value" type="label"/></eidea:message>" ng-model="datadictTypePo.value" >

                </div>
                <div class="form-group">
                    <label for="name" ><%--名称--%><eidea:label key="base.datadictType.label.name"/></label>
                            <input type="text" class="form-control" id="name" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.datadictType.label.name" type="label"/></eidea:message>" ng-model="datadictTypePo.name" >

                </div>
                <div class="form-group">
                    <label for="remark" ><%--备注--%><eidea:label key="base.datadictType.label.remark"/></label>
                            <input type="text" class="form-control" id="remark" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.datadictType.label.remark" type="label"/></eidea:message>" ng-model="datadictTypePo.remark" >

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