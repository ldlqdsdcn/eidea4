<%--
User: 刘大磊
Date: 2017-05-03 17:51:03
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">
    <div class="page-header">
        <ol class="breadcrumb">
            <li><a href="javascript:;"><i class="icon-fire"></i><eidea:label key="fieldSelectItem.title"/></a></li>
        </ol>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <br>
            <form role="form" name="editForm" novalidate  ng-submit="save()">
                <div class="form-group">
                    <label for="fieldId" ><%--fieldId--%><eidea:label key="base.fieldSelectItem.label.fieldId"/></label>
                            <input type="text" class="form-control" id="fieldId" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fieldSelectItem.label.fieldId" type="label"/></eidea:message>" ng-model="fieldSelectItemPo.fieldId" >

                </div>
                <div class="form-group">
                    <label for="selectItemId" ><%--selectItemId--%><eidea:label key="base.fieldSelectItem.label.selectItemId"/></label>
                            <input type="text" class="form-control" id="selectItemId" placeholder="<eidea:message key="common.please.input"><eidea:param value="base.fieldSelectItem.label.selectItemId" type="label"/></eidea:message>" ng-model="fieldSelectItemPo.selectItemId" >

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
            </form>
        </div>
    </div>
</div>