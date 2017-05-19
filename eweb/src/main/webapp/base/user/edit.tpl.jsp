<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/12/13
  Time: 14:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">
    <form role="form" name="editForm" novalidate ng-submit="save()" class="form-horizontal form-label-left">
        <jsp:include page="/common/common_edit_button.jsp"/>
        <div class="row-fluid">
            <div class="span12">
                    <div class="form-group">
                        <label class="col-sm-2 text-right"><%--登录名称--%><eidea:label key="login.username.login"/></label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="username" name="username" ng-model="userBo.username"
                                   placeholder="<eidea:message key="login.input.loginname"/>" required ng-minlength="2" ng-maxlength="50" ng-blur="getExistUserName()">
                            <input type="checkbox" ng-true-value="'N'" ng-false-value="'Y'" ng-model="userBo.isactive">
                            <span><%--停用--%><eidea:label key="datadict.column.isactive"/></span>
                        </div>

                    </div>
                    <div class="form-group">
                        <label for="name" class="col-sm-2 text-right"><%--密码--%><eidea:label key="user.column.password"/></label>
                        <div class="col-sm-10">
                            <input type="password" class="form-control" id="password" placeholder="<%--请输入密码--%><eidea:message key="login.input.password"/>" ng-model="userBo.password" required minlength="6" ng-maxlength="45">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="name" class="col-sm-2 text-right"><%--密码确认--%><eidea:label key="user.ConfirmPassword"/></label>
                        <div class="col-sm-10">
                            <input type="password" class="form-control" id="repassword" placeholder="<%--请输入密码确认--%><eidea:message key="login.input.password.enter"/>" ng-model="repassword" required minlength="6" ng-maxlength="45">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 text-right"><%--用户名称--%><eidea:label key="user.user.name"/></label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="name" placeholder="<%--请输入用户名称--%><eidea:message key="user.input.user.name"/>" ng-model="userBo.name"
                                   required minlength="2" ng-maxlength="45">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 text-right"><%--邮箱--%><eidea:label key="customer.column.email"/></label>
                        <div class="col-sm-10">
                            <input type="email" class="form-control" id="email" placeholder="<%--请输入邮箱--%><eidea:message key="user.input.user.email"/>" ng-model="userBo.email"
                                   ng-maxlength="100">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 text-right"><%--电话--%><eidea:label key="user.column.telephone"/></label>
                        <div class="col-sm-10">
                            <input type="tel" class="form-control" id="telephone" placeholder="<%--请输入电话--%><eidea:message key="user.input.user.phone"/>" ng-model="userBo.telephone"
                                   ng-maxlength="45">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 text-right"><%--实体--%><eidea:label key="user.column.client"/></label>
                        <div class="col-sm-10 text-left">
                            <select class="form-control" ng-model="userBo.clientId"
                                    ng-options="client.id as client.name for client in clientList" required></select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 text-right"><%--组织--%><eidea:label key="realtrans.column.org"/></label>
                        <div class="col-sm-10 text-left">
                            <select class="form-control" ng-model="userBo.orgId"
                                    ng-options="org.id as org.name for org in orgList" required></select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 text-right"><%--角色--%><eidea:label key="user.column.role"/></label>
                        <div class="col-sm-10 text-left">
                            <span ng-repeat="role in roleList track by $index" ng-class-even="success">
                               <input type="checkbox" ng-model="role.roleDelFlag">&nbsp;&nbsp;&nbsp;{{role.name}}&nbsp;</input>
                            </span>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 text-right"><%--初始化--%><eidea:label key="user.initialization"/></label>
                        <div class="col-sm-10 text-left"><input type="checkbox"  id="init" ng-true-value="'Y'" ng-false-value="'N'" ng-model="userBo.init" ng-disabled="true"></label>
                        </div>
                    </div>
                    <div class="form-group">
                        <p class="text-right">
                            <button type="reset" ng-click="create()" class="btn btn-default btn-sm" ng-show="canAdd"><eidea:label key="common.button.create"/><%--新建--%></button>
                            <button type="submit" class="btn btn-default btn-sm" ng-show="canSave"><eidea:label key="common.button.save"/><%--保存--%></button>
                            <a href="#/list" class="btn btn-default btn-sm"><eidea:label key="common.button.back"/><%--返回--%></a>
                        </p>
                    </div>
                    <div class="form-group">
                        <p class="text-center" style="color: red"  >
                            {{message}}
                        </p>
                        <p class="text-center" style="color: red" ng-repeat="msg in errorMessages track by $index" ng-show="errorCode==3">
                            {{msg.message}}
                        </p>
                    </div>
            </div>
        </div>
    </form>
    <jsp:include page="/common/common_upload.jsp"/>
</div>
<script type='text/javascript' src="<c:url value="/js/ondrag-start.js"/>"></script>
