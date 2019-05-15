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
    <title>实时外场任务统计KPI</title>
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
		initYearsList4WCRW_2();
		$("#year_wcrw_2").combobox({  	
		     onSelect: function(rec){  		
		          	$('#twoyear_wcrw_2').combobox('enable');
		      }  
		});  
	});
		function initYearsList4WCRW_2(){
		$.ajax({
			type: "post",
			url: servletURL+"?KPI=wcrw&method=getExistsYearsList_2",
			dataType: "jsonp",
			jsonp: "callback",
			success: function(json){
				$('#year_wcrw_2').combobox({
						data : json,
						width:60,
						panelHeight:80,
						editable:false,
						valueField:'id', 
						textField:'text' 
				});
				$('#twoyear_wcrw_2').combobox({
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
	function doSearch4WCRWTJ_2(flag){
		var year = "";
		var twoyear = "";
		if(flag == "I"){
			var dd = new Date();
			year = dd.getFullYear();
		}else{
			year = $("#year_wcrw_2").combobox('getValue');
			twoyear = $("#twoyear_wcrw_2").combobox('getValue');
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
			url: servletURL+"?KPI=wcrw&method=getWcrwtjInfo_2",
			dataType: "jsonp",
			jsonp: "callback",
			data:{
				year : year,
				twoyear : twoyear,
				locationDesc: encodeURI($("#locationDesc_wcrw_2").val()) //站点
			},
			success: function(json){
				drawLineChart4WCRWTJ_2(json);
			}
		});
	}
	function drawLineChart4WCRWTJ_2(chartData) {
		var nowyear = new Date().getFullYear() + "	";
		var year = $("#year_wcrw_2").combobox('getValue');
		var twoyear = $("#twoyear_wcrw_2").combobox('getValue');
		var locationDesc = $("#locationDesc_wcrw_2").val();
		if(year != "" && year != undefined){showText = year + "	";}else{showText = nowyear + "	";}
		if(twoyear != "" && twoyear != undefined){showText += twoyear + "	";}
		if(locationDesc != ""){showText += locationDesc + "站点	";}
		showText += " 实时外场任务统计 ";
		$("#wcrw_2Chart").highcharts({
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
                    text: '任务数'
                },
                stackLabels: {
                    enabled: true,
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
                        this.series.name +': '+ this.y +'<br/>'+
                        '总数: '+ this.point.stackTotal;
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
	setTimeout("doSearch4WCRWTJ_2('I')",500);
</script>
	<div id="wcrw_2KpiLayOut" class="easyui-layout" data-options="fit:true"> 
		<div data-options="region:'south',title:'实时外场任务统计 -> 查询条件'" style="height:120px;">
			<table width="100%" align="center" style="margin-top: 10px" border="0">
				<tr>
					<td align="right">年份：</td><td><input id='year_wcrw_2' type="text" name="year_wcrw_2"></td>
					<td align="right">对比年份：</td><td><input id='twoyear_wcrw_2' type="text" name="twoyear_wcrw_2"></td>
					<td align="right">站点：</td>
					<td>
						<input id="locationDesc_wcrw_2" name="locationDesc_wcrw_2">
						<img align="absmiddle" src="../kpi/images/lookup.gif" style="cursor:pointer;display:inline;margin:0px" onclick="showLocDiv('locationDesc_wcrw_2')">
					</td>
					<td><input type="button" value="查询" onclick="doSearch4WCRWTJ_2('S')"></td>
				</tr>
			</table>
		</div> 
		<div data-options="region:'center',title:'实时外场任务统计'" id="wcrw_2Chart"></div> 
	</div>
</body>
</html>
