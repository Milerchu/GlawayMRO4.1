<%@ page contentType="text/html;charset=UTF-8" buffer="128kb" autoFlush="true" import="com.glaway.mro.page.jspload.JspMgr" %>
<%
	JspMgr loginMgr = null;
	try {
		loginMgr = new JspMgr(request, response);
	} catch(Exception e) {
	}
	if(loginMgr == null) {
		response.sendRedirect(request.getContextPath() + "/mro");
	} else {
	    String loginwindow = request.getParameter("loginwindow");
		if(loginwindow!=null){
			out.print(loginMgr.getLoginPage());
		}else{
		    %>
		    <script language="javascript">
				var timestamp = Date.parse(new Date());
				var win = window.open("login.jsp?loginwindow=true&timestamp="+timestamp,"login"+timestamp,"toolbar=no,menubar=no,scrollbars=no,location=no,status=yes,resizable=yes,width="+(screen.width-8)+",height="+(screen.height-88)+",top=0,left=0");
				var bname = navigator.appName;
				if(win!=null){
					window.opener = null;
					window.open('','_self');
					setTimeout("window.close()",300);
				}
			</script>
		    <%
		}
	}
%>