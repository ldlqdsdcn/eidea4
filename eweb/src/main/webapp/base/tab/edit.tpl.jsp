<%--
User: 刘大磊
Date: 2017-05-02 15:43:14
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
                        <label for="windowId" ><%--windowId--%><eidea:label key="base.tab.label.windowId"/></label>
                                <input type="text" class="form-control" id="windowId" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.windowId" type="label"/></eidea:message>" ng-model="tabPo.windowId" >

                    </div>
                    <div class="form-group">
                        <label for="name" ><%--名称--%><eidea:label key="base.tab.label.name"/></label>
                                <input type="text" class="form-control" id="name" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.name" type="label"/></eidea:message>" ng-model="tabPo.name" >

                    </div>
                    <div class="form-group">
                        <label for="level" ><%--等级--%><eidea:label key="base.tab.label.level"/></label>
                                <input type="text" class="form-control" id="level" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.level" type="label"/></eidea:message>" ng-model="tabPo.level" >

                    </div>
                    <div class="form-group">
                        <label for="sortno" ><%--sortno--%><eidea:label key="base.tab.label.sortno"/></label>
                                <input type="text" class="form-control" id="sortno" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.sortno" type="label"/></eidea:message>" ng-model="tabPo.sortno" >

                    </div>
                    <div class="form-group">
                        <label for="description" ><%--description--%><eidea:label key="base.tab.label.description"/></label>
                                <input type="text" class="form-control" id="description" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.description" type="label"/></eidea:message>" ng-model="tabPo.description" >

                    </div>
                    <div class="form-group">
                        <label for="includedTabId" ><%--includedTabId--%><eidea:label key="base.tab.label.includedTabId"/></label>
                                <input type="text" class="form-control" id="includedTabId" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.includedTabId" type="label"/></eidea:message>" ng-model="tabPo.includedTabId" >

                    </div>
                    <div class="form-group">
                        <label for="tableId" ><%--tableId--%><eidea:label key="base.tab.label.tableId"/></label>
                                <input type="text" class="form-control" id="tableId" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.tableId" type="label"/></eidea:message>" ng-model="tabPo.tableId" >

                    </div>
                    <div class="form-group">
                        <label for="tableColumnId" ><%--tableColumnId--%><eidea:label key="base.tab.label.tableColumnId"/></label>
                                <input type="text" class="form-control" id="tableColumnId" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.tableColumnId" type="label"/></eidea:message>" ng-model="tabPo.tableColumnId" >

                    </div>
                    <div class="form-group">
                        <label for="isactive" ><%--是否有效--%><eidea:label key="base.tab.label.isactive"/></label>
                                <input id="isactive" type="checkbox"  ng-true-value="'Y'" ng-false-value="'N'" ng-model="tabPo.isactive">
                    </div>
                    <div class="form-group">
                        <label for="created" ><%--创建时间--%><eidea:label key="base.tab.label.created"/></label>
                                <div class="input-group date bootstrap-datetime">
                                    <input type="text" class="form-control" id="created" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.created" type="label"/></eidea:message>" ng-model="tabPo.created"
                                    uib-datepicker-popup="yyyy-MM-dd HH:mm:ss">
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>

                    </div>
                    <div class="form-group">
                        <label for="createdby" ><%--创建人--%><eidea:label key="base.tab.label.createdby"/></label>
                                <input type="text" class="form-control" id="createdby" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.createdby" type="label"/></eidea:message>" ng-model="tabPo.createdby" >

                    </div>
                    <div class="form-group">
                        <label for="updated" ><%--修改时间--%><eidea:label key="base.tab.label.updated"/></label>
                                <div class="input-group date bootstrap-datetime">
                                    <input type="text" class="form-control" id="updated" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.updated" type="label"/></eidea:message>" ng-model="tabPo.updated"
                                    uib-datepicker-popup="yyyy-MM-dd HH:mm:ss">
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>

                    </div>
                    <div class="form-group">
                        <label for="updatedby" ><%--修改人--%><eidea:label key="base.tab.label.updatedby"/></label>
                                <input type="text" class="form-control" id="updatedby" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.updatedby" type="label"/></eidea:message>" ng-model="tabPo.updatedby" >

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