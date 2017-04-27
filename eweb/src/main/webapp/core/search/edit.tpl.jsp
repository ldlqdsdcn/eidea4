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
    <div class="page-header">
    <ol class="breadcrumb">
        <li><a href="javascript:;"><i class="icon-orders"></i> <%--查询条件维护--%><eidea:label key="search.condition"/></a></li>
    </ol>
        </div>
    <div class="row-fluid">
        <div class="span12">
            <br>
            <form role="form" name="searchForm" novalidate ng-submit="save()">

                <div class="form-group">
                    <label for="name">name</label>
                    <input type="text" class="form-control" id="name" name="value" ng-model="searchBo.name" placeholder="<eidea:message key="login.input.name"/>" required ng-minlength="2" ng-maxlength="50">
                </div>
                <div class="form-group">
                    <label for="uri"><%--标识符--%><eidea:label key="base.identifier"/></label>
                    <input type="text" class="form-control" id="uri" placeholder="<eidea:message key="search.table.name"/>" ng-model="searchBo.uri" required ng-maxlength="100" >
                </div>
                <div class="form-group">
                    <label for="showType"><%--查询类型--%><eidea:label key="officeTestExam.select.selectTypeList"/></label>
                    <select ng-model="searchBo.showType" id="showType"
                            ng-options="option.key as option.desc for option in showTypeList"></select>
                </div>
                  <div class="form-group">
                    <label for="isactive"><%--是否有效--%><eidea:label key="base.whetherEffective"/><input type="checkbox" id="isactive"   ng-true-value="'Y'" ng-false-value="'N'" ng-model="searchBo.isactive"  ></label>
                </div>
                <div class="form-group">
                    <label for="remark">remark</label>
                    <input type="text" class="form-control" id="remark" placeholder="<eidea:message key="base.input.remark"/>" ng-model="searchBo.remark"   ng-maxlength="200">
                </div>
                <div >
                    <p>
                        <%--字段信息--%><eidea:label key="base.Field.information"/><button type="button"  class="btn btn-default btn-sm" ng-click="addOneColumn()">添加列</button> <button type="button"  class="btn btn-default btn-sm" ng-disabled="!canDelete()"  ng-click="deleteColumns()"><%--删除选择列--%><eidea:label key="base.delete.select.column"/></button>
                    </p>
                    <table id="international_list"  class="table table-hover table-striped table-myinvalidate">
                        <thead>
                        <tr>
                            <th  class="form-group" ><input type="checkbox" name="selectAll" style="margin:0px;" ng-change="selectAll()" formnovalidate
                                       ng-model="delFlag"></th>
                            <th style="width: 100px;">seqNo</th><th  style="width: 100px;">name</th><th  style="width: 150px;">labelKey</th><th  style="width: 100px;"><%--属性名--%><eidea:label key="table.label.attributename"/></th><th style="width: 100px;"><%--数据类型--%><eidea:label key="searchcolumn.column.datatype"/></th>
                            <th style="width: 100px;"><%--显示类型--%><eidea:label key="searchcolumn.column.showtype"/></th><th style="width: 100px;"><%--连接符--%><eidea:label key="search.connection.symbol"/></th>
                            <th  style="width: 100px;">fkTable</th><th  style="width: 100px;">fkKeyColumn</th><th  style="width: 100px;">fkLabelColumn</th><th>coditions</th><th>remark</th><th  style="width: 50px;">newline</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="searchColumn in searchBo.searchColumnBoList track by $index">
                            <td  class="form-group" >
                                <input type="checkbox" ng-model="searchColumn.delFlag" formnovalidate>
                            </td>
                            <td class="form-group" style="width: 100px;">
                                <input ng-model="searchColumn.seqNo"  required type="number" style="width: 100px;" ng-pattern="/^\d+$/" min="0"></td>
                            <td class="form-group" style="width: 100px;">
                                <input type="text"  ng-model="searchColumn.name"  style="width: 100px;" required>
                            </td>
                            <td  class="form-group" style="width: 150px;">
                                <input type="text"  ng-model="searchColumn.labelKey"  style="width: 150px;">
                            </td>
                            <td  class="form-group"  style="width: 100px;">
                                <input type="text" required ng-model="searchColumn.propertyName"  style="width: 100px;">
                            </td>
                            <td  class="form-group"  style="width: 100px;">
                                <select ng-model="searchColumn.dataType"
                                        ng-options="option.key as option.desc for option in searchDataType"></select>
                            </td>
                            <td class="form-group" style="width: 100px;">
                                <select ng-model="searchColumn.showType"
                                        ng-options="option.key as option.desc for option in searchPageFieldInput"></select>
                            </td>
                            <td class="form-group" style="width: 100px;">
                                <div  ng-repeat="relOper in searchColumn.relOperList track by $index" >
                                    <input type="checkbox" ng-model="relOper.checked" formnovalidate>{{relOper.desc}}
                                </div>

                            </td>
                            <td class="form-group"  style="width: 100px;">
                                <input type="text" class="form-control"   ng-model="searchColumn.fkTable"  >
                            </td>
                            <td class="form-group"  style="width: 100px;">
                                <input type="text" class="form-control"    ng-model="searchColumn.fkKeyColumn"  >
                            </td>
                            <td class="form-group"  style="width: 100px;">
                                <input type="text" class="form-control"    ng-model="searchColumn.fkLabelColumn"  >
                            </td>
                            <td class="form-group">
                                <input type="text" class="form-control"    ng-model="searchColumn.coditions"  >
                            </td>
                            <td class="form-group">
                                <input type="text" class="form-control"    ng-model="searchColumn.remark"  >
                            </td>
                            <td class="form-group" style="width: 50px;">
                                <input type="checkbox" class="form-control"    ng-true-value="'Y'" ng-false-value="'N'" ng-model="searchColumn.newline"  >
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="form-group">
                    <p class="text-right">
                        <a href="#/edit"  class="btn btn-default btn-sm" ng-show="canAdd"><eidea:label key="common.button.create"/><%--新建--%></a>
                        <button type="submit" class="btn btn-default btn-sm" ng-show="canSave"><eidea:label key="common.button.save"/><%--保存--%></button>
                        <a href="#/list" class="btn btn-default btn-sm"><eidea:label key="common.button.back"/><%--返回--%></a>
                    </p>
                </div>
            </form>
        </div>
    </div>
</div>
