$(function() {
	/* 
	   设置缺省顶部导航选择 
	   HOME_NAV：主页；PROD_NAV：产品状态；SUPPTASK_NAV：保障任务；
	   RESO_NAV：保障资源；STAT_NAV：统计查询；ABOUT_NAV：关于我们
	 */
	setDefaultNavSelected("SUPPTASK_NAV");
	
	loadTaskChart(); //加载任务统计图
	
	loadTaskPage(); //加载当前任务的任务
	
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
	    	drawSpiderChart(dataResp);
		}
	});
}

function drawSpiderChart(chartData) {
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
		isFirstRowSel: false, 
		onRowSelect: null
	});

}	


