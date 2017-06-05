<%--
  Created by IntelliJ IDEA.
  User: 刘大磊
  Date: 2017/6/2
  Time: 15:43
  帮助
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp"%>
<!-- 模态框（Modal） -->
<div class="modal fade" id="helpModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"  ng-app="searchApp">
    <div class="modal-dialog"  ng-controller="searchCtrl">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h3 class="modal-title" id="myModalLabel">
                    <c:out value="${windowHelpBo.name}"/>
                </h3>
            </div>
            <div class="modal-body">
                <p class="lead">
                    <c:out value="${windowHelpBo.help}"/>
                </p>
                <c:forEach var="tab" items="${windowHelpBo.tabHelpBoList}">
                    <h4><c:out value="${tab.name}"></c:out></h4>
                    <p class="lead">
                        <c:out value="${tab.help}"></c:out>
                    </p>
                    <c:forEach var="field" items="${tab.fieldHelpBoList}">
                        <h5><c:out value="${field.name}"/></h5>
                        <p class="lead">
                            <c:out value="${field.help}"></c:out>
                        </p>
                    </c:forEach>
                </c:forEach>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><%--关闭--%><eidea:label key="common.button.closed"/>
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->

</div>
