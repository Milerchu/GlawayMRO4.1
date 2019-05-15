<%@ page contentType="text/html;charset=UTF-8" buffer="128kb" autoFlush="true" import="com.glaway.mro.page.jspload.JspMgr" %>
<%
	JspMgr loginMgr = new JspMgr(request, response);
	out.print(loginMgr.checkSession());
%>