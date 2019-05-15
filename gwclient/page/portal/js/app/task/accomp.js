$(function() {
	/* 
	   设置缺省顶部导航选择 
	   HOME_NAV：主页；PROD_NAV：产品状态；SUPPTASK_NAV：保障任务；
	   RESO_NAV：保障资源；STAT_NAV：统计查询；ABOUT_NAV：关于我们
	 */
	setDefaultNavSelected("SUPPTASK_NAV");	
	
	loadCommTaskPage(); //加载当前任务的任务
	
	loadCommPerYearData(); //获取按年统计的外场任务完成情况数据
	
});

/* 加载至今已完成的外场任务 */
function loadCommTaskPage() {
	$("#taskList").pagegrid({
		title:'年度外场任务完成情况',
		width:'100%',
		height:350,
		dataUrl: CLIENT_PATH + "/portal/jsp/task/accompListReq.jsp",
		additionParams: '', //额外的数据源检索条件
		keyFields: ["fieldtasknum", "siteid"],
		columns:[
			{field:'fieldtasknum',title:'外场任务编号',width:100},
			{field:'description',title:'任务内容',width:220},
			{field:'tasktype',title:'任务类型',width:120},
			{field:'displayname',title:'责任人', width:110},
			{field:'completetime',title:'完成时间',width:120},
			{field:'createdate',title:'创建时间',width:120},
			{field:'custname',title:'所属部队',width:120},
			{field:'location',title:'出差地点',width:100}
		],
		isFirstRowSel: false, 
		onRowSelect: null
	});
}

/* 获取按年统计的外场任务完成情况数据 */
function loadCommPerYearData() {
	changeChartTitleName("年度外场任务完成情况"); //根据不同统计图像，显示与其对应的小标题
	$.ajax({ 
		url: CLIENT_PATH + "/portal/jsp/task/accompChartReq.jsp", 
		cache: false,
		dataType: "json",
		error: function() {
			//alert("dd");
		},
		success: function(dataResp){    	
	    	//清除之前的绘图
	    	$("#yearChart").find("div:first").remove();
	    	$("#yearChart").html("");
	    	drawCommPerYearChart(dataResp); //绘制年度外场任务完成情况图
		}
	});
} 

/* 绘制年度外场任务完成情况图 */
function drawCommPerYearChart(chartData) {
	var chart = new Highcharts.Chart({
           chart: {
               renderTo: 'yearChart'
           },
           title: {
               text: null
           },
           xAxis: {
               categories: chartData.categories
           },
           yAxis: {
           	title: {
				text: "数量（个）"
			},
			allowDecimals: false,
			gridLineWidth: 1,
			plotLines: [{
				value: 0,
				width: 1,
				color: '#808080'
			}]
           },
           tooltip: {
               formatter: function() {
                   var s = "";
                   if (this.point.name) { // the pie chart
                   } else {
                   	s = "<b>" + this.x + "年外场任务完成情况</b><br/>"
                   		+ this.series.name + "：" + this.y + "<br />"
                   		+ "<font color='#745e3d'>点击可查看年内统计</font>";
                   }
                   return s;
               }
           },
           plotOptions: {
           	series: {
           		cursor: 'pointer',
           		point: {
           			events: {
           				click: function() {
           					changeChartTitleName(this.category + "年外场任务完成情况"); //根据不同统计图像，显示与其对应的小标题
           					
	                		loadYearTaskPage(this.category); //加载某年内完成的外场任务情况
	                		for(var i = 0, LENGTH = chartData.categories.length; i < LENGTH; i++) {
	                			if(chartData.categories[i] == this.category) {
	                				loadCommInYear(this.category, chartData.totals[i], chartData.comms[i]); //获取年内每月统计的外场任务完成情况数据 */
	                				break;
	                			}
	                		}
	                	}
           			}
           		}
           	}
           },
           series: [{
               type: 'column',
               name: '总任务数',
               data: chartData.totals,
               color: "#4673a7"
           }, {
               type: 'spline',
               name: '完成任务数',
               data: chartData.comms,
               color: "#a05452"
           }]
       });
}

/* 加载某年内完成的外场任务情况 */
function loadYearTaskPage(year) {
	$("#taskList").pagegrid({
		title: year + '年外场任务完成情况',
		width:'100%',
		height:350,
		dataUrl: CLIENT_PATH + "/portal/jsp/task/accompListReq.jsp",
		additionParams: 'year=' + year, //额外的数据源检索条件
		keyFields: ["fieldtasknum", "siteid"],
		columns:[
			{field:'fieldtasknum',title:'外场任务编号',width:100},
			{field:'description',title:'任务内容',width:220},
			{field:'tasktype',title:'任务类型',width:120},
			{field:'displayname',title:'责任人', width:110},
			{field:'completetime',title:'完成时间',width:120},
			{field:'createdate',title:'创建时间',width:120},
			{field:'custname',title:'所属部队',width:120},
			{field:'location',title:'出差地点',width:100}
		],
		isFirstRowSel: false, 
		onRowSelect: null
	});
}	

/* 获取年内每月统计的外场任务完成情况数据 */
function loadCommInYear(year, total, comm) {
	$.ajax({ 
		url: CLIENT_PATH + "/portal/jsp/task/accompYearChartReq.jsp?year=" + year, 
		cache: false,
		dataType: "json",
		error: function() {
			//alert("dd");
		},
		success: function(dataResp){    	
			//清除之前的绘图
	    	$("#yearChart").find("div:first").remove();
	    	$("#yearChart").html("");
	    	drawCommPerMonthChart(year, dataResp, total, comm); //绘制某年内月度外场任务完成情况图
		}
	});
} 

/* 绘制年内月度外场任务完成情况图 */
function drawCommPerMonthChart(year, chartData, total, comm) {
	var chart = new Highcharts.Chart({
           chart: {
               renderTo: 'yearChart'
           },
           title: {
               text: null//year + '年外场任务完成情况'
           },
           xAxis: {
               categories: chartData.categories
           },
           yAxis: {
	           	title: {
					text: "数量（个）"
				},
				allowDecimals: false,
				gridLineWidth: 1,
				plotLines: [{
					value: 0,
					width: 1,
					color: '#808080'
				}]
           },
           tooltip: {
               formatter: function() {
                   var s = "";
                   if (this.point.name) { // the pie chart
                   		var showStr = "";
                   		if(this.point.name == "完成任务") {
                   			showStr =   '完成任务总数：' + this.y + '个<br/>'
	                   					+ '未完成任务总数：' + (total - this.y) + '个<br/>'
                   		} else {
                   			showStr =   '完成任务总数：' + (total - this.y) + '个<br/>'
	                   					+ '未完成任务总数：' + this.y + '个<br/>'
                   		}
                   		s = '<b>' + year + "年任务总数：" + total + '个</b><br/>' 
	                   		+ showStr
	                   		+ "<font color='#745e3d'>点击可查看年度统计</font>";
                   } else {
                   		s = "<b>" + this.x + "月份外场任务完成情况</b><br/>"
                   			+ this.series.name + "：" + this.y;
                   }
                   return s;
               }
           },
           plotOptions: {
           	series: {
           		cursor: 'pointer',
           		point: {
           			events: {
           				click: function() {
	                		if(this.shapeType && this.shapeType == "arc") { //当前点击年度总统计数
	                			forwardToAnnualStat(); //转向总数据统计 
	                		}
	                	}
           			}
           		}
           	}
           },
           series: [{
               type: 'column',
               name: '月任务总数',
               data: chartData.totals,
               color: "#4673a7"
           }, {
               type: 'spline',
               name: '月完成任务数',
               data: chartData.comms,
               color: "#a05452"
           }, {
               type: 'pie',
               name: '年统计',
               data: [{
                   name: '完成任务',
                   y: comm,
                   color: '#a3d51c' 
               }, {
                   name: '未完成任务',
                   y: (total - comm),
                   color: '#af663a' 
               }],
               center: [100, 80],
               size: 100,
               showInLegend: false,
               dataLabels: {
                   enabled: false
               }
          }]
       });
}

/* 转向总数据统计 */
function forwardToAnnualStat() {
	loadCommTaskPage(); //加载当前任务的任务
	
	loadCommPerYearData(); //获取按年统计的外场任务完成情况数据
}

/* 根据不同统计图像，显示与其对应的小标题 */
function changeChartTitleName(title) {
	$("#chartTitle").html(title);
}


