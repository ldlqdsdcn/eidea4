<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/commons/taglib.jsp"%>



<HTML>
<head>
<title>error page</title>

<script type="text/javascript" src="../js/jquery/jquery-1.11.1.min.js"/></script>
<script type="text/javascript" src="../js/jquery/jquery-ui-1.11.4.custom/jquery-ui.min.js"/></script>

<link rel="stylesheet" href="../css/style.css" type="text/css" media="all"/>
<link rel="stylesheet" href="../css/delmar.css" type="text/css" media="all"/>
<style type="text/css">
.404error{
	width:540px;
	margin:auto;
	text-align:center;
}

div.body {
 align:center;
 width:1000px;
 
 margin:auto;
 padding:20px;

}

div.errorpic {		
	height:205px;
	margin:auto;
	overflow:hidden;
	background-image: url("../images/error_say.png");
	background-repeat: no-repeat;
	
}


#errorcontainer {
   position:relative;
   width:700px;
   margin:0 0 0 20px;
   color:#505050;
   line-height:2;
   text-align:left;

   top:-200px;
   left:230px;
 }
#errorcontainer .title {}
#errorcontainer .content {
  padding-left:20px;
  color:#ff0000 !important;
}

.detailbutton {
  
   text-align:right;
}

.detailbutton a{margin-right:1em; font-size:14px;color:#007ab7;text-decoration:none;}




.detailerror{
 position:relative;
 top:-80px;
 color:#218fd5;
 background-color:#fafafa;
 border-top:#eee 1px solid;
 border-bottom:#eee 1px solid;
 font-weight:bold;
 font-size:16px;
 padding-left:10px;
 line-height:30px;
 
}

.detailcontent{
text-align:left; 

line-height:30px;
font-size:14px; 
}


.hide 
{
  dispaly:none;
  visibility:hidden;
}

</style>
</head>
<body class="404error">

 <table border="0" cellpadding="0" cellspacing="0" class="cTableBorder">
        <tr> 
          <td align="left" class="navig">
	<delmar:message key="linkman.location"/>
          </td>
         <td background="../images/table_page_bg.gif" width="*"  height="26" align="right">
          </td>
        </tr>
</table>
      
<div class="body">
<div class="errorpic"></div>

<div id="errorcontainer">
<div class="title"><delmar:message key="public.error.title"/></div>
<div class="content">
<c:out value="${MAP_KEY_OF_SESSION.errorMessage}"/>
</div>
<div class="detailbutton">
<c:if test="${MAP_KEY_OF_SESSION.errorDetail!=null}">
<a href="#" onclick="javascript:viewdetailerror()" id="viewdetail"><delmar:message key="public.error.viewdetail"/></a>
</c:if>
<a href="javascript:history.go(-1)">&#9666;<delmar:message key="public.error.goback"/></a>
</div>
</div>  <!--#errorcontainer-->

<div id="detailerror"  class="detailerror hide">
<div id="detailcontent" class="detailcontent">
<c:out value="${MAP_KEY_OF_SESSION.errorDetail}"/>
</div>
<div class="detailbutton">
<a href="javascript:history.go(-1)">&#9666;<delmar:message key="public.error.goback"/></a>
</div>
</div>   <!--detailerror-->

</div>
</body>



</body>
</html>



<script type="text/javascript">
function viewdetailerror()
{
	
  if ($("#detailerror").hasClass("hide"))
  {
     $("#detailerror").removeClass("hide");
     $("#viewdetail").html('<delmar:message key="public.error.hidedetail"/>');
  }
  else
	{

      $("#detailerror").addClass("hide");
      $("#viewdetail").html('<delmar:message key="public.error.viewdetail"/>');
	}
		
}
</script>


