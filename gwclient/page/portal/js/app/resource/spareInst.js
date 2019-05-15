$(function() {
	/* 
	   设置缺省顶部导航选择 
	   HOME_NAV：主页；PROD_NAV：产品状态；SUPPTASK_NAV：保障任务；
	   RESO_NAV：保障资源；TASK_NAV：工作任务；ABOUT_NAV：关于我们
	 */
	setDefaultNavSelected("RESO_NAV");
	
	loadSpareInstPage(); //加载当前任务的任务
});

/* 加载当前任务的任务 */
function loadSpareInstPage() {
	$("#spareInst").pagegrid({
		title:'所内备件',
		width:'100%',
		height:350,
		dataUrl: CLIENT_PATH + "/portal/jsp/resource/spareInstListReq.jsp",
		additionParams: '', //额外的数据源检索条件
		keyFields: ["itemnum", "location"],
		columns:[
			/**/
			{field:'itemnum',title:'备件编号',width:100},
			{field:'item_description',title:'备件名称',width:100},
			{field:'location',title:'库房',width:220},
			{field:'curbaltotal',title:'当前余量',width:120},
			{field:'reservedqty',title:'当前预留数量', width:110},
			{field:'expiredqty',title:'已过期数量',width:120},
			{field:'avblbalance',title:'可用数量',width:120},
			{field:'issueunit',title:'单位',width:100},
			{field:'manufacturer',title:'制造商',width:100},
			{field:'abctype',title:'ABC 类型',width:100}
		],
		isFirstRowSel: true, 
		onRowSelect: onInstSpareRowSelect
	});

}	

var preIsRowIndex = -1;	//记录之前的选择行号	
/* 行选中事件 */
function onInstSpareRowSelect(rowIndex, rowData) {
	if(preIsRowIndex != rowIndex) {
		creatAndDrawChart(rowData); //根据选中行生成图表所需数据，并展示
		preIsRowIndex = rowIndex; //记录当前最新选中的信息行标识
	}
}

/* 根据选中行生成图表所需数据，并展示 */
function creatAndDrawChart(rowData) {
	var chartData = {categories: [], series:[]};
	var now = new Date();
	var year = now.getYear();
	chartData.categories = ["" + (year-3), "" + (year-2), "" + (year-1), "" + (year)];
	var data = [parseFloat("" + rowData.issue3yrago), parseFloat("" + rowData.issue2yrago), parseFloat("" + rowData.issue1yrago), parseFloat("" + rowData.issueytd)];
	var name = rowData.itemnum + ": " + rowData.item_description;
	chartData.series = [{name: name, data: data}];
	drawLineChart("spareInstChart", chartData, rowData.issueunit);
}

function drawLineChart(renderTo, chartData, issueunit) {
	for(var i = 0, LENGTH = chartData.categories.length; i < LENGTH; i++) {
		chartData.categories[i] = chartData.categories[i] + "年";
	}
	
	var chart;
	chart = new Highcharts.Chart({
		chart: {
			renderTo: renderTo,
			type: 'line'
		},
		title: {
			text: null
		},
		xAxis: {
			gridLineWidth: 1,
			categories: chartData.categories
		},
		yAxis: {
			title: {
				text: "数量"
			},
			min: 0,
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
					return '<b>' + this.series.name + '</b><br/>' +
					this.x + ': ' + this.y + issueunit;
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
		series: chartData.series
	});
}
			
			