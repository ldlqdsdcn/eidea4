<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">
    <div class="form-group">
        <div class="up-buttons">
            <div ngf-drop ngf-select ng-model="files" ngf-multiple="multiple" ngf-max-size="20MB"
                 ngf-pattern="pattern" ngf-accept="acceptSelect" class="drop-box">
                <%--附件上传--%><eidea:label key="common.upload.attachment"/>
            </div>
        </div>
        <div class="preview">
            <img ngf-src="!files[0].$error && files[0]">
        </div>
    </div>
</div>