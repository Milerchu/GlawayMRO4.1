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
	function doSearch4WCRWTJ_1(){
		$.ajax({
			type: "post",
			url: servletURL+"?KPI=wcrw&method=getWcrwtjInfo_1",
			dataType: "jsonp",
			jsonp: "callback",
			data:{
				tasktype:  encodeURI($("#tasktype_wcrw_1").combobox('getValue')), //任务类型
				locationDesc: encodeURI($("#locationDesc_wcrw_1").val()) //站点
			},
			success: function(json){
				drawLineChart4WCRWTJ_1(json);
			}
		});
	}
	function drawLineChart4WCRWTJ_1(chartData) {
		var showText = $("#tasktype_wcrw_1").combobox('getText') + "任务类型	";
		var locationDesc = $("#locationDesc_wcrw_1").val();
		if(locationDesc != ""){showText += locationDesc + "站点	";}
		showText += " 实时外场任务统计 ";
		$("#wcrw_1Chart").highcharts({
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
	setTimeout("doSearch4WCRWTJ_1()",500);
</script>
	<div id="wcrw_1KpiLayOut" class="easyui-layout" data-options="fit:true"> 
		<div data-options="region:'south',title:'实时外场任务统计 -> 查询条件'" style="height:120px;">
			<table width="100%" align="center" style="margin-top: 10px" border="0">
				<tr>
					<td align="right">外场任务类型：</td>
					<td>
						<input class="easyui-combobox" id="tasktype_wcrw_1" name="tasktype_wcrw_1"
								data-options="
										url:'<%=basePath%>kpi?KPI=TASKTYPE&method=fieldQuery',
										valueField:'id',
										textField:'text',
										editable:false,
										panelHeight:'auto'
						"> 
					</td>
					<td align="right">站点：</td>
					<td>
						<input id="locationDesc_wcrw_1" name="locationDesc_wcrw_1">
						<img align="absmiddle" src="../kpi/images/lookup.gif" style="cursor:pointer;display:inline;margin:0px" onclick="showLocDiv('locationDesc_wcrw_1')">
					</td>
					<td><input type="button" value="查询" onclick="doSearch4WCRWTJ_1()"></td>
				</tr>
			</table>
		</div> 
		<div data-options="region:'center',title:'实时外场任务统计'" id="wcrw_1Chart"></div> 
	</div>
</body>
</html>
