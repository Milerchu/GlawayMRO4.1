<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<script type="text/javascript" src="<%=basePath %>page/js/jquery1.7.2.js"></script>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>page/portal/js/easyui1.5/themes/gray/easyui.css">
	<link rel="stylesheet" type="text/css" href="<%=basePath %>page/portal/js/easyui1.5/themes/icon.css">
	<script type="text/javascript" src="<%=basePath %>page/portal/js/easyui1.5/jquery.easyui.min.js"></script>
  	
  	<style>
  		/* easyui表格样式自定义start */
		.datagrid-header-inner, #x_assetinfo .datagrid-header-row {
			background-color: #efefef;
		}
		.datagrid-header-row td {
			border-right: 1px solid #cdcdcd;
		}
		
		.datagrid-header-inner, #tableContainer .datagrid-header-row {
			background-color: #bcccdb;
		}
		.datagrid-header-row td {
			border-right: 1px solid #93a8bb;
		}
		
		.panel-body {
			border-top: 0 none;
			border-right: 0 none;
			border-bottom: 0 none;
			border-left: 0 none;
		}
		/* easyui表格样式自定义end */
  	
  		.layouttable {
  			width: 1000;
  		}
  		.gridcontainer {
  			width: 100%;
  		 	height: 300px;
  		 	overflow: auto;
  		 	border: 1px solid #CDCDCD;
  		}
  		.gridcontainer.xml {
  		 	height: 500px;
  		}
  		 .gridunit {
  		 	width: 100%;
  		 	height: 100%;
  		 }
  		 textarea {
  		 	width: 100%;
  		 	height: 100%;
  		 }
  	</style>
  	
  	 <script type="text/javascript">
    	var BASE_PATH = "<%=basePath %>";
  	 	
  	 	function getXML() {
			var dataObj = {};	
			dataObj["XML"] = $("#xmlname").val();
			dataObj["TYPE"] = "XML";
			$.ajax({
				type : "post",
				url : BASE_PATH + "servlet/cacheInfo",
				data: dataObj,
				dataType : "text",
				success : function(xmlText) {
					$("#xml").val(xmlText);
					return;
				}
			});
  	 	}
  	 </script>
  </head>
  
  <body>
		<table align="center">
			<tr>
				<td>
					XML名称：
				</td>
				<td>
					<input id="xmlname" type="text" size="50"/>
					<input type="button" value="查询" onclick="getXML()" />
				</td>
			</tr>
		</table>
		<table class="layouttable" align="center">
			<tr>
				<td colspan="2">
					<div class="gridcontainer xml">
						<textarea id="xml"></textarea>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
