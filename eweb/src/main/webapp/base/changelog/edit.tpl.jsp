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
	<form role="form" name="editForm" novalidate>
		<jsp:include page="/common/common_edit_button.jsp"/>
		<div class="row-fluid">
			<div class="span12">
				<br>
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
			</div>
		</div>
	</form>
	<jsp:include page="/common/common_upload.jsp"/>
</div>
<script type='text/javascript' src="<c:url value="/js/ondrag-start.js"/>"></script>