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
<%%>
<div  class="container-fluid" ng-controller="tab${tabId}listCtrl">
    <jsp:include page="/general/inc/list_button.jsp"/>
    <div class="row-fluid">
        <div class="span12">
            <table  class="table table-hover table-striped table-condensed">
                <thead>
                <tr>
                    <th>编号</th>
                    <c:forEach items="${fieldInListPageBoList}" var="item">
                        <c:if test="${item.fieldPo.isdisplaygrid=='Y'}">
                    <th>
                        <c:out value="${item.name}"/>
                    </th>
                    </c:if>
                    </c:forEach>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>

                <tr ng-repeat="model in modelList track by $index" ng-class-even="success">
                    <td>{{(queryParams.pageNo-1)*queryParams.pageSize+$index+1}}</td>
                    <c:forEach items="${fieldInListPageBoList}" var="item">
                        <c:if test="${item.fieldPo.isdisplaygrid=='Y'}">
                            <td>
                                <c:choose>
                                    <c:when test="${item.fieldPo.showType eq FieldShowType.LINKED}">
                                        {{model.idLinked<c:out value="${item.id}"/>}}
                                    </c:when>
                                    <c:otherwise>
                                        {{model.id<c:out value="${item.id}"/>}}
                                    </c:otherwise>
                                </c:choose>

                            </td>
                        </c:if>


                    </c:forEach>
                    <td>
                        <button class="btn btn-primary btn-xs" ng-click="edit(model.${pk})">
                            <i class="fa fa-pencil-square-o" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.edit"/>
                        </button>
                    </td>
                </tr>
                <tr>
                    <td colspan="6" class="text-center" ng-show="isLoading">
                        <i class='fa fa-spinner fa-pulse loading'></i>&nbsp;<eidea:message key="login.msg.logining"/>
                    </td>
                </tr>
                </tbody>
            </table>
            <ul uib-pagination boundary-links="true" total-items="queryParams.totalRecords" ng-model="queryParams.pageNo"
                max-size="maxSize" first-text="<eidea:label key="common.label.firstpage"/>" previous-text="<eidea:label key="common.label.previouspage"/>" next-text="<eidea:label key="common.label.nextpage"/>" last-text="<eidea:label key="common.label.lastpage"/>"
                class="pagination-sm" boundary-link-numbers="true" rotate="false" items-per-page="queryParams.pageSize"
                ng-change="pageChanged()"></ul>
            <div class="text-left ng-binding padding_total_banner"><eidea:message key="common.msg.result.prefix"/><span>{{queryParams.totalRecords}}</span><eidea:message key="common.msg.result.suffix"/></div>
        </div>
    </div>
</div>