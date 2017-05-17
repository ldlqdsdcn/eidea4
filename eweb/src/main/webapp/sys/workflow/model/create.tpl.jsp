<%--
  Created by IntelliJ IDEA.
  User: 刘大磊
  Date: 2017/5/16
  Time: 8:37
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="createCtrl">
    <div class="page-header">
        <ol class="breadcrumb">
            <li><a href="javascript:;"><i class="icon-fire"></i>新建工作流模型<%--<eidea:label key="model.title"/>--%></a></li>
        </ol>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <br>
            <form role="form" name="editForm" novalidate  ng-submit="saveNext()">
                <div class="form-group">
                    <label for="name" >名称</label>
                    <input type="text" class="form-control" id="name" placeholder="<eidea:message key="common.please.input">名称</eidea:message>" ng-model="model.name" required minlength="2" maxlength="50">
                </div>
                <div class="form-group">
                    <label for="key" >键值</label>
                    <input type="text" class="form-control" id="key" placeholder="<eidea:message key="common.please.input">键值</eidea:message>" ng-model="model.key" minlength="2" maxlength="50">
                </div>
                <div class="form-group">
                    <label for="description" >描述</label>
                    <input type="text" class="form-control" id="description" placeholder="<eidea:message key="common.please.input">描述</eidea:message>" ng-model="model.description" >
                </div>
                <div class="form-group">
                    <p class="text-right">
                        <button type="reset" ng-click="create()"  class="btn btn-default btn-sm" ng-show="canAdd"><%--新建--%><eidea:label key="common.button.create"/></button>
                        <button type="submit" class="btn btn-default btn-sm" ng-show="canAdd"><%--保存--%><eidea:label key="common.button.save"/></button>
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
