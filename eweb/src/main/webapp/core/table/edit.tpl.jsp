<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/10/19
  Time: 15:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=utf-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">
    <ol class="breadcrumb">
        <li><a href="javascript:;"><i class="icon-orders"></i><%-- 表信息--%><eidea:label key="table.title"/></a></li>
    </ol>
    <div class="box mar15">
        <div class="box-body no-padding">
            <br>
            <form role="form" name="tableForm" novalidate ng-submit="save()">

                <div class="form-group">
                    <label for="name"><%--名称--%><eidea:label key="datadict.column.name"/></label>
                    <input type="text" class="form-control" id="name" name="value" ng-model="tableBo.name"
                           placeholder="<eidea:message key="org.input.name"/>" required ng-minlength="2" ng-maxlength="50">
                </div>
                <div class="form-group">
                    <label for="tableName"><%--table name--%><eidea:label key="searchcolumn.column.tableName"/></label>
                    <input type="text" class="form-control" id="tableName" placeholder="<eidea:message key="table.input.table_name"/>"
                           ng-model="tableBo.tableName" required ng-minlength="2" ng-maxlength="45">
                </div>
                <div class="form-group">
                    <label for="poName"><%--Po 类--%><eidea:label key="table.column.po"/></label>
                    <input type="text" class="form-control" id="poName" placeholder="<eidea:message key="table.input.po"/>"
                           ng-model="tableBo.poClass" required ng-minlength="2" ng-maxlength="100">
                </div>
                <div class="form-group"><label><%--输出日志--%><eidea:label key="table.column_output_log"/><input type="checkbox" id="outLog" ng-true-value="'Y'"
                                                          ng-false-value="'N'" ng-model="tableBo.outLog"></label>
                </div>
                <div class="form-group">
                    <label for="isactive"><%--是否有效--%><eidea:label key="base.whetherEffective"/><input type="checkbox" id="isactive" ng-true-value="'Y'"
                                                      ng-false-value="'N'" ng-model="tableBo.isactive"></label>
                </div>
                <div class="form-group">
                    <label for="remark"><%--remark--%><eidea:label key="base.remarks"/></label>
                    <input type="text" class="form-control" id="remark" placeholder="<eidea:message key="org.input.remark"/>"
                           ng-model="tableBo.remark" ng-maxlength="200">
                </div>
                <div>
                    <p>
                        <%--字段信息--%><eidea:label key="table.lable.information"/>
                    </p>
                    <table id="international_list" class="table table-hover table-striped table-myinvalidate">
                        <thead>
                        <tr>
                            <th><%--顺序--%><eidea:label key="table.column.order"/></th>
                            <th><%--名称--%><eidea:label key="datadict.column.name"/></th>
                            <th><%--列名--%><eidea:label key="table.label.column"/></th>
                            <th><%--字段名--%><eidea:label key="table.label.field_name"/></th>
                            <th><%--类型--%><eidea:label key="dymenus.label.type"/></th>
                            <th><%--是否显示--%><eidea:label key="table.lable.isview"/></th>
                            <th><%--记录日志--%><eidea:label key="table.lable.log"/></th>
                            <th><%--唯一--%><eidea:label key="table.lable.only"/></th>
                            <th><%--columnSize--%><eidea:label key="table.lable.column_size"/></th>
                            <th><%--digits--%><eidea:label key="table.lable.digits"/></th>
                            <th><%--remark--%><eidea:label key="base.remarks"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="tableColumn in tableBo.tableColumnBoList track by $index">
                            <td>{{$index+1}}</td>
                            <td class="form-group">
                                <input type="text" readonly ng-model="tableColumn.name" class="form-control">
                            </td>
                            <td class="form-group">
                                <input type="text" required ng-model="tableColumn.columnName" class="form-control">
                            </td>
                            <td class="form-group">
                                <input type="text" required ng-model="tableColumn.propertyName" class="form-control">
                            </td>
                            <td>
                                <select ng-model="tableColumn.dataType"
                                        ng-options="option.key as option.desc for option in columnDataTypes"></select>
                            </td>
                            <td class="form-group"><input type="checkbox" class="form-control" ng-true-value="'Y'"
                                                          ng-false-value="'N'" ng-model="tableColumn.canShow"></td>
                            <td class="form-group">
                                <input type="checkbox" class="form-control" ng-true-value="'Y'" ng-false-value="'N'"
                                       ng-model="tableColumn.outLog">
                            </td>
                            <td class="form-group">
                                <input type="checkbox" class="form-control" ng-true-value="'Y'" ng-false-value="'N'"
                                       ng-model="tableColumn.isUnique">
                            </td>
                            <td class="form-group">
                                <input type="number" class="form-control" ng-model="tableColumn.columnSize" ng-pattern="/^\d+$/" min="0">
                            </td>
                            <td class="form-group">
                                <input type="number" class="form-control" ng-model="tableColumn.digits" ng-pattern="/^\d+$/" min="0">
                            </td>
                            <td class="form-group">
                                <input type="text" class="form-control" ng-model="tableColumn.remark">
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="form-group">
                    <p class="text-right">
                        <a href="#/edit" class="btn btn-default btn-sm" ng-show="canAdd"><%--新建--%><eidea:label key="common.button.create"/></a>
                        <button type="submit" class="btn btn-default btn-sm" ng-show="canSave"><%--保存--%><eidea:label key="common.button.save"/></button>
                        <a href="#/list" class="btn btn-default btn-sm"><%--返回--%><eidea:label key="common.button.back"/></a>
                    </p>
                </div>
            </form>
        </div>
    </div>
</div>
