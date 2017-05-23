<%--
User: 刘大磊
Date: 2017-05-06 07:51:36
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">
    <form role="form" name="editForm" novalidate  ng-submit="save()">
        <jsp:include page="/common/common_edit_button.jsp"/>
        <div class="row-fluid">
            <div class="span12">
                <br>

                <div class="form-group">
                    <label for="key" ><%--key--%><eidea:label key="base.settings.label.key"/></label>
                            <input type="text" class="form-control" id="key" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.settings.label.key" type="label"/></eidea:message>" ng-model="settingsPo.key" >

                </div>
                <div class="form-group">
                    <label for="value" ><%--键值--%><eidea:label key="base.settings.label.value"/></label>
                            <input type="text" class="form-control" id="value" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.settings.label.value" type="label"/></eidea:message>" ng-model="settingsPo.value" >

                </div>
                <div class="form-group">
                    <label for="description" ><%--description--%><eidea:label key="base.settings.label.description"/></label>
                            <input type="text" class="form-control" id="description" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.settings.label.description" type="label"/></eidea:message>" ng-model="settingsPo.description" >

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