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
--%>
<%--
*********************************************************************************************************

This page logs out a user from the MAXIMO. This can be customized for any further calls for LDAP

*********************************************************************************************************
--%><%@ page contentType="text/html;charset=UTF-8" buffer="128kb" autoFlush="true" import= "java.util.Locale, psdi.util.*, psdi.webclient.system.session.*, psdi.webclient.system.runtime.*, psdi.webclient.system.websession.*" %><%
	WebClientRuntime wcr = WebClientRuntime.getWebClientRuntime();
	boolean subFrameAllowed = false;
	WebClientSessionManager wcsm = WebClientSessionManager.getWebClientSessionManager(session);
	if (wcsm != null)
	{
		try
		{
			WebClientSession wcs = wcsm.getWebClientSession(request);
			if (wcs != null)
			{
				subFrameAllowed = wcs.subFrameAllowed();
				wcsm.removeWebClientSession(wcs);
			}
		}
		catch (MXException mxe)
		{
			//wcs timedout so it's been already removed so continue on
		}
	}
	MXSession mxs = WebClientRuntime.getMXSession(session);
	Locale locale = null;
	String langcode = null;
	if (mxs != null && mxs.getUserInfo() != null)
	{
		langcode = mxs.getUserInfo().getLangCode();
		locale = mxs.getUserInfo().getLocale();
	}
	else
	{
		Object[] settings = WebClientRuntime.getLocaleFromRequest(request);
		if (settings[0] instanceof String)
		{
		    langcode = (String)settings[0];
		}
		if (settings[1] instanceof Locale)
		{
		    locale = (Locale)settings[1];
		}
	}
	if (langcode == null)
	{
		langcode = psdi.server.MXServer.getMXServer().getBaseLang();
	}
	boolean timeout = request.getParameter("timeout") != null;
	boolean unavailable = request.getParameter("unavailable") != null;
	boolean formAuth = session.getAttribute("formAuth") != null;

	String exitpage;
	if (unavailable)
	{
		exitpage = "/webclient/login/loginerror.jsp?unavailable=true" + (formAuth ? "" : "&basic=true");
	}
	else
	{
		exitpage = "/webclient/login/exit.jsp" + (formAuth ? "?formAuth=true" : "");
	}
	String message = null;
	String loginurl = null;
	String exiturl = WebClientRuntime.getMaximoRequestContextURL(request) + exitpage;
	String loginPage = WebClientRuntime.getWebClientProperty("webclient.loginpage");
	if (timeout)
	{
		if (wcsm != null && wcsm.hasSessions())
		{
			//Still has valid sessions so don't logout the user. Just redirect to exit.jsp with 
			response.sendRedirect(exiturl + (formAuth?"&":"?") + "wcstimeout=true");
			return;
		}
		loginurl = new java.net.URL(new java.net.URL(WebClientRuntime.getMaximoRequestContextURL(request) + "/ui/maximo"), loginPage).toString();
		message = mxs.getMessage("jspmessages", "session_timeout");
	}
	else
	{
		String goback = (String)session.getAttribute("signoutfrom");
		if(goback != null)
		{
			loginurl = new java.net.URL(new java.net.URL(goback), loginPage).toString();
		}
		else
		{
			loginurl = new java.net.URL(new java.net.URL(WebClientRuntime.getMaximoRequestContextURL(request) + "/ui/maximo"), loginPage).toString();
		}
		message = (String)session.getAttribute("signoutmessage");	
	}
	if(message != null)
	{
		message = WebClientRuntime.replaceString(message, "\'", "\\\'");
	}

	mxs.disconnect();

	if (session.getAttribute("redirecturl") != null)
	{
		loginurl = (String)session.getAttribute("redirecturl");
	}

	if(WebAppEnv.useAppServerSecurity() || message == null)
	{
		session.removeAttribute("signoutmessage");
	}

	if(subFrameAllowed)
	{
		loginurl += (loginurl.contains("?") ? "&" : "?") + "allowinsubframe=true";
	}

	session.invalidate();

	HttpSession s = request.getSession(true);
	if (s != null)
	{
		s.setAttribute("langcode", langcode);
		mxs.setLocale(null);
	}
	if (!WebAppEnv.useAppServerSecurity())
	{
		if (s != null)
		{
			s.setAttribute("signoutmessage", message);
		}
		response.sendRedirect(loginurl);
		return;
	}

	boolean isWAS = false;
	if (System.getProperty("was.install.root") != null)
	{
		isWAS = true;
		if (unavailable && WebClientRuntime.send503Error(response, mxs, exiturl))
		{
			return;
		}
	}

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<body>
<%	if (isWAS)
	{	%>
	<form method="post" action="ibm_security_logout" name="logoutfrm">
		<input type="hidden" name="logoutExitPage" value="<%=exitpage%>">
	</form> 
<%	}	%>

	<script language="JavaScript">
<%	if(message != null)
	{	%>
		alert('<%=message%>');
<%	}
	if (isWAS)
	{	%>
		document.logoutfrm.submit();
<%	}
	else
	{	%>
		document.location = "<%=exiturl%>";
<%	}	%>
	</script>
</body>  
</html>