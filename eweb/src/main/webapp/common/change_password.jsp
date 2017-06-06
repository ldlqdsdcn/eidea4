<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="changePasswordModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div id="changePassword" class="modal-dialog" ng-app='passwordApp'>
        <div class="modal-content" ng-controller="listCtrl">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel"><%--密码修改--%><eidea:label key="change.password.modification"/></h4>
            </div>
            <form name="editForm" novalidate ng-submit="save()" method="post" class="form-horizontal form-label-left">
                <div class="modal-body">
                    <div class="form-group">
                        <label><%--旧密码--%><eidea:label key="change.password.button.old.password"/></label>
                        <input type="password" class="form-control" placeholder="<eidea:label key="change.password.input.old.password"/>" ng-model="userBo.oldPassword" required minlength="6" ng-maxlength="45"/>
                    </div>
                    <div class="form-group">
                        <label><%--新密码--%><eidea:label key="change.password.button.new.password"/></label>
                        <input type="password" class="form-control" placeholder="<eidea:label key="change.password.input.new.password"/>" ng-model="userBo.password" required minlength="6" ng-maxlength="45"/>
                    </div>
                    <div class="form-group">
                        <label><%--确认密码--%><eidea:label key="change.password.button.confirm.password"/></label>
                        <input type="password" class="form-control" placeholder="<eidea:label key="change.password.input.confirm.password"/>" ng-model="userBo.repassword" required minlength="6" ng-maxlength="45"/>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal"><%--关闭--%><eidea:label key="common.button.closed"/></button>
                    <button type="submit" class="btn btn-primary" ng-disabled="unableChange"><%--提交更改--%><eidea:label key="change.password.input.submit.changes"/></button>
                </div>
                <div class="form-group">
                    <p class="text-center" style="color: red"  >
                        {{message}}
                    </p>
                    <p class="text-center" style="color: red" ng-repeat="msg in errorMessages track by $index" ng-show="errorCode==3">
                        {{msg.message}}
                    </p>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    var app = angular.module('passwordApp', ['ngRoute', 'ui.bootstrap', 'jcs-autoValidate']).config(['$routeProvider', function ($routeProvider) {
    }]);
    app.controller('listCtrl', function ($scope, $http) {
        $scope.save=function () {
            if($scope.userBo.oldPassword == $scope.userBo.password){
                $scope.userBo.password="";
                $scope.userBo.repassword="";
                $scope.message="<eidea:message key="change.password.msg.new.old.password.can.not.same"/>";
                return false;
            }else{
                $scope.message="";
            }
            if($scope.userBo.password == $scope.userBo.repassword){
                $scope.message="";
            }else{
                $scope.message="<eidea:message key="change.password.msg.new.confirm.password.not.same"/>";
                $scope.userBo.repassword="";
                return false;
            }
            //用户密码修改
            if ($scope.editForm.$valid) {
                $scope.unableChange=true;
                changePasswordVo={oldPassword:md5($scope.userBo.oldPassword),password:md5($scope.userBo.password)};
                $http.post("<c:url value="/common/userCenter/changePassword"/>",changePasswordVo).success(function (data) {
                    if (data.success) {
                        $scope.message=data.data;
                    }else {
                        $scope.message=data.message;
                    }
                    $scope.unableChange=false;
                });
            }
        }
    });
    angular.bootstrap(document.getElementById("changePassword"), ['passwordApp']);
</script>