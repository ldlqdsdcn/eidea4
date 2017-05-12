<%--
User: 刘大磊
Date: 2017-05-04 13:22:23
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">
    <div class="page-header">
        <ol class="breadcrumb">
            <li><a href="javascript:;"><i class="icon-fire"></i><eidea:label key="field.title"/></a></li>
        </ol>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <br>
            <form role="form" name="editForm" novalidate ng-submit="save()">
                <div class="form-group">
                    <label for="seqNo"><%--seqNo--%><eidea:label key="base.field.label.seqNo"/></label>
                    <input type="text" class="form-control" id="seqNo"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.seqNo" type="label"/></eidea:message>"
                           ng-model="fieldPo.seqNo" ng-minLength="1" ng-maxLength="11" required>

                </div>
                <div class="form-group">
                    <label for="name"><%--名称--%><eidea:label key="base.field.label.name"/></label>
                    <input type="text" class="form-control" id="name"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.name" type="label"/></eidea:message>"
                           ng-model="fieldPo.name" ng-minLength="2" ng-maxLength="200" required>

                </div>
                <div class="form-group">
                    <label for="fieldName"><%--fieldName--%><eidea:label key="base.field.label.fieldName"/></label>
                    <input type="text" class="form-control" id="fieldName"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.fieldName" type="label"/></eidea:message>"
                           ng-model="fieldPo.fieldName" ng-minLength="2" ng-maxLength="200" required>

                </div>
                <div class="form-group">
                    <label for="columnId"><%--columnId--%><eidea:label key="base.field.label.columnId"/></label>
                    <input type="text" class="form-control" id="columnId"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.columnId" type="label"/></eidea:message>"
                           ng-model="fieldPo.columnId" ng-maxLength="11">

                </div>
                <div class="form-group">
                    <label for="required"><%--required--%><eidea:label key="base.field.label.required"/></label>
                    <input type="checkbox" id="required" ng-true-value="'Y'" ng-false-value="'N'"
                           ng-model="fieldPo.required">

                </div>
                <div class="form-group">
                    <label for="isactive"><%--是否有效--%><eidea:label key="base.field.label.isactive"/></label>
                    <input id="isactive" type="checkbox" ng-true-value="'Y'" ng-false-value="'N'"
                           ng-model="fieldPo.isactive">
                </div>
                <div class="form-group">
                    <label for="created"><%--创建时间--%><eidea:label key="base.field.label.created"/></label>
                    <div class="input-group date bootstrap-datetime">
                        <input type="text" class="form-control" id="created"
                               placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.created" type="label"/></eidea:message>"
                               ng-model="fieldPo.created"
                               uib-datepicker-popup="yyyy-MM-dd HH:mm:ss" required>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                    </div>

                </div>
                <div class="form-group">
                    <label for="createdby"><%--创建人--%><eidea:label key="base.field.label.createdby"/></label>
                    <input type="text" class="form-control" id="createdby"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.createdby" type="label"/></eidea:message>"
                           ng-model="fieldPo.createdby" ng-minLength="1" ng-maxLength="11" required>

                </div>
                <div class="form-group">
                    <label for="updated"><%--修改时间--%><eidea:label key="base.field.label.updated"/></label>
                    <div class="input-group date bootstrap-datetime">
                        <input type="text" class="form-control" id="updated"
                               placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.updated" type="label"/></eidea:message>"
                               ng-model="fieldPo.updated"
                               uib-datepicker-popup="yyyy-MM-dd HH:mm:ss" required>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                    </div>

                </div>
                <div class="form-group">
                    <label for="updatedby"><%--修改人--%><eidea:label key="base.field.label.updatedby"/></label>
                    <input type="text" class="form-control" id="updatedby"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.updatedby" type="label"/></eidea:message>"
                           ng-model="fieldPo.updatedby" ng-minLength="1" ng-maxLength="11" required>

                </div>
                <div class="form-group">
                    <label for="description"><%--description--%><eidea:label
                            key="base.field.label.description"/></label>
                    <input type="text" class="form-control" id="description"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.description" type="label"/></eidea:message>"
                           ng-model="fieldPo.description" ng-maxLength="500">

                </div>
                <div class="form-group">
                    <label for="inputType"><%--inputType--%><eidea:label key="base.field.label.inputType"/></label>
                    <input type="text" class="form-control" id="inputType"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.inputType" type="label"/></eidea:message>"
                           ng-model="fieldPo.inputType" ng-minLength="1" ng-maxLength="11" required>

                </div>
                <div class="form-group">
                    <label for="isDisplayed"><%--isDisplayed--%><eidea:label
                            key="base.field.label.isDisplayed"/></label>
                    <input type="checkbox" id="isDisplayed"ng-true-value="'Y'" ng-false-value="'N'" ng-model="fieldPo.isDisplayed">

                </div>
                <div class="form-group">
                    <label for="displayedlogic"><%--displayedlogic--%><eidea:label
                            key="base.field.label.displayedlogic"/></label>
                    <input type="text" class="form-control" id="displayedlogic"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.displayedlogic" type="label"/></eidea:message>"
                           ng-model="fieldPo.displayedlogic" ng-maxLength="2000">

                </div>
                <div class="form-group">
                    <label for="displaylength"><%--displaylength--%><eidea:label
                            key="base.field.label.displaylength"/></label>
                    <input type="text" class="form-control" id="displaylength"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.displaylength" type="label"/></eidea:message>"
                           ng-model="fieldPo.displaylength" ng-maxLength="11">

                </div>
                <div class="form-group">
                    <label for="isreadonly"><%--isreadonly--%><eidea:label key="base.field.label.isreadonly"/></label>
                    <input type="checkbox" id="isreadonly" ng-true-value="'Y'" ng-false-value="'N'"
                           ng-model="fieldPo.isreadonly">

                </div>
                <div class="form-group">
                    <label for="issameline"><%--issameline--%><eidea:label key="base.field.label.issameline"/></label>
                    <input type="checkbox" id="issameline" ng-true-value="'Y'" ng-false-value="'N'"
                           ng-model="fieldPo.issameline">

                </div>
                <div class="form-group">
                    <label for="isencrypted"><%--isencrypted--%><eidea:label
                            key="base.field.label.isencrypted"/></label>
                    <input type="checkbox" id="isencrypted" ng-true-value="'Y'" ng-false-value="'N'"
                           ng-model="fieldPo.isencrypted">

                </div>
                <div class="form-group">
                    <label for="defaultvalue"><%--defaultvalue--%><eidea:label
                            key="base.field.label.defaultvalue"/></label>
                    <input type="text" class="form-control" id="defaultvalue"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.defaultvalue" type="label"/></eidea:message>"
                           ng-model="fieldPo.defaultvalue" ng-maxLength="2000">

                </div>
                <div class="form-group">
                    <label for="isdisplaygrid"><%--isdisplaygrid--%><eidea:label
                            key="base.field.label.isdisplaygrid"/></label>
                    <input type="checkbox" id="isdisplaygrid" ng-true-value="'Y'" ng-false-value="'N'"
                           ng-model="fieldPo.isdisplaygrid">

                </div>
                <div class="form-group">
                    <label for="seqnogrid"><%--seqnogrid--%><eidea:label key="base.field.label.seqnogrid"/></label>
                    <input type="text" class="form-control" id="seqnogrid"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.seqnogrid" type="label"/></eidea:message>"
                           ng-model="fieldPo.seqnogrid" ng-maxLength="11">

                </div>
                <div class="form-group">
                    <label for="isprinted"><%--isprinted--%><eidea:label key="base.field.label.isprinted"/></label>
                    <input type="checkbox" id="isprinted" ng-true-value="'Y'" ng-false-value="'N'"
                           ng-model="fieldPo.isprinted">

                </div>
                <div class="form-group">
                    <label for="isallowcopy"><%--isallowcopy--%><eidea:label
                            key="base.field.label.isallowcopy"/></label>
                    <input type="checkbox"  id="isallowcopy"
                           ng-true-value="'Y'" ng-false-value="'N'"
                           ng-model="fieldPo.isallowcopy">

                </div>
                <div class="form-group">
                    <label for="isreport"><%--isreport--%><eidea:label key="base.field.label.isreport"/></label>
                    <input type="checkbox" id="isreport" ng-true-value="'Y'" ng-false-value="'N'"
                           ng-model="fieldPo.isreport">

                </div>

                <div class="form-group">
                    <p class="text-right">
                        <button type="reset" ng-click="create()" class="btn btn-default btn-sm" ng-show="canAdd"><%--新建--%>
                        <eidea:label key="common.button.create"/></button>
                        <button type="submit" class="btn btn-default btn-sm" ng-show="canSave"><%--保存--%><eidea:label
                                key="common.button.save"/></button>
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
            </form>
        </div>
    </div>
</div>