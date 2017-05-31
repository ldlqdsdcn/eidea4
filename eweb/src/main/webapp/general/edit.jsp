<%@ page import="com.dsdl.eidea.core.def.FieldInputType" %>
<%@ page import="com.dsdl.eidea.core.def.FieldShowType" %>
<%--
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
                    <c:if test="${fieldStructure.fieldPo.isDisplayed=='Y'}">
                        <div class="form-group">
                            <label for="id${fieldStructure.fieldPo.id}">${fieldStructure.fieldTrlPo.name}</label>
                            <c:choose>
                                <c:when test="${fieldStructure.fieldInputType eq FieldInputType.DATEPICKER}">
                                    <c:choose>
                                        <c:when test="${fieldStructure.fieldPo.isreadonly=='Y'}">
                                            <input type="date" class="form-control" id="id${fieldStructure.fieldPo.id}" name="id${fieldStructure.fieldPo.id}" value="{{model.id${fieldStructure.fieldPo.id}|date:'yyyy-MM-dd'}}"   readonly>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="input-group date bootstrap-date">
                                                <input type="date" class="form-control"  id="id${fieldStructure.fieldPo.id}" name="id${fieldStructure.fieldPo.id}" placeholder="<eidea:message key="common.please.input"><eidea:param value="${fieldStructure.fieldTrlPo.name}" /></eidea:message>" ng-model="model.id${fieldStructure.fieldPo.id}"
                                                       uib-datepicker-popup="yyyy-MM-dd HH:mm:ss">
                                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                </c:when>
                                <c:when test="${fieldStructure.fieldInputType eq FieldInputType.DATETIMEPICKER}">
                                    <c:choose>
                                        <c:when test="${fieldStructure.fieldPo.isreadonly=='Y'}">
                                            <input type="datetime" class="form-control" id="id${fieldStructure.fieldPo.id}" name="id${fieldStructure.fieldPo.id}" value="{{model.id${fieldStructure.fieldPo.id}|date:'yyyy-MM-dd HH:mm:ss'}}"   readonly>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="input-group date bootstrap-datetime">
                                                <input type="datetime" class="form-control"  id="id${fieldStructure.fieldPo.id}" name="id${fieldStructure.fieldPo.id}" placeholder="<eidea:message key="common.please.input"><eidea:param value="${fieldStructure.fieldTrlPo.name}" /></eidea:message>" ng-model="model.id${fieldStructure.fieldPo.id}"
                                                       uib-datepicker-popup="yyyy-MM-dd HH:mm:ss">
                                                <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                </c:when>
                                <c:when test="${fieldStructure.fieldInputType eq FieldInputType.CHECKBOX}">
                                    <input type="checkbox" ng-true-value="'${fieldStructure.trueValue}'" ng-false-value="'${fieldStructure.falseValue}'" ng-model="model.id${fieldStructure.fieldPo.id}" name="id${fieldStructure.fieldPo.id}">
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${fieldStructure.fieldPo.showType eq FieldShowType.LINKED}">
                                            <input type="text" class="form-control" id="idLinked${fieldStructure.fieldPo.id}" name="idLinked${fieldStructure.fieldPo.id}" ng-model="model.idLinked${fieldStructure.fieldPo.id}" placeholder="<eidea:message key="common.please.input"><eidea:param value="${fieldStructure.fieldTrlPo.name}" /></eidea:message>"
                                                ${fieldStructure.fieldPo.isreadonly=='Y'?'readonly':''} >
                                        </c:when>
                                        <c:otherwise>
                                            <input type="text" class="form-control" id="id${fieldStructure.fieldPo.id}" name="id${fieldStructure.fieldPo.id}" ng-model="model.id${fieldStructure.fieldPo.id}" placeholder="<eidea:message key="common.please.input"><eidea:param value="${fieldStructure.fieldTrlPo.name}" /></eidea:message>"
                                                ${fieldStructure.fieldPo.isreadonly=='Y'?'readonly':''} >
                                        </c:otherwise>
                                    </c:choose>

                                </c:otherwise>
                            </c:choose>
                        </div>

                    </c:if>

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