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
    <div class="page-title">
        <h3><%-- 组织设置--%><eidea:label key="org.title"/></h3>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <form role="form" name="editForm" novalidate ng-submit="save()" class="form-horizontal form-label-left">

                <div class="form-group">
                    <label for="code" class="col-sm-2 text-right"><%--no--%><eidea:label key="org.column.no"/></label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="code" name="code" ng-model="orgBo.no"
                               placeholder="<eidea:message key="client.input.no"/>" required ng-minlength="2" ng-maxlength="10"
                               ng-disabled="clientBo.id!=null" >
                    </div>

                </div>
                <div class="form-group">
                    <label for="name" class="col-sm-2 text-right"><%--name--%><eidea:label key="org.column.name"/></label>
                    <div class="col-sm-10">
                    <input type="text" class="form-control" id="name" placeholder="<eidea:message key="org.input.name"/>" ng-model="orgBo.name" required ng-min="2" ng-maxlength="20">
                        </div>
                </div>
                <div class="form-group">
                    <label for="remark" class="col-sm-2 text-right"><%--实体--%><eidea:label key="user.column.client"/></label>
                    <div class="col-sm-10">
                    <select class="form-control" id="client" ng-model="orgBo.client.id"
                            ng-options="client.id as client.name for client in clientList"></select>
                        </div>
                </div>
                <div class="form-group">
                    <label for="remark" class="col-sm-2 text-right"><%--remark--%><eidea:label key="base.remarks"/></label>
                    <div class="col-sm-10">
                    <input type="text" class="form-control" id="remark" placeholder="<eidea:message key="org.input.remark"/>" ng-model="orgBo.remark"
                           ng-maxlength="200">
                        </div>
                </div>
                <div class="form-group">
                    <label for="isactive" class="col-sm-2 text-right"><%--是否有效--%><eidea:label key="base.whetherEffective"/></label>
                        <div class="col-sm-10 text-left"><input type="checkbox"  id="isactive"
                                                     ng-true-value="'Y'" ng-false-value="'N'" ng-model="orgBo.isactive">
                    </div>
                </div>
                <div class="form-group">
                    <p class="text-right">
                        <button type="reset" ng-click="create()" class="btn btn-default btn-sm" ng-show="canAdd"><%--新建--%><eidea:label key="common.button.create"/></button>
                        <button type="submit" class="btn btn-default btn-sm" ng-show="canSave"><%--保存--%><eidea:label key="common.button.save"/></button>
                        <a href="#/list" class="btn btn-default btn-sm"><%--返回--%><eidea:label key="common.button.back"/></a>
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
            </form>
        </div>
    </div>
</div>
