<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<!-- 模态框（Modal） -->
<div class="modal fade modal-fail" id="attachmentModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    附件上传
                </h4>
            </div>
            <div class="modal-body">
                <form role="form" name="editForm">
                    <button type="button" class="btn  btn-primary btn-sm" ng-click="attachmentUpload()" title="<eidea:label key="common.button.upload"/>">
                        <i class="fa fa-upload fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.upload"/>
                    </button></br>
                    <div class="row-fluid">
                        <div class="span12">
                            <div class="form-group">
                                <label for="fileKeyword">附件关键字</label>
                                <input type="text" id="fileKeyword" class="form-control" ng-model="commonFileBo.fileKeyword"
                                       placeholder="附件关键字" ng-maxlength="255">
                            </div>
                            <div class="form-group">
                                <label for="fileAbstract">附件简要</label>
                                <input type="text" id="fileAbstract" class="form-control" ng-model="commonFileBo.fileAbstract"
                                       placeholder="附件简要" ng-maxlength="255">
                            </div>
                            <div class="form-group">
                                <label>附件名称：{{files[0].name}}</label>
                            </div>
                            <div class="form-group">
                                <div class="up-buttons">
                                    <div ngf-drop ngf-select ng-model="files" ngf-model-invalid="invalidFiles"
                                         ngf-model-options="modelOptionsObj"
                                         ngf-multiple="multiple" ngf-pattern="pattern" ngf-accept="acceptSelect"
                                         ng-disabled="disabled" ngf-capture="capture"
                                         ngf-drag-over-class="dragOverClassObj"
                                         ngf-validate="validateObj"
                                         ngf-resize="resizeObj"
                                         ngf-resize-if="resizeIfFn($file, $width, $height)"
                                         ngf-dimensions="dimensionsFn($file, $width, $height)"
                                         ngf-duration="durationFn($file, $duration)"
                                         ngf-keep="keepDistinct ? 'distinct' : keep"
                                         ngf-fix-orientation="orientation"
                                         ngf-max-files="maxFiles"
                                         ngf-ignore-invalid="ignoreInvalid"
                                         ngf-run-all-validations="runAllValidations"
                                         ngf-allow-dir="allowDir" class="drop-box" ngf-drop-available="dropAvailable" aria-required="true"
                                    >附件上传
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
                        <th>序号</th>
                        <th>附件名称</th>
                        <th>上传时间</th>
                        <th>操作</th>
                    </thead>
                    <tbody>
                    <tr ng-repeat="attachment in attachmentList track by $index" ng-class-even="success">
                        <input type="hidden" ng-model="attachmentId" value="{{attachment.id}}"/>
                        <td>{{$index+1}}</td>
                        <td>{{attachment.filename}}</td>
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