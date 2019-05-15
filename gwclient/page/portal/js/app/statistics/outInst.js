$(function() {
	/* 
	   设置缺省顶部导航选择 
	   HOME_NAV：主页；PROD_NAV：产品状态；SUPPTASK_NAV：保障任务；
	   RESO_NAV：保障资源；STAT_NAV：统计查询；ABOUT_NAV：关于我们
	 */
	setDefaultNavSelected("STAT_NAV");

	initUiWidget(); //初始化页面所有控件
	
	loadData(); //加载页面数据
});

/* //初始化页面所有控件 */
function initUiWidget() {
	$("#outdate").sSelect({panelWidth:100});
	
	$("#outdateFrom").sSelect({panelWidth:100});
	$("#outdateTo").sSelect({panelWidth:100});
	
	$("#scopes").sSelect({panelWidth:100});
	
	
	$(".compTop").css("border-bottom", "1px dashed #238fcd");
	$(".compTop").css("color", "#000");
	$(".compBtm").css("border-bottom", "0 none");
	$(".compBtm").css("color", "#cdcdcd");
	$('#outdateFrom').disable(true);
	$('#outdateTo').disable(true);
	
	$("#isCompYear").change(function() {
		if("checked" == $(this).attr("checked")) {
			$(".compTop").css("border-bottom", "0 none");
			$(".compTop").css("color", "#cdcdcd");
			$(".compBtm").css("border-bottom", "1px dashed #238fcd");
			$(".compBtm").css("color", "#000");
			
			$('#outdate').disable(true);
			$("#isCompDelivery").attr("disabled", "disabled");
			$('#outdateFrom').disable(false);
			$('#outdateTo').disable(false);
			
		} else {
			$(".compTop").css("border-bottom", "1px dashed #238fcd");
			$(".compTop").css("color", "#000");
			$(".compBtm").css("border-bottom", "0 none");
			$(".compBtm").css("color", "#cdcdcd");
			
			$('#outdateFrom').disable(true);
			$('#outdateTo').disable(true);
			$("#isCompDelivery").removeAttr("disabled");
			$('#outdate').disable(false);
			
		}
	});
}

/* 当输入信息变化后，根据条件查询相关数据 */
function loadData() {
	loadOutInst(); //加载符合条件的出所台帐数据清单
	loadChartData(); //加载统计图表数据
}

var outInstPage = null; //出所台帐分页控件对象
/* 加载符合条件的出所台帐数据清单 */
function loadOutInst() {
	var condStr = "";
	if("checked" == $("#isCompYear").attr("checked")) {
		condStr += "isCompYear=1";
		condStr += "&outdateFrom=" + $("#outdateFrom").val();
		condStr += "&outdateTo=" + $("#outdateTo").val();
	} else {
		condStr += "isCompYear=0";
		condStr += "&outdate=" + $("#outdate").val();
		condStr += "&isCompDelivery=" + ("checked" == $("#isCompDelivery").attr("checked")? 1: 0);
	}
	
	if(outInstPage != null) {
		outInstPage.setAdditionParams(condStr);//重置附加查询条件 
		outInstPage.reloadData(); //重载数据 
		
	} else {
		outInstPage = $("#outInstList").pagegrid({
			title:'出所产品台帐',
			width:'100%',
			height:350,
			dataUrl: CLIENT_PATH + "/portal/jsp/statistics/outInstListReq.jsp",
			additionParams: condStr, //额外的数据源检索条件
			keyFields: ["assetnum", "siteid"],
			columns:[
				{field:'assetnum',title:'产品序列号',width:100},
				{field:'productcode',title:'产品代号',width:100},
				{field:'productorderno',title:'产品令号',width:100},
				{field:'radarnum',title:'雷达编号',width:100},
				{field:'drawno',title:'雷达图号',width:100},
				{field:'description',title:'装备名称',width:100},
				{field:'outdate',title:'出所时间',width:100},
				{field:'attachstatus',title:'备附件状态',width:100}
			],
			isFirstRowSel: false, 
			onRowSelect: null
		});
	}
}

/* 加载统计图表数据 */
function loadChartData() {
	var condStr = "";
	if("checked" == $("#isCompYear").attr("checked")) {
		condStr += "isCompYear=1";
		condStr += "&outdateFrom=" + $("#outdateFrom").val();
		condStr += "&outdateTo=" + $("#outdateTo").val();
	} else {
		condStr += "isCompYear=0";
		condStr += "&outdate=" + $("#outdate").val();
		condStr += "&isCompDelivery=" + ("checked" == $("#isCompDelivery").attr("checked")? 1: 0);
	}
	
	$.ajax({ 
		type: "POST",
		url: CLIENT_PATH + "/portal/jsp/statistics/outInstChartReq.jsp?" +　condStr, 
		cache: false,
		dataType: "json",
		success: function(dataResp){
	    	drawLineChart(dataResp);//显示每月健康台帐所占比例线图
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("ERROR: " + XMLHttpRequest + "  " + textStatus);
		}
	});
}

/* 线性趋势图 */
function drawLineChart(chartData) {
	new Highcharts.Chart({
		chart: {
			renderTo: 'outInstChart',
			type: 'line'
		},
		title: {
			text: null 
		},
		xAxis: {
			title: {
				text: "月"
			},
			gridLineWidth: 1,
			categories: ["01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"]
		},
		yAxis: {
			title: {
				text: "出所数量（台套）"
			},
			gridLineWidth: 1,
			min: 0,
			plotLines: [{
				value: 0,
				width: 1,
				color: '#808080'
			}]
		},
		tooltip: {
			formatter: function() {
					return '<b>'+ this.x + "月份" + this.series.name + "：</b>" + this.y + "台套<br/>";
			}
		},
		legend: {
			layout: 'vertical',
			align: 'right',
			verticalAlign: 'top',
			x: -10,
			y: 100,
			borderWidth: 0,
			enabled: false
		},
		series: chartData
	});
}

/* 柱状对比图 */
function drawColumnChart(chartData) {
	new Highcharts.Chart({
        chart: {
            renderTo: 'monthHealthChart',
            type: 'column'
        },
        title: {
            text: null
        },
        xAxis: {
            title: {
				text: "月"
			},
			gridLineWidth: 1,
			categories: chartData.categories
		},
        yAxis: {
            title: {
				text: "健康台帐比例（%）"
			},
			gridLineWidth: 1,
			min: 0,
			max: 100,
			plotLines: [{
				value: 0,
				width: 1,
				color: '#808080'
			}],
			tickInterval: 20
        },
        legend: {
            enabled: false
        },
        tooltip: {
            formatter: function() {
				var index = parseInt(this.x, 10) - 1;
				return '<b>'+ this.x + "月健康台帐比例：</b>" + this.y + "%<br/>";
			}
       	},
        plotOptions: {
            column: {
                pointPadding: 0.2,
                borderWidth: 0
            }
        },
        series: [{
            name: '',
            data: chartData.data

        }]
   	});
}




