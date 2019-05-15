<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
  <head>
  	<title>系统提示</title>
	<style>
		body {
			margin: 0px;
			padding: 0px;
			background-color: #62b2e5;
		}
		table {
			font-size: 13px;
		}
		.map_notie6 {
			width: 1013px; 
			height: 582px;
			background-image: url("<%=basePath%>/webclient/license/images/map.png");
		}
		.map_ie6 {
			width: 1013px; 
			height: 582px; 
			filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true,sizingMethod=scale, src="<%=basePath%>/webclient/license/images/map.png")
		}
		.bg1 {
			width: 352px;
			height: 193px;
			background-image: url("<%=basePath%>/webclient/license/images/bg.jpg");
		}
		.infoBg {
			width: 345px;
			height: 221px;
			background-image: url("<%=basePath%>/webclient/license/images/bg2.png");
		}
		.uploadlic {
			width: 67px;
			height: 23px;
			background-image: url("<%=basePath%>/webclient/license/images/upload.jpg");
			border: none;
			cursor: hand;
		}
		.uploadlic_over {
			width: 67px;
			height: 23px;
			background-image: url("<%=basePath%>/webclient/license/images/upload_over.jpg");
			border: none;
			cursor: hand;
		}
		.exitlic {
			width: 67px;
			height: 23px;
			background-image: url("<%=basePath%>/webclient/license/images/exit.jpg");
			border: none;
			cursor: hand;
		}
		.exitlic_over {
			width: 67px;
			height: 23px;
			background-image: url("<%=basePath%>/webclient/license/images/exit_over.jpg");
			border: none;
			cursor: hand;
		}
	</style>
  </head>
  
  <body>
  	<form method="post" action="editServer.do">
	<table width="100%" height="100%">
		<tr>
			<td valign="middle">
				<table style="width: 100%; height: 658px; background-image: url('<%=basePath%>/webclient/license/images/bg1.jpg');">
				<tr>
					<td align="center" valign="middle">
						<div id="info" class="infoBg">
							<table border="0" style="width: 100%; height: 100%">
								<tr>
									<td valign="bottom" height="50" width="88" align="right" style="padding-right: 10px;">
										<img src="<%=basePath%>/webclient/license/images/info.jpg"
											width="48" height="48" />
									</td>
									<td valign="bottom">
										无法连接License服务器!
									</td>
								</tr>
								<tr>
									<td colspan="2" align="center" height="30">请重新配置License服务器地址</td>
								</tr>
							<!-- 
								<tr>
									<td colspan="2" align="center" height="40">
										<input type="text" style="border: 1px #E5E4E9 solid; width: 150px;"
											id="serverIP" name="loginBean.serverIp" value="${serverIp }"/>:
										<input type="text" style="border: 1px #E5E4E9 solid; width: 70px;"
											id="serverPort" name="loginBean.serverPort" value="${serverPort}"/>
									</td>
								</tr>
								<tr>
									<td colspan="2" height="40" align="right" style="padding-right: 100px;">
										<input type="button" class="exitlic" 
											onmouseover="this.className='exitlic_over'"
											onmouseout="this.className='exitlic'"
											onclick="javascript: window.close()"/>
									</td>
								</tr>
								<tr>
									<td colspan="2"></td>
								</tr>
							 -->
							</table>
						</div>
					</td>
				</tr>
				</table>
			</td>
		</tr>
	</table>
	</form>
  </body>
</html>
