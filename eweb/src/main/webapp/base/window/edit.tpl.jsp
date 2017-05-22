<%--
User: 刘大磊
Date: 2017-05-02 15:41:30
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">
    <form role="form" name="editForm" novalidate  ng-submit="save()">
        <jsp:include page="/common/common_edit_button.jsp"/>
        <div class="row-fluid">
            <div class="span12">
                <br>
                    <div class="form-group">
                        <label for="name" ><%--名称--%><eidea:label key="base.window.label.name"/></label>
                                <input type="text" class="form-control" id="name" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.window.label.name" type="label"/></eidea:message>" ng-model="windowPo.name" >

                    </div>
                    <div class="form-group">
                        <label for="isactive" ><%--是否有效--%><eidea:label key="base.window.label.isactive"/></label>
                                <input id="isactive" type="checkbox"  ng-true-value="'Y'" ng-false-value="'N'" ng-model="windowPo.isactive">
                    </div>
                    <div class="form-group">
                        <label for="description" ><%--description--%><eidea:label key="base.window.label.description"/></label>
                                <input type="text" class="form-control" id="description" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.window.label.description" type="label"/></eidea:message>" ng-model="windowPo.description" >

                    </div>
                    <div class="form-group">
                        <label for="clientId" ><%--实体--%><eidea:label key="base.window.label.clientId"/></label>
                                <input type="text" class="form-control" id="clientId" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.window.label.clientId" type="label"/></eidea:message>" ng-model="windowPo.clientId" >

                    </div>
                    <div class="form-group">
                        <label for="orgId" ><%--组织--%><eidea:label key="base.window.label.orgId"/></label>
                                <input type="text" class="form-control" id="orgId" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.window.label.orgId" type="label"/></eidea:message>" ng-model="windowPo.orgId" >

                    </div>
                    <div class="form-group">
                        <label for="createdby" ><%--创建人--%><eidea:label key="base.window.label.createdby"/></label>
                                <input type="text" class="form-control" id="createdby" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.window.label.createdby" type="label"/></eidea:message>" ng-model="windowPo.createdby" >

                    </div>
                    <div class="form-group">
                        <label for="created" ><%--创建时间--%><eidea:label key="base.window.label.created"/></label>
                                <div class="input-group date bootstrap-datetime">
                                    <input type="text" class="form-control" id="created" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.window.label.created" type="label"/></eidea:message>" ng-model="windowPo.created"
                                    uib-datepicker-popup="yyyy-MM-dd HH:mm:ss">
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>

                    </div>
                    <div class="form-group">
                        <label for="updated" ><%--修改时间--%><eidea:label key="base.window.label.updated"/></label>
                                <div class="input-group date bootstrap-datetime">
                                    <input type="text" class="form-control" id="updated" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.window.label.updated" type="label"/></eidea:message>" ng-model="windowPo.updated"
                                    uib-datepicker-popup="yyyy-MM-dd HH:mm:ss">
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>

                    </div>
                    <div class="form-group">
                        <label for="updatedby" ><%--修改人--%><eidea:label key="base.window.label.updatedby"/></label>
                                <input type="text" class="form-control" id="updatedby" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.window.label.updatedby" type="label"/></eidea:message>" ng-model="windowPo.updatedby" >

                    </div>
                    <div class="form-group">
                        <label for="requestMapping" ><%--requestMapping--%><eidea:label key="base.window.label.requestMapping"/></label>
                                <input type="text" class="form-control" id="requestMapping" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.window.label.requestMapping" type="label"/></eidea:message>" ng-model="windowPo.requestMapping" >

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
            </div>
        </div>
    </form>
    <jsp:include page="/common/common_upload.jsp"/>
</div>