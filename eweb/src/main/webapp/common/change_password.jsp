<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="modal fade" id="changePasswordModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" ng-app='passwordApp'>
        <div class="modal-content" ng-controller="listCtrl">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">密码修改</h4>
            </div>
            <form name="editForm" novalidate ng-submit="save()" method="post" class="form-horizontal form-label-left">
                <div class="modal-body">
                    <div class="form-group">
                        <label>旧密码</label>
                        <input type="password" class="form-control" placeholder="请输入旧密码" ng-model="userBo.oldPassword" required minlength="6" ng-maxlength="45"/>
                    </div>
                    <div class="form-group">
                        <label>新密码</label>
                        <input type="password" class="form-control" placeholder="请输入新密码" ng-model="userBo.password" required minlength="6" ng-maxlength="45"/>
                    </div>
                    <div class="form-group">
                        <label>确认密码</label>
                        <input type="password" class="form-control" placeholder="请输入确认密码" ng-model="userBo.repassword" required minlength="6" ng-maxlength="45"/>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="submit" class="btn btn-primary">提交更改</button>
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
                $scope.message="旧密码和新密码不能一致";
                return false;
            }else{
                $scope.message="";
            }
            if($scope.userBo.password == $scope.userBo.repassword){
                $scope.message="";
            }else{
                $scope.message="新密码和确认密码不一致";
                $scope.userBo.repassword="";
                return false;
            }
            //用户密码修改
            if ($scope.editForm.$valid) {
                changePasswordVo={oldPassowrd:md5($scope.userBo.oldPassowrd),password:md5($scope.userBo.password)};
                $http.post("<c:url value="/common/userCenter/changePassword"/>",changePasswordVo).success(function (data) {
                    if (data.success) {
                        $scope.message=data.data;
                    }else {
                        $scope.message=data.message;
                    }
                });
            }
        }
    });
</script>