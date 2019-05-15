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
    <title>健康度KPI</title>
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
		initYearsList4JKDTJ();
		$("#year_jkdtj").combobox({  	
		     onSelect: function(rec){  		
		          	$('#twoyear_jkdtj').combobox('enable');
		      }  
		});  
	});
	function initYearsList4JKDTJ(){
		$.ajax({
			type: "post",
			url: servletURL+"?KPI=jkdtj&method=getExistsYearsList",
			dataType: "jsonp",
			jsonp: "callback",
			success: function(json){
				$('#year_jkdtj').combobox({
						data : json,
						width:60,
						panelHeight:80,
						editable:false,
						valueField:'id', 
						textField:'text' 
				});
				$('#twoyear_jkdtj').combobox({
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
	function doSearch4JKDTJ(flag){
		var year = "";
		var twoyear = "";
		if(flag == "I"){
			var dd = new Date();
			year = dd.getFullYear();
		}else{
			year = $("#year_jkdtj").combobox('getValue');
			twoyear = $("#twoyear_jkdtj").combobox('getValue');
			if(year == ""){
				$.messager.alert('提示','请选择一个年份！'); 
				return;		
			}
			if(year == twoyear){
				$.messager.alert('提示','对比不能选择相同年份！'); 
				return;		
			}
		}
		$.ajax({
			type: "post",
			url: servletURL+"?KPI=jkdtj&method=getJkdtjInfo",
			dataType: "jsonp",
			jsonp: "callback",
			data:{
				year : year,
				twoyear : twoyear,
				lySelect:  encodeURI($("#lySelect_jkdtj").combobox('getValue')), //领域
				qaperiod: encodeURI($("#qaperiod_jkdtj").combobox('getValue')), //保修期
				model: encodeURI($("#model_jkdtj").val()),	//型号
				custom: encodeURI($("#custom_jkdtj").val()),	//客户
				productorderno: encodeURI($("#productorderno_jkdtj").val()),	//生产令号
				armytype: encodeURI($("#armytype_jkdtj").combobox('getValue'))	//军兵种
			},
			success: function(json){
				drawLineChart4JKDTJ(json);
				//将第二年的滞空
				$('#twoyear_jkdtj').combobox('setValues', '');
			}
		});
	}
	function drawLineChart4JKDTJ(chartData) {
		var nowyear = new Date().getFullYear() + "	";
		var year = $("#year_jkdtj").combobox('getValue');
		var twoyear = $("#twoyear_jkdtj").combobox('getValue');
		var lySelect = $("#lySelect_jkdtj").combobox('getText');
		var qaperiod = $("#qaperiod_jkdtj").combobox('getValue');
		var model = $("#model_jkdtj").val();
		var custom = $("#custom_jkdtj").val();
		var productorderno = $("#productorderno_jkdtj").val();
		var armytype = $("#armytype_jkdtj").combobox('getText');
	
		showText = lySelect + "领域	";
		if(year != "" && year != undefined){showText += year + "	";}else{showText = nowyear + "	";}
		if(twoyear != "" && twoyear != undefined){showText += twoyear + "	";}
		if(qaperiod != "" && qaperiod != undefined){showText += qaperiod + "	";}
		if(model != "" && model != undefined){showText += model + "型号	";}
		if(custom != "" && custom != undefined){showText += custom + "客户	";}
		if(productorderno != "" && productorderno != undefined){showText += productorderno + "生产令号	";}
		if(armytype != "" && armytype != undefined){showText += armytype + "军兵种	";}
		
		showText += " 健康度统计 ";
		$("#jkdtjChart").highcharts({
			chart: {
                type: 'column'
            },
            title: {
                text: showText
            },
            xAxis: {
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
            yAxis: {
                min: 0,
                allowDecimals:false,
                title: {
                    text: '数量（台）'
                },
                stackLabels: {
                    enabled: false,
                    style: {
                        fontWeight: 'bold',
                        color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                    }
                }
            },
            legend: {
                align: 'right',
                x: -70,
                verticalAlign: 'top',
                y: 20,
                floating: true,
                backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColorSolid) || 'white',
                borderColor: '#CCC',
                borderWidth: 1,
                shadow: false
            },
            tooltip: {
                formatter: function() {
                    return '<b>'+ this.x +'</b><br/>'+
                        this.series.name +': '+ this.y +'<br/>';
                        //'总数: '+ this.point.stackTotal;
                }
            },
            plotOptions: {
                column: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: true,
                        color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white'
                    }
                }
            },
            series: chartData
		});
	}
	setTimeout("doSearch4JKDTJ('I')",500);
</script>
	<div id="jkdtjKpiLayOut" class="easyui-layout" data-options="fit:true"> 
		<div data-options="region:'south',title:'健康度统计 -> 查询条件'" style="height:120px;">
			<table width="100%" align="center" style="margin-top: 10px" border="0">
				<tr>
					<td align="right">年份：</td><td><input id='year_jkdtj' type="text" name="year_jkdtj"></td>
					<td align="right">对比年份：</td><td><input id='twoyear_jkdtj' type="text" name="twoyear_jkdtj"></td>
					<td align="right">产品领域：</td>
					<td>
						<input class="easyui-combobox" id="lySelect_jkdtj" name="lySelect_jkdtj"
								data-options="
										url:'<%=basePath%>kpi?KPI=ASSETDOMAIN&method=fieldQuery',
										valueField:'id',
										textField:'text',
										editable:false,
										panelHeight:'auto'
						"> 
					</td>
					<td align="right">保修期：</td>
					<td>
						<select id="qaperiod_jkdtj" class="easyui-combobox" name="qaperiod_jkdtj" data-options="width:60,panelHeight:70,editable:false">
							<option value="">全部</option> 
							<option value="保内">保内</option> 
							<option value="保外">保外</option> 
						</select>
					</td>
					<td></td>
				</tr>
				<tr>
					<td align="right">产品型号：</td>
					<td>
						<input id="model_jkdtj" name="model_jkdtj">
						<img align="absmiddle" src="../kpi/images/lookup.gif" style="cursor:pointer;display:inline;margin:0px" onclick="showModelDiv('model_jkdtj')">
					</td>
					<td align="right">客户：</td>
					<td>
						<input id="custom_jkdtj" name="custom_jkdtj">
						<img align="absmiddle" src="../kpi/images/lookup.gif" style="cursor:pointer;display:inline;margin:0px" onclick="showCustDiv('custom_jkdtj')">
					</td>
					<td align="right">生产令号：</td><td><input id="productorderno_jkdtj" name="productorderno_jkdtj"></td>
					<td align="right">军兵种：</td>
					<td>
						<input class="easyui-combobox" id="armytype_jkdtj" name="armytype_jkdtj"
								data-options="
										url:'<%=basePath%>kpi?KPI=ARMYTYPE&method=fieldQuery',
										valueField:'id',
										textField:'text',
										editable:false,
										panelHeight:'auto'
						">
				</td>
					<td><input type="button" value="查询" onclick="doSearch4JKDTJ('S')"></td>
				</tr>
			</table>
		</div> 
		<div data-options="region:'center',title:'健康度统计'" id="jkdtjChart"></div> 
	</div>
</body>
</html>
