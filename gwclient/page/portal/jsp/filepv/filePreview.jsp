<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	response.setHeader("Access-Control-Allow-Origin", "*");
	session = request.getSession();
    session.setAttribute("OPENURLSTR", request.getAttribute("OPENURLSTR"));
    session.setAttribute("encryptName", request.getAttribute("encryptName"));
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path;
	
%>
<html>
<head>
		<title>MRO</title>
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7 charset=utf-8" />
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<script type="text/javascript" src="page/portal/jsp/filepv/js/jquery.js"></script>
		<script type="text/javascript" src="page/portal/jsp/filepv/officecontrol/OfficeContorlFunctions.js" charset="utf-8"></script>

	</head>
	<body onload='intializePage("<%=basePath%>/downloadattachments")'>
	<object classid="clsid:916EE952-83C7-485f-8469-69D975889ED2" codebase="page/portal/jsp/filepv/officecontrol/ntkopdfdoc.cab#version=4,0,0,2" width="100%" height="100%"> 
							</object>
		<script type="text/javascript" src="page/portal/jsp/filepv/officecontrol/ntkoofficecontrol.js" charset="utf-8"></script>
		<div id=statusBar style="height:20px;width:100%;background-color:#c0c0c0;font-size:12px;"></div>
		<script language="JScript" for=TANGER_OCX event="OnDocumentClosed()">
			setFileOpenedOrClosed(false);
		</script>
		<script language="JScript" for="TANGER_OCX" event="OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj)">
			OFFICE_CONTROL_OBJ.Menubar = false;
			OFFICE_CONTROL_OBJ.IsShowToolMenu = false;
			OFFICE_CONTROL_OBJ.IsShowEditMenu = false;
			//OFFICE_CONTROL_OBJ.Toolbars = false;
			OFFICE_CONTROL_OBJ.SetReadOnly(true);
			//获取文档控件中打开的文档的文档类型
			switch(OFFICE_CONTROL_OBJ.doctype) {
				case 1:
					fileType = "Word.Document";
					fileTypeSimple = "wrod";
					break;
				case 2:
					fileType = "Excel.Sheet";
					fileTypeSimple = "excel";
					break;
				case 3:
					fileType = "PowerPoint.Show";
					fileTypeSimple = "ppt";
					break;
				case 4:
					fileType = "Visio.Drawing";
					break;
				case 5:
					fileType = "MSProject.Project";
					break;
				case 6:
					fileType = "WPS Doc";
					fileTypeSimple = "wps";
					break;
				case 7:
					fileType = "Kingsoft Sheet";
					fileTypeSimple = "et";
					break;
				default:
					fileType = "unkownfiletype";
					fileTypeSimple = "unkownfiletype";
			}
			setFileOpenedOrClosed(true);
		</script>
		<script language="JScript" for=TANGER_OCX event="BeforeOriginalMenuCommand(TANGER_OCX_str,TANGER_OCX_obj)">
			alert("BeforeOriginalMenuCommand事件被触发");
		</script>
		<script language="JScript" for=TANGER_OCX event="OnFileCommand(TANGER_OCX_str,TANGER_OCX_obj)">
			if(TANGER_OCX_str == 3) {
				//alert("不能保存！");
				//saveWjnr();
				CancelLastCommand = true;
			}
		</script>
		<script language="JScript" for=TANGER_OCX event="AfterPublishAsPDFToURL(result,code)">
			result = trim(result);
			alert(result);
			document.all("statusBar").innerHTML = "服务器返回信息:" + result;
			if(result == "文档保存成功。") {
				window.close();
			}
		</script>
		<script language="JScript" for=TANGER_OCX event="OnCustomMenuCmd2(menuPos,submenuPos,subsubmenuPos,menuCaption,menuID)">
			alert("第" + menuPos + "," + submenuPos + "," + subsubmenuPos + "个菜单项,menuID=" + menuID + ",菜单标题为\"" + menuCaption + "\"的命令被执行.");
		</script>
		</div>

		
	</body>
</html>