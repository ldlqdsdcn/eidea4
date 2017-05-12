<%--
User: 刘大磊
Date: 2017-05-02 15:49:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">
    <div class="page-header">
        <ol class="breadcrumb">
            <li><a href="javascript:;"><i class="icon-fire"></i><eidea:label key="fieldValidator.title"/></a></li>
        </ol>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <br>
            <form role="form" name="editForm" novalidate ng-submit="save()">
                <div class="form-group">
                    <label for="fieldId"><%--fieldId--%><eidea:label key="base.fieldValidator.label.fieldId"/></label>
                    <input type="text" class="form-control" id="fieldId"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fieldValidator.label.fieldId" type="label"/></eidea:message>"
                           ng-model="fieldValidatorPo.fieldId" required ng-minLength="1" ng-maxLength="11">

                </div>
                <div class="form-group">
                    <label for="validateType"><%--validateType--%><eidea:label
                            key="base.fieldValidator.label.validateType"/></label>
                    <input type="text" class="form-control" id="validateType"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fieldValidator.label.validateType" type="label"/></eidea:message>"
                           ng-model="fieldValidatorPo.validateType" required ng-minLength="1" ng-maxLength="11">

                </div>
                <div class="form-group">
                    <label for="maxValue"><%--maxValue--%><eidea:label
                            key="base.fieldValidator.label.maxValue"/></label>
                    <input type="text" class="form-control" id="maxValue"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fieldValidator.label.maxValue" type="label"/></eidea:message>"
                           ng-model="fieldValidatorPo.maxValue" ng-maxLength="10">

                </div>
                <div class="form-group">
                    <label for="minValue"><%--minValue--%><eidea:label
                            key="base.fieldValidator.label.minValue"/></label>
                    <input type="text" class="form-control" id="minValue"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fieldValidator.label.minValue" type="label"/></eidea:message>"
                           ng-model="fieldValidatorPo.minValue" ng-maxLength="10">

                </div>
                <div class="form-group">
                    <label for="maxDate"><%--maxDate--%><eidea:label key="base.fieldValidator.label.maxDate"/></label>
                    <div class="input-group date bootstrap-datetime">
                        <input type="text" class="form-control" id="maxDate"
                               placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fieldValidator.label.maxDate" type="label"/></eidea:message>"
                               ng-model="fieldValidatorPo.maxDate"
                               uib-datepicker-popup="yyyy-MM-dd HH:mm:ss" ng-maxLength="19">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                    </div>

                </div>
                <div class="form-group">
                    <label for="minDate"><%--minDate--%><eidea:label key="base.fieldValidator.label.minDate"/></label>
                    <div class="input-group date bootstrap-datetime">
                        <input type="text" class="form-control" id="minDate"
                               placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fieldValidator.label.minDate" type="label"/></eidea:message>"
                               ng-model="fieldValidatorPo.minDate"
                               uib-datepicker-popup="yyyy-MM-dd HH:mm:ss" ng-maxLenth="19">
                        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                    </div>

                </div>
                <div class="form-group">
                    <label for="maxLength"><%--maxLength--%><eidea:label
                            key="base.fieldValidator.label.maxLength"/></label>
                    <input type="text" class="form-control" id="maxLength"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fieldValidator.label.maxLength" type="label"/></eidea:message>"
                           ng-model="fieldValidatorPo.maxLength" ng-maxLength="11">

                </div>
                <div class="form-group">
                    <label for="minLength"><%--minLength--%><eidea:label
                            key="base.fieldValidator.label.minLength"/></label>
                    <input type="text" class="form-control" id="minLength"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fieldValidator.label.minLength" type="label"/></eidea:message>"
                           ng-model="fieldValidatorPo.minLength" ng-maxLength="11">

                </div>
                <div class="form-group">
                    <label for="validatorPatten"><%--validatorPatten--%><eidea:label
                            key="base.fieldValidator.label.validatorPatten"/></label>
                    <input type="text" class="form-control" id="validatorPatten"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fieldValidator.label.validatorPatten" type="label"/></eidea:message>"
                           ng-model="fieldValidatorPo.validatorPatten" ng-maxLength="500">

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