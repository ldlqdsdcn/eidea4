<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/12/9
  Time: 8:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">
    <div class="page-header">
        <ol class="breadcrumb">
            <li><a href="javascript:;"><i class="icon-tasks"></i><eidea:label key="menu.module"/></a></li>
        </ol>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <br>
            <form role="form" name="editForm" novalidate  ng-submit="save()">

                <div class="form-group">
                    <label><eidea:label key="module.column.name"/></label>
                    <input type="text" class="form-control" placeholder="<eidea:message key="module.column.modulename"/>" id="name" ng-model="moduleBo.name" required ng-minlength="2" ng-maxlength="45">
                </div>
                <div class="form-group">
                    <label><eidea:label key="base.remarks"/></label>
                    <input type="text" class="form-control" placeholder="<eidea:message key="login.input.remark"/>" id="remark" ng-model="moduleBo.remark" ng-maxlength="200">
                </div>
                <div class="form-group">
                    <label><eidea:label key="base.whetherEffective"/>
                    <input type="checkbox" ng-true-value="'Y'" ng-false-value="'N'" id="isactive" ng-model="moduleBo.isactive"></label>
                </div>
                <div class="form-group">
                    <p class="text-right">
                        <button type="reset" ng-click="create()"  class="btn btn-default btn-sm" ng-show="canAdd"><eidea:label key="common.button.create"/></button>
                        <button type="submit" class="btn btn-default btn-sm" ng-show="canSave"><eidea:label key="common.button.save"/></button>
                        <a href="#/list" class="btn btn-default btn-sm"><eidea:label key="common.button.back"/></a>
                    </p>
                </div>
            </form>
                <div class="form-group">
                    <p class="text-center" style="color: red">
                        {{message}}
                    </p>
                </div>
                <!--tab页-->
                <ul id="myTab" class="nav nav-tabs">
                    <li class="active"><a ng-click="tab('menu')" data-toggle="tab"><eidea:label key="dymenuForm.title2"/></a></li>
                    <li><a ng-click="tab('page')" data-toggle="tab"><eidea:label key="module.column.moduleaccessdirectory"/></a></li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="menu" ng-model="menu">
                        <!--菜单设置-->
                        <table  class="table table-hover table-striped table-condensed">
                            <thead>
                            <tr>
                                <th><input type="checkbox" name="selectMenuAll" style="margin:0px;" ng-change="selectMenuAll()"
                                           ng-model="menuDelFlag"></th>
                                <th><eidea:label key="dymenu.label.menuname"/></th>
                                <th><eidea:label key="dymenu.label.chainedaddress"/></th>
                                <th><eidea:label key="base.remarks"/></th>
                            </tr>
                            </thead>
                            <tbody>

                            <tr ng-repeat="menu in menuList track by $index" ng-class-even="success">
                                <td>
                                    <input type="checkbox" ng-model="menu.menuDelFlag">
                                </td>
                                <td>
                                    {{menu.name}}
                                </td>
                                <td>
                                    {{menu.url}}
                                </td>
                                <td>
                                    {{menu.remark}}
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <!--菜单设置-->
                    </div>
                    <div class="tab-pane fade" id="page">
                        <!--访问目录设置-->
                        <table  class="table table-hover table-striped table-condensed">
                            <thead>
                            <tr>
                                <th><input type="checkbox" name="selectAll" style="margin:0px;" ng-change="selectDirectoryMenuAll()"
                                           ng-model="directoryDelFlag"></th>
                                <th><eidea:label key="dymenuForm.label.name"/></th>
                                <th><eidea:label key="dymenu.label.chainedaddress"/></th>
                                <th><eidea:label key="directory.access.repository"/></th>
                                <th><eidea:label key="base.remarks"/></th>
                            </tr>
                            </thead>
                            <tbody>

                            <tr ng-repeat="directory in directoryList track by $index" ng-class-even="success">
                                <td>
                                    <input type="checkbox" ng-model="directory.directoryDelFlag">
                                </td>
                                <td>
                                    {{directory.name}}
                                </td>
                                <td>
                                    {{directory.directory}}
                                </td>
                                <td>
                                    {{directory.repository}}
                                </td>
                                <td>
                                    {{directory.remark}}
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <!--访问目录设置-->
                    </div>
                </div>
                <!--tab页-->
        </div>
    </div>
</div>