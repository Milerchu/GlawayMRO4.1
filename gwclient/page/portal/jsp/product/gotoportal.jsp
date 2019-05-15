<%@ page contentType="text/html; charset=utf-8" language="java"
	import="com.glaway.mro.page.jspload.JspMgr" errorPage=""%>
<%
	JspMgr loginMgr = new JspMgr(request, response);
	out.print(loginMgr.getPortalPage());
%>
