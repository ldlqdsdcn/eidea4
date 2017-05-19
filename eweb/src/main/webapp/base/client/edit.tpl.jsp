<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/12/9
  Time: 8:46
  To change this template use File | Settings | File Templates.
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
                        <label for="code"><%--no--%><eidea:label key="client.column.no"/></label>
                        <input type="text" class="form-control" id="code" name="code" ng-model="clientBo.no" placeholder="<eidea:message key="client.input.no"/>" required ng-minlength="2" ng-maxlength="10"
                               ng-disabled="clientBo.id!=null" >
                    </div>
                    <div class="form-group">
                        <label for="name"><%--name--%><eidea:label key="datadict.column.name"/></label>
                        <input type="text" class="form-control" id="name" name="name"  ng-model="clientBo.name"placeholder="<eidea:message key="org.input.name"/>"
                               required ng-minlength="2" ng-maxlength="100">
                    </div>
                    <div class="form-group">
                        <label for="remark"><%--remark--%><eidea:label key="base.remarks"/></label>
                        <input type="text" class="form-control" id="remark" placeholder="<eidea:message key="org.input.remark"/>" ng-model="clientBo.remark"   ng-maxlength="200">
                    </div>
                    <div class="form-group">
                        <label for="isactive"><%--是否有效--%><eidea:label key="base.whetherEffective"/><input type="checkbox" class="form-control" id="isactive"   ng-true-value="'Y'" ng-false-value="'N'" ng-model="clientBo.isactive"  ></label>

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