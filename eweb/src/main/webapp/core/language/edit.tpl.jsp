<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/12/9
  Time: 8:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">
    <form role="form" name="editForm" novalidate ng-submit="save()">
        <div class="page-header button-css">
            <button type="reset" ng-click="create()" class="btn btn-primary btn-sm" ng-show="canAdd" title="<eidea:label key="common.button.create"/>"><%--新建--%>
                <i class="fa fa-file-o fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.create"/>
            </button>
            <button type="submit" class="btn btn-primary btn-sm" ng-show="canSave" title="<eidea:label key="common.button.save"/>"><%--保存--%>
                <i class="fa fa-save fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.save"/></button>
            <a href="#/list" class="btn btn-primary btn-sm" title="<eidea:label key="common.button.back"/>"><%--返回--%>
                <i class="fa fa-mail-reply fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.back"/></a>
            <button type="button" class="btn  btn-primary btn-sm" title="<eidea:label key="common.button.home.page"/>">
                <i class="fa fa-step-backward fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.label.firstpage"/>
            </button>
            <button type="button" class="btn  btn-primary btn-sm" title="<eidea:label key="common.button.previous.page"/>">
                <i class="fa fa-caret-left fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.label.previouspage"/>
            </button>
            <button type="button" class="btn  btn-primary btn-sm" title="<eidea:label key="common.button.next.page"/>">
                <i class="fa fa-caret-right fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.label.nextpage"/>
            </button>
            <button type="button" class="btn  btn-primary btn-sm" title="<eidea:label key="common.button.last.page"/>">
                <i class="fa fa-step-forward fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.label.lastpage"/>
            </button>
            <button type="button" class="btn  btn-primary btn-sm" title="<eidea:label key="common.button.copy"/>">
                <i class="fa fa-copy fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.copy"/>
            </button>
            <button type="button" class="btn  btn-primary btn-sm" title="<eidea:label key="common.button.auditing"/>">
                <i class="fa fa-gavel fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.auditing"/>
            </button>
            <div class="btn-group">
                <button type="button" class="btn btn-primary btn-sm" data-toggle="dropdown" title="<eidea:label key="common.button.export"/>">
                    <i class="fa fa-download fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.export"/>
                    <span class="caret"></span>
                </button>
                <ul class="dropdown-menu" role="menu">
                    <li><a href="#" class="list-group-item"><i class="fa fa-file-archive-o fa-1x" aria-hidden="true"></i>&nbsp;csv</a></li>
                    <li><a href="#" class="list-group-item"><i class="fa fa-file-excel-o fa-1x" aria-hidden="true"></i>&nbsp;excel</a></li>
                    <li><a href="#" class="list-group-item"><i class="fa fa-file-pdf-o fa-1x" aria-hidden="true"></i>&nbsp;pdf</a></li>
                    <li><a href="#" class="list-group-item"><i class="fa fa-file-archive-o fa-1x" aria-hidden="true"></i>&nbsp;rtf</a></li>
                    <li><a href="#" class="list-group-item"><i class="fa fa-file-word-o fa-1x" aria-hidden="true"></i>&nbsp;word</a></li>
                    <li><a href="#" class="list-group-item"><i class="fa fa-file-archive-o fa-1x" aria-hidden="true"></i>&nbsp;xml</a></li>
                </ul>
            </div>
            <button type="button" class="btn  btn-primary btn-sm" title="<eidea:label key="common.button.print"/>">
                <i class="fa fa-print fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.print"/>
            </button>
            <button type="button" class="btn  btn-primary btn-sm" title="<eidea:label key="common.button.attachment"/>">
                <i class="fa fa-ils fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.attachment"/>
            </button>
            <button type="button" class="btn  btn-primary btn-sm" title="<eidea:label key="common.button.refresh"/>">
                <i class="fa fa-refresh fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.refresh"/>
            </button>
            <button type="button" class="btn  btn-primary btn-sm" title="<eidea:label key="common.button.operation.log"/>">
                <i class="fa fa-file-text-o fa-1x" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.operation.log"/>
            </button>
        </div>
        <div class="row-fluid">
            <div class="span12">
                <br>
                    <div class="form-group">
                        <label for="code"><%--code--%><eidea:label key="language.column.code"/></label>
                        <input type="text" class="form-control" id="code" name="code" ng-model="languageBo.code"
                               placeholder="<eidea:message key="language.input.code"/>" required ng-minlength="2" ng-maxlength="10"
                               ng-disabled="!languageBo.created">
                    </div>
                    <div class="form-group">
                        <label for="name"><%--name--%><eidea:label key="datadict.column.name"/></label>
                        <input type="text" class="form-control" id="name" placeholder="<eidea:message key="org.input.name"/>" ng-model="languageBo.name"
                               required ng-minlength="2" ng-maxlength="100">
                    </div>
                    <div class="form-group">
                        <label for="remark"><%--remark--%><eidea:label key="base.remarks"/></label>
                        <input type="text" class="form-control" id="remark" placeholder="<eidea:message key="org.input.remark"/>"
                               ng-model="languageBo.remark" ng-maxlength="500">
                    </div>
                    <div class="form-group">
                        <label for="isactive"><%--是否有效--%><eidea:label key="base.whetherEffective"/><input type="checkbox" class="form-control" id="isactive"
                                                          ng-true-value="'Y'" ng-false-value="'N'"
                                                          ng-model="languageBo.isactive"></label>

                    </div>
                    <div>
                        <p><%--语言翻译--%><eidea:label key="language.translation"/></p>
                        <table id="international_list" class="table table-hover table-bordered">
                            <thead>
                            <tr>
                                <th><%--序号--%><eidea:label key="base.serialNumber"/></th>
                                <th><%--language--%><eidea:label key="language.language"/></th>
                                <th><%--名称--%><eidea:label key="datadict.column.name"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr ng-repeat="languageTrlBo in languageBo.languageTrlBoList track by $index">
                                <td>{{$index+1}}</td>
                                <td>
                                    <input type="text" readonly ng-model="languageTrlBo.lang">
                                </td>
                                <td class="form-group">
                                    <input type="text" required ng-model="languageTrlBo.name">
                                </td>

                            </tr>
                            </tbody>
                        </table>
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