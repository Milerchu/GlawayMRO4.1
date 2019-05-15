<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String CLIENT_PATH = basePath + "webclient";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>出差人员_天KPI</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
<body>
<script src="<%=CLIENT_PATH%>/portal/js/highcharts/highcharts3.0.7.js"></script>
<script src="<%=CLIENT_PATH%>/portal/js/highcharts/highcharts-more.js"></script>
<script type="text/javascript">
	$(function(){
		initYearsList4CCRY_D();
		$("#oneYear_ccry_d").combobox({  	
		     onSelect: function(rec){  		
		          	$('#toYears_ccry_d').combobox('enable');
		      }  
		});  
	});
	function initYearsList4CCRY_D(){
		$.ajax({
			type: "post",
			url: servletURL+"?KPI=ccry&method=getExistsYearsList",
			dataType: "jsonp",
			jsonp: "callback",
			success: function(json){
				$('#oneYear_ccry_d').combobox({
						data : json,
						width:60,
						panelHeight:80,
						editable:false,
						valueField:'id', 
						textField:'text' 
				});
				$('#toYears_ccry_d').combobox({
						data : json,
						width:60,
						panelHeight:80,
						editable:false,
						valueField:'id', 
						textField:'text' 
				}).combobox('disable');
			}
		});
	}
	
	function doSearch4CCRY_D(flag){
		var oneYear = "";
		var toYears = "";
		if(flag == "I"){
			var dd = new Date();
			oneYear = dd.getFullYear();
		}else{
			oneYear = $("#oneYear_ccry_d").combobox('getValue');
			toYears = $("#toYears_ccry_d").combobox('getValue');
			if(oneYear == ""){
				$.messager.alert('提示','请选择年份！'); 
				return;		
			}
			if(oneYear == toYears){
				$.messager.alert('提示','对比不能选择相同年份！'); 
				return;		
			}
		}
		$.ajax({
			type: "post",
			url: servletURL+"?KPI=ccry&method=getCcrytj4DInfo",
			dataType: "jsonp",
			jsonp: "callback",
			data:{
				oneYear : oneYear,
				toYears : toYears,
				fundorderno: encodeURI($("#fundorderno_ccry_d").val()),
				deptName :　encodeURI($("#deptName_ccry_d").val()),
				tasktype :　encodeURI($("#tasktype_ccry_d").combobox('getValue')),
				craftName :　encodeURI($("#craftName_ccry_d").val())
			},
			success: function(json){
				drawLineChart4CCRY_D(json[0].char);
				//将第二年的滞空
				$('#toYears_ccry_d').combobox('setValues', '');
			}
		});
	}
	function getShowText4CCRY_D(){
		var title = new Date().getFullYear() + "	";
		var oneYear = $("#oneYear_ccry_d").combobox('getValue');
		var toYears = $("#toYears_ccry_d").combobox('getValue');
		var fundorderno = $("#fundorderno_ccry_d").val();
		var deptName = $("#deptName_ccry_d").val();
		var tasktype = $("#tasktype_ccry_d").combobox('getText');
		var craftName = $("#craftName_ccry_d").val();
		if(oneYear != "" && oneYear != undefined){
			title = oneYear + "	";
		}
		if(toYears != "" && toYears != undefined){
			title += toYears + "	";
		}
		if(fundorderno != "" && fundorderno != undefined){
			title += fundorderno + "经费令号	";
		}
		if(deptName != "" && deptName != undefined){
			title += deptName + "	";
		}
		if(tasktype != "" && tasktype != undefined){
			title += tasktype + "任务类型	";
		}
		if(craftName != "" && craftName != undefined){
			title += craftName + "工种	";
		}
		title += "出差人员";
		return title;
	}
	function drawLineChart4CCRY_D(chartData) {
		$("#ccry4dChart").highcharts({
			title:{
				text : getShowText4CCRY_D()
			},
			xAxis:{
				categories:[
					'1月',
					'2月',
					'3月',
					'4月',
					'5月',
					'6月',
					'7月',
					'8月',
					'9月',
					'10月',
					'11月',
					'12月'
				]
			},
			yAxis: [{ // Primary yAxis
	           	 	allowDecimals:false,
	                labels: {
	                    format: '{value}天',
	                    style: {
	                        color: '#89A54E'
	                    }
	                },
	                title: {
	                    text: '出差天数(天)',
	                    style: {
	                        color: '#89A54E'
	                    }
	                }
	           	 }, {
	           	 	allowDecimals:false,
	                labels: {
	                    format: '{value}天',
	                    style: {
	                        color: '#89A54E'
	                    }
	                },
	                title: {
	                    text: '出差天数(天)',
	                    style: {
	                        color: '#89A54E'
	                    }
	                }, 
	                opposite: true 
            }],
			tooltip:{
				valueSuffix: '天'
			
			},
			series:chartData
		});
	}
	setTimeout("doSearch4CCRY_D('I')",500);
</script>
<div id="cstzKpiLayOut" class="easyui-layout" data-options="fit:true"> 
<div data-options="region:'south',title:'出差人员 -> 查询条件'" style="height:100px;">
	<table width="100%" align="center" style="margin-top: 10px" border="0">
		<tr>
			<td align="right">年份：</td><td><input id='oneYear_ccry_d' type="text" name="oneYear_ccry_d"></td>
			<td align="right">对比年份：</td><td><input id='toYears_ccry_d' type="text" name="toYears_ccry_d"></td>
			<td align="right">经费令号：</td><td><input id="fundorderno_ccry_d" name="fundorderno_ccry_d"></td>
			<td align="right">任务类型：</td>
			<td>
				<input class="easyui-combobox" id="tasktype_ccry_d" name="tasktype_ccry_d"
						data-options="
								url:'<%=basePath%>kpi?KPI=TASKTYPE&method=fieldQuery',
								valueField:'id',
								textField:'text',
								editable:false,
								panelHeight:'auto'
				"> 
			</td>
			<td align="right">工种：</td>
			<td>
				<input id="craftName_ccry_d" name="craftName_ccry_d">
				<img align="absmiddle" src="../kpi/images/lookup.gif" style="cursor:pointer;display:inline;margin:0px" onclick="showCraftDiv('craftName_ccry_d')">
			</td>
			<td align="right">部门：</td>
			<td>
				<input id="deptName_ccry_d" name="deptName_ccry_d">
				<img align="absmiddle" src="../kpi/images/lookup.gif" style="cursor:pointer;display:inline;margin:0px" onclick="showDeptDiv('deptName_ccry_d')">
			</td>
			<td><input type="button" value="查询" onclick="doSearch4CCRY_D('S')"></td>
		</tr>
	</table>
</div> 
<div data-options="region:'center',title:'按出差天数'" id="ccry4dChart"></div> 

</div>
</body>
</html>
