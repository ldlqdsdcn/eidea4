app.controller('listCtrl', function ($scope, $http) {
$scope.modelList = [];
$scope.delFlag = false;
$scope.isLoading = true;
$scope.canDel=PrivilegeService.hasPrivilege('delete');
$scope.canAdd=PrivilegeService.hasPrivilege('add');
$scope.updateList = function (result) {
$scope.modelList = result.data;
$scope.queryParams.totalRecords = result.totalRecords;
$scope.queryParams.init = false;
};
$scope.selectAll = function () {
for (var i = 0; i < $scope.modelList.length; i++) {
$scope.modelList[i].delFlag=$scope.delFlag;
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
$scope.pageChanged = function () {
$http.post("<c:url value="/${module}/${model?uncap_first}/list"/>", $scope.queryParams)
.success(function (response) {
$scope.isLoading = false;
if (response.success) {
$scope.updateList(response.data);
}
else {
bootbox.alert(response.message);
}

});
}

//批量删除
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
}, callback: function (result) {
if (result) {
var ids = [];
for (var i = 0; i < $scope.modelList.length; i++) {
if ($scope.modelList[i].delFlag) {
ids.push($scope.modelList[i].id);
}
}
$scope.queryParams.init=true;
var param={"queryParams":$scope.queryParams,"ids":ids};
$http.post("<c:url value="/${module}/${model?uncap_first}/deletes"/>", param).success(function (data) {
if (data.success) {
$scope.updateList(data.data);
bootbox.alert("<eidea:message key="module.deleted.success"/>");
} else {
bootbox.alert(data.message);
}
});
}
}
});
};




//可现实分页item数量
$scope.maxSize =${r'${pagingSettingResult.pagingButtonSize}'};
$scope.queryParams = {
pageSize:${r'${pagingSettingResult.perPageSize}'},//每页显示记录数
pageNo: 1, //当前页
totalRecords: 0,//记录数
init: true
};
$scope.pageChanged();
});