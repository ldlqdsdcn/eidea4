<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2016/10/8
  Time: 16:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.dsdl.eidea.common.web.vo.SearchFormRow" %>
<%@ page import="com.dsdl.eidea.common.web.vo.SearchColumnVo" %>
<%@ page import="com.dsdl.eidea.core.def.SearchPageFieldInputType" %>
<%@ page import="com.dsdl.eidea.core.entity.bo.CommonSearchResult" %>
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
                <form  name="myForm" class="form-horizontal"  novalidate>

                    <%List<SearchFormRow> searchFormRowList=(List<SearchFormRow>)request.getAttribute("searchFormRows");
                        if(searchFormRowList!=null&&searchFormRowList.size()>0)
                        for(SearchFormRow searchFormRow:searchFormRowList)
                        {
                            if(searchFormRow==null)
                            {
                                continue;
                            }
                            List<SearchColumnVo> searchColumnVoList= searchFormRow.getSearchColumnVoList();
                    %>
                    <div class="row">
                        <%  for(SearchColumnVo searchColumnVo:searchColumnVoList)
                        {
                        %>
                        <div class="form-group col-sm-6">
                            <label class="control-label col-sm-4"><%=searchColumnVo.getColumnLabel()%></label>
                            <div class="col-sm-8">
                                <%if(searchColumnVo.getShowType()== SearchPageFieldInputType.INPUT.getKey()||searchColumnVo.getShowType()== SearchPageFieldInputType.DATEPICKER.getKey()||searchColumnVo.getShowType()== SearchPageFieldInputType.DATETIMEPICKER.getKey()){%>
                                <input class="form-control" type="text" id="<%=searchColumnVo.getColumnId()%>" name="<%=searchColumnVo.getColumnName()%>" value="<%=searchColumnVo.getValue()==null?"":searchColumnVo.getValue()%>"/>
                                <%} else if(searchColumnVo.getShowType()== SearchPageFieldInputType.SELECT.getKey()){%>
                                <select class="form-control" id="<%=searchColumnVo.getColumnId()%>" name="<%=searchColumnVo.getColumnId()%>">
                                    <option value="">-------</option>
                                    <%
                                        List<CommonSearchResult> commonSearchResultList=  searchColumnVo.getCommonSearchResultList();
                                        for(CommonSearchResult commonSearchResult:commonSearchResultList)
                                        {
                                    %>
                                    <option value="<%=commonSearchResult.getKey()%>"  <%=commonSearchResult.getKey().equals(searchColumnVo.getValue())?"selected":""%> ><%=commonSearchResult.getLabel()%></option>
                                    <%}%>
                                </select>
                                <%}
                                %>
                            </div>
                        </div>
                        <% }%>
                    </div>
                    <%}%>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal"><%--关闭--%><eidea:label key="common.button.closed"/>
                </button>
                <button type="button" class="btn btn-primary" id="searchBtn">
                    <%--查找--%><eidea:label key="common.button.search"/>
                </button>
            </div>
        </div><!-- /.modal-content -->
        <script type="text/javascript">
            <%
                for(SearchFormRow searchFormRow:searchFormRowList)
                {
                    if(searchFormRow==null)
                        {
                            continue;
                        }
                    List<SearchColumnVo> searchColumnVoList= searchFormRow.getSearchColumnVoList();
                    for(SearchColumnVo searchColumnVo:searchColumnVoList)
                {
                  if(searchColumnVo.getShowType()== SearchPageFieldInputType.DATEPICKER.getKey()){
                %>
            $('#<%=searchColumnVo.getColumnId()%>').datetimepicker({
                dayOfWeekStart : 1,
                lang:'en',
                timepicker:false,
                format:"Y-m-d"
            });

            <%} else if(searchColumnVo.getShowType()== SearchPageFieldInputType.DATETIMEPICKER.getKey()){%>

            $('#<%=searchColumnVo.getColumnId()%>').datetimepicker({
                dayOfWeekStart : 1,
                lang:'en',
                format:"Y-m-d H:i:s",step:10
            });
            <%}
            }
            }%>
            $("#searchBtn").click(function()
            {
                var array=<%=request.getAttribute("searchColumnJson")%>;
                for(var i=0;i<array.length;i++)
                {
                    var oneColumn=array[i];
                    delete array[i].relOpearList;
                    delete array[i].commonSearchResultList;
                    oneColumn.value=$("#"+oneColumn.columnId).val();
                    if(oneColumn.dataType==2)
                    {
                        if(!isInt( oneColumn.value))
                        {
                            bootbox.alert(oneColumn.columnName+"<eidea:message key="common.search.error.Integer.column_name"/>");
                            return false;
                        }
                    }
                    else if(oneColumn.dataType==3)
                    {
                        if(!isFloat( oneColumn.value))
                        {
                            bootbox.alert(oneColumn.columnName+"<eidea:message key="common.search.error.number.column_name"/>");
                            return false;
                        }
                    }
                }
                var postData={"searchColumnVoList":array,"uri":"${uri}"};
                $.ajax( {
                    type:"POST",
                    dataType: "json",
                    contentType:"application/json",
                    data:JSON.stringify(postData),
                    url : "<c:url value="/common/doSearch2"/>",
                    success : function(result) {
                        if(result.success)
                        {
                            window.location.reload();
                        }
                        else {
                            bootbox.alert(result.message);
                        }

                    }
                });
            });
        </script>
    </div><!-- /.modal -->

    </div>