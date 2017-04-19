app.controller('listCtrl', function ($scope, $http) {
$scope.modelList = [];
$scope.delFlag = false;
$scope.isLoading = true;

$scope.updateList = function (result) {
$scope.modelList = result.data;
$scope.queryParams.totalRecords = result.totalRecords;
$scope.queryParams.init = false;
};
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