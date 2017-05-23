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
    <form role="form" name="editForm" novalidate  ng-submit="saveNext()">
        <jsp:include page="/common/common_edit_button.jsp"/>
        <div class="row-fluid">
            <div class="span12">
                <br>
                    <div class="form-group">
                        <label for="name" ><%--名称--%><eidea:label key="workflow.column.name"/></label>
                        <input type="text" class="form-control" id="name" placeholder="<eidea:message key="workflow.input.name"/>" ng-model="model.name" required minlength="2" maxlength="50">
                    </div>
                    <div class="form-group">
                        <label for="key" ><%--键值--%><eidea:label key="workflow.column.keyvalue"/></label>
                        <input type="text" class="form-control" id="key" placeholder="<eidea:message key="workflow.input.keyvalue"/>" ng-model="model.key" minlength="2" maxlength="50">
                    </div>
                    <div class="form-group">
                        <label for="description" ><%--描述--%><eidea:label key="workflow.column.description"/></label>
                        <input type="text" class="form-control" id="description" placeholder="<eidea:message key="workflow.input.description"/>" ng-model="model.description" >
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
            </div>
        </div>
    </form>
</div>
