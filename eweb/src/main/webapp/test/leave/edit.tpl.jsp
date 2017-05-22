<%--
User: 刘大磊
Date: 2017-05-12 13:36:48
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">
    <div class="page-header">
        <ol class="breadcrumb">
            <li><a href="javascript:;"><i class="icon-fire"></i><eidea:label key="leave.title"/></a></li>
        </ol>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <br>
            <form role="form" name="editForm" novalidate  ng-submit="save()">
                <div class="form-group">
                    <label for="title" ><%--标题--%><eidea:label key="test.leave.label.title"/></label>
                            <input type="text" class="form-control" id="title" placeholder="<eidea:message key="common.please.input"><eidea:param value="test.leave.label.title" type="label"/></eidea:message>" ng-model="leavePo.title" >

                </div>
                <div class="form-group">
                    <label for="content" ><%--content--%><eidea:label key="test.leave.label.content"/></label>
                            <input type="text" class="form-control" id="content" placeholder="<eidea:message key="common.please.input"><eidea:param value="test.leave.label.content" type="label"/></eidea:message>" ng-model="leavePo.content" >

                </div>
                <div class="form-group">
                    <label for="bgnTime" ><%--开始时间--%><eidea:label key="test.leave.label.bgnTime"/></label>
                            <div class="input-group date bootstrap-datetime">
                                <input type="text" class="form-control" id="bgnTime" placeholder="<eidea:message key="common.please.input"><eidea:param value="test.leave.label.bgnTime" type="label"/></eidea:message>" ng-model="leavePo.bgnTime"
                                uib-datepicker-popup="yyyy-MM-dd HH:mm:ss">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                            </div>

                </div>
                <div class="form-group">
                    <label for="endTime" ><%--结束时间--%><eidea:label key="test.leave.label.endTime"/></label>
                            <div class="input-group date bootstrap-datetime">
                                <input type="text" class="form-control" id="endTime" placeholder="<eidea:message key="common.please.input"><eidea:param value="test.leave.label.endTime" type="label"/></eidea:message>" ng-model="leavePo.endTime"
                                uib-datepicker-popup="yyyy-MM-dd HH:mm:ss">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                            </div>

                </div>
                <div class="form-group">
                    <label for="leaveUserId" ><%--leaveUserId--%><eidea:label key="test.leave.label.leaveUserId"/></label>
                            <input type="text" class="form-control" id="leaveUserId" placeholder="<eidea:message key="common.please.input"><eidea:param value="test.leave.label.leaveUserId" type="label"/></eidea:message>" ng-model="leavePo.leaveUserId" >

                </div>


                <div class="form-group">
                    <p class="text-right">
                        <button type="reset" ng-click="create()"  class="btn btn-default btn-sm" ng-show="canAdd"><%--新建--%><eidea:label key="common.button.create"/></button>
                        <button type="submit" class="btn btn-default btn-sm" ng-show="canSave"><%--保存--%><eidea:label key="common.button.save"/></button>
                        <button type="button" class="btn btn-default btn-sm" ng-show="canSave" ng-click="submitApprove()">提交申请</button>
                        <a href="#/list" class="btn btn-default btn-sm"><%--返回--%><eidea:label key="common.button.back"/></a>
                    </p>
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
        </div>
    </div>
</div>