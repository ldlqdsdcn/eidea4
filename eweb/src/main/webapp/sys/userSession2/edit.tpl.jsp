<%--
User: 刘大磊
Date: 2017-05-08 09:55:07
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
                        <label for="sessionId" ><%--sessionId--%><eidea:label key="sys.userSession2.label.sessionId"/></label>
                                <input type="text" class="form-control" id="sessionId" placeholder="<eidea:message key="common.please.input"><eidea:param value="sys.userSession2.label.sessionId" type="label"/></eidea:message>" ng-model="userSession2Po.sessionId" >

                    </div>
                    <div class="form-group">
                        <label for="loginDate" ><%--loginDate--%><eidea:label key="sys.userSession2.label.loginDate"/></label>
                                <div class="input-group date bootstrap-datetime">
                                    <input type="text" class="form-control" id="loginDate" placeholder="<eidea:message key="common.please.input"><eidea:param value="sys.userSession2.label.loginDate" type="label"/></eidea:message>" ng-model="userSession2Po.loginDate"
                                    uib-datepicker-popup="yyyy-MM-dd HH:mm:ss">
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>

                    </div>
                    <div class="form-group">
                        <label for="logoutDate" ><%--logoutDate--%><eidea:label key="sys.userSession2.label.logoutDate"/></label>
                                <div class="input-group date bootstrap-datetime">
                                    <input type="text" class="form-control" id="logoutDate" placeholder="<eidea:message key="common.please.input"><eidea:param value="sys.userSession2.label.logoutDate" type="label"/></eidea:message>" ng-model="userSession2Po.logoutDate"
                                    uib-datepicker-popup="yyyy-MM-dd HH:mm:ss">
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>

                    </div>
                    <div class="form-group">
                        <label for="remoteAddr" ><%--remoteAddr--%><eidea:label key="sys.userSession2.label.remoteAddr"/></label>
                                <input type="text" class="form-control" id="remoteAddr" placeholder="<eidea:message key="common.please.input"><eidea:param value="sys.userSession2.label.remoteAddr" type="label"/></eidea:message>" ng-model="userSession2Po.remoteAddr" >

                    </div>
                    <div class="form-group">
                        <label for="remoteHost" ><%--remoteHost--%><eidea:label key="sys.userSession2.label.remoteHost"/></label>
                                <input type="text" class="form-control" id="remoteHost" placeholder="<eidea:message key="common.please.input"><eidea:param value="sys.userSession2.label.remoteHost" type="label"/></eidea:message>" ng-model="userSession2Po.remoteHost" >

                    </div>
                    <div class="form-group">
                        <label for="sysUserId" ><%--sysUserId--%><eidea:label key="sys.userSession2.label.sysUserId"/></label>
                                <input type="text" class="form-control" id="sysUserId" placeholder="<eidea:message key="common.please.input"><eidea:param value="sys.userSession2.label.sysUserId" type="label"/></eidea:message>" ng-model="userSession2Po.sysUserId" >

                    </div>
                    <div class="form-group">
                        <label for="token" ><%--token--%><eidea:label key="sys.userSession2.label.token"/></label>
                                <input type="text" class="form-control" id="token" placeholder="<eidea:message key="common.please.input"><eidea:param value="sys.userSession2.label.token" type="label"/></eidea:message>" ng-model="userSession2Po.token" >

                    </div>


                    <div class="form-group">
                        <p class="text-right">
                            <button type="reset" ng-click="create()"  class="btn btn-default btn-sm" ng-show="canAdd"><%--新建--%><eidea:label key="common.button.create"/></button>
                            <button type="submit" class="btn btn-default btn-sm" ng-show="canSave"><%--保存--%><eidea:label key="common.button.save"/></button>
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
            </div>
        </div>
    </form>
    <jsp:include page="/common/common_upload.jsp"/>
</div>
<script type='text/javascript' src="<c:url value="/js/ondrag-start.js"/>"></script>