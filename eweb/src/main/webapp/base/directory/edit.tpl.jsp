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
                        <label for="name"><%--页面名称--%><eidea:label key="page.column.name"/></label>
                        <input type="text" class="form-control" id="name" name="name" ng-model="directoryBo.name" placeholder="<%--请输入页面名称--%><eidea:message key="directory.input.pagename"/>" required ng-minlength="2" ng-maxlength="10">
                    </div>
                    <div class="form-group">
                        <label for="name"><%--链接地址--%><eidea:label key="dymenu.label.chainedaddress"/></label>
                        <input type="text" class="form-control" id="directory" placeholder="<%--请输入链接地址--%><eidea:message key="directory.input.link.address"/>" ng-model="directoryBo.directory" required ng-minlength="2" ng-maxlength="100">
                    </div>
                    <div class="form-group">
                        <label for="remark"><%--备注--%><eidea:label key="base.remarks"/></label>
                        <input type="text" class="form-control" id="remark" placeholder="<%--请输入备注--%><eidea:message key="login.input.remark"/>" ng-model="directoryBo.remark"   ng-maxlength="500">
                    </div>
                    <div class="form-group">
                        <label for="remark"><%--基础权限--%><eidea:label key="directory.access.repository"/></label>
                        <input type="text" class="form-control" id="repository" placeholder="<%--请输入备注--%><eidea:message key="directory.repository"/>" ng-model="directoryBo.repository"   ng-maxlength="500">
                    </div>
                    <div class="form-group">
                        <label for="isactive"><%--是否有效--%><eidea:label key="base.whetherEffective"/><input type="checkbox" class="form-control" id="isactive"   ng-true-value="'Y'" ng-false-value="'N'" ng-model="directoryBo.isactive"  ></label>

                    </div>
                    <div class="form-group">
                        <p class="text-center" style="color: red">
                            {{message}}
                        </p>
                    </div>
            </div>
        </div>
    </form>
    <jsp:include page="/common/common_upload.jsp"/>
</div>