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
  		.gridcontainer.object {
  		 	height: 200px;
  		}
  		 .gridunit {
  		 	width: 100%;
  		 	height: 100%;
  		 }
  	</style>
  	
  	 <script type="text/javascript">
    	var BASE_PATH = "<%=basePath %>";
  	 	$(function(){
  	 		$("#prop").datagrid({
				fitColumns: true,
				autoRowHeight: false,
				singleSelect: true,
				columns: [[
					{field:'PROPNAME',width: '10%', title:'属性名称'},
					{field:'DESCRIPTION',width: '10%', title:'描述'},
					{field:'USERDEFINED',width: '10%', title:'用户定义'},
					{field:'INPUTVALUE',width: '10%', title:'当前输入值'},
					{field:'DEFAULTVALUE',width: '10%', title:'缺省值'},
					{field:'CACHEDVALUE',width: '10%', title:'当前值'},
					{field:'MROTYPE',width: '10%', title:'数据类型'},
					{field:'DOMAINID',width: '10%', title:'域'},
					{field:'NULLSALLOWED',width: '10%', title:'允许空值'}
				]],
				onSelect: function(rowIndex, rowData) {
					alert(JSON.stringify(rowData));
					return false;
				}
			});
  	 	});
  	 	
  	 	function getPropGrid() {
			var dataObj = {};	
			dataObj["PROPS"] = $("#propname").val();
			dataObj["TYPE"] = "PROP";
			$.ajax({
				type : "post",
				url : BASE_PATH + "servlet/cacheInfo",
				data: dataObj,
				dataType : "json",
				success : function(json) {
					$("#prop").datagrid("loadData", json);
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
					属性名称：
				</td>
				<td>
					<input id="propname" type="text" size="50"/>
					<input type="button" value="查询" onclick="getPropGrid()" />
				</td>
			</tr>
		</table>
		<table class="layouttable" align="center">
			<tr>
				<td colspan="2">
					<div class="gridcontainer">
						属性
						<table id="prop" class="gridunit"></table>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
