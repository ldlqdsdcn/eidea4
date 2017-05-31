<%--
User: 刘大磊
Date: 2017-05-02 15:49:09
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editFieldValidatorCtrl">
    <form role="form" name="editForm" novalidate ng-submit="save()" class="form-horizontal form-label-left input_mask">
        <table class="table table-borderless">
            <tr>
                <td class="control-label"><%--fieldId--%><eidea:label key="base.fieldValidator.label.fieldId"/></td>
                <td class="form-group"><input type="text" class="form-control" id="fieldId"
                                              placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fieldValidator.label.fieldId" type="label"/></eidea:message>"
                                              ng-model="fieldValidatorPo.fieldId" required ng-minLength="1"
                                              ng-maxLength="11"></td>
                <td class="control-label"><%--validateType--%><eidea:label
                        key="base.fieldValidator.label.validateType"/></td>
                <td class="form-group"><input type="text" class="form-control" id="validateType"
                                              placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fieldValidator.label.validateType" type="label"/></eidea:message>"
                                              ng-model="fieldValidatorPo.validateType" required ng-minLength="1"
                                              ng-maxLength="11"></td>
            </tr>
            <tr>
                <td class="control-label"><%--maxValue--%><eidea:label key="base.fieldValidator.label.maxValue"/></td>
                <td class="form-group"><input type="text" class="form-control" id="maxValue"
                                              placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fieldValidator.label.maxValue" type="label"/></eidea:message>"
                                              ng-model="fieldValidatorPo.maxValue" ng-maxLength="10"></td>
                <td class="control-label"><%--minValue--%><eidea:label key="base.fieldValidator.label.minValue"/></td>
                <td class="form-group"><input type="text" class="form-control" id="minValue"
                                              placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fieldValidator.label.minValue" type="label"/></eidea:message>"
                                              ng-model="fieldValidatorPo.minValue" ng-maxLength="10"></td>
            </tr>
            <tr>
                <td class="control-label"><%--maxDate--%><eidea:label key="base.fieldValidator.label.maxDate"/></td>
                <td class="input-group date bootstrap-datetime"><input type="text" class="form-control" id="maxDate"
                                                                       placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fieldValidator.label.maxDate" type="label"/></eidea:message>"
                                                                       ng-model="fieldValidatorPo.maxDate"
                                                                       uib-datepicker-popup="yyyy-MM-dd HH:mm:ss"
                                                                       ng-maxLength="19">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span></td>
                <td class="control-label"><%--minDate--%><eidea:label key="base.fieldValidator.label.minDate"/></td>
                <td class="input-group date bootstrap-datetime"><input type="text" class="form-control" id="minDate"
                                                                       placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fieldValidator.label.minDate" type="label"/></eidea:message>"
                                                                       ng-model="fieldValidatorPo.minDate"
                                                                       uib-datepicker-popup="yyyy-MM-dd HH:mm:ss"
                                                                       ng-maxLenth="19">
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span></td>

            </tr>
            <tr>
                <td class="control-label"><%--maxLength--%><eidea:label key="base.fieldValidator.label.maxLength"/></td>
                <td class="form-group"><input type="text" class="form-control" id="maxLength"
                                              placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fieldValidator.label.maxLength" type="label"/></eidea:message>"
                                              ng-model="fieldValidatorPo.maxLength" ng-maxLength="11"></td>
                <td class="control-label"><%--minLength--%><eidea:label key="base.fieldValidator.label.minLength"/></td>
                <td class="form-group"><input type="text" class="form-control" id="minLength"
                                              placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fieldValidator.label.minLength" type="label"/></eidea:message>"
                                              ng-model="fieldValidatorPo.minLength" ng-maxLength="11"></td>
            </tr>
            <tr>
                <td class="control-label"><%--validatorPatten--%><eidea:label
                        key="base.fieldValidator.label.validatorPatten"/></td>
                <td class="form-group" colspan="3"><input type="text" class="form-control" id="validatorPatten"
                                                          placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fieldValidator.label.validatorPatten" type="label"/></eidea:message>"
                                                          ng-model="fieldValidatorPo.validatorPatten"
                                                          ng-maxLength="500"></td>
            </tr>
        </table>
        <div class="form-group">
            <p class="text-right">
                <button type="reset" ng-click="create()" class="btn btn-default btn-sm" ng-show="canAdd"><%--新建--%>
                    <eidea:label key="common.button.create"/></button>
                <button type="submit" class="btn btn-default btn-sm" ng-show="canSave"><%--保存--%><eidea:label
                        key="common.button.save"/></button>
                <button type="button" class="btn btn-default btn-sm" ng-show="canSave"
                        ng-click="back()"><eidea:label key="common.button.back"/></button>
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