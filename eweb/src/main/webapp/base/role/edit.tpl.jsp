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
            <li><a href="javascript:;"><i class="icon-fire"></i><eidea:label key="role.setUp"/><%--角色设置--%></a></li>
        </ol>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <br>
            <form role="form" name="editForm" novalidate ng-submit="save()">

                <div class="form-group">
                    <label for="name">name</label>
                    <input type="text" class="form-control" id="name" placeholder="<eidea:message key="login.input.name"/>" ng-model="roleBo.name"
                           required ng-minlength="2" ng-maxlength="100">
                </div>
                <div class="form-group">
                    <label for="remark">remark</label>
                    <input type="text" class="form-control" id="remark" placeholder="<eidea:message key="base.input.remark"/>" ng-model="roleBo.remark"
                           ng-maxlength="500">
                </div>
                <div class="form-group">
                    <label for="isactive"><eidea:label key="base.whetherEffective"/><%--是否有效--%><input type="checkbox" class="form-control" id="isactive"
                                                     ng-true-value="'Y'" ng-false-value="'N'"
                                                     ng-model="roleBo.isactive"></label>

                </div>
                <div class="form-group">
                    <p class="text-right">
                        <button type="reset" ng-click="create()" class="btn btn-default btn-sm" ng-show="canAdd"><eidea:label key="common.button.create"/><%--新建--%>
                        </button>
                        <button type="submit" class="btn btn-default btn-sm" ng-show="canSave"><eidea:label key="common.button.save"/><%--保存--%></button>
                        <a href="#/list" class="btn btn-default btn-sm"><eidea:label key="common.button.back"/><%--返回--%></a>
                    </p>
                </div>
                <div class="form-group">
                    <p class="text-center" style="color: red">
                        {{message}}
                    </p>
                </div>
                <!--tab页-->
                <ul id="myTab" class="nav nav-tabs">
                    <li class="active"><a ng-click="tab('menu')" data-toggle="tab"><eidea:label key="role.operatingAuthority"/><%--操作权限--%></a></li>
                    <li><a ng-click="tab('page')" data-toggle="tab"><eidea:label key="role.data.access.rights"/><%--数据访问权限--%></a></li>
                </ul>
                <div id="myTabContent" class="tab-content">
                    <div class="tab-pane fade in active" id="menu" ng-model="menu">
                        <!--模块设置-->
                        <table class="table table-hover table-striped table-condensed">
                            <thead>
                            <tr>

                                <th><%--模块名称--%><eidea:label key="role.data.modelName"/></th>
                                <th colspan="4"><%--操作控制--%><eidea:label key="role.operationController"/></th>
                            </tr>
                            </thead>
                            <tbody>

                            <tr ng-repeat="moduleRoleBo in roleBo.moduleRoleBoList track by $index"
                                ng-class-even="success">
                                <td>
                                    {{moduleRoleBo.moduleName}}

                                </td>
                                <td ng-repeat="privilegeBo in moduleRoleBo.privilegeBoList track by $index">
                                    <input type="checkbox" ng-model="privilegeBo.checked" >
                                    {{privilegeBo.operatorName}}
                                </td>

                            </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="tab-pane fade" id="page">
                        <!--可访问组织-->
                        <table class="table table-hover table-striped table-condensed">
                            <thead>
                            <tr>
                                <th class="form-group"><input type="checkbox" name="selectAll" style="margin:0px;"
                                                              ng-change="selectAllOrg()" formnovalidate
                                                              ng-model="allOrg"></th>
                                <th><%--组织名称--%><eidea:label key="role.organizationName"/></th>
                            </tr>
                            </thead>
                            <tbody>

                            <tr ng-repeat="roleOrg in  roleBo.roleOrgAccessBoList track by $index"
                                ng-class-even="success">
                                <td class="form-group">
                                    <input type="checkbox" ng-model="roleOrg.checked" >
                                </td>
                                <td>
                                    {{roleOrg.orgName}}
                                </td>
                            </tr>
                            </tbody>
                        </table>
                        <!--访问目录设置-->
                    </div>
                </div>
                <!--tab页-->
            </form>
        </div>
    </div>
</div>