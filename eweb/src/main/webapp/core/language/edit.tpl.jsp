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
    <ul class="nav nav-tab vertical-tab" role="tablist" id="vtab">
        <li role="presentation" class="active">
            <a href="#tab-home" role="tab" data-toggle="tab">tab-home</a>
        </li>
        <li role="presentation">
            <a href="#tab-1" role="tab" data-toggle="tab">tab-1</a>
        </li>
        <li role="presentation">
            <a href="#tab-2" role="tab" data-toggle="tab">tab-2</a>
        </li>
        <li role="presentation">
            <a href="#tab-3" role="tab" data-toggle="tab">tab-3</a>
        </li>
        <li role="presentation">
            <a href="javascript:void(0);" role="tab" class="tab-not-active">tab-4</a>
        </li>
    </ul>
    <div class="tab-content vertical-tab-content">
        <div role="tabpanel" class="tab-pane active" id="tab-home">
            <form role="form" name="editForm" novalidate ng-submit="save()">
                <jsp:include page="/common/common_edit_button.jsp"/>
                <div class="row-fluid">
                    <div class="span12">
                        <br>
                        <div class="form-group">
                            <label for="code"><%--code--%><eidea:label key="language.column.code"/></label>
                            <input type="text" class="form-control" id="code" name="code" ng-model="languageBo.code"
                                   placeholder="<eidea:message key="language.input.code"/>" required ng-minlength="2"
                                   ng-maxlength="10"
                                   ng-disabled="!languageBo.created">
                        </div>
                        <div class="form-group">
                            <label for="name"><%--name--%><eidea:label key="datadict.column.name"/></label>
                            <input type="text" class="form-control" id="name"
                                   placeholder="<eidea:message key="org.input.name"/>" ng-model="languageBo.name"
                                   required ng-minlength="2" ng-maxlength="100" >
                        </div>
                        <div class="form-group">
                            <label for="languageIso"><%--语言码--%><eidea:label key="language.language.iso"/></label>
                            <input type="text" class="form-control" id="languageIso"
                                   placeholder="<eidea:message key="language.input.iso"/>" ng-model="languageBo.languageIso"
                                   required ng-minlength="2" ng-maxlength="2"  ng-disabled="!languageBo.created">
                        </div>
                        <div class="form-group">
                            <label for="countryCode"><%--区域码--%><eidea:label key="language.country.code"/></label>
                            <input type="text" class="form-control" id="countryCode"
                                   placeholder="<eidea:message key="language.input.country_code"/>" ng-model="languageBo.countryCode"
                                   required ng-minlength="2" ng-maxlength="2" ng-disabled="!languageBo.created">
                        </div>
                        <div class="form-group">
                            <label for="remark"><%--remark--%><eidea:label key="base.remarks"/></label>
                            <input type="text" class="form-control" id="remark"
                                   placeholder="<eidea:message key="org.input.remark"/>"
                                   ng-model="languageBo.remark" ng-maxlength="500">
                        </div>
                        <div class="form-group">
                            <label for="isactive"><%--是否有效--%><eidea:label key="base.datadict.label.isactive"/><input
                                    type="checkbox" class="form-control" id="isactive"
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
        <div role="tabpanel" class="tab-pane" id="tab-1">tab-1</div>
        <div role="tabpanel" class="tab-pane" id="tab-2">tab-2</div>
        <div role="tabpanel" class="tab-pane" id="tab-3">tab-3</div>
        <div role="tabpanel" class="tab-pane" id="tab-4">tab-4</div>
    </div>

</div>
<script type='text/javascript' src="<c:url value="/js/ondrag-start.js"/>"></script>