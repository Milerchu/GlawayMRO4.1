$(function() {
	/* 
	   设置缺省顶部导航选择 
	   HOME_NAV：主页；PROD_NAV：产品状态；SUPPTASK_NAV：保障任务；
	   RESO_NAV：保障资源；STAT_NAV：统计查询；ABOUT_NAV：关于我们
	 */
	setDefaultNavSelected("SUPPTASK_NAV"); 
	
	loadTaskChart(); //加载任务统计图
	
	loadTaskPage(); //加载当前任务的任务
	
	initTab(); //初始化Tab选中事件
});

/* 加载任务统计图 */
function loadTaskChart() {
	$.ajax({ 
		url: CLIENT_PATH + "/portal/jsp/task/currentChartReq.jsp", 
		cache: false,
		dataType: "json",
		error: function() {
			//alert("dd");
		},
		success: function(dataResp){
	    	drawSpideWebChart(dataResp);
		}
	});
}

function drawSpideWebChart(chartData) {
	/*$("#taskChart").highcharts({
		chart:{
			polar: true,
			type: 'line'
		},
		title: {
			text: null,
		},
		pane: {
			size: "80%"
		},
		xAxis: {
			categories: chartData.categories,
			tickmarkPlacement: 'on',
			lineWidth: 0
		},
		yAxis: {
			gridLineInterpolation: 'polygon',
			lineWidth: 0,
			min: 0
		},
		tooltip: {
			shared: true,
			formatter: function() {
               return "<b>" + this.x + "所占比例：" + (chartData.total!=0? (this.y*100.0/chartData.total).toFixed(1): 0) + "%" + "</b><br />"
               			+ "当前任务总量：" + chartData.total + "<br />"
                   	    + this.x + '数量：' + this.y;
            }
		},
		legend: {
			enabled: false
		},
		series: [{
			name: "",
			data: chartData.counts,
			pointPlacement: "on"
		}]
	});*/
	
	var seriesData = [];
	for(var i = 0, LENGTH = chartData.categories.length; i < LENGTH; i++) {
		seriesData[i] = [chartData.categories[i], chartData.counts[i]];
	}
	new Highcharts.Chart({
          chart: {
              renderTo: 'taskChart',
              plotBackgroundColor: null,
              plotBorderWidth: null,
              plotShadow: false
          },
          title: {
              text: null
          },
          tooltip: {
			  formatter: function() {
	                 //return '<b>'+ this.point.name +'</b>: '+ GW_DP(this.percentage, 2) + '%';
	                 return "<b>当前外场任务总数：</b>" + chartData.total + "次<br/>" 
	                           		+ "<b>" + this.point.name + "：</b>" + this.y + "次<br/>"
	                           		+ "<b>所占比例：</b>" + GW_PCT(this.y, chartData.total) + "%";
	             },
			  percentageDecimals: 1
          },
          plotOptions: {
              pie: {
				allowPointSelect: true,
				cursor: 'pointer',
				dataLabels: {
				    enabled: true,
				    color: '#000000',
				    connectorColor: '#000000',
				    formatter: function() {
                       	return this.point.name + ': ' + this.y + '次';
                           /*return "<b>相关外场任务总数：</b>" + total + "次" 
                           		+ this.point.name + ': ' + this.y + '次'
                           		+ "所占比例：" + (this.y / total);*/
                       }//this.percentage
				}
              }
          },
          series: [{
              type: 'pie',
              data: seriesData
          }]
      });
}

/* 加载当前任务的任务 */
var taskList = null;
function loadTaskPage() {
	taskList = $("#taskList").pagegrid({
		title:'当前任务',
		width:'100%',
		height:350,
		dataUrl: CLIENT_PATH + "/portal/jsp/task/currentListReq.jsp",
		additionParams: '', //额外的数据源检索条件
		keyFields: ["productcode", "fieldtasknum", "siteid"],
		columns:[
			/**/
			{field:'productcode',title:'产品代号',width:100},
			{field:'fieldtasknum',title:'外场任务编号',width:100},
			{field:'description',title:'任务内容',width:220},
			{field:'tasktype',title:'任务类型',width:120},
			{field:'displayname',title:'责任人', width:110},
			{field:'custname',title:'所属部队',width:120},
			{field:'location',title:'出差地点',width:100}
		],
		isFirstRowSel: true, 
		onRowSelect: loadTaskContent
	});

}	

var preTask = "";	//记录之前选择的外场任务编号fieldtasknum		
var preRowData = null;
/* 任务行选中事件 */
function loadTaskContent(rowIndex, rowData) {
	currRowData = rowData;
	
	if(preTask != rowData.fieldtasknum) {
		loadTaskBaseData(rowData);  //加载任务基本数据
		loadSubTaskData(rowData); //加载子任务项数据
		
		preTask = rowData.fieldtasknum; //记录当前最新选中的要显示的外场任务
		preRowData = rowData;
	}
}

/* 加载任务基本数据 */
function loadTaskBaseData(rowData) {
	$("#taskBaseData").find("span").each(function() {
		$(this).html(eval("rowData." + $(this).attr("id")));
	});
}

/* 加载子任务项数据 */
function loadSubTaskData(rowData) {
	$("#subTask").pagegrid({
		title:'子任务项',
		width:'100%',
		height:350,
		dataUrl: CLIENT_PATH + "/portal/jsp/task/planSubTaskListReq.jsp",
		additionParams: 'fieldtasknum=' + rowData['fieldtasknum'] + "&siteid=" + rowData['siteid'], //额外的数据源检索条件
		keyFields: ["sencyclicnum", "siteid"],
		columns:[
			{field:'ordernum',title:'序号',width:100},
			{field:'description',title:'任务内容',width:220},
			{field:'person_displayname',title:'责任人',width:120},
			{field:'assetnum',title:'装备序列号',width:120},
			{field:'schstarttime',title:'计划开始时间', width:110},
			{field:'schcompletetime',title:'计划完成时间',width:120},
			{field:'actcompletetime',title:'实际完成时间',width:120},
			{field:'legacynum',title:'关联遗留问题',width:100}
		],
		onRowSelect: null
	});
} 

/* 加载任务所需资源 */
function loadTaskResoData(rowData) {
	$.ajax({ 
		url: CLIENT_PATH + "/portal/jsp/task/planTaskResoChartReq.jsp?fieldtasknum=" + rowData['fieldtasknum'] + "&siteid=" + rowData['siteid'], 
		cache: false,
		dataType: "json",
		error: function() {
			//alert("dd");
		},
		success: function(dataResp){
	    	drawColumnChart(dataResp.spareChart);//drawPieChart("spareChart", dataResp.spareChart);
	    	drawPieChart("personChart", dataResp.personChart, "人员", "人");
	    	drawPieChart("equipChart", dataResp.equipChart, "设备", "台");
		}
	});
} 

var preTask = "";	//记录之前选择的外场任务编号fieldtasknum	
/* 初始化Tab选中事件 */
function initTab() {
	$("#tabs").tabs();
	$("#tabs").bind('tabsselect', function(event, ui) {
		if(ui.index == 1) { //第2个标签被选中
			if(preRowData != null) {
				loadTaskResoData(preRowData);
			}
		}
		return true;
	});
}

/* 绘制统计图 */
var itemnums = [];
function drawColumnChart(chartData) {
	itemnums = chartData.itemnums;
	$("#spareChart").highcharts({
		chart: {
           type: 'bar'
       },
       title: {
       		text: null
       },
       xAxis: {
           categories: chartData.categories,
           title: {
           	text: null
           }
       },
       yAxis: {
           min: 0,
           title: {
               text: '计划量'
           },
           labels : {
           		overflow: "justify"
           }
       },
       tooltip: {
           formatter: function() {
               return   "<b>计划备件</b><br />"
               			+ "备件编号：" + itemnums[this.point.x] + "<br />"
               			+ "备件名称：" + this.x + "<br />"
                   	    + "计划量：" + this.y;
           }
       },
       plotOptions: {
       		bar: {
       			dataLabels: {
       				enabled: true
       			}
       		}
       },
       legend: {
   			enabled: false
       },

        series: [{
           name: '',
           data: chartData.datas
       }]
	});
}

/*
 * 绘制饼图
 * renderTo：绘制的目标dom ID
 * chartData： 饼图数据，如[['零星出差',   45.0],['军贸服务',   26.8]]
*/
function drawPieChart(renderTo, chartData, title, unit) {
	var total = 0;
	for(var i = 0, LENGTH = chartData.length; i < LENGTH; i++) {
		total += chartData[i][1];
	}
	
	var chart;
	chart = new Highcharts.Chart({
		chart: {
			renderTo: renderTo,
			plotBackgroundColor: null,
			plotBorderWidth: null,
			plotShadow: false
		},
		title: {
			text: null
		},
		tooltip: {
			formatter: function() {
                return "<b>计划" + title + "总数：</b>" + total + unit + "<br/>" 
                          		+ "<b>" + this.point.name + "：</b>" + this.y + unit + "<br/>"
                          		+ "<b>所占比例：</b>" + GW_PCT(this.y, total) + "%";
            },
			percentageDecimals: 1
		},
		legend: {
			layout: 'vertical',
			align: 'right',
			verticalAlign: 'bottom',
			borderWidth: 0
		},
		plotOptions: {
			pie: {
				allowPointSelect: true,
				cursor: 'pointer',
				dataLabels: {
				    enabled: true,
				    color: '#000000',
				    connectorColor: '#000000',
				    formatter: function() {
                       	return this.point.name + ': ' + this.y + unit;
                    }
				}
             }
		},
		series: [{
			type: 'pie',
			data: chartData
		}]
	});	
	
	
}


