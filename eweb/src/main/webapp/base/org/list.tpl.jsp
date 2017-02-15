<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div   ng-controller="listCtrl">
    <div class="page-title">
        <h3>
            <div class="row">
                <div class="col-lg-4"><%--组织信息--%><eidea:label key="org.title"/></div>
                <div class="col-lg-8  text-right">
                    <a href="#/edit" class="btn  btn-primary btn-sm" ng-show="canAdd"><%--新建--%><eidea:label key="common.button.create"/></a>
                    <button type="button" class="btn  btn-primary btn-sm" id="search_but" data-toggle="modal"
                            data-target="#searchModal"><%--查找--%><eidea:label key="common.button.search"/></button>
                    <button type="button" class="btn  btn-primary btn-sm" ng-disabled="!canDelete()"
                            ng-click="deleteRecord()"  ng-show="canDel"><%--删除--%><eidea:label key="common.button.delete"/></button>
                </div>
            </div>
        </h3>
    </div>


    <div class="row-fluid">
        <div class="span12">
            <table  class="table table-hover table-striped table-condensed">
                <thead>
                <tr>
                    <th><input type="checkbox" name="selectAll" style="margin:0px;" ng-change="selectAll()"
                               ng-model="delFlag"></th>
                    <th><%--序号--%><eidea:label key="base.serialNumber"/></th>
                    <th><%--no--%><eidea:label key="org.column.no"/></th>
                    <th><%--name--%><eidea:label key="org.column.name"/></th>
                    <th><%--实体--%><eidea:label key="user.column.client"/></th>
                    <th><%--remark--%><eidea:label key="base.remarks"/></th>
                    <th><%--是否有效--%><eidea:label key="base.whetherEffective"/></th>
                    <th><%--编辑--%><eidea:label key="common.button.edit"/></th>
                </tr>
                </thead>
                <tbody>

                <tr ng-repeat="model in modelList track by $index" ng-class-even="success">
                    <td>
                        <input type="checkbox" ng-model="model.delFlag">
                    </td>
                    <td>{{(bigCurrentPage-1)*itemsPerPage+$index+1}}</td>
                    <td>
                        {{model.no}}
                    </td>
                    <td>
                        {{model.name}}
                    </td>
                    <td>
                         {{model.client.name}}
                    </td>
                    <td>
                        {{model.remark}}
                    </td>
                    <td>
                        {{model.isactive}}
                    </td>
                    <td>
                        <a class="btn btn-primary btn-xs" href="#/edit?id={{model.id}}"><%--编辑--%><eidea:label key="common.button.edit"/></a>
                    </td>
                </tr>
                </tbody>
            </table>
            <ul uib-pagination boundary-links="true" total-items="bigTotalItems" ng-model="bigCurrentPage"
                max-size="maxSize" first-text="<eidea:label key="common.label.firstpage"/>" previous-text="<eidea:label key="common.label.previouspage"/>" next-text="<eidea:label key="common.label.nextpage"/>" last-text="<eidea:label key="common.label.lastpage"/>"
                class="pagination-sm" boundary-link-numbers="true" rotate="false" items-per-page="itemsPerPage"
                ng-change="pageChanged()"></ul>
        </div>
    </div>
</div>
