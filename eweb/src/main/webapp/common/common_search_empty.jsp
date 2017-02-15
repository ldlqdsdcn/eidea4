<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/4
  Time: 23:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/inc/taglib.jsp"%>
<!-- 模态框（Modal） -->
<div class="modal fade" id="searchModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
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
                <%--查询条件为空--%><eidea:label key="common.search.query.condition.empty"/>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><%--关闭--%><eidea:label key="common.button.closed"/>
                </button>
                <button type="button" class="btn btn-primary" disabled>
                    <%--查找--%><eidea:label key="common.button.search"/>
                </button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
