<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/inc/taglib.jsp"%>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.util.Properties" %>
<html>
<head>

<style type="text/css">
A:visited{TEXT-DECORATION:none}
A:link{text-decoration:none}
A:hover{TEXT-DECORATION:underline}

DIV,FORM,P,TD,BODY{FONT-SIZE:15pt;}

BODY {
  margin: 0px;
  padding: 0px;
	SCROLLBAR-FACE-COLOR: #0650d2; FONT-SIZE: 15pt; BACKGROUND: #ffffff; MARGIN: 0px; SCROLLBAR-HIGHLIGHT-COLOR: #0650d2; SCROLLBAR-SHADOW-COLOR: #449ae8; SCROLLBAR-3DLIGHT-COLOR: #449ae8; LINE-HEIGHT: 150%; SCROLLBAR-ARROW-COLOR: #02338a; SCROLLBAR-TRACK-COLOR: #0650d2; FONT-FAMILY: "宋体"; SCROLLBAR-DARKSHADOW-COLOR: #0650d2; TEXT-DECORATION: none
}


#TableTitleLink A:link,#TableTitleLink A:visited{COLOR:#000000;}
.a1{background-color:#2a3f54 ;COLOR:#000000;}
.a2{BACKGROUND-COLOR: #2a3f54 ;}
.a3{BACKGROUND-COLOR: #2a3f54 ;}
.a4{BACKGROUND-COLOR: #2a3f54 ;}

.menuskin {
	BORDER-RIGHT: #0A2999 1px solid ;
	BORDER-TOP: #0A2999 1px solid;
	BORDER-LEFT: #0A2999 1px solid;
	BORDER-BOTTOM: #0A2999 1px solid;
	POSITION: absolute;
	VISIBILITY: hidden;
}

.menuitems {
	PADDING-RIGHT: 1px;
	PADDING-TOP: 1px;
	PADDING-LEFT: 1px;
	PADDING-BOTTOM: 1px;
	MARGIN: 2px;
	font-size:9pt;
	line-height:14pt;
}
#mouseoverstyle {
	PADDING-RIGHT: 0px;
	PADDING-LEFT: 0px;
	PADDING-BOTTOM: 0px;
	PADDING-TOP: 0px;
	BORDER-RIGHT: #2a3f54 1px solid;
	BORDER-TOP: #2a3f54 1px solid;
	BORDER-LEFT: #2a3f54 1px solid;
	BORDER-BOTTOM: #2a3f54 1px solid;
	BACKGROUND-COLOR: #FFEEC2 
}
.menuskin A {PADDING-RIGHT:10px;PADDING-LEFT:30px;}

.a1{    BACKGROUND: #2a3f54; COLOR: #ffffff}
.a2{BORDER-RIGHT: #2a3f54 1px solid; BORDER-TOP: #2a3f54 1px solid; BORDER-LEFT: #2a3f54 1px solid; BORDER-BOTTOM: #2a3f54 1px solid}
.a3{BACKGROUND-COLOR: #F2F8FF;}
.a4{
	BACKGROUND: #ffffff; LINE-HEIGHT: 120%
}

.t1 {font:14px 宋体;color:#000000}
.t2 {font:14px 宋体;color:#ffffff}
.t3 {font:14px 宋体;color:#ffff00}
.t4 {font:14px 宋体;color:#800000}
.t5 {font:14px 宋体;color:#191970}
.mytd{width:50%; height:35px; bgcolor:#f0f0f0}
.aaa:hover{
	Font-unline:yes;
	font-family: "宋体";
	color: #FFFFFF;
	text-decoration: underline;
	background-color: #CCCCCC;
}

.tdbg {
	BACKGROUND: #ffffff; LINE-HEIGHT: 120%
}

</style>

	<%
	
	   String ip=request.getRemoteAddr();
	     
	 %>
		 
	<script type="text/javascript">
		function getBrowserInfo()
		{
			var agent = navigator.userAgent.toLowerCase() ;
			alert(agent);
			var regStr_ie = /msie [\d.]+;/gi ;
			var regStr_ff = /firefox\/[\d.]+/gi
			var regStr_chrome = /chrome\/[\d.]+/gi ;
			var regStr_saf = /safari\/[\d.]+/gi ;
//IE
			if(agent.indexOf("msie") > 0)
			{
				return agent.match(regStr_ie) ;
			}

//firefox
			if(agent.indexOf("firefox") > 0)
			{
				return agent.match(regStr_ff) ;
			}

//Chrome
			if(agent.indexOf("chrome") > 0)
			{
				return agent.match(regStr_chrome) ;
			}

//Safari
			if(agent.indexOf("safari") > 0 && agent.indexOf("chrome") < 0)
			{
				return agent.match(regStr_saf) ;
			}

		}

		function getBrowseVersion() {
			var browser = getBrowserInfo() ;
			var verinfo = (browser+"").replace(/[^0-9.]/ig,"");
			return verinfo;
		}
		function get_browser(){
			var N=navigator.appName, ua=navigator.userAgent, tem;
			var M=ua.match(/(opera|chrome|safari|firefox|msie)\/?\s*(\.?\d+(\.\d+)*)/i);
			if(M && (tem= ua.match(/version\/([\.\d]+)/i))!= null) M[2]= tem[1];
			M=M? [M[1], M[2]]: [N, navigator.appVersion, '-?'];
			return M[0];
		}
		function get_browser_version(){
			var N=navigator.appName, ua=navigator.userAgent, tem;
			var M=ua.match(/(opera|chrome|safari|firefox|msie)\/?\s*(\.?\d+(\.\d+)*)/i);
			if(M && (tem= ua.match(/version\/([\.\d]+)/i))!= null) M[2]= tem[1];
			M=M? [M[1], M[2]]: [N, navigator.appVersion, '-?'];
			return M[1];
		}
		function getBrowser(n) {
			var ua = navigator.userAgent.toLowerCase(),
					s,
					name = '',
					ver = 0;
			//探测浏览器
			(s = ua.match(/msie ([\d.]+)/)) ? _set("ie", _toFixedVersion(s[1])):
					(s = ua.match(/firefox\/([\d.]+)/)) ? _set("firefox", _toFixedVersion(s[1])) :
							(s = ua.match(/chrome\/([\d.]+)/)) ? _set("chrome", _toFixedVersion(s[1])) :
									(s = ua.match(/opera.([\d.]+)/)) ? _set("opera", _toFixedVersion(s[1])) :
											(s = ua.match(/version\/([\d.]+).*safari/)) ? _set("safari", _toFixedVersion(s[1])) : 0;

			function _toFixedVersion(ver, floatLength) {
				ver = ('' + ver).replace(/_/g, '.');
				floatLength = floatLength || 1;
				ver = String(ver).split('.');
				ver = ver[0] + '.' + (ver[1] || '0');
				ver = Number(ver).toFixed(floatLength);
				return ver;
			}
			function _set(bname, bver) {
				name = bname;
				ver = bver;
			}
			return (n == 'n' ? name : (n == 'v' ? ver : name + ver));
		}
	</script>
</head>
<body topmargin="0" leftmargin="0"><br>
<table cellpadding="2" cellspacing="1" border="0" width="90%" align="center" class="a2">	
  <tr>
		<td class="a1" colspan="2" height="40" align="center"><b>系统信息</b></td>
  </tr>	
	<tr class="a4">
		<td class="mytd">本机IP：<span class="t4"><%=ip%></span></td>
		<%
		
		Properties props=System.getProperties(); //获得系统属性集    
		String osName = props.getProperty("os.name"); //操作系统名称    
		String osArch = props.getProperty("os.arch"); //操作系统构架    
		String osVersion = props.getProperty("os.version"); //操作系统版本
		DecimalFormat df = new DecimalFormat("0.00") ;
		 %>
		
		<td class="mytd">操作系统：<span class="t4"><%=osName%>(<%=osVersion%>)</span></td>
	</tr>
	<tr class="a4">
		<td class="mytd">浏览器内核：<span class="t4"><script>document.write(getBrowser("n"))</script></span></td>
		<td class="mytd">浏览器版本：<span class="t4"><script>document.write(getBrowser("v"))</script></span></td>
	</tr>
	<tr class="a4">
		<td class="mytd">系统语言：<span class="t4"><script>document.write(navigator.language)</script></span></td>
		<td class="mytd">浏览器语言：<span class="t4"><script>document.write(navigator.language)</script></span></td>
	</tr>
	<tr class="a4">
		<td class="mytd">在线情况：<span class="t4"><script>document.write(navigator.onLine) </script></span></td>
		<td class="mytd">屏幕分辨率：<span class="t4"><script>document.write(window.screen.width+"x"+window.screen.height)</script></span></td>
	</tr>
	<tr class="a4">
		<td class="mytd">最大内存：<span class="t4"><%=df.format(Runtime.getRuntime().maxMemory()*1.0/1024.00f/1024.00f)%>MB</span></td>
		<td class="mytd">总内存：<span class="t4"><%=df.format(Runtime.getRuntime().totalMemory()/1024f/1024f)%>MB</span></td>
	</tr>
	<tr class="a4">
		<td class="mytd">空闲内存：<span class="t4"><%=df.format(Runtime.getRuntime().freeMemory()/1024f/1024f)%>MB</span></td>
		<td class="mytd">当前使用内存：<span class="t4"><%=df.format((Runtime.getRuntime().totalMemory()-Runtime.getRuntime().freeMemory())/1024f/1024f)%>MB</span></td>
	</tr>
	<tr class="a4"><td class="mytd">可用处理器数：<span class="t4"><%=Runtime.getRuntime().availableProcessors()%></span></td>
        <td class="mytd"></td></tr>
	
</table>
<br>


</body>
</html>