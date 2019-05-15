<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	request.setAttribute("path", path);
	request.setAttribute("basePath", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
  <head>
  	<title>系统提示</title>
	<style>
		body {
			margin: 0px;
			padding: 0px;
			background-color: #ebebeb;
		}
		table {
			font-size: 13px;
		}
		.map_notie6 {
			width: 1013px; 
			height: 582px;
			background-image: url("${basePath }/base/images/license/map.png");
		}
		.map_ie6 {
			width: 1013px; 
			height: 582px; 
			filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true,sizingMethod=scale, src="${basePath }/base/images/license/map.png")
		}
		.bg1 {
			width: 352px;
			height: 193px;
			background-image: url("../base/images/license/bg.jpg");
		}
		.infoBg {
			width: 454px;
			height: 217px;
			background-image: url("${basePath }/base/images/license/bg2.png");
		}
		.uploadlic {
			width: 67px;
			height: 23px;
			background-image: url("${basePath }/base/images/license/upload.jpg");
			border: none;
			cursor: hand;
		}
		.uploadlic_over {
			width: 67px;
			height: 23px;
			background-image: url("${basePath }/base/images/license/upload_over.jpg");
			border: none;
			cursor: hand;
		}
		.exitlic {
			width: 67px;
			height: 23px;
			background-image: url("${basePath }/base/images/license/exit.jpg");
			border: none;
			cursor: hand;
		}
		.exitlic_over {
			width: 67px;
			height: 23px;
			background-image: url("${basePath }/base/images/license/exit_over.jpg");
			border: none;
			cursor: hand;
		}
	</style>
  </head>
  
  <body>
	<table width="100%" height="100%">
		<tr>
			<td valign="middle">
				<table style="width: 100%; height: 658px; background-image: url('${basePath}/base/images/license/bg1.jpg');">
				<tr>
					<td align="center" valign="middle">
						<div id="info" class="infoBg">
							<table border="0" style="width: 100%; height: 100%">
								<tr>
									<td align="center">
										 License验证没有通过，机器码错误。
									</td>
								</tr>
							</table>
						</div>
					</td>
				</tr>
				</table>
			</td>
		</tr>
	</table>
  </body>
</html>
