app.controller('listCtrl', function ($scope,$rootScope, $http) {
$scope.allList = [];
$scope.modelList = [];
$scope.delFlag = false;
$scope.canDel=PrivilegeService.hasPrivilege('delete');
$scope.canAdd=PrivilegeService.hasPrivilege('add');
$http.get("<c:url value="/${module}/${model?uncap_first}/list"/>")
.success(function (response) {
if (response.success) {
$scope.updateList(response.data);
}
else {
bootbox.alert(response.message);
}

});
$scope.updateList = function (data) {
$scope.allList = data;
$scope.queryParams.totalRecords  = $scope.allList.length;
$scope.modelList.length = 0;
$scope.pageChanged();
};
$scope.pageChanged = function (delF) {
var bgn = ($scope.queryParams.pageNo - 1) * $scope.queryParams.pageSize;
var end = bgn +  $scope.queryParams.pageSize;
$scope.modelList.length = 0;
if (delF == null) {
delF = false;
}
for (var i = bgn; i < end && i < $scope.allList.length; i++) {
var item = $scope.allList[i];
item.delFlag = delF;
$scope.modelList.push(item);

}
}
$scope.canDelete = function () {
for (var i = 0; i < $scope.modelList.length; i++) {
if ($scope.modelList[i].delFlag) {
return true;
}
}
return false;
}
$scope.selectAll = function () {
$scope.pageChanged($scope.delFlag);
}
$scope.deleteRecord = function () {

bootbox.confirm({
message: "<eidea:message key="common.warn.confirm.deletion"/>",
buttons: {
confirm: {
label: '<eidea:label key="common.button.yes"/>',
className: 'btn-success'
},
cancel: {
label: '<eidea:label key="common.button.no"/>',
className: 'btn-danger'
}
},
callback: function (result) {
if (result) {
var ${pkProp}s = [];
for (var i = 0; i < $scope.modelList.length; i++) {
if ($scope.modelList[i].delFlag) {
ids.push($scope.modelList[i].id);
}
}
$http.post("<c:url value="/${module}/${model?uncap_first}/deletes"/>", ${pkProp}).success(function (data) {
if (data.success) {
bootbox.alert("<eidea:message key="common.warn.deleted.success"/>");
$scope.updateList(data.data);
}
else {
bootbox.alert(data.message);
}

});
}
}
});
};
//可现实分页item数量
$scope.maxSize =${r'${pagingSettingResult.pagingButtonSize}'};
if ($rootScope.listQueryParams != null) {
$rootScope.queryParams = $scope.listQueryParams;
$rootScope.queryParams.init = true;
}
else {
$scope.queryParams = {
pageSize:${r'${pagingSettingResult.perPageSize}'},//每页显示记录数
pageNo: 1, //当前页
totalRecords: 0,//记录数
init: true
};
$rootScope.listQueryParams = $scope.queryParams;
}
$scope.pageChanged();
});