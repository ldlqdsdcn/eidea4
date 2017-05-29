<%@ page import="com.dsdl.eidea.core.def.FieldInputType" %><%--
  Created by IntelliJ IDEA.
  User: 刘大磊
  Date: 2017/5/3
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="tab${tabId}editCtrl">
    <form role="form" name="editForm" novalidate  ng-submit="save()">
        <jsp:include page="/general/inc/edit_button.jsp"/>
        <div class="row-fluid">
            <div class="span12">
                <br>
                <c:forEach var="fieldStructure" items="${tabFormStructureBo.fieldStructureBoList}">
                <div class="form-group">
                    <label for="id${fieldStructure.fieldPo.id}">${fieldStructure.fieldTrlPo.name}</label>
                    <c:choose>
                        <c:when test="${fieldStructure.fieldInputType eq FieldInputType.DATEPICKER}">
                            <div class="input-group date bootstrap-date">
                                <input type="text" class="form-control"  id="id${fieldStructure.fieldPo.id}" name="id${fieldStructure.fieldPo.id}" placeholder="<eidea:message key="common.please.input"><eidea:param value="${fieldStructure.fieldTrlPo.name}" /></eidea:message>" ng-model="model.id${fieldStructure.fieldPo.id}"
                                       uib-datepicker-popup="yyyy-MM-dd HH:mm:ss">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                            </div>
                        </c:when>
                        <c:when test="${fieldStructure.fieldInputType eq FieldInputType.DATETIMEPICKER}">
                            <div class="input-group date bootstrap-datetime">
                                <input type="text" class="form-control"  id="id${fieldStructure.fieldPo.id}" name="id${fieldStructure.fieldPo.id}" placeholder="<eidea:message key="common.please.input"><eidea:param value="${fieldStructure.fieldTrlPo.name}" /></eidea:message>" ng-model="model.id${fieldStructure.fieldPo.id}"
                                       uib-datepicker-popup="yyyy-MM-dd HH:mm:ss">
                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <input type="text" class="form-control" id="id${fieldStructure.fieldPo.id}" name="id${fieldStructure.fieldPo.id}" ng-model="model.id${fieldStructure.fieldPo.id}" placeholder="<eidea:message key="common.please.input"><eidea:param value="${fieldStructure.fieldTrlPo.name}" /></eidea:message>" required ng-minlength="2" ng-maxlength="10">
                        </c:otherwise>
                    </c:choose>


                </div>
                </c:forEach>
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