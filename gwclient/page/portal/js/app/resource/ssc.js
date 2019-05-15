$(function() {
	/* 
	   设置缺省顶部导航选择 
	   HOME_NAV：主页；PROD_NAV：产品状态；SUPPTASK_NAV：保障任务；
	   RESO_NAV：保障资源；TASK_NAV：工作任务；ABOUT_NAV：关于我们
	 */
	setDefaultNavSelected("RESO_NAV");
	
	loadSscPage(); //加载当前任务的任务
	
	loadTravelPerMonthData(); //获取按月统计的出差人次数据
	
});

/* 加载人力资源分页列表 */
function loadSscPage() {
	$("#sscList").pagegrid({
		title:'人力资源',
		width:'100%',
		height:350,
		dataUrl: CLIENT_PATH + "/portal/jsp/resource/sscListReq.jsp",
		additionParams: '', //额外的数据源检索条件 
		keyFields: ["laborcode"],
		columns:[
			{field:'laborcode',title:'人工',width:100},
			{field:'personname',title:'人员名称',width:100},
			{field:'craftname',title:'缺省工种',width:120},
			{field:'skilllevel',title:'缺省技术等级',width:120},
			{field:'title',title:'头衔',width:120},
			{field:'jobcode',title:'职务代码',width:120},
			{field:'department',title:'部门',width:120},
			{field:'status',title:'状态',width:120}
		],
		isFirstRowSel: false, 
		onRowSelect: null
	});
}

/* 获取按月统计的出差人次数据 */
function loadTravelPerMonthData() {
	$.ajax({ 
		url: CLIENT_PATH + "/portal/jsp/resource/sscChartReq.jsp", 
		cache: false,
		dataType: "json",
		error: function() {
			//alert("dd");
		},
		success: function(dataResp){    	
	    	drawTravelPerMonthChart(dataResp); //绘制月出差人次统计图
		}
	});
} 

/* 绘制月出差人次统计图 */
function drawTravelPerMonthChart(chartData) {
	var now = new Date();
	var year = now.getYear();
	for(var i = 0; i < 12; i++) {
		chartData.categories[i] = (i + 1) + "月";
	}
	$('#sscChart').highcharts({
	    chart: {
	        type: 'columnrange',
	        inverted: true
	    },
	    title: {
	        text: null
	    },
	    xAxis: {
	        categories: chartData.categories
	    },
	    yAxis: {
	        title: {
	            text: '人次（月均' + chartData.average + "人次）"
	        }
	    },
	    tooltip: {
	        formatter: function() {
					return "<b>"+ this.x + "出差：</b>" + (this.point.low==chartData.average? this.point.high: this.point.low)  + "人次<br/>" 
							+ "<b>月均出差：</b>" + chartData.average + "人次";
			}
	    },
	    plotOptions: {
	        columnrange: {
	        	dataLabels: {
	        		enabled: true,
	        		formatter: function () {
	        			return this.y + '人次';
	        		}
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



