<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<!-- 附件上传模态框（Modal） -->
<div class="modal fade modal-fail" id="attachmentModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    <%--附件上传--%><eidea:label key="common.title.attachment.upload"/>
                </h4>
            </div>
            <div class="modal-body">
                <form role="form" name="uploadForm" novalidate ng-submit="attachmentUpload()">
                    <button type="submit" class="btn  btn-primary btn-sm" title="<eidea:label key="common.button.upload"/>" ng-disabled="canUpload">
                        <i class="fa fa-upload fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.upload"/>
                    </button></br>
                    <div class="row-fluid">
                        <div class="span12">
                            <div class="form-group">
                                <label for="fileKeyword"><%--附件关键字--%><eidea:label key="common.upload.fileKeyword"/></label>
                                <input type="text" id="fileKeyword" class="form-control" ng-model="commonFileBo.fileKeyword"
                                       placeholder="<eidea:label key="common.upload.fileKeyword"/>" ng-maxlength="255">
                            </div>
                            <div class="form-group">
                                <label for="fileAbstract"><%--附件简要--%><eidea:label key="common.upload.fileAbstract"/></label>
                                <input type="text" id="fileAbstract" class="form-control" ng-model="commonFileBo.fileAbstract"
                                       placeholder="<eidea:label key="common.upload.fileAbstract"/>" ng-maxlength="255">
                            </div>
                            <div class="form-group">
                                <label><%--附件名称--%><eidea:label key="common.upload.attachment.name"/>：{{files[0].name}}</label>
                            </div>
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
                    </div>
                </form>
                <table class="table table-hover table-striped table-condensed">
                    <thead>
                        <th><%--序号--%><eidea:label key="base.serialNumber"/></th>
                        <th><%--附件名称--%><eidea:label key="common.upload.attachment.name"/></th>
                        <th><%--上传时间--%><eidea:label key="common.upload.date"/></th>
                        <th><%--操作--%><eidea:label key="common.upload.operation"/></th>
                    </thead>
                    <tbody>
                    <tr ng-repeat="attachment in attachmentList track by $index" ng-class-even="success">
                        <input type="hidden" ng-model="attachmentId" value="{{attachment.id}}"/>
                        <td>{{$index+1}}</td>
                        <td>{{attachment.fileName}}</td>
                        <td>{{attachment.created|date:'yyyy-MM-dd HH:mm:ss'}}</td>
                        <td>
                            <button type="button" class="btn  btn-primary btn-sm" ng-click="attachmentDelete(attachment.id)" title="<eidea:label key="common.button.delete"/>"><%--删除--%>
                                <i class="fa fa-trash-o fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.delete"/>
                            </button>
                            <a href="<c:url value="/common/attachmentDownload"/>?id={{attachment.id}}" class="btn btn-primary btn-sm"
                               title="<eidea:label key="common.button.download"/>"><%--下载--%>
                                <i class="fa fa-download fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.download"/>
                            </a>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
<!--操作日志模态框-->
<div class="modal fade modal-fail" id="changeLogModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="changeLogo">
                    <%--操作日志--%><eidea:label key="common.title.operation.log"/>
                </h4>
            </div>
            <div class="modal-body">
                <div class="row-fluid">
                    <div class="span12">
                        <br>
                        <div class="form-group">
                            <label ><eidea:label key="searchcolumn.column.tableName"/></label> &nbsp;&nbsp;<span ng-bind="changelogVo.changelogBo.name"></span> &nbsp;&nbsp;&nbsp;

                        </div>
                        <div class="form-group">
                            <label><eidea:label key="javaclass.column.classname"/></label> &nbsp;&nbsp;<span ng-bind="changelogVo.changelogBo.poClass"></span>
                        </div>
                        <div class="form-group">
                            <label ><eidea:label key="changelog.business.name"/></label> &nbsp;&nbsp;&nbsp;<span
                                ng-bind="changelogVo.changelogBo.tableName"></span>
                        </div>
                        <div class="form-group">
                            <label><eidea:label key="changelog.primarykey"/></label>&nbsp;&nbsp; <span ng-bind="changelogVo.changelogBo.pk"></span>&nbsp;&nbsp;&nbsp;
                        </div>
                        <div class="form-group">
                            <label ><eidea:label key="table.column.buskey"/></label> &nbsp;&nbsp;<span ng-bind="changelogVo.changelogBo.buPk"></span>
                        </div>
                        <div>
                            <p><eidea:label key="changelog.operation.list"/></p>
                            <div style="overflow:auto;">
                                <table id="international_list"
                                       class="table table-hover table-bordered">
                                    <thead>
                                    <tr>
                                        <th ng-repeat="model in changelogVo.header track by $index">{{model}}</th>
                                    </tr>
                                    </thead>
                                    <tbody>

                                    <tr ng-repeat="model in changelogVo.bodyList track by $index">
                                        <td ng-repeat="column in model track by $index">{{column}}</td>
                                    </tr>

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--模态框显示图片-->
<!--工作流图片模态框-->
<div class="modal fade modal-fail" id="imageShowModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="imageShowModalLabel">
                    <%--图片预览--%><eidea:label key="workflow.title.picture.preview"/>
                </h4>
            </div>
            <div class="modal-body">
                <img id="imageShow" style="width:800px;"/>
            </div>
        </div>
    </div>
</div>