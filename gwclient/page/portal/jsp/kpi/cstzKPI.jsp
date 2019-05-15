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
    <title>出所台帐KPI</title>
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
		initYearsList4CSTZ();
		$("#oneYear_cstz").combobox({  	
		     onSelect: function(rec){  		
		          	$('#toYears_cstz').combobox('enable');
		      }  
		});  
	});
	function initYearsList4CSTZ(){
		$.ajax({
			type: "post",
			url: servletURL+"?KPI=cstz&method=getExistsYearsList",
			dataType: "jsonp",
			jsonp: "callback",
			success: function(json){
				$('#oneYear_cstz').combobox({
						data : json,
						width:60,
						panelHeight:80,
						editable:false,
						valueField:'id', 
						textField:'text' 
				});
				$('#toYears_cstz').combobox({
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
	function doSearch4CSTZ(flag){
		var oneYear = "";
		var toYears = "";
		if(flag == "I"){
			var dd = new Date();
			oneYear = dd.getFullYear();
		}else{
			oneYear = $("#oneYear_cstz").combobox('getValue');
			toYears = $("#toYears_cstz").combobox('getValue');
			if(oneYear == ""){
				$.messager.alert('提示','请选择出所年份！'); 
				return;		
			}
			if(oneYear == toYears){
				$.messager.alert('提示','对比不能选择相同年份！'); 
				return;		
			}
		}
		$.ajax({
			type: "post",
			url: servletURL+"?KPI=cstz&method=getCstzInfo",
			dataType: "jsonp",
			jsonp: "callback",
			data:{
				oneYear : oneYear,
				toYears : toYears,
				lySelect:  encodeURI($("#lySelect_cstz").combobox('getValue')),
				model: encodeURI($("#model_cstz").val()),
				custname: encodeURI($("#custname_cstz").val()),
				armytype: encodeURI($("#armytype_cstz").combobox('getValue'))
			},
			success: function(json){
				drawLineChart4CSTZ(json[0].char);
				//将第二年的滞空
				$('#toYears_cstz').combobox('setValues', '');
			}
		});
	}
	function getShowText4CSTZ(){
		var title = new Date().getFullYear() + "	";
		var oneYear = $("#oneYear_cstz").combobox('getValue');
		var toYears = $("#toYears_cstz").combobox('getValue');
		var lySelect = $("#lySelect_cstz").combobox('getText');
		var model = $("#model_cstz").val();
		var custname = $("#custname_cstz").val();
		var armytype = $("#armytype_cstz").combobox('getText');
		if(oneYear != "" && oneYear != undefined){
			title = oneYear + "	";
		}
		if(toYears != "" && toYears != undefined){
			title += toYears + "	";
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
		title += "出所台帐";
		return title;
	}
	function drawLineChart4CSTZ(chartData) {
		$("#cstzChart").highcharts({
			title:{
				text : getShowText4CSTZ()
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
	                    text: '出所数量(台)',
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
	                    text: '出所数量(台)',
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
	setTimeout("doSearch4CSTZ('I')",500);
</script>
<div id="cstzKpiLayOut" class="easyui-layout" data-options="fit:true"> 
<div data-options="region:'south',title:'出所台帐 -> 查询条件'" style="height:100px;">
	<table width="100%" align="center" style="margin-top: 10px" border="0">
		<tr>
			<td align="right">出所年份：</td><td><input id='oneYear_cstz' type="text" name="oneYear_cstz"></td>
			<td align="right">对比出所年份：</td><td><input id='toYears_cstz' type="text" name="toYears_cstz"></td>
			<td align="right">产品领域：</td>
			<td>				
				<input class="easyui-combobox" id="lySelect_cstz" name="lySelect_cstz"
						data-options="
								url:'<%=basePath%>kpi?KPI=ASSETDOMAIN&method=fieldQuery',
								valueField:'id',
								textField:'text',
								editable:false,
								panelHeight:'auto'
				"> 
			</td>
			<td></td>
		</tr>
		<tr>
			<td align="right">产品型号：</td>
			<td>
				<input id="model_cstz" name="model_cstz">
				<img align="absmiddle" src="../kpi/images/lookup.gif" style="cursor:pointer;display:inline;margin:0px" onclick="showModelDiv('model_cstz')">
			</td>
			<td align="right">客户：</td>
			<td>
				<input id="custname_cstz" name="custname_cstz">
				<img align="absmiddle" src="../kpi/images/lookup.gif" style="cursor:pointer;display:inline;margin:0px" onclick="showCustDiv('custname_cstz')">
			</td>
			<td align="right">军兵种：</td>
			<td>
				<input class="easyui-combobox" id="armytype_cstz" name="armytype_cstz"
						data-options="
								url:'<%=basePath%>kpi?KPI=ARMYTYPE&method=fieldQuery',
								valueField:'id',
								textField:'text',
								editable:false,
								panelHeight:'auto'
				">
			</td>
			<td><input type="button" value="查询" onclick="doSearch4CSTZ('S')"></td>
		</tr>
	</table>
</div> 
<div data-options="region:'center',title:'出所台帐'" id="cstzChart"></div> 

</div>
</body>
</html>
