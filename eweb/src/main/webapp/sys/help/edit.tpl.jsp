<%--
User: 刘大磊
Date: 2017-04-26 15:55:56
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
                        <label for="uri" ><%--uri--%><eidea:label key="sys.help.label.uri"/></label>
                                <input type="text" class="form-control" id="uri" placeholder="<eidea:message key="common.please.input"><eidea:param value="sys.help.label.uri" type="label"/></eidea:message>" ng-model="helpPo.uri" >

                    </div>
                    <div class="form-group">
                        <label for="msgtext" ><%--信息--%><eidea:label key="sys.help.label.msgtext"/></label>
                                <input type="text" class="form-control" id="msgtext" placeholder="<eidea:message key="common.please.input"><eidea:param value="sys.help.label.msgtext" type="label"/></eidea:message>" ng-model="helpPo.msgtext" >

                    </div>
                    <div class="form-group">
                        <label for="created" ><%--创建时间--%><eidea:label key="sys.help.label.created"/></label>
                                <div class="input-group date bootstrap-datetime">
                                    <input type="text" class="form-control" id="created" placeholder="<eidea:message key="common.please.input"><eidea:param value="sys.help.label.created" type="label"/></eidea:message>" ng-model="helpPo.created"
                                    uib-datepicker-popup="yyyy-MM-dd HH:mm:ss">
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>

                    </div>
                    <div class="form-group">
                        <label for="createdby" ><%--创建人--%><eidea:label key="sys.help.label.createdby"/></label>
                                <input type="text" class="form-control" id="createdby" placeholder="<eidea:message key="common.please.input"><eidea:param value="sys.help.label.createdby" type="label"/></eidea:message>" ng-model="helpPo.createdby" >

                    </div>
                    <div class="form-group">
                        <label for="updated" ><%--修改时间--%><eidea:label key="sys.help.label.updated"/></label>
                                <div class="input-group date bootstrap-datetime">
                                    <input type="text" class="form-control" id="updated" placeholder="<eidea:message key="common.please.input"><eidea:param value="sys.help.label.updated" type="label"/></eidea:message>" ng-model="helpPo.updated"
                                    uib-datepicker-popup="yyyy-MM-dd HH:mm:ss">
                                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                </div>

                    </div>
                    <div class="form-group">
                        <label for="updatedby" ><%--修改人--%><eidea:label key="sys.help.label.updatedby"/></label>
                                <input type="text" class="form-control" id="updatedby" placeholder="<eidea:message key="common.please.input"><eidea:param value="sys.help.label.updatedby" type="label"/></eidea:message>" ng-model="helpPo.updatedby" >

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