<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">
    <div class="modal-body">
        <div class="row-fluid">
            <div class="form-group">
                <label><%--工作流文件名称--%><eidea:label key="common.upload.workflow.name"/>：{{files[0].name}}</label>
            </div>
            <div class="form-group">
                <div class="up-buttons">
                    <div ngf-drop ngf-select ng-model="files" ngf-multiple="multiple" ngf-max-size="20MB"
                         ngf-pattern="pattern" ngf-accept="acceptSelect" class="drop-box">
                        <%--请点击选择工作流文件--%><eidea:label key="sys.workflow.select.upload.file"/>
                    </div>
                </div>
                <div class="preview">
                    <img ngf-src="!files[0].$error && files[0]">
                </div>
            </div>
        </div>
    </div>
</div>