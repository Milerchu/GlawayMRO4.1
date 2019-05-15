<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":"
			+ request.getServerPort() + path + "/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script>
	/* 当会话超时或被关闭时，用于跳转页面至登录界面 */
	if(window.opener != null) { //弹出窗口内
		window.location.href = "<%=basePath%>page/login/login.jsp";
		
	} else if(window.parent != null) { //iframe内
		window.parent.location.href = "<%=basePath%>page/login/login.jsp";
		
	} else { //页面内
		window.location.href = "<%=basePath%>page/login/login.jsp";
	}
</script>
</head>
<body>
</body>
</html>