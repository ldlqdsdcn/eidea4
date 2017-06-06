<script type="text/javascript">
    var buttonHeader={
        listInit:function ($scope,$window) {
            $scope.commonListHeader=function (type) {
                if(type == 'refresh'){
                    $window.location.reload();
                }
            }
            $('a').each(function(){
                $(this).attr("ondragstart","return false");
            });
            buttonHeader.checkTimeOut();
        },
        editInit:function ($scope,$http,$window,$timeout, Upload,directoryUrl) {
            $scope.commonEditHeader=function (type) {
                if(type == 'log'){
                    if($scope.tableId != null){
                        $http.post("<c:url value="/common/changeLogList"/>",{"tableId":$scope.tableId,"uri":"${uri}"}).success(function (data) {
                            if (data.success) {
                                if(data.data == null){
                                    $scope.alert("<eidea:message key="common.log.no.operation.information"/>");
                                }else {
                                    $("#changeLogModal").modal("show");
                                    $scope.changelogVo=data.data;
                                }
                            }else {
                                $scope.message = data.message;
                            }
                        });
                    }else {
                        $scope.alert("<eidea:message key="common.log.no.operation.information"/>");
                    }
                }else if(type == 'refresh'){
                    $window.location.reload();
                }else if(type == 'attachment'){
                    if($scope.tableId != null){
                        var reg = /^[0-9]+$/ ;
                        if(reg.test($scope.tableId)){
                            $("#attachmentModal").modal('show');
                            $http.post("<c:url value="/common/attachmentList"/>",{"tableId":$scope.tableId,"uri":"${uri}","directoryUrl":directoryUrl}).success(function (data) {
                                if (data.success) {
                                    $scope.attachmentList = data.data;
                                }else {
                                    $scope.alert(data.message);
                                }
                            });
                        }else {
                            $scope.alert("<eidea:message key="common.upload.does.not.required"/>");
                        }
                    }else {
                        $scope.alert("<eidea:message key="common.upload.before.save.success"/>");
                    }
                }
            }
            $scope.$watch('files', function (files) {
                $scope.formUpload = false;
                if (files != null) {
                    if (!angular.isArray(files)) {
                        $timeout(function () {
                            $scope.files = files = [files];
                        });
                        return;
                    }
                }
            });
            $scope.attachmentUpload=function () {
                if($scope.files==null){
                    $scope.alert('<eidea:message key="common.upload.select.attachment"/>');return;
                }
                for (var i = 0; i < $scope.files.length; i++) {
                    $scope.errorMsg = null;
                    (function (f) {
                        $scope.upload(f, true);
                    })($scope.files[i])
                    ;
                }
            }
            $scope.upload = function (file) {
                $scope.unableUpload=true;
                file.upload =Upload.upload({
                    //服务端接收
                    url: "<c:url value="/common/attachmentUpload"/>",
                    data: {'fileKeyword':$scope.commonFileBo==null?null:$scope.commonFileBo.fileKeyword,"fileAbstract":$scope.commonFileBo==null?null:$scope.commonFileBo.fileAbstract,
                        "directoryUrl":directoryUrl,"tableId":$scope.tableId,"uri":"${uri}"},
                    //上传的文件
                    file: file
                }).success(function (data, status, headers, config) {
                    //上传成功
                    $scope.alert('<eidea:message key="common.upload.success"/>');
                    $scope.attachmentList = data.data;
                    $scope.commonFileBo=null;
                    $scope.files=null;
                    $scope.unableUpload=false;
                }).error(function (data, status, headers, config) {
                    //上传失败
                    console.log('error status: ' + status);
                });
            };
            $scope.attachmentDelete=function (id) {
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
                            $http.post("<c:url value="/common/attachmentDelete"/>",{"id":id,"tableId":$scope.tableId,"uri":"${uri}","directoryUrl":directoryUrl}).success(function (data) {
                                if (data.success) {
                                    $scope.alert('<eidea:message key="common.upload.delete.success"/>');
                                    $scope.attachmentList = data.data;
                                }else {
                                    $scope.message = data.message;
                                }
                            });
                        }
                    }
                });
            }
            $scope.alert=function (message) {
                bootbox.alert({
                    buttons: {
                        ok: {
                            label: '<i class="fa fa-close" aria-hidden="true"></i>&nbsp;<eidea:label key="common.button.closed"/>',
                            className: 'btn-primary'
                        }
                    },
                    message: message,
                });
            }
            $('a').each(function(){
                $(this).attr("ondragstart","return false");
            });
            buttonHeader.checkTimeOut();
        },
        checkTimeOut:function () {
            $('body').click(function () {
                $.ajax({
                    url:"<c:url value="/checkTimeout"/>",
                    data:"systemTimeStamp="+window.parent.document.getElementById("systemTimeStamp").value,
                    type:"POST",
                    dataType:"JSON",
                    success:function(data){
                        if(data.data){
                            window.parent.location.href="<c:url value="/login.jsp"></c:url>";
                        }
                    }
                })
            })
        }
    }
</script>