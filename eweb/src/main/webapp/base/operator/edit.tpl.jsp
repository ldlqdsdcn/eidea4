
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp" %>
<div class="container-fluid" ng-controller="editCtrl">
    <div class="page-header">
        <ol class="breadcrumb">
            <li><a href="javascript:;"><i class="icon-tasks"></i>操作控制</a></li>
        </ol>
    </div>
    <div class="row-fluid">
        <div class="span12">
            <br>
            <form role="form" name="editForm" novalidate  ng-submit="save()">
                <div class="form-group">
                    <label for="name">编号</label>
                    <input type="text" class="form-control" id="no" placeholder="请输入编号" ng-model="operatorBo.no" required ng-minlength="2" ng-maxlength="100" ng-disabled="operatorBo.id!=null">
                </div>
                <div class="form-group">
                    <label for="name">操作名</label>
                    <input type="text" class="form-control" id="name" name="name" ng-model="operatorBo.name" placeholder="请输入name" required ng-minlength="2" ng-maxlength="10">
                </div>

                <div class="form-group">
                    <label for="remark">remark</label>
                    <input type="text" class="form-control" id="remark" placeholder="请输入remark" ng-model="operatorBo.remark"   ng-maxlength="500">
                </div>
                <div class="form-group">
                    <label for="isactive">是否有效       <input type="checkbox" class="form-control" id="isactive"   ng-true-value="'Y'" ng-false-value="'N'" ng-model="operatorBo.isactive"  ></label>

                </div>
                <div class="form-group">
                    <p class="text-right">
                        <button type="reset" ng-click="create()"  class="btn btn-default btn-sm">新建</button>
                        <button type="submit" class="btn btn-default btn-sm">保存</button>
                        <a href="#/list" class="btn btn-default btn-sm">返回</a>
                    </p>
                </div>
                <div class="form-group">
                    <p class="text-center" style="color: red">
                        {{message}}
                    </p>
                </div>
            </form>
        </div>
    </div>
</div>