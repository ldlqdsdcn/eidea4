<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/12/16
  Time: 10:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="wizardCtrl">
    <div class="page-header">
        <h3><%--根据表结构生成生成table和table_column--%><eidea:label key="table.wizard.title"/>
        </h3>
    </div>
    <form role="form" name="myform">
        <div class="form-group row" >
            <label for="name" class="col-sm-2 control-label"><%--表名--%><eidea:label key="searchcolumn.column.tableName"/></label>
            <div class="col-md-4">
                <input type="text" id="name" placeholder="<eidea:message key="org.input.name"/>" ng-model="tableInfo.name" ng-keypress="enter($event)"
                       class="form-control " required >
            </div>
            <label for="tableTrlName" class="col-sm-2 control-label"><%--表注释--%><eidea:label key="table.wizard.table_notes"/></label>
            <div class="col-md-4">
                <input type="text" id="tableTrlName" placeholder="<eidea:message key="table.input.table_notes"/>" ng-model="tableInfo.tableTrlName"
                       class="form-control " required>
            </div>
        </div>
        <div class="form-group row" >
            <label for="index" class="col-sm-2 control-label"><%--主键--%><eidea:label key="changelog.primarykey"/></label>
            <div class="col-md-4">
                <input type="text" id="index" readonly class="form-control" ng-model="tableInfo.pkColumn">
            </div>
            <label for="busPk" class="col-sm-2 control-label"><%--业务主键--%><eidea:label key="table.column.buskey"/></label>
            <div class="col-md-4">
                <input type="text" id="busPk"  class="form-control" ng-model="tableInfo.busPk">
            </div>
        </div>
        <div class="form-group row">
            <label for="outLog" class="col-sm-2 control-label"><%--输出日志--%><eidea:label key="table.column_output_log"/></label>
            <div class="col-sm-1">

                <input type="checkbox" id="outLog"  class="form-control" ng-model="tableInfo.outLog">

            </div>

        </div>
        <div class="form-group row">
            <label for="className" class="control-label col-sm-2 "><%--类名--%><eidea:label key="javaclass.column.classname"/></label>
            <div class="col-md-10">
                <input type="text" id="className"  class="form-control" ng-model="tableInfo.className" required ng-minlength="10" ng-maxlength="100">
            </div>
        </div>


        <div class="form-group row">
            <label for="remark" class="control-label col-sm-2 "><%--备注--%><eidea:label key="base.remarks"/></label>
            <div class="col-md-10">
                <input type="text" id="remark"  class="form-control" ng-model="tableInfo.remark" ng-maxlength="200">
            </div>
        </div>

        <div class="form-group">
            <button  type="submit" class="btn btn-primary" ng-click="submit()"  ng-disabled="tableInfo.columnList==null" ng-show="canAdd"><%--保存--%><eidea:label key="common.button.save"/></button>
            <a href="#/list" class="btn btn-primary"><%--返回--%><eidea:label key="common.button.back"/></a>
        </div>
        <div class="form-group" ng-show="tableInfo.uniqueKeyList!=null">
            <label for="unique_list" class="control-label"><%--索引--%><eidea:label key="table.wizard.index"/></label>
            <table id="unique_list"  class="table table-hover table-bordered">
                <thead>
                <th><%--字段名--%><eidea:label key="table.label.field_name"/></th>
                <th><%--索引名--%><eidea:label key="table.label.index_name"/></th>
                </thead>
                <tbody>
                <tr ng-repeat="x in tableInfo.uniqueKeyList track by $index">
                    <td>{{x.indexName}}</td><td>{{x.indexColumnName}}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="form-group" ng-show="tableInfo.exportedFK!=null" >
            <label for="exportedFK" class="control-label"><%--外部关联表--%><eidea:label key="table.label.external_association_table"/></label>
            <table id="exportedFK" class="table table-hover table-bordered">
                <thead>
                <td><%--字段--%><eidea:label key="common.search.field"/></td><td><%--引用表--%><eidea:label key="table.label.reference_table"/></td><td><%--引用字段--%><eidea:label key="table.label.reference_field"/></td>
                </thead>
                <tbody>
                <tr ng-repeat="x in tableInfo.exportedFK track by $index">
                    <td>{{x.pkColumnName}}</td><td>{{x.fkTableName}}</td><td>{{x.fkColumnName}}</td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="form-group" ng-show="tableInfo.importedFK!=null">
            <label for="importedFK" class="control-label"><%--外键--%><eidea:label key="table.label.foreign_key"/></label>
            <table id="importedFK" class="table table-hover table-bordered">
                <thead>
                <td><%--字段--%><eidea:label key="common.search.field"/></td><td><%--引用表--%><eidea:label key="table.label.reference_table"/></td><td><%--引用字段--%><eidea:label key="table.label.reference_field"/></td>
                </thead>
                <tbody>
                <tr ng-repeat="x in tableInfo.importedFK track by $index">
                    <td>{{x.fkColumnName}}</td><td>{{x.pkTableName}}</td><td>{{x.pkColumnName}}</td>
                </tr>
                </tbody>
            </table>

        </div>
        <div class="form-group" ng-show="tableInfo.columnList!=null">
            <label for="column_list" class="control-label"><%--字段列表--%><eidea:label key="common.search.fields"/></label>
            <table id="column_list"  class="table table-hover table-bordered">
                <thead>
                <th><%--字段名--%><eidea:label key="table.label.field_name"/></th>
                <th><%--字段类型--%><eidea:label key="table.label.field_type"/></th>
                <th><%--数据类型--%><eidea:label key="table.label.data_type"/></th>
                <th><%--大小--%><eidea:label key="table.label.size"/></th>
                <th><%--小数位--%><eidea:label key="table.label.decimal_places"/></th>
                <th><%--是否为空--%><eidea:label key="table.label.is_empty"/></th>
                <th><%--备注--%><eidea:label key="base.remarks"/></th>
                <th><%--默认值--%><eidea:label key="table.label.defaults"/></th>
                </thead>
                <tbody>
                <tr ng-repeat="x in tableInfo.columnList track by $index">
                    <td>{{x.columnName}}</td>
                    <td>{{x.type}}</td>
                    <td>
                        <%--<select   ng-model="x.dataType"  >
                            <option ng-repeat="option in columnDataTypes" ng-value="option.key">{{option.desc}}</option>
                        </select>--%>
                        <select ng-model="x.dataType"
                                ng-options="option.key as option.desc for option in columnDataTypes"></select>
                        <%--ng-options="option.desc for option in columnDataTypes track by option.key"--%>
                        <%--<select   ng-model="x.dataType" >--%>
                        <%--<option ng-repeat="option in columnDataTypes" ng-value="option.key">{{option.desc}}</option>--%>
                        <%--</select>--%>
                    </td>
                    <td>{{x.columnSize}}</td>
                    <td>{{x.decimalDigits}}</td>
                    <td><input type="checkbox" ng-model="x.nullable"></td>
                    <td><input type="text" ng-model="x.remarks" required/></td>
                    <td><input type="text" ng-model="x.columnDefault"/></td>
                </tr>

                </tbody>
            </table>
        </div>
    </form>
</div>