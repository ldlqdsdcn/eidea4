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
            <li><a href="javascript:;"><i class="icon-tasks"></i> <%--标签--%><eidea:label key="customerext.column.proplabel"/></a></li>
        </ol>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <br>
            <form role="form" name="editForm" novalidate  ng-submit="save()">

                <div class="form-group">
                    <label for="key"><%--key值--%><eidea:label key="label.keyvalueone"/></label>
                    <input type="text" class="form-control" id="key" name="key" ng-model="labelBo.key" placeholder="<eidea:message key="label.input.keyvalue"/>" required ng-minlength="10" ng-maxlength="50"  >
                </div>
                <div class="form-group">
                    <label for="msgtext"><%--消息内容--%><eidea:label key="label.messagevalue"/></label>
                    <input type="text" class="form-control" id="msgtext" placeholder="<eidea:message key="label.input.message"/>" ng-model="labelBo.msgtext" required ng-minlength="1" ng-maxlength="100">
                </div>
                <div class="form-group">
                    <label for="isactive"><%--是否有效--%><eidea:label key="base.whetherEffective"/><input type="checkbox" class="form-control" id="isactive"   ng-true-value="'Y'" ng-false-value="'N'" ng-model="labelBo.isactive"  ></label>

                </div>
                <div>
                    <p><%--标签翻译--%><eidea:label key="label.translation"/></p>
                 <table id="international_list"  class="table table-hover table-bordered">
                        <thead>
                        <tr>
                            <th><%--序号--%><eidea:label key="base.serialNumber"/></th><th>language</th><th><%--名称--%><eidea:label key="datadict.column.name"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr ng-repeat="labelTrlBo in labelBo.labelTrlBoList track by $index">
                            <td>{{$index+1}}</td>
                            <td class="form-group">
                                <input type="text" readonly ng-model="labelTrlBo.lang" disabled formnovalidate>
                            </td>
                            <td  class="form-group">
                                <input type="text" required ng-model="labelTrlBo.msgtext" >
                            </td>

                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="form-group">
                    <p class="text-right">
                        <button type="reset" ng-click="create()"  class="btn btn-default btn-sm" ng-show="canAdd"><eidea:label key="common.button.create"/><%--新建--%></button>
                        <button type="submit" class="btn btn-default btn-sm" ng-show="canSave"><eidea:label key="common.button.save"/><%--保存--%></button>
                        <a href="#/list" class="btn btn-default btn-sm"><eidea:label key="common.button.back"/><%--返回--%></a>
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