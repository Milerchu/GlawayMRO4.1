<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>台帐ALL统计</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  	<table border="0" width="100%" height="100%">
  		<tr height="50%">
  			<td width="50%"><div id="cstzTD" class="easyui-panel" data-options="href : '../kpi/cstzKPI.jsp',fit:true"></div></td>
  			<td width="50%"><div id="jztzTD" class="easyui-panel" data-options="href : '../kpi/jztzKPI.jsp',fit:true"></div></td>
  		</tr>
  		<tr height="50%">
  			<td width="50%"><div id="csjzDBTD" class="easyui-panel" data-options="href : '../kpi/csjzDBKPI.jsp',fit:true"></div></td>
  			<td width="50%"><div id="sstzTD" class="easyui-panel" data-options="href : '../kpi/sstzKPI.jsp',fit:true"></div></td>
  		</tr>
  	</table>
  </body>
</html>
