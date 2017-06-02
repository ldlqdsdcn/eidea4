<%--
User: 刘大磊
Date: 2017-05-02 13:09:39
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
                        <label for="filename" ><%--filename--%><eidea:label key="base.commonFile.label.filename"/></label>
                                <input type="text" class="form-control" id="filename" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.commonFile.label.filename" type="label"/></eidea:message>" ng-model="commonFilePo.filename" >

                    </div>
                    <div class="form-group">
                        <label for="fileType" ><%--fileType--%><eidea:label key="base.commonFile.label.fileType"/></label>
                                <input type="text" class="form-control" id="fileType" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.commonFile.label.fileType" type="label"/></eidea:message>" ng-model="commonFilePo.fileType" >

                    </div>
                    <div class="form-group">
                        <label for="path" ><%--path--%><eidea:label key="base.commonFile.label.path"/></label>
                                <input type="text" class="form-control" id="path" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.commonFile.label.path" type="label"/></eidea:message>" ng-model="commonFilePo.path" >

                    </div>
                    <div class="form-group">
                        <label for="extension" ><%--extension--%><eidea:label key="base.commonFile.label.extension"/></label>
                                <input type="text" class="form-control" id="extension" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.commonFile.label.extension" type="label"/></eidea:message>" ng-model="commonFilePo.extension" >

                    </div>
                    <div class="form-group">
                        <label for="fileSize" ><%--fileSize--%><eidea:label key="base.commonFile.label.fileSize"/></label>
                                <input type="text" class="form-control" id="fileSize" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.commonFile.label.fileSize" type="label"/></eidea:message>" ng-model="commonFilePo.fileSize" >

                    </div>
                    <div class="form-group">
                        <label for="fileCreated" ><%--fileCreated--%><eidea:label key="base.commonFile.label.fileCreated"/></label>
                                <div class="input-group date bootstrap-datetime">
                                    <input type="text" class="form-control" id="fileCreated" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.commonFile.label.fileCreated" type="label"/></eidea:message>" ng-model="commonFilePo.fileCreated"
                                    uib-datepicker-popup="yyyy-MM-dd HH:mm:ss" ng-disabled="true">
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>

                    </div>
                    <div class="form-group">
                        <label for="fileUpdated" ><%--fileUpdated--%><eidea:label key="base.commonFile.label.fileUpdated"/></label>
                                <div class="input-group date bootstrap-datetime">
                                    <input type="text" class="form-control" id="fileUpdated" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.commonFile.label.fileUpdated" type="label"/></eidea:message>" ng-model="commonFilePo.fileUpdated"
                                    uib-datepicker-popup="yyyy-MM-dd HH:mm:ss" ng-disabled="true">
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>

                    </div>
                    <div class="form-group">
                        <label for="fileIsreadonly" ><%--fileIsreadonly--%><eidea:label key="base.commonFile.label.fileIsreadonly"/></label>
                                <input type="text" class="form-control" id="fileIsreadonly" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.commonFile.label.fileIsreadonly" type="label"/></eidea:message>" ng-model="commonFilePo.fileIsreadonly" >

                    </div>
                    <div class="form-group">
                        <label for="fileIshidden" ><%--fileIshidden--%><eidea:label key="base.commonFile.label.fileIshidden"/></label>
                                <input type="text" class="form-control" id="fileIshidden" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.commonFile.label.fileIshidden" type="label"/></eidea:message>" ng-model="commonFilePo.fileIshidden" >

                    </div>
                    <div class="form-group">
                        <label for="created" ><%--创建时间--%><eidea:label key="base.commonFile.label.created"/></label>
                                <div class="input-group date bootstrap-datetime">
                                    <input type="text" class="form-control" id="created" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.commonFile.label.created" type="label"/></eidea:message>" ng-model="commonFilePo.created"
                                    uib-datepicker-popup="yyyy-MM-dd HH:mm:ss" ng-disabled="true">
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>

                    </div>
                    <div class="form-group">
                        <label for="commonFileSettingId" ><%--commonFileSettingId--%><eidea:label key="base.commonFile.label.commonFileSettingId"/></label>
                                <input type="text" class="form-control" id="commonFileSettingId" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.commonFile.label.commonFileSettingId" type="label"/></eidea:message>" ng-model="commonFilePo.commonFileSettingId" >

                    </div>
                    <div class="form-group">
                        <label for="fileAbstract" ><%--fileAbstract--%><eidea:label key="base.commonFile.label.fileAbstract"/></label>
                                <input type="text" class="form-control" id="fileAbstract" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.commonFile.label.fileAbstract" type="label"/></eidea:message>" ng-model="commonFilePo.fileAbstract" >

                    </div>
                    <div class="form-group">
                        <label for="fileKeyword" ><%--fileKeyword--%><eidea:label key="base.commonFile.label.fileKeyword"/></label>
                                <input type="text" class="form-control" id="fileKeyword" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.commonFile.label.fileKeyword" type="label"/></eidea:message>" ng-model="commonFilePo.fileKeyword" >

                    </div>
                    <div class="form-group">
                        <label for="fileMode" ><%--fileMode--%><eidea:label key="base.commonFile.label.fileMode"/></label>
                                <input type="text" class="form-control" id="fileMode" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.commonFile.label.fileMode" type="label"/></eidea:message>" ng-model="commonFilePo.fileMode" >

                    </div>
                    <div class="form-group">
                        <label for="fileBlob" ><%--fileBlob--%><eidea:label key="base.commonFile.label.fileBlob"/></label>
                                <input type="text" class="form-control" id="fileBlob" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.commonFile.label.fileBlob" type="label"/></eidea:message>" ng-model="commonFilePo.fileBlob" >

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