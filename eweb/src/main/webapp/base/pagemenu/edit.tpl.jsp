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
            <li ng-if="pageMenuBo.isCheck=='true'"><a href="javascript:;"><i class="icon-tasks"></i><eidea:label key="dymenu.label.editmenu"/></a></li>
            <li ng-if="pageMenuBo.isCheck=='false'"><a href="javascript:;"><i class="icon-tasks"></i><eidea:label key="dymenu.label.newmenu"/></a></li>
        </ol>
    </div>
    <div class="row-fluid">
        <div class="from-group">
            <form role="form" name="editForm" novalidate  ng-submit="save()" class="form-horizontal form-label-left">
                <div class="form-group">
                    <label class="col-sm-2 text-right"><eidea:label key="dymenuForm.label.sequence"/></label>
                    <div class="col-sm-10">
                        <input type="number" class="form-control" id="seqNo" placeholder="<eidea:message key="login.input.order"/>" ng-model="pageMenuBo.seqNo" required ng-minlength="1" ng-maxlength="10" min="0" ng-pattern="/^\d+$/">
                     </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 text-right">name</label>
                    <div class="col-sm-10">
                    <input type="text" class="form-control" id="name" placeholder="<eidea:message key="login.input.name"/>" ng-model="pageMenuBo.name" required ng-minlength="2" ng-maxlength="100">
                    </div>
                </div>
                <div class="form-group">
                <label class="col-sm-2 text-right">url</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="url" placeholder="<eidea:message key="login.input.url"/>" ng-model="pageMenuBo.url"   ng-maxlength="500">
                </div>
            </div>
                <div class="form-group">
                    <label class="col-sm-2 text-right">icon</label>
                    <div class="col-sm-10">
                        <input type="text" class="form-control" id="icon" placeholder="<eidea:label key="dymenuForm.label.icon"/>" ng-model="pageMenuBo.icon"   ng-maxlength="100">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 text-right">remark</label>
                    <div class="col-sm-10">
                    <input type="text" class="form-control" id="remark" placeholder="<eidea:message key="login.input.remark"/>" ng-model="pageMenuBo.remark"   ng-maxlength="500">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 text-right"><eidea:label key="dymenuForm.label.menutype"/></label>
                    <div class="col-sm-10">
                        <select class="form-control" ng-model="pageMenuBo.menuType" ng-options="id for (id,name) in menuTypeList"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 text-right"><eidea:label key="module.label.parentmenu"/></label>
                    <div class="col-sm-10">
                        <select class="form-control" ng-model="pageMenuBo.parentMenuId" ng-options="parentMenu.id as parentMenu.name for parentMenu in pageMenuBoList"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 text-right"><eidea:label key="base.whetherEffective"/></label>
                    <div class="col-sm-10">
                        <input type="checkbox" id="isactive"   ng-true-value="'Y'" ng-false-value="'N'" ng-model="pageMenuBo.isactive" ></label>
                    </div>
                </div>
                <div>
                     <p><%--标签翻译--%><eidea:label key="label.translation"/></p>
                  <table id="international_list"  class="table table-hover table-bordered">
                         <thead>
                         <tr>
                             <th><%--序号--%><eidea:label key="base.serialNumber"/></th><th>language</th><th><%--名称--%><eidea:label key="datadict.column.name"/></th><th><eidea:label key="base.remarks"/></th>
                         </tr>
                         </thead>
                         <tbody>
                         <tr ng-repeat="pageMenuTrlBo in pageMenuBo.pageMenuTrlBo track by $index">
                             <td>{{$index+1}}</td>
                             <td class="form-group">
                                 <input type="text" readonly ng-model="pageMenuTrlBo.languageCode" disabled formnovalidate>
                             </td>
                             <td  class="form-group">
                                 <input type="text" required ng-model="pageMenuTrlBo.name" >
                             </td>
                             <td  class="form-group">
                                 <input type="text"  ng-model="pageMenuTrlBo.remark" >
                             </td>
 
                         </tr>
                         </tbody>
                     </table>
                </div>
                <div class="from-group">
                    <p class="text-right">
                        <button type="reset" ng-click="create()"  class="btn btn-default btn-sm" ng-show="canAdd"><eidea:label key="common.button.create"/></button>
                        <button type="submit" class="btn btn-default btn-sm" ng-show="canSave"><eidea:label key="common.button.save"/></button>
                        <a href="#/list" class="btn btn-default btn-sm"><eidea:label key="common.button.back"/></a>
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