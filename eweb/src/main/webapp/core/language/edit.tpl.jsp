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
    <div class="page-header">
        <ol class="breadcrumb">
            <li><a href="javascript:;"><i class="icon-tasks"></i><%-- 语言信息--%><eidea:label key="language.title"/></a></li>
        </ol>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <br>
            <form role="form" name="editForm" novalidate ng-submit="save()">

                <div class="form-group">
                    <label for="code"><%--code--%><eidea:label key="language.column.code"/></label>
                    <input type="text" class="form-control" id="code" name="code" ng-model="languageBo.code"
                           placeholder="<eidea:message key="language.input.code"/>" required ng-minlength="2" ng-maxlength="10"
                           ng-disabled="!languageBo.created">
                </div>
                <div class="form-group">
                    <label for="name"><%--name--%><eidea:label key="datadict.column.name"/></label>
                    <input type="text" class="form-control" id="name" placeholder="<eidea:message key="org.input.name"/>" ng-model="languageBo.name"
                           required ng-minlength="2" ng-maxlength="100" ng-blur="findExistLanguageByName()">
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
                    <p class="text-right">
                        <button type="reset" ng-click="create()" class="btn btn-default btn-sm" ng-show="canAdd"><%--新建--%><eidea:label key="common.button.create"/>
                        </button>
                        <button type="submit" class="btn btn-default btn-sm" ng-show="canSave"><%--保存--%><eidea:label key="common.button.save"/></button>
                        <a href="#/list" class="btn btn-default btn-sm"><%--返回--%><eidea:label key="common.button.back"/></a>
                    </p>
                </div>
                <div class="form-group">
                    <p class="text-center" style="color: red">
                        {{message}}
                    </p>
                </div>
            </form>
        </div>
    </div>
</div>