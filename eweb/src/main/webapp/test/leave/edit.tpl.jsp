<%--
User: 刘大磊
Date: 2017-05-12 13:36:48
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">
    <form role="form" name="editForm" novalidate  ng-submit="save()">
        <jsp:include page="/common/common_edit_button.jsp"/>
        <div class="row-fluid">
            <div class="span12">
                <br>
                    <div class="form-group">
                        <label for="title" ><%--标题--%><eidea:label key="test.leave.label.title"/></label>
                                <input type="text" class="form-control" id="title" placeholder="<eidea:message key="common.please.input"><eidea:param value="test.leave.label.title" type="label"/></eidea:message>"
                                       ng-model="leavePo.title" required ng-minlength="2" ng-maxlength="300">

                    </div>
                    <div class="form-group">
                        <label for="content" ><%--content--%><eidea:label key="test.leave.label.content"/></label>
                                <input type="text" class="form-control" id="content" placeholder="<eidea:message key="common.please.input"><eidea:param value="test.leave.label.content" type="label"/></eidea:message>"
                                       ng-model="leavePo.content" required ng-minlength="2" ng-maxlength="65535">

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
                                <input type="text" class="form-control" id="leaveUserId" placeholder="<eidea:message key="common.please.input"><eidea:param value="test.leave.label.leaveUserId" type="label"/></eidea:message>" ng-model="leavePo.leaveUserId" ng-disabled="true">

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
            </div>
        </div>
    </form>
    <jsp:include page="/common/common_upload.jsp"/>
</div>