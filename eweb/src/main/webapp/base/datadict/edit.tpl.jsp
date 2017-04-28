<%--
User: 刘大磊
Date: 2017-04-26 15:34:59
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">
    <div class="page-header">
        <ol class="breadcrumb">
            <li><a href="javascript:;"><i class="icon-fire"></i><eidea:label key="datadict.title"/></a></li>
        </ol>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <br>
            <form role="form" name="editForm" novalidate  ng-submit="save()">
                <div class="form-group">
                    <label for="code" ><%--code--%><eidea:label key="base.datadict.label.code"/></label>
                            <input type="text" class="form-control" id="code" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.datadict.label.code" type="label"/></eidea:message>"
                                   ng-model="datadictPo.code" required ng-minlength="1" ng-maxlength="20" ng-trim="true" >

                </div>
                <div class="form-group">
                    <label for="msgtext" ><%--信息--%><eidea:label key="base.datadict.label.msgtext"/></label>
                            <input type="text" class="form-control" id="msgtext" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.datadict.label.msgtext" type="label"/></eidea:message>"
                                   ng-model="datadictPo.msgtext" required ng-minlength="2" ng-maxlength="200">

                </div>
                <div class="form-group">
                    <label for="isactive" ><%--是否有效--%><eidea:label key="base.datadict.label.isactive"/></label>
                            <input id="isactive" type="checkbox"  ng-true-value="'Y'" ng-false-value="'N'" ng-model="datadictPo.isactive">
                </div>
                <div class="form-group">
                    <label for="dataType" ><%--dataType--%><eidea:label key="base.datadict.label.dataType"/></label>
                            <select id="dataType" class="form-control" ng-model="datadictPo.dataType"
                                    ng-options="datadictTypePo.value as datadictTypePo.value for datadictTypePo in datadictTypeList" required></select>
                <div class="form-group">
                    <label for="remark" ><%--备注--%><eidea:label key="base.datadict.label.remark"/></label>
                            <input type="text" class="form-control" id="remark" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.datadict.label.remark" type="label"/></eidea:message>"
                                   ng-model="datadictPo.remark" ng-maxlength="200">

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