<%--
User: 刘大磊
Date: 2017-05-02 15:47:35
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
            <form role="form" name="editForm" novalidate  ng-submit="save()">
                <div class="form-group">
                    <label for="name" ><%--名称--%><eidea:label key="base.field.label.name"/></label>
                            <input type="text" class="form-control" id="name" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.name" type="label"/></eidea:message>" ng-model="fieldPo.name" >

                </div>
                <div class="form-group">
                    <label for="fieldName" ><%--fieldName--%><eidea:label key="base.field.label.fieldName"/></label>
                            <input type="text" class="form-control" id="fieldName" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.fieldName" type="label"/></eidea:message>" ng-model="fieldPo.fieldName" >

                </div>
                <div class="form-group">
                    <label for="columnId" ><%--columnId--%><eidea:label key="base.field.label.columnId"/></label>
                            <input type="text" class="form-control" id="columnId" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.columnId" type="label"/></eidea:message>" ng-model="fieldPo.columnId" >

                </div>
                <div class="form-group">
                    <label for="required" ><%--required--%><eidea:label key="base.field.label.required"/></label>
                            <input type="text" class="form-control" id="required" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.required" type="label"/></eidea:message>" ng-model="fieldPo.required" >

                </div>
                <div class="form-group">
                    <label for="isactive" ><%--是否有效--%><eidea:label key="base.field.label.isactive"/></label>
                            <input id="isactive" type="checkbox"  ng-true-value="'Y'" ng-false-value="'N'" ng-model="fieldPo.isactive">
                </div>
                <div class="form-group">
                    <label for="created" ><%--创建时间--%><eidea:label key="base.field.label.created"/></label>
                            <div class="input-group date bootstrap-datetime">
                                <input type="text" class="form-control" id="created" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.created" type="label"/></eidea:message>" ng-model="fieldPo.created"
                                uib-datepicker-popup="yyyy-MM-dd HH:mm:ss">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                            </div>

                </div>
                <div class="form-group">
                    <label for="createdby" ><%--创建人--%><eidea:label key="base.field.label.createdby"/></label>
                            <input type="text" class="form-control" id="createdby" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.createdby" type="label"/></eidea:message>" ng-model="fieldPo.createdby" >

                </div>
                <div class="form-group">
                    <label for="updated" ><%--修改时间--%><eidea:label key="base.field.label.updated"/></label>
                            <div class="input-group date bootstrap-datetime">
                                <input type="text" class="form-control" id="updated" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.updated" type="label"/></eidea:message>" ng-model="fieldPo.updated"
                                uib-datepicker-popup="yyyy-MM-dd HH:mm:ss">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                            </div>

                </div>
                <div class="form-group">
                    <label for="updatedby" ><%--修改人--%><eidea:label key="base.field.label.updatedby"/></label>
                            <input type="text" class="form-control" id="updatedby" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.updatedby" type="label"/></eidea:message>" ng-model="fieldPo.updatedby" >

                </div>
                <div class="form-group">
                    <label for="description" ><%--description--%><eidea:label key="base.field.label.description"/></label>
                            <input type="text" class="form-control" id="description" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.description" type="label"/></eidea:message>" ng-model="fieldPo.description" >

                </div>
                <div class="form-group">
                    <label for="inputType" ><%--inputType--%><eidea:label key="base.field.label.inputType"/></label>
                            <input type="text" class="form-control" id="inputType" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.inputType" type="label"/></eidea:message>" ng-model="fieldPo.inputType" >

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