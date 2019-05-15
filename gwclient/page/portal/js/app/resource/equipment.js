$(function() {
	/* 
	   设置缺省顶部导航选择 
	   HOME_NAV：主页；PROD_NAV：产品状态；SUPPTASK_NAV：保障任务；
	   RESO_NAV：保障资源；TASK_NAV：工作任务；ABOUT_NAV：关于我们
	 */
	setDefaultNavSelected("RESO_NAV");
	
	loadEquipPage(); //加载当前任务的任务
	
	loadEquipYearFrqData(); //加载设备年使用频次较高的前n项
});

/* 加载人力资源分页列表 */
function loadEquipPage() {
	$("#equipList").pagegrid({
		title:'设备器材',
		width:'100%',
		height:350,
		dataUrl: CLIENT_PATH + "/portal/jsp/resource/equipmentListReq.jsp",
		additionParams: '', //额外的数据源检索条件 
		keyFields: ["laborcode"],
		isFiltering: false,
		columns:[
			{field:'devicenum',title:'设备编号',width:100},
			{field:'description',title:'描述',width:100},
			{field:'specmodel',title:'规格型号',width:120},
			{field:'quantity',title:'数量',width:120}
		],
		isFirstRowSel: false, 
		onRowSelect: null
	});
}	

/* 加载设备年使用频次较高的前n项 */
function loadEquipYearFrqData() {
	var TOP = 15;
	$.ajax({ 
		url: CLIENT_PATH + "/portal/jsp/resource/equipmentChartReq.jsp?top=" + TOP, 
		cache: false,
		dataType: "json",
		error: function() {
			//alert("dd");
		},
		success: function(dataResp){    	
	    	drawEquipYearFrChart(dataResp); //绘制统计图
		}
	});	
}

/* 绘制统计图 */
function drawEquipYearFrChart(chartData) {
	new Highcharts.Chart({
	    chart: {
	        renderTo: 'equipChart',
	        type: 'column',
	        margin: [ 50, 50, 100, 80]
	    },
	    title: {
	        text: null
	    },
	    xAxis: {
	        categories: chartData.categories,
	        labels: {
	            rotation: -45,
	            align: 'right',
	            style: {
	                fontSize: '13px',
	                fontFamily: 'Verdana, sans-serif'
	            }
	        }
	    },
	    yAxis: {
	        min: 0,
	        title: {
	            text: '频次'
	        }
	    },
	    legend: {
	        enabled: false
	    },
	    tooltip: {
	        formatter: function() {
	            return '<b>'+ this.x +'</b><br/>'+
	                '年使用频次：' + this.y;
	        }
	    },
	    series: [{
	        name: '',
	        data: chartData.datas,
	        dataLabels: {
	            enabled: true,
	            rotation: -90,
	            color: '#FFFFFF',
	            align: 'right',
	            x: -3,
	            y: 10,
	            formatter: function() {
	                return this.y;
	            },
	            style: {
	                fontSize: '13px',
	                fontFamily: 'Verdana, sans-serif'
	            }
	        }
	    }]
	});
}





