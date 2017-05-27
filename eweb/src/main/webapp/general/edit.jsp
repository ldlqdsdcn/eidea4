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
                <div class="form-group">
                    <label for="id${fieldStructure.fieldPo.id}">${fieldStructure.fieldTrlPo.name}</label>
                        <input type="text" class="form-control" id="code" name="code" ng-model="clientBo.no" placeholder="<eidea:message key="common.please.input"><eidea:param value="${fieldStructure.fieldTrlPo.name}" /></eidea:message>" required ng-minlength="2" ng-maxlength="10">
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