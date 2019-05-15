<%--
* Licensed Materials - Property of IBM
* 
* 5724-U18
* 
* (C) COPYRIGHT IBM CORP. 2006, 2011 All Rights Reserved.
* 
* US Government Users Restricted Rights - Use, duplication or
* disclosure restricted by GSA ADP Schedule Contract with
* IBM Corp.
--%><%@ page contentType="text/html;charset=UTF-8" import="psdi.webclient.system.runtime.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<body style="background-image:url(../images/retrievinginfo.gif);background-position:center center;background-repeat:no-repeat">
	</body>

	<script language="JavaScript" type="text/javascript">
		<%
		String url = new java.net.URL(new java.net.URL(WebClientRuntime.getMaximoRequestURL(request)), request.getContextPath() + "/ui/login").toString();
		%>
		//parent.disableDoc();	
		parent.document.location="<%=url%>";
	</script>	
</html>