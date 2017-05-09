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
            <li><a href="javascript:;"><i class="icon-tasks"></i><eidea:label key="dymenus.title"/></a></li>
        </ol>
    </div>
    <div class="x_content">
        <div class="from-group">
            <form role="form" name="editForm" novalidate  ng-submit="save()" class="form-horizontal form-label-left input_mask">
                <table class="table table-borderless" >
                    <tr>
                        <td class="control-label"><eidea:label key="dymenuForm.label.sequence"/></td>
                        <td class="form-group"><input type="number" class="form-control" id="seqNo" placeholder="<eidea:message key="login.input.order"/>" ng-model="pageMenuBo.seqNo" required ng-minlength="1" ng-maxlength="10" min="0" ng-pattern="/^\d+$/"></td>
                        <td class="control-label">name</td><td class="form-group"> <input type="text" class="form-control" id="name" placeholder="<eidea:message key="login.input.name"/>" ng-model="pageMenuBo.name" required ng-minlength="2" ng-maxlength="100"></td>
                    </tr>
                    <tr>
                        <td class="control-label"> url</td><td colspan="3" class="form-group"> <input type="text" class="form-control" id="url" placeholder="<eidea:message key="login.input.url"/>" ng-model="pageMenuBo.url"   ng-maxlength="500"></td>
                    </tr>
                    <tr>
                        <td  class="control-label">icon</td>
                        <td class="form-group">  <input type="text" class="form-control" id="icon" placeholder="<eidea:label key="dymenuForm.label.icon"/>" ng-model="pageMenuBo.icon"   ng-maxlength="100"></td>
                        <td class="control-label"> <eidea:label key="dymenuForm.label.menutype"/></td>
                        <td class="form-group"> <select class="form-control"  ng-model="pageMenuBo.menuType" ng-options="id for (id,name) in menuTypeList"/></td>
                    </tr>
                    <tr>
                        <td class="control-label">  remark</td>
                        <td class="form-group" colspan="3"> <input type="text" class="form-control" id="remark" placeholder="<eidea:message key="login.input.remark"/>" ng-model="pageMenuBo.remark"   ng-maxlength="500"></td>
                    </tr>
                    <tr>
                        <td class="control-label"> <eidea:label key="module.label.parentmenu"/></td>
                        <td  class="form-group"><select class="form-control" ng-model="pageMenuBo.parentMenuId" ng-options="parentMenu.id as parentMenu.name for parentMenu in pageMenuBoList"/></td>
                        <td class="control-label"> <eidea:label key="base.whetherEffective"/></td><td> <input type="checkbox" id="isactive"   ng-true-value="'Y'" ng-false-value="'N'" ng-model="pageMenuBo.isactive" ></td>
                    </tr>
                </table>
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