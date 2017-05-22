<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">
    <form role="form" name="editForm" novalidate  ng-submit="save()">
        <jsp:include page="/common/common_edit_button.jsp"/>
        <div class="row-fluid">
            <div class="span12">
                <br>
                    <div class="form-group">
                        <label for="key"><%--键值--%><eidea:label key="label.keyvalue"/></label>
                        <input type="text" class="form-control" id="key" ng-model="reportBo.key" placeholder="<eidea:message key="common.please.input">
    <eidea:param value="label.keyvalue" type="label"/></eidea:message>" required ng-minlength="2" ng-maxlength="45" ng-disabled="!reportBo.created">
                    </div>
                    <div class="form-group">
                        <label for="value"><%--信息--%><eidea:label key="label.message"/></label>
                        <input type="text" class="form-control" id="value" placeholder="<eidea:message key="common.please.input">
    <eidea:param value="label.message" type="label"/></eidea:message>" ng-model="reportBo.value" required ng-minlength="2" ng-maxlength="45">
                    </div>
                    <div class="form-group">
                        <label for="init"><%--系统初始化--%><eidea:label key="reportSettings.init"/>
                            <input type="checkbox"  id="init" ng-true-value="'Y'" ng-false-value="'N'" ng-model="reportBo.init" ng-disabled="true">
                        </label>
                    </div>
                    <div class="form-group">
                        <p class="text-center" style="color: red">
                            {{message}}
                        </p>
                    </div>
            </div>
        </div>
    </form>
</div>