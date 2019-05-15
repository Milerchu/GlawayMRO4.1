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
  	 		$("#object").datagrid({
				fitColumns: true,
				autoRowHeight: false,
				singleSelect: true,
				columns: [[
					{field:'OBJECTNAME',width: '10%', title:'对象'},
					{field:'DESCRIPTION',width: '10%', title:'描述'},
					{field:'CLASSNAME',width: '10%', title:'类'},
					{field:'ISPERSISTENT',width: '10%', title:'持久性'},
					{field:'SITEORGTYPE',width: '10%', title:'级别'},
					{field:'ISVIEW',width: '10%', title:'视图'},
					{field:'VIEWFROM',width: '10%', title:'视图FROM'},
					{field:'VIEWWHERE',width: '10%', title:'视图WHERE'},
					{field:'ROWSTAMPING',width: '10%', title:'启用rowstamp限制'}
				]],
				onSelect: function(rowIndex, rowData) {
					alert(JSON.stringify(rowData));
					return false;
				}
			});
  	 		
  	 		$("#field").datagrid({
				fitColumns: true,
				autoRowHeight: false,
				singleSelect: true,
				columns: [[
					{field:'FIELDNAME',width: '5%', title:'对象'},
					{field:'TITLE',width: '8%', title:'标题'},
					{field:'DESCRIPTION',width: '10%', title:'描述'},
					{field:'CLASSNAME',width: '7%', title:'类'},
					{field:'DOMAINID',width: '6%', title:'域'},
					{field:'DEFAULTVALUE',width: '5%', title:'缺省值'},
					{field:'FIELDTYPE',width: '5%', title:'类型'},
					{field:'LENGTH',width: '5%', title:'长度'},
					{field:'SCALE',width: '5%', title:'小数位数'},
					{field:'ISPOSITIVE',width: '5%', title:'正数'},
					{field:'ISPERSISTENT',width: '5%', title:'持久性'},
					{field:'ISREQUIRED',width: '5%', title:'必填项'},
					{field:'SAMEASTABLE',width: '6%', title:'等同对象'},
					{field:'SAMEASFIELD',width: '6%', title:'与属性相同'},
					{field:'AUTOKEYSITEORGTYPE',width: '5%', title:'自动编号级别'},
					{field:'AUTOKEYNAME',width: '5%', title:'自动编号'},
					{field:'SEQNAME',width: '6%', title:'顺序名称'}
				]],
				onSelect: function(rowIndex, rowData) {
					alert(JSON.stringify(rowData));
					return false;
				}
			});
  	 		
  	 		$("#lookup").datagrid({
				fitColumns: true,
				autoRowHeight: false,
				singleSelect: true,
				columns: [[
					{field:'LOOKUPFIELD',width: '30%', title:'查找字段'},
					{field:'THISFIELD',width: '30%', title:'目标字段'},
					{field:'SRCFIELD',width: '30%', title:'源字段'}
				]],
				onSelect: function(rowIndex, rowData) {
					alert(JSON.stringify(rowData));
					return false;
				}
			});
  	 		
  	 		$("#relation").datagrid({
				fitColumns: true,
				autoRowHeight: false,
				singleSelect: true,
				columns: [[
					{field:'PARENT',width: '20%', title:'父对象'},
					{field:'NAME',width: '20%', title:'关联'},
					{field:'CHILD',width: '20%', title:'子对象'},
					{field:'WHERECLAUSE',width: '20%', title:'Where 子句'},
					{field:'DESCRIPTION',width: '20%', title:'备注'}
				]],
				onSelect: function(rowIndex, rowData) {
					alert(JSON.stringify(rowData));
					return false;
				}
			});
  	 		
  	 		$("#sequence").datagrid({
				fitColumns: true,
				autoRowHeight: false,
				singleSelect: true,
				columns: [[
					{field:'OBJECTNAME',width: '30%', title:'父对象'},
					{field:'SEQUENCENAME',width: '30%', title:'关联'},
					{field:'FIELDNAME',width: '30%', title:'子对象'}
				]],
				onSelect: function(rowIndex, rowData) {
					alert(JSON.stringify(rowData));
					return false;
				}
			});
  	 	});
  	 	
  	 	function getObjectGrid() {
			var dataObj = {};	
			dataObj["OBJS"] = $("#objectname").val();
			dataObj["TYPE"] = "OBJECT";
			$.ajax({
				type : "post",
				url : BASE_PATH + "servlet/cacheInfo",
				data: dataObj,
				dataType : "json",
				success : function(json) {
					$("#object").datagrid("loadData", json);
					return;
				}
			});
			
			dataObj["TYPE"] = "FIELD";
			$.ajax({
				type : "post",
				url : BASE_PATH + "servlet/cacheInfo",
				data: dataObj,
				dataType : "json",
				success : function(json) {
					$("#field").datagrid("loadData", json);
					return;
				}
			});
			
			dataObj["TYPE"] = "LOOKUP";
			$.ajax({
				type : "post",
				url : BASE_PATH + "servlet/cacheInfo",
				data: dataObj,
				dataType : "json",
				success : function(json) {
					$("#lookup").datagrid("loadData", json);
					return;
				}
			});
			
			dataObj["TYPE"] = "RELATION";
			$.ajax({
				type : "post",
				url : BASE_PATH + "servlet/cacheInfo",
				data: dataObj,
				dataType : "json",
				success : function(json) {
					$("#relation").datagrid("loadData", json);
					return;
				}
			});
			
			dataObj["TYPE"] = "SEQUENCE";
			$.ajax({
				type : "post",
				url : BASE_PATH + "servlet/cacheInfo",
				data: dataObj,
				dataType : "json",
				success : function(json) {
					$("#sequence").datagrid("loadData", json);
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
					对象：
				</td>
				<td>
					<input id="objectname" type="text" size="50"/>
					<input type="button" value="查询" onclick="getObjectGrid()" />
				</td>
			</tr>
		</table>
		<table class="layouttable" align="center">
			<tr>
				<td colspan="2">
					<div class="gridcontainer object">
						对象
						<table id="object" class="gridunit"></table>
					</div>
				</td>
			</tr>
			<tr>
				<td width="50%">
					<div class="gridcontainer">
						属性
						<table id="field" class="gridunit"></table>
					</div>
				</td>
				<td width="50%">
					<div class="gridcontainer">
						属性查找映射
						<table id="lookup" class="gridunit"></table>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="gridcontainer">
						关系
						<table id="relation" class="gridunit"></table>
					</div>
				</td>
				<td>
					<div class="gridcontainer">
						序列
						<table id="sequence" class="gridunit"></table>
					</div>
				</td>
			</tr>
		</table>
	</body>
</html>
