<%--
  Created by IntelliJ IDEA.
  User: 刘大磊
  Date: 2016/8/29
  Time: 20:05
  公共查询，组合查询功能
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp"%>
<!-- 模态框（Modal） -->
<div class="modal fade" id="searchModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"  ng-app="searchApp">
  <div class="modal-dialog"  ng-controller="searchCtrl">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
          &times;
        </button>
        <h4 class="modal-title" id="myModalLabel">
          <%--查询--%><eidea:label key="common.button.inquire"/>
        </h4>
      </div>
      <div class="modal-body">
        <form role="form" name="myform" class="form-inline" >
          <div class="form-group">
            <select  class="form-control" id="columnName" ng-model="currentColumn" ng-options="option.columnLabel for option in columnList">
            </select>
          </div>
          <div class="form-group">
            <select  class="form-control" id="opearType" ng-model="currentColumn.opearType" ng-options="m for m in  currentColumn.relOpearList">
            </select>
          </div>
          <div class="form-group">
            <input type="text" class="form-control" ng-model="currentColumn.value"  ng-show="currentColumn.showType==1">
            <select  class="form-control"  ng-model="currentColumn.value" ng-options="option.key as option.label for option in currentColumn.commonSearchResultList" ng-show="currentColumn.showType==2"></select>
            <input type="text"  ng-show="currentColumn.showType==3" class="form-control" id="value-date"  ng-model="currentColumn.value">
            <input type="text" class="form-control" id="value-date-time" ng-show="currentColumn.showType==4"  ng-model="currentColumn.value" >
          </div>
            <div class="form-group">
            <button  type="button" class="btn btn-primary" ng-click="submit()"  ng-disabled="currentColumn.value==null"><%--添加--%><eidea:label key="common.button.add"/></button>
          </div>
            <br>
          <div class="form-group" ng-show="searchColumnList!=null">
            <label for="column_list" class="control-label"><%--字段列表--%><eidea:label key="common.search.fields"/></label>
            <table id="column_list"  class="table table-hover table-bordered">
              <thead>
              <th><%--字段--%><eidea:label key="common.search.field"/></th>
              <th><%--比较符--%><eidea:label key="common.search.character"/></th>
              <th><%--值--%><eidea:label key="common.search.value"/></th>
              <th></th>
              </thead>
              <tbody>
              <tr ng-repeat="x in searchColumnList track by $index">
                <td>{{x.columnLabel}}</td>
                <td>{{x.opearType}}</td>
                <td>
                  <div ng-switch="x.showType">
                    <div ng-switch-when="2">
                      <select   ng-model="x.value" ng-options="option.key as option.label for option in x.commonSearchResultList" disabled="disabled"></select>
                    </div>
                    <div ng-switch-default>
                      {{x.value}}
                    </div>
                  </div>
                </td>
                <td>
                  <button  type="button" class="btn btn-default" ng-click="delete(x)"><%--删除--%><eidea:label key="common.button.delete"/></button>
                </td>
              </tr>

              </tbody>
            </table>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal"><%--关闭--%><eidea:label key="common.button.closed"/>
        </button>
        <button type="button" class="btn btn-primary"  ng-click="search()" ng-keyup="doSearch($event)">
          <%--查找--%><eidea:label key="common.button.search"/>
        </button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal -->

</div>
<script type="text/javascript">
    var searchApp = angular.module('searchApp', []);
    console.log("search ...");
    searchApp.controller('searchCtrl', function ($scope, $http)  {
        $('#value-date').datepicker({
          language:  'zh-CN',
          format: 'yyyy-mm-dd',
          autoclose: 1,
          todayBtn:  1,
          clearBtn:true
        });
      $('#value-date-time').datetimepicker({
        language:  'zh-CN',
        format: 'yyyy-mm-dd hh:ii:ss',
        weekStart: 1,
        todayBtn:  1,
        autoclose: 1,
        todayHighlight: 1,
        startView: 2,
        forceParse: 0,
        showMeridian: 1,
        clearBtn: true
      });
        $scope.columnList=${searchColumnJson};
        console.log("columnList="+$scope.columnList);
        $scope.searchColumnList=${addedSearchColumnVoListJson};
        console.log("searchColumnList="+$scope.searchColumnList);
        $scope.currentColumn=$scope.columnList[0];
        $scope.submit = function () {
            if($scope.myform.$valid)
            {
                var addColumn={};
                angular.copy( $scope.currentColumn,addColumn);
                console.log("addColumn="+JSON.stringify(addColumn));
                console.log("currentColumn="+$scope.currentColumn.columnname);
                $scope.searchColumnList.push(addColumn);
            }
        };
        $scope.delete=function(x)
        {
            var index = $scope.searchColumnList.indexOf(x);
            $scope.searchColumnList.splice(index, 1);
        };
      $scope.doSearch=function($event){
        if($event.keyCode==13){//回车
          alert("enter");
          $scope.search();
        }
      }
        $scope.search=function()
        {
            console.log(JSON.stringify($scope.searchColumnList));
            var postData={"searchColumnVoList":$scope.searchColumnList,"uri":"${uri}"};
            $http.post("<c:url value="/common/doSearch"/>",postData).success(function(data)
            {
                if(data.success)
                {
                    window.location.reload();
                }
                else {
                    bootbox.alert(data.message);
                }
            });
        };
    });
    angular.bootstrap(document.getElementById('searchModal'), ['searchApp']);
</script>