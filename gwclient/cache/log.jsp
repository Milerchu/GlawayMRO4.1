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
  			width: 1800;
  		}
  		.gridcontainer {
  			width: 100%;
  		 	height: 300px;
  		 	overflow: auto;
  		 	border: 1px solid #CDCDCD;
  		}
  		.gridcontainer.log {
  		 	height: 800px;
  		}
  		 .gridunit {
  		 	width: 100%;
  		 	height: 100%;
  		 }
  	</style>
  	
  	 <script type="text/javascript">
    	var BASE_PATH = "<%=basePath %>";
  	 	$(function(){
  	 		$("#log").datagrid({
				fitColumns: true,
				autoRowHeight: false,
				singleSelect: true,
				columns: [[
					{field:'prop',width: '20%', title:'日志属性'},
					{field:'value',width: '20%', title:'属性值'}
				]],
				onSelect: function(rowIndex, rowData) {
					alert(JSON.stringify(rowData));
					return false;
				}
			});
			
			getMsgGrid();
  	 	});
  	 	
  	 	function getMsgGrid() {
			var dataObj = {};	
			dataObj["TYPE"] = "LOG";
			$.ajax({
				type : "post",
				url : BASE_PATH + "servlet/cacheInfo",
				data: dataObj,
				dataType : "json",
				success : function(json) {
					$("#log").datagrid("loadData", json);
					return;
				}
			});
  	 	}
  	 </script>
  </head>
  
  <body>
		<table class="layouttable" align="center">
			<tr>
				<td colspan="2">
					<div class="gridcontainer log">
						日志配置
						<table id="log" class="gridunit"></table>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
