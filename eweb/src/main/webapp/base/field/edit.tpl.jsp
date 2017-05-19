<%--
User: 刘大磊
Date: 2017-05-04 13:22:23
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
                        <label for="seqNo" ><%--seqNo--%><eidea:label key="base.field.label.seqNo"/></label>
                                <input type="text" class="form-control" id="seqNo" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.seqNo" type="label"/></eidea:message>" ng-model="fieldPo.seqNo" >

                    </div>
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
                        <label for="isDisplayed" ><%--isDisplayed--%><eidea:label key="base.field.label.isDisplayed"/></label>
                                <input type="text" class="form-control" id="isDisplayed" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.isDisplayed" type="label"/></eidea:message>" ng-model="fieldPo.isDisplayed" >

                    </div>
                    <div class="form-group">
                        <label for="displayedlogic" ><%--displayedlogic--%><eidea:label key="base.field.label.displayedlogic"/></label>
                                <input type="text" class="form-control" id="displayedlogic" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.displayedlogic" type="label"/></eidea:message>" ng-model="fieldPo.displayedlogic" >

                    </div>
                    <div class="form-group">
                        <label for="displaylength" ><%--displaylength--%><eidea:label key="base.field.label.displaylength"/></label>
                                <input type="text" class="form-control" id="displaylength" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.displaylength" type="label"/></eidea:message>" ng-model="fieldPo.displaylength" >

                    </div>
                    <div class="form-group">
                        <label for="isreadonly" ><%--isreadonly--%><eidea:label key="base.field.label.isreadonly"/></label>
                                <input type="text" class="form-control" id="isreadonly" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.isreadonly" type="label"/></eidea:message>" ng-model="fieldPo.isreadonly" >

                    </div>
                    <div class="form-group">
                        <label for="issameline" ><%--issameline--%><eidea:label key="base.field.label.issameline"/></label>
                                <input type="text" class="form-control" id="issameline" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.issameline" type="label"/></eidea:message>" ng-model="fieldPo.issameline" >

                    </div>
                    <div class="form-group">
                        <label for="isencrypted" ><%--isencrypted--%><eidea:label key="base.field.label.isencrypted"/></label>
                                <input type="text" class="form-control" id="isencrypted" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.isencrypted" type="label"/></eidea:message>" ng-model="fieldPo.isencrypted" >

                    </div>
                    <div class="form-group">
                        <label for="defaultvalue" ><%--defaultvalue--%><eidea:label key="base.field.label.defaultvalue"/></label>
                                <input type="text" class="form-control" id="defaultvalue" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.defaultvalue" type="label"/></eidea:message>" ng-model="fieldPo.defaultvalue" >

                    </div>
                    <div class="form-group">
                        <label for="isdisplaygrid" ><%--isdisplaygrid--%><eidea:label key="base.field.label.isdisplaygrid"/></label>
                                <input type="text" class="form-control" id="isdisplaygrid" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.isdisplaygrid" type="label"/></eidea:message>" ng-model="fieldPo.isdisplaygrid" >

                    </div>
                    <div class="form-group">
                        <label for="seqnogrid" ><%--seqnogrid--%><eidea:label key="base.field.label.seqnogrid"/></label>
                                <input type="text" class="form-control" id="seqnogrid" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.seqnogrid" type="label"/></eidea:message>" ng-model="fieldPo.seqnogrid" >

                    </div>
                    <div class="form-group">
                        <label for="isallowcopy" ><%--isallowcopy--%><eidea:label key="base.field.label.isallowcopy"/></label>
                                <input type="text" class="form-control" id="isallowcopy" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.field.label.isallowcopy" type="label"/></eidea:message>" ng-model="fieldPo.isallowcopy" >

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
<script type='text/javascript' src="<c:url value="/js/ondrag-start.js"/>"></script>