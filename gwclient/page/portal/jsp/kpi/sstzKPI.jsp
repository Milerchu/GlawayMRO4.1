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
    <title>交装台帐KPI</title>
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
	function doSearch4SSTZ(){
		$.ajax({
			type: "post",
			url: servletURL+"?KPI=sstz&method=getSstzInfo",
			dataType: "jsonp",
			jsonp: "callback",
			data:{
				lySelect:  encodeURI($("#lySelect_sstz").combobox('getValue')),
				model: encodeURI($("#model_sstz").val()),
				custname: encodeURI($("#custname_sstz").val()),
				armytype: encodeURI($("#armytype_sstz").combobox('getValue'))
			},
			success: function(json){
				drawLineChart4SSTZ(json[0].char);
			}
		});
	}
	function drawLineChart4SSTZ(chartData) {
		var lySelect = $("#lySelect_sstz").combobox('getText');
		var model = $("#model_sstz").val();
		var armytype = $("#armytype_sstz").combobox('getText');
		var custname = $("#custname_sstz").val();
		var showText = lySelect + "领域	";
		if(model != ""){showText += model + "型号	";}
		if(armytype != ""){showText += armytype + "型号	";}
		if(custname != ""){showText += custname + "客户	";}
		showText += "实时台帐";
		$("#sstzChart").highcharts({
			 title: {
	            text: showText
	         },
	         tooltip: {
	    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
	         }, 
	         plotOptions: {
            	pie: {
	                allowPointSelect: true,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: true,
	                    color: '#000000',
	                    connectorColor: '#000000',
	                    format: '<b>{point.name}</b>: {point.y} 台'
	                }
            	}
        	},
	        series:[{
	        	type: 'pie',
	        	name: '所占百分比',
	        	data:chartData
	        }]
		});
	}
	setTimeout("doSearch4SSTZ()",500);
</script>
	<div id="sstzKpiLayOut" class="easyui-layout" data-options="fit:true"> 
		<div data-options="region:'south',title:'实时台帐 -> 查询条件'" style="height:100px;">
			<table width="100%" align="center" style="margin-top: 10px" border="0">
				<tr>
					<td align="right">产品领域：</td>
					<td>
						<input class="easyui-combobox" id="lySelect_sstz" name="lySelect_sstz"
								data-options="
										url:'<%=basePath%>kpi?KPI=ASSETDOMAIN&method=fieldQuery',
										valueField:'id',
										textField:'text',
										editable:false,
										panelHeight:'auto'
						">
					</td>
					<td align="right">产品型号：</td>
					<td>
						<input id="model_sstz" name="model_sstz">
						<img align="absmiddle" src="../kpi/images/lookup.gif" style="cursor:pointer;display:inline;margin:0px" onclick="showModelDiv('model_sstz')">
					</td>
					<td align="right">客户：</td>
					<td>
						<input id="custname_sstz" name="custname_sstz">
						<img align="absmiddle" src="../kpi/images/lookup.gif" style="cursor:pointer;display:inline;margin:0px" onclick="showCustDiv('custname_sstz')">
					</td>
					<td align="right">军兵种：</td>
					<td>
						<input class="easyui-combobox" id="armytype_sstz" name="armytype_sstz"
								data-options="
										url:'<%=basePath%>kpi?KPI=ARMYTYPE&method=fieldQuery',
										valueField:'id',
										textField:'text',
										editable:false,
										panelHeight:'auto'
						">
					</td>
					<td><input type="button" value="查询" onclick="doSearch4SSTZ()"></td>
				</tr>
			</table>
		</div> 
		<div data-options="region:'center',title:'实时台帐'" id="sstzChart"></div> 
	</div>
</body>
</html>
