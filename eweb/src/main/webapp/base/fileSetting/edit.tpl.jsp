<%--
User: 刘大磊
Date: 2017-05-02 13:07:50
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">
    <div class="page-header">
        <ol class="breadcrumb">
            <li><a href="javascript:;"><i class="icon-fire"></i><eidea:label key="fileSetting.title"/></a></li>
        </ol>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <br>
            <form role="form" name="editForm" novalidate  ng-submit="save()">
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
             <%--   <div class="form-group">
                    <label for="fileTypes" >&lt;%&ndash;fileTypes&ndash;%&gt;<eidea:label key="base.fileSetting.label.fileTypes"/></label>
                            <input type="text" class="form-control" id="fileTypes" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fileSetting.label.fileTypes" type="label"/></eidea:message>" ng-model="fileSettingPo.fileTypes" >

                </div>
                <div class="form-group">
                    <label for="storageMode" >&lt;%&ndash;storageMode&ndash;%&gt;<eidea:label key="base.fileSetting.label.storageMode"/></label>
                            <input type="text" class="form-control" id="storageMode" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fileSetting.label.storageMode" type="label"/></eidea:message>" ng-model="fileSettingPo.storageMode" >

                </div>
                <div class="form-group">
                    <label for="ftpcommectionId" >&lt;%&ndash;ftpcommectionId&ndash;%&gt;<eidea:label key="base.fileSetting.label.ftpcommectionId"/></label>
                            <input type="text" class="form-control" id="ftpcommectionId" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fileSetting.label.ftpcommectionId" type="label"/></eidea:message>" ng-model="fileSettingPo.ftpcommectionId" >

                </div>--%>
                <div class="form-group">
                    <label for="created" ><%--创建时间--%><eidea:label key="base.fileSetting.label.created"/></label>
                            <div class="input-group date bootstrap-datetime">
                                <input type="text" required class="form-control" id="created" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fileSetting.label.created" type="label"/></eidea:message>" ng-model="fileSettingPo.created"
                                uib-datepicker-popup="yyyy-MM-dd HH:mm:ss">
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