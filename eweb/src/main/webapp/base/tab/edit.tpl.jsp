<%--
User: 刘大磊
Date: 2017-05-02 15:43:14
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editTabCtrl">
    <div class="row-fluid">
        <div class="span12">
            <br>
            <form role="form" name="editForm" novalidate ng-submit="save()">
                <div class="form-group">
                    <label for="windowId"><%--windowId--%><eidea:label key="base.tab.label.windowId"/></label>
                    <input type="text" class="form-control" id="windowId"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.windowId" type="label"/></eidea:message>"
                           ng-model="tabPo.windowId" required ng-minLength="1" ng-maxLength="11">

                </div>
                <div class="form-group">
                    <label for="name"><%--名称--%><eidea:label key="base.tab.label.name"/></label>
                    <input type="text" class="form-control" id="name"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.name" type="label"/></eidea:message>"
                           ng-model="tabPo.name" required ng-minLength="2" ng-maxLength="200">

                </div>
                <div class="form-group">
                    <label for="level"><%--等级--%><eidea:label key="base.tab.label.level"/></label>
                    <input type="text" class="form-control" id="level"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.level" type="label"/></eidea:message>"
                           ng-model="tabPo.level" required ng-minLength="1" ng-maxLength="11">

                </div>
                <div class="form-group">
                    <label for="sortno"><%--sortno--%><eidea:label key="base.tab.label.sortno"/></label>
                    <input type="text" class="form-control" id="sortno"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.sortno" type="label"/></eidea:message>"
                           ng-model="tabPo.sortno" required ng-minLength="1" ng-maxLength="11">

                </div>
                <div class="form-group">
                    <label for="description"><%--description--%><eidea:label key="base.tab.label.description"/></label>
                    <input type="text" class="form-control" id="description"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.description" type="label"/></eidea:message>"
                           ng-model="tabPo.description" ng-maxLength="500">

                </div>
                <div class="form-group">
                    <label for="includedTabId"><%--includedTabId--%><eidea:label
                            key="base.tab.label.includedTabId"/></label>
                    <input type="text" class="form-control" id="includedTabId"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.includedTabId" type="label"/></eidea:message>"
                           ng-model="tabPo.includedTabId">

                </div>
                <div class="form-group">
                    <label for="tableId"><%--tableId--%><eidea:label key="base.tab.label.tableId"/></label>
                    <select id="tableId" class="form-control" ng-model="tabPo.tableId"
                            ng-options="tablePo.id as tablePo.name for tablePo in tablePoList"
                            ng-blur="getTableColumnList(tabPo.tableId)" required></select>
                </div>
                <div class="form-group">
                    <label for="tableColumnId"><%--tableColumnId--%><eidea:label
                            key="base.tab.label.tableColumnId"/></label>
                    <select id="tableColumnId" class="form-control" ng-model="tabPo.tableColumnId"
                            ng-options="tableColumn.id as tableColumn.name for tableColumn in tableColumnList" required></select>
                </div>
                <div class="form-group">
                    <label for="isactive"><%--是否有效--%><eidea:label key="base.tab.label.isactive"/></label>
                    <input id="isactive" type="checkbox" ng-true-value="'Y'" ng-false-value="'N'"
                           ng-model="tabPo.isactive">
                </div>
                <div class="form-group">
                    <label for="created"><%--创建时间--%><eidea:label key="base.tab.label.created"/></label>
                    <div class="input-group date bootstrap-datetime">
                        <input type="text" class="form-control" id="created"
                               placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.created" type="label"/></eidea:message>"
                               ng-model="tabPo.created"
                               uib-datepicker-popup="yyyy-MM-dd HH:mm:ss" required>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                    </div>

                </div>
                <div class="form-group">
                    <label for="createdby"><%--创建人--%><eidea:label key="base.tab.label.createdby"/></label>
                    <input type="text" class="form-control" id="createdby"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.createdby" type="label"/></eidea:message>"
                           ng-model="tabPo.createdby" required ng-minLength="1" ng-maxLength="11">

                </div>
                <div class="form-group">
                    <label for="updated"><%--修改时间--%><eidea:label key="base.tab.label.updated"/></label>
                    <div class="input-group date bootstrap-datetime">
                        <input type="text" class="form-control" id="updated"
                               placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.updated" type="label"/></eidea:message>"
                               ng-model="tabPo.updated"
                               uib-datepicker-popup="yyyy-MM-dd HH:mm:ss" required>
                        <span class="input-group-addon"><span class="glyphicon glyphicon-calendar" ></span></span>
                    </div>

                </div>
                <div class="form-group">
                    <label for="updatedby"><%--修改人--%><eidea:label key="base.tab.label.updatedby"/></label>
                    <input type="text" class="form-control" id="updatedby"
                           placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.updatedby" type="label"/></eidea:message>"
                           ng-model="tabPo.updatedby" ng-minLength="1" ng-maxLength="11" required>

                </div>


                <div class="form-group">
                    <p class="text-right">
                        <button type="reset" ng-click="create()" class="btn btn-default btn-sm" ng-show="canAdd"><%--新建--%>
                        <eidea:label key="common.button.create"/></button>
                        <button type="submit" class="btn btn-default btn-sm" ng-show="canSave"><%--保存--%><eidea:label
                                key="common.button.save"/></button>
                        <button class="btn btn-default btn-sm" ng-click="backItemList()"><eidea:label key="common.button.back"/></button>
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