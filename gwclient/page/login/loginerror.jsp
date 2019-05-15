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
--%><%@ page contentType="text/html;charset=UTF-8" buffer="128kb" autoFlush="true" import= "java.util.Locale, psdi.util.*, psdi.webclient.system.session.*, psdi.webclient.system.runtime.*" %><%

	MXSession mxs = WebClientRuntime.getMXSession(session);

	Locale locale = null;
	String langcode = null;
	if (mxs.getUserInfo() != null)
	{
		langcode = mxs.getUserInfo().getLangCode();
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

	if (request.getParameter("redirect") != null)
	{
		//This redirect is done to insure the proper lanuage is used if this page sits long enough for the session to time out
		//and thus lose the MXSession and the session attribute langcode
		response.sendRedirect("../../ui/login");
	}

	if(langcode != null)
	{
		langcode = langcode.toUpperCase();
		session.setAttribute("langcode", langcode);
		mxs.setLangCode(langcode);
	}
	if(locale != null)
	{
		session.setAttribute("_locale_", locale);
		mxs.setLocale(locale);
	}

	String errorlabel;
	String message;
	String message2 = null;
	String[] btnLabels = null;
	boolean unavailable = request.getParameter("unavailable") != null;
	if (unavailable)
	{
		String[] unavailMsgs = mxs.getMessages("login", new String[] {"maxuisessionstitle", "maxuisessions", "maxuisessionsclose"});
		errorlabel = unavailMsgs[0];
		message = unavailMsgs[1];
		message2 = unavailMsgs[2];
	}
	else
	{
		errorlabel = mxs.getMessage("login", "loginerrorlabel");
		message = (String)session.getAttribute("signoutmessage");
		if (message == null)
		{
			message = mxs.getMessage("access", "invaliduser");
		}
		btnLabels = mxs.getMessages("logout", new String[] {"loginbtntext", "closebtntext"});
	}
	
	
	if (unavailable)
	{
		WebClientSessionManager wcsm = WebClientSessionManager.getWebClientSessionManager(session);
		if (wcsm == null || !wcsm.hasSessions())
		{
			session.invalidate();
		}
	}
	else
	{
		session.invalidate();
	}

%><!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title><%=mxs.getMessage("fusion","CompanyName")%></title>
	<link href="css/login.css" rel="stylesheet" type="text/css" />
<%	String reverseAlign = "right";
	if(BidiUtils.isGUIMirrored(langcode))
	{	
		reverseAlign = "left";%>
	<link href="css/RTLlogin.css" rel="stylesheet" type="text/css" />
<%	}	%>	
</head>
<body>
	<div>
		<table class="main_tbl" cellpadding="0" cellspacing="3">
			<tr>
				<td></td>
				<td align="<%=reverseAlign%>"><img src="images/ibm-logo-white.gif" alt=""></td>
			</tr>
			<tr>
				<td class="dialog" colspan="2">
					<table cellpadding="4" cellspacing="0" width="100%">
						<tr valign="top">
							<td rowspan="2" width="1%"><img src="images/st34informational_shad.png" alt=""/></td>
							<td><b><%=errorlabel%></b></td>
						</tr>
						<tr>
							<td>
								<%=message%>
<%							if(request.getParameter("basic") != null)
							{	%>		
								<br /><br /><%=message2%>
<%							}	%>
							</td>
						</tr>
<%					if(!unavailable)
					{	%>
						<tr>
							<td align="<%=reverseAlign%>" colspan="2">
								<button class="tiv_btn" onclick="javascript:document.getElementById('returnFrm').submit()">
									<%=btnLabels[0]%>
								</button>
							</td>
						</tr>
<%					}	%>
					</table>
				</td>
			</tr>
		</table>
	</div>
<%
	String chkLang;
	String acceptLang = request.getHeader("accept-language");
	if (acceptLang != null)
	{
		if (acceptLang.length() > 2)
		{
			chkLang = acceptLang.substring(0, 2);
		}
		else
		{
			chkLang = acceptLang;
		}
	}
	else
	{
		chkLang = psdi.server.MXServer.getMXServer().getBaseLang();
	}

	String actionStr;
	if (langcode == null || langcode.equalsIgnoreCase(chkLang))
	{
		actionStr="action='../../ui/login'";
	}
	else
	{
		actionStr="";
	}
%>
	<form id="returnFrm" name="returnFrm" method="post" <%=actionStr%>>
		<input type="hidden" id="redirect" name="redirect" value="1" />
		<input type="hidden" id="langcode" name="langcode" value="<%=langcode%>" />
	</form>
</body>
</html>