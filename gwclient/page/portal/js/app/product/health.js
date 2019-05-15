$(function() {
	/* 
	   设置缺省顶部导航选择 
	   HOME_NAV：主页；PROD_NAV：产品状态；SUPPTASK_NAV：保障任务；
	   RESO_NAV：保障资源；STAT_NAV：统计查询；ABOUT_NAV：关于我们
	 */
	setDefaultNavSelected("PROD_NAV"); 
	
	initPageDatagrid(); //初始化easyui分页控件
	
	loadHealthChart(); //加载雷达健康状况统计图表 
});

/* 初始化easyui分页控件 */
function initPageDatagrid() {
	$("#ledger").pagegrid({
		title:'产品台帐',
		width:'100%',
		height:350,
		dataUrl: CLIENT_PATH + "/portal/jsp/product/healthListReq.jsp",
		additionParams: '', //额外的数据源检索条件
		keyFields: ["assetnum", "siteid"],
		columns:[
			/**/
			{field:'productcode',title:'产品代号',width:100},
			{field:'productorderno',title:'产品令号',width:100},
			{field:'radarnum',title:'雷达编号',width:100},
			{field:'description',title:'装备名称',width:100},
			{field:'outdate',title:'出所时间',width:120},
			{field:'deliverydate',title:'交装时间',width:120},
			{field:'qaperiod',title:'质保期', width:110},
			{field:'status',title:'状态', width:110},
			{field:'siteid',title:'地点',width:100}
		],
		isFirstRowSel: false, 
		onRowSelect: null
	});
}

/* 加载雷达健康状况统计图表 */
function loadHealthChart() {
	$.ajax({ 
		type: "POST",
		url: CLIENT_PATH + "/portal/jsp/product/healthChartReq.jsp", 
		cache: false,
		dataType: "json",
		success: function(dataResp){
	    	drawLineChart(dataResp.healMonth);//显示每月健康台帐所占比例线图
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("ERROR: " + XMLHttpRequest + "  " + textStatus);
		}
	});
}	

/* 绘制每月健康台帐所占比例线图 */
var totals = null;
var healths = null;
function drawLineChart(healMonth) {
	totals = healMonth.totals;
	healths = healMonth.healths;
	var cateLength = healMonth.categories.length;
	if(healMonth.percents.length < cateLength) {
		for(var i = healMonth.percents.length; i < cateLength; i++) {
			healMonth.percents[i] = null;
		}
	}
	var now = new Date();
	var year = now.getYear();
	var chart;
	chart = new Highcharts.Chart({
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
				categories: healMonth.categories
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
					return '<b>'+ this.x + "月健康台帐比例：</b>" + this.y + "%<br/>" 
								 + "健康数量：" + healths[index] + "<br/>" 
								 + "总数量：" + totals[index];
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
                data: healMonth.percents
    
            }]
        });
	/*
	 * line chart sets	
	chart = new Highcharts.Chart({
		chart: {
			renderTo: 'monthHealthChart',
			type: 'line'
		},
		title: {
			text: null //text: year + "年产品健康状况统计"
		},
		xAxis: {
			title: {
				text: "月"
			},
			gridLineWidth: 1,
			categories: healMonth.categories
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
		tooltip: {
			formatter: function() {
					var index = parseInt(this.x, 10) - 1;
					return '<b>'+ this.x + "月健康台帐比例：</b>" + this.y + "%<br/>" 
								 + "健康数量：" + healths[index] + "<br/>" 
								 + "总数量：" + totals[index];
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
		series: [{
			name: '',
			data: healMonth.percents
		}]
	});*/
}


