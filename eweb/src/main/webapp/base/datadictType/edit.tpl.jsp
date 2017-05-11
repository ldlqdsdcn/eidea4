<%@ taglib prefix="ediea" uri="http://eidea.cn" %>
<%--
User: 刘大磊
Date: 2017-04-26 15:34:17
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">
    <div ng-controller="tabCtrl">
        <uib-tabset class="nav nav-tab vertical-tab" vertical="true" active="active">
            <uib-tab heading="<ediea:label key="datadict.title"/>" select="showDatadict()"></uib-tab>
            <uib-tab heading="<ediea:label key="datadict.detail"/>" select="showDetail()"></uib-tab>
        </uib-tabset>
    </div>
    <div class="tab-content vertical-tab-content" >

        <div class="row-fluid">
            <div class="span12">
                <br>
                <form role="form" name="editForm" novalidate ng-submit="save()"ng-show="show">
                    <jsp:include page="../../common/common_edit_button.jsp"></jsp:include>
                    <div class="form-group">
                        <label for="value"><%--键值--%><eidea:label key="base.datadictType.label.value"/></label>
                        <input type="text" class="form-control" id="value"
                               placeholder="<eidea:message key="common.please.input"><eidea:param value="base.datadictType.label.value"
                                                                       type="label"/></eidea:message>"
                               ng-model="datadictTypePo.value"
                               required ng-minlength="2" ng-maxlength="50">

                    </div>
                    <div class="form-group">
                        <label for="name"><%--名称--%><eidea:label key="base.datadictType.label.name"/></label>
                        <input type="text" class="form-control" id="name"
                               placeholder="<eidea:message key="common.please.input"><eidea:param value="base.datadictType.label.name"
                                                                       type="label"/></eidea:message>"
                               ng-model="datadictTypePo.name" required ng-minlength="2" ng-maxlength="200">

                    </div>
                    <div class="form-group">
                        <label for="remark"><%--备注--%><eidea:label
                                key="base.datadictType.label.remark"/></label>
                        <input type="text" class="form-control" id="remark"
                               placeholder="<eidea:message key="common.please.input"><eidea:param value="base.datadictType.label.remark"
                                                                       type="label"/></eidea:message>"
                               ng-model="datadictTypePo.remark" ng-maxlength="200">
                    </div>
                    <div class="form-group">
                        <label for="isactive"><%--是否有效--%>
                            <eidea:label key="base.datadictType.label.isactive"/>
                            <input type="checkbox" class="form-control" id="isactive"
                                   ng-model="datadictTypePo.isactive" ng-true-value="'Y'"
                                   ng-false-value="'N'"></label>

                    </div>
                    <div class="form-group">
                        <p class="text-center" style="color: red">
                            {{message}}
                        </p>
                        <p>
    <span ng-repeat="error in errors track by $index">
    {{error.message}}
    </span>
                        </p>
                    </div>
                </form>
                <div ng-show="!show">
                    <jsp:include page="../../common/common_edit_button.jsp"></jsp:include>
                    <table class="table table-hover table-striped table-condensed">
                        <thead>
                        <tr>
                            <th><input type="checkbox" name="selectAll" style="margin:0px;" ng-change="selectAll()"
                                       ng-model="delFlag"></th>
                            <th><%--序号--%><eidea:label key="base.serialNumber"/></th>
                            <th><%--code--%><eidea:label key="base.datadict.label.code"/></th>
                            <th><%--信息--%><eidea:label key="base.datadict.label.msgtext"/></th>
                            <th><%--是否有效--%><eidea:label key="base.datadict.label.isactive"/></th>
                            <th><%--dataType--%><eidea:label key="base.datadict.label.dataType"/></th>
                            <th><%--备注--%><eidea:label key="base.datadict.label.remark"/></th>
                            <th><%--编辑--%><eidea:label key="common.button.edit"/></th>
                        </tr>
                        </thead>
                        <tbody>

                        <tr ng-repeat="model in modelList track by $index" ng-class-even="success">
                            <td>
                                <input type="checkbox" ng-model="model.delFlag">
                            </td>
                            <td>{{(queryParams.pageNo-1)*queryParams.pageSize+$index+1}}</td>
                            <td>
                                {{model.code}}
                            </td>
                            <td>
                                {{model.msgtext}}
                            </td>
                            <td>
                                {{model.isactive}}
                            </td>
                            <td>
                                {{model.dataType}}
                            </td>
                            <td>
                                {{model.remark}}
                            </td>
                            <td>
                                <a class="btn btn-primary btn-xs" href="#/editDetail?id={{model.id}}"><eidea:label
                                        key="common.button.edit"/><%--编辑--%></a>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                    <ul uib-pagination boundary-links="true" total-items="queryParams.totalRecords"
                        ng-model="queryParams.pageNo"
                        max-size="maxSize" first-text="<eidea:label key="common.label.firstpage"/>"
                        previous-text="<eidea:label key="common.label.previouspage"/>"
                        next-text="<eidea:label key="common.label.nextpage"/>"
                        last-text="<eidea:label key="common.label.lastpage"/>"
                        class="pagination-sm" boundary-link-numbers="true" rotate="false"
                        items-per-page="queryParams.pageSize"
                        ng-change="pageChanged()"></ul>
                    <div class="text-left ng-binding padding_total_banner"><eidea:message
                            key="common.msg.result.prefix"/><span>{{queryParams.totalRecords}}</span><eidea:message
                            key="common.msg.result.suffix"/></div>
                </div>
            </div>
        </div>
    </div>
</div>

