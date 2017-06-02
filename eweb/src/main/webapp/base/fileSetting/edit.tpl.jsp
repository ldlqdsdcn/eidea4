<%--
User: 刘大磊
Date: 2017-05-02 13:07:50
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
                        <label for="name" ><%--名称--%><eidea:label key="base.fileSetting.label.name"/></label>
                                <input type="text" required maxlength="255" class="form-control" id="name" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fileSetting.label.name" type="label"/></eidea:message>" ng-model="fileSettingPo.name" >

                    </div>
                    <div class="form-group">
                        <label for="rootDirectory" ><%--rootDirectory--%><eidea:label key="base.fileSetting.label.rootDirectory"/></label>
                                <input type="text" required maxlength="255" class="form-control" id="rootDirectory" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fileSetting.label.rootDirectory" type="label"/></eidea:message>" ng-model="fileSettingPo.rootDirectory" >

                    </div>
                    <div class="form-group">
                        <label for="fileSize" ><%--fileSize--%><eidea:label key="base.fileSetting.label.fileSize"/></label>
                                <input type="text" required maxlength="11" class="form-control" id="fileSize" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fileSetting.label.fileSize" type="label"/></eidea:message>" ng-model="fileSettingPo.fileSize" >

                    </div>
                    <div class="form-group">
                        <label for="created" ><%--创建时间--%><eidea:label key="base.fileSetting.label.created"/></label>
                                <div class="input-group date bootstrap-datetime">
                                    <input type="text" required class="form-control" id="created" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fileSetting.label.created" type="label"/></eidea:message>" ng-model="fileSettingPo.created"
                                    uib-datepicker-popup="yyyy-MM-dd HH:mm:ss" ng-disabled="true">
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>

                    </div>
                    <div class="form-group">
                        <label for="moduleId" ><%--moduleId--%><eidea:label key="base.fileSetting.label.moduleId"/></label>
                               <%-- <input type="text"  class="form-control" id="moduleId" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fileSetting.label.moduleId" type="label"/></eidea:message>" ng-model="fileSettingPo.moduleId" >--%>
                                <select id="moduleId" class="form-control" ng-model="fileSettingPo.moduleId" ng-options="module.id as module.name for module in fileSettingPo.moduleBos">
                                </select>
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
    <jsp:include page="/common/common_upload.jsp"/>
</div>