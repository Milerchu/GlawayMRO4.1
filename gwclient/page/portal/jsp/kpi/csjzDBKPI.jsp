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
    <title>出所/交装台帐对比KPI</title>
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
		initYearsList4CSJZDB(); 
	});
	function initYearsList4CSJZDB(){
		$.ajax({
			type: "post",
			url: servletURL+"?KPI=jztz&method=getExistsYearsList4CsJzDB",
			dataType: "jsonp",
			jsonp: "callback",
			success: function(json){
				$('#oneYear_csjzDB').combobox({
						data : json,
						width:60,
						panelHeight:80,
						editable:false,
						valueField:'id', 
						textField:'text' 
				});
			}
		});
	}
	function doSearch4CSJZDB(flag){
		var oneYear = "";
		if(flag == "I"){
			var dd = new Date();
			oneYear = dd.getFullYear();
		}else{
			oneYear = $("#oneYear_csjzDB").combobox('getValue');
		}
		if(oneYear == ""){
			$.messager.alert('提示','请选择对比年份！'); 
		}else{
			$.ajax({
				type: "post",
				url: servletURL+"?KPI=jztz&method=getJzCstzDBInfo",
				dataType: "jsonp",
				jsonp: "callback",
				data:{
					oneYear : oneYear,
					lySelect:  encodeURI($("#lySelect_csjzDB").combobox('getValue')),
					model: encodeURI($("#model_csjzDB").val()),
					armytype: encodeURI($("#armytype_csjzDB").combobox('getValue')),
					custname: encodeURI($("#custname_csjzDB").val())
				},
				success: function(json){
					drawLineChart4CSJZDB(json[0].char);
				}
			});
		}
	}
	
	function getShowText4CSJZDB(){
		var title = new Date().getFullYear() + "	";
		var oneYear = $("#oneYear_csjzDB").combobox('getValue');
		var lySelect = $("#lySelect_csjzDB").combobox('getText');
		var model = $("#model_csjzDB").val();
		var armytype = $("#armytype_csjzDB").combobox('getText');
		var custname = $("#custname_csjzDB").val();
		if(oneYear != "" && oneYear != undefined){
			title = oneYear + "	";
		}
		title += lySelect + "领域	";
		if(model != "" && model != undefined){
			title += model + "型号	";
		}
		if(armytype != "" && armytype != undefined){
			title += armytype + "军兵种	";
		}
		if(custname != "" && custname != undefined){
			title += custname + "客户	";
		}
		title += "出所/交装台帐对比"; 
		return title;
	}
	function drawLineChart4CSJZDB(chartData) {
		$("#csjzDBChart").highcharts({
			title:{
				text : getShowText4CSJZDB()
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
	                    format: '{value}台',
	                    style: {
	                        color: '#89A54E'
	                    }
	                },
	                title: {
	                    text: '数量(台)',
	                    style: {
	                        color: '#89A54E'
	                    }
	                }
	           	 }, {
	           	 	allowDecimals:false,
	                labels: {
	                    format: '{value}台',
	                    style: {
	                        color: '#89A54E'
	                    }
	                },
	                title: {
	                    text: '数量(台)',
	                    style: {
	                        color: '#89A54E'
	                    }
	                }, 
	                opposite: true 
            }],
			tooltip:{
				valueSuffix: '台'
			
			},
			series:chartData
		});
	}
	setTimeout("doSearch4CSJZDB('I')",500);
	
</script>
<div id="CsJzDBLayOut" class="easyui-layout" data-options="fit:true"> 
<div data-options="region:'south',title:'出所/交装台帐对比 -> 查询条件'" style="height:100px;">
	<table width="90%" align="center" style="margin-top: 10px" border="0">
		<tr>
			<td>对比年份：<input id='oneYear_csjzDB' type="text" name="oneYear_csjzDB"></td>
			<td>
				产品领域：				
				<input class="easyui-combobox" id="lySelect_csjzDB" name="lySelect_csjzDB"
						data-options="
								url:'<%=basePath%>kpi?KPI=ASSETDOMAIN&method=fieldQuery',
								valueField:'id',
								textField:'text',
								editable:false,
								panelHeight:'auto'
				">  
			</td>
			<td>产品型号：<input id="model_csjzDB" name="model_csjzDB">
						<img align="absmiddle" src="../kpi/images/lookup.gif" style="cursor:pointer;display:inline;margin:0px" onclick="showModelDiv('model_csjzDB')">
			</td>
		    <td>客户:<input id="custname_csjzDB" name="custname_csjzDB">
		    		<img align="absmiddle" src="../kpi/images/lookup.gif" style="cursor:pointer;display:inline;margin:0px" onclick="showCustDiv('custname_csjzDB')">
		    </td>
			<td>军兵种：
				<input class="easyui-combobox" id="armytype_csjzDB" name="armytype_csjzDB"
						data-options="
								url:'<%=basePath%>kpi?KPI=ARMYTYPE&method=fieldQuery',
								valueField:'id',
								textField:'text',
								editable:false,
								panelHeight:'auto'
				">
			</td>
			<td><input type="button" value="查询" onclick="doSearch4CSJZDB('S')"></td>
		</tr>
	</table>
</div> 
<div data-options="region:'center',title:'对比统计'" id="csjzDBChart"></div> 
</div>
</body>
</html>
