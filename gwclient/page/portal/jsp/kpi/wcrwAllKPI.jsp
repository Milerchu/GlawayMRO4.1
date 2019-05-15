<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>实时外场任务统计</title>
    
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
  		<tr height="100%">
  			<td width="50%"><div id="cstzTD" class="easyui-panel" data-options="href : '../kpi/wcrw1KPI.jsp',fit:true"></div></td>
  			<td width="50%"><div id="jztzTD" class="easyui-panel" data-options="href : '../kpi/wcrw2KPI.jsp',fit:true"></div></td>
  		</tr>
  	</table>
  </body>
</html>
