<%--
User: 刘大磊
Date: 2017-05-02 15:43:14
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editTabCtrl">
    <div ui-view ng-show="tabTrlListShow||fieldListShow||fieldTrlListShow||fieldValidatorListShow"></div>
    <form role="form" name="editForm" novalidate ng-submit="save()" ng-show="tabEditShow"
          class="form-horizontal form-label-left input_mask">
        <table class="table table-borderless">
            <tr>
                <td class="control-label"><%--窗体ID--%><eidea:label key="base.tab.label.windowId"/></td>
                <td class="form-group"><input type="text" class="form-control" id="windowId"
                                              placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.windowId" type="label"/></eidea:message>"
                                              ng-model="tabPo.windowId" required ng-minLength="1" ng-maxLength="11">
                </td>
                <td class="control-label"><%--名称--%><eidea:label key="base.tab.label.name"/></td>
                <td class="form-group"><input type="text" class="form-control" id="name"
                                              placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.name" type="label"/></eidea:message>"
                                              ng-model="tabPo.name" required ng-minLength="2" ng-maxLength="200"></td>
            </tr>
            <tr>
                <td class="control-label"><%--等级--%><eidea:label key="base.tab.label.level"/></td>
                <td class="form-group"><input type="text" class="form-control" id="level"
                                              placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.level" type="label"/></eidea:message>"
                                              ng-model="tabPo.level" required ng-minLength="1" ng-maxLength="11"></td>
                <td class="control-label"><%--编号--%><eidea:label key="base.tab.label.sortno"/></td>
                <td class="form-group"><input type="text" class="form-control" id="sortno"
                                              placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.sortno" type="label"/></eidea:message>"
                                              ng-model="tabPo.sortno" required ng-minLength="1" ng-maxLength="11"></td>
            </tr>
            <tr>
                <td class="control-label"><%--描述--%><eidea:label key="base.tab.label.description"/></td>
                <td class="form-group" colspan="3"><input type="text" class="form-control" id="description"
                                                          placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.description" type="label"/></eidea:message>"
                                                          ng-model="tabPo.description" ng-maxLength="500"></td>
            </tr>
            <tr>
                <td class="control-label"><eidea:label key="base.tab.label.includedTabId"/></td>
                <td class="form-group"><input type="text" class="form-control" id="includedTabId"
                                              placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.includedTabId" type="label"/></eidea:message>"
                                              ng-model="tabPo.includedTabId"></td>
                <td class="control-label"><%--是否有效--%><eidea:label key="base.tab.label.isactive"/></td>
                <td class="form-group"><input id="isactive" type="checkbox" ng-true-value="'Y'" ng-false-value="'N'"
                                              ng-model="tabPo.isactive"></td>
            </tr>
            <tr>
                <td class="control-label"><%--表id--%><eidea:label key="base.tab.label.tableId"/></td>
                <td class="form-group"><select id="tableId" class="form-control" ng-model="tabPo.tableId"
                                               ng-options="tablePo.id as tablePo.name for tablePo in tablePoList"
                                               ng-blur="getTableColumnList(tabPo.tableId)" required></select></td>
                <td class="control-label"><%--包含的列id--%><eidea:label key="base.tab.label.tableColumnId"/></td>
                <td class="form-group"><select id="tableColumnId" class="form-control" ng-model="tabPo.tableColumnId"
                                               ng-options="tableColumn.id as tableColumn.name for tableColumn in tableColumnList"
                                               required></select></td>
            </tr>
            <tr>
                <td class="control-label"><%--创建人--%><eidea:label key="base.tab.label.createdby"/></td>
                <td class="form-group"><input type="text" class="form-control" id="createdby"
                                              placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.createdby" type="label"/></eidea:message>"
                                              ng-model="tabPo.createdby" required ng-minLength="1" ng-maxLength="11">
                </td>
                <td class="control-label"><%--创建时间--%><eidea:label key="base.tab.label.created"/></td>
                <td class="input-group date bootstrap-datetime"><input type="text" class="form-control" id="created"
                                                                       placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.created" type="label"/></eidea:message>"
                                                                       ng-model="tabPo.created"
                                                                       uib-datepicker-popup="yyyy-MM-dd HH:mm:ss"
                                                                       required>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span></td>
            </tr>
            <tr>
                <td class="control-label"><%--修改人--%><eidea:label key="base.tab.label.updatedby"/></td>
                <td class="form-group"><input type="text" class="form-control" id="updatedby"
                                              placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.updatedby" type="label"/></eidea:message>"
                                              ng-model="tabPo.updatedby" ng-minLength="1" ng-maxLength="11" required>
                </td>
                <td class="control-label"><%--修改时间--%><eidea:label key="base.tab.label.updated"/></td>
                <td class="input-group date bootstrap-datetime"><input type="text" class="form-control" id="updated"
                                                                       placeholder="<eidea:message key="common.please.input"><eidea:param value="base.tab.label.updated" type="label"/></eidea:message>"
                                                                       ng-model="tabPo.updated"
                                                                       uib-datepicker-popup="yyyy-MM-dd HH:mm:ss"
                                                                       required>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span></td>
            </tr>

        </table>
        <div class="form-group">
            <p class="text-right">
                <button type="reset" ng-click="create()" class="btn btn-default btn-sm" ng-show="canAdd"><%--新建--%>
                    <eidea:label key="common.button.create"/></button>
                <button type="submit" class="btn btn-default btn-sm" ng-show="canSave"><%--保存--%><eidea:label
                        key="common.button.save"/></button>
                <button class="btn btn-default btn-sm" ng-click="back()"><eidea:label
                        key="common.button.back"/></button>
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