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
            <li><a href="javascript:;"><i class="icon-fire"></i><eidea:label key="client.title"/></a></li>
        </ol>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <br>
            <form role="form" name="editForm" novalidate  ng-submit="save()">

                <div class="form-group">
                    <label for="code"><%--no--%><eidea:label key="client.column.no"/></label>
                    <input type="text" class="form-control" id="code" name="code" ng-model="clientBo.no" placeholder="<eidea:message key="client.input.no"/>" required ng-minlength="2" ng-maxlength="10"
                           ng-disabled="clientBo.id!=null" ng-pattern="/^[a-zA-Z0-9]+$/">
                </div>
                <div class="form-group">
                    <label for="name"><%--name--%><eidea:label key="datadict.column.name"/></label>
                    <input type="text" class="form-control" id="name" placeholder="<eidea:message key="org.input.name"/>" ng-model="clientBo.name" required ng-minlength="2" ng-maxlength="100">
                </div>
                <div class="form-group">
                    <label for="remark"><%--remark--%><eidea:label key="base.remarks"/></label>
                    <input type="text" class="form-control" id="remark" placeholder="<eidea:message key="org.input.remark"/>" ng-model="clientBo.remark"   ng-maxlength="200">
                </div>
                <div class="form-group">
                    <label for="isactive"><%--是否有效--%><eidea:label key="base.whetherEffective"/><input type="checkbox" class="form-control" id="isactive"   ng-true-value="'Y'" ng-false-value="'N'" ng-model="clientBo.isactive"  ></label>

                </div>
                <div class="form-group">
                    <p class="text-right">
                        <button type="reset" ng-click="create()"  class="btn btn-default btn-sm" ng-show="canAdd"><%--新建--%><eidea:label key="common.button.create"/></button>
                        <button type="submit" class="btn btn-default btn-sm" ng-show="canSave"><%--保存--%><eidea:label key="common.button.save"/></button>
                        <a href="#/list" class="btn btn-default btn-sm"><%--返回--%><eidea:label key="common.button.back"/></a>
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