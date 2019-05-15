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
    <title>故障统计KPI</title>
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
		//initYearsList4GZTJ();
	});
	function initYearsList4GZTJ(){
		$.ajax({
			type: "post",
			url: servletURL+"?KPI=gztj&method=getExistsYearsList",
			dataType: "jsonp",
			jsonp: "callback",
			success: function(json){
				$('#outdate_gztj').combobox({
						data : json[0].outYear,
						width:60,
						panelHeight:80,
						editable:false,
						valueField:'id', 
						textField:'text' 
				});
				$('#deliverydate_gztj').combobox({
						data : json[0].deliveryYear,
						width:60,
						panelHeight:80,
						editable:false,
						valueField:'id', 
						textField:'text' 
				});
			}
		});
	}
	function doSearch4GZTJ(){
	/*	var outdate = "";	//出所年份
		var deliverydate = ""; //交装年份
		if(flag == "I"){
			var dd = new Date();
			outdate = dd.getFullYear();
			deliverydate = dd.getFullYear();
		}else{
			outdate = $("#outdate_gztj").combobox('getValue');
			deliverydate = $("#deliverydate_gztj").combobox('getValue');
			if(oneYear == ""){
				$.messager.alert('提示','请选择交装年份！'); 
				return;		
			}
			if(oneYear == toYears){
				$.messager.alert('提示','对比不能选择相同年份！'); 
				return;		
			}
		}*/
		$.ajax({
			type: "post",
			url: servletURL+"?KPI=gztj&method=getGztjInfo",
			dataType: "jsonp",
			jsonp: "callback",
			data:{
				lySelect:  encodeURI($("#lySelect_gztj").combobox('getValue')), //产品领域
				model: encodeURI($("#model_gztj").val()),	//型号
				custom: encodeURI($("#custom_gztj").val()),	//客户
				qaperiod: encodeURI($("#qaperiod_gztj").combobox('getValue')), //保修期
			//	productcode: encodeURI($("#productcode_gztj").val()), //产品代号
				productorderno: encodeURI($("#productorderno_gztj").val()), //生产令号
				armytype: encodeURI($("#armytype_gztj").combobox('getValue')) //军兵种
			//	outdate:$("#outdate_gztj").combobox('getValue'),	//出所年份
			//	deliverydate:$("#deliverydate_gztj").combobox('getValue')	//交装年份
			},
			success: function(json){
				drawLineChart4GZTJ(json[0].char);
			}
		});
	}
	function drawLineChart4GZTJ(chartData) {
		var lySelect = $("#lySelect_gztj").combobox('getText');
		var model = $("#model_gztj").val();
		var custom = $("#custom_gztj").val();
		var qaperiod = $("#qaperiod_gztj").combobox('getValue');
	//	var productcode = $("#productcode_gztj").val();
		var productorderno = $("#productorderno_gztj").val();
		var armytype = $("#armytype_gztj").combobox('getText');
	//	var outdate = $("#outdate_gztj").combobox('getValue');
	//	var deliverydate = $("#deliverydate_gztj").combobox('getValue');
		var showText = lySelect + "产品领域	";
		if(model != ""){showText += model + "型号	";}
		if(custom != ""){showText += custom + "客户	";}
		if(qaperiod != ""){showText += qaperiod + "	";}
	//	if(productcode != ""){showText += productcode + "产品代号	";}
		if(productorderno != ""){showText += productorderno + "生产令号	";}
		if(armytype != ""){showText += armytype + "军兵种		";}
	//	if(outdate != ""){showText += outdate + "年出所	";}
	//	if(deliverydate != ""){showText += outdate + "年交装	";}
		showText += " 故障率统计 ";
		$("#gztjChart").highcharts({
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
	setTimeout("doSearch4GZTJ()",500);
</script>
	<div id="gztjKpiLayOut" class="easyui-layout" data-options="fit:true"> 
		<div data-options="region:'south',title:'故障统计 -> 查询条件'" style="height:120px;">
			<table width="100%" align="center" style="margin-top: 10px" border="0">
				<tr>
					<td align="right">产品领域：</td>
					<td>
						<input class="easyui-combobox" id="lySelect_gztj" name="lySelect_gztj"
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
						<input id="model_gztj" name="model_gztj">
						<img align="absmiddle" src="../kpi/images/lookup.gif" style="cursor:pointer;display:inline;margin:0px" onclick="showModelDiv('model_gztj')">
					</td>
					<td align="right">保修期：</td>
					<td>
						<select id="qaperiod_gztj" class="easyui-combobox" name="qaperiod_gztj" data-options="width:60,panelHeight:70,editable:false">
							<option value="">全部</option> 
							<option value="保内">保内</option> 
							<option value="保外">保外</option> 
						</select>
					</td>
					<td align="right"></td><td></td>
				</tr>
				<tr>
					<td align="right">生产令号：</td><td><input id="productorderno_gztj" name="productorderno_gztj"></td>
					<td align="right">客户：</td>
					<td>
						<input id="custom_gztj" name="custom_gztj">
						<img align="absmiddle" src="../kpi/images/lookup.gif" style="cursor:pointer;display:inline;margin:0px" onclick="showCustDiv('custom_gztj')">
					</td>
					<td align="right">军兵种：</td>
					<td>
						<input class="easyui-combobox" id="armytype_gztj" name="armytype_gztj"
								data-options="
										url:'<%=basePath%>kpi?KPI=ARMYTYPE&method=fieldQuery',
										valueField:'id',
										textField:'text',
										editable:false,
										panelHeight:'auto'
						">
				</td>
					<td colspan="2" align="center"><input type="button" value="查询" onclick="doSearch4GZTJ()"></td>
				</tr>
			<!-- 
				<td align="right">产品代号:</td><td><input id="productcode_gztj" name="productcode_gztj"></td>
				<td align="right">出所年份:</td><td><input id='outdate_gztj' type="text" name="outdate_gztj"></td>
				<td align="right">交装年份:</td><td><input id='deliverydate_gztj' type="text" name="deliverydate_gztj"></td>
				<td align="right"></td>
			 -->
			</table>
		</div> 
		<div data-options="region:'center',title:'故障统计'" id="gztjChart"></div> 
	</div>
</body>
</html>
