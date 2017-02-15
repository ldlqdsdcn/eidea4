<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/12/9
  Time: 8:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/inc/taglib.jsp"%>
<div class="container-fluid" ng-controller="editCtrl">
	<div class="page-header">
		<ol class="breadcrumb">
			<li><a href="javascript:;"><i class="icon-tasks"></i><eidea:label key="menu.changelog"/></a></li>
		</ol>
	</div>
	<div class="row-fluid">
		<div class="span12">
			<br>
			<form role="form" name="editForm" novalidate>

				<div class="form-group">
					<label ><eidea:label key="searchcolumn.column.tableName"/></label> &nbsp;&nbsp;<span ng-bind="changelogVo.changelogBo.name"></span> &nbsp;&nbsp;&nbsp;

				</div>
				<div class="form-group">
					<label><eidea:label key="javaclass.column.classname"/></label> &nbsp;&nbsp;<span ng-bind="changelogVo.changelogBo.poClass"></span>
				</div>
				<div class="form-group">
					<label ><eidea:label key="changelog.business.name"/></label> &nbsp;&nbsp;&nbsp;<span
						ng-bind="changelogVo.changelogBo.tableName"></span>
				</div>
				<div class="form-group">
					<label><eidea:label key="changelog.primarykey"/></label>&nbsp;&nbsp; <span ng-bind="changelogVo.changelogBo.pk"></span>&nbsp;&nbsp;&nbsp;
				</div>
				<div class="form-group">
					<label ><eidea:label key="table.column.buskey"/></label> &nbsp;&nbsp;<span ng-bind="changelogVo.changelogBo.buPk"></span>
				</div>
				<div class="form-group">
					<input type="button" value="<eidea:label key="changelog.show.all"/>" ng-click="showAll()">
					<input	type="button" value="<eidea:label key="changelog.show.thisartical"/>" ng-click="showSelf()">
				</div>
				<div>
					<p><eidea:label key="changelog.operation.list"/></p>
					<table id="international_list"
						class="table table-hover table-bordered">
						<thead>
							<tr>
								<th ng-repeat="model in headerList track by $index">{{model}}</th>
							</tr>
						</thead>
						<tbody>

							<tr ng-repeat="model in bodyList track by $index">
								<td ng-repeat="column in model track by $index">{{column}}</td>
							</tr>

						</tbody>
					</table>
				</div>
				<div class="form-group">
					<p class="text-right">
						<a href="#/list" class="btn btn-default btn-sm"><eidea:label key="common.button.back"/></a>
					</p>
				</div>

			</form>
		</div>
	</div>
</div>