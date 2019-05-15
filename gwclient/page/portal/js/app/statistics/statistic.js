$(function() {
	/* 
	   设置缺省顶部导航选择 
	   HOME_NAV：主页；PROD_NAV：产品状态；SUPPTASK_NAV：保障任务；
	   RESO_NAV：保障资源；STAT_NAV：统计查询；ABOUT_NAV：关于我们
	 */
	setDefaultNavSelected("TASK_NAV");
	
	//loadTaskChart(); //加载任务统计图
	
	loadTaskPage(); //加载当前任务的任务

	initUiWidget(); //初始化页面所有控件
});

/* 加载任务统计图 */
function loadTaskChart() {
	$.ajax({ 
		url: CLIENT_PATH + "/portal/jsp/statistics/statisticsChartReq.jsp", 
		cache: false,
		dataType: "json",
		error: function() {
			//alert("dd");
		},
		success: function(dataResp){
	    	drawColumnChart(dataResp);
		}
	});
}

function drawColumnChart(chartData) {
	$("#taskChart").highcharts({
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
	});
}

/* 加载当前任务的任务 */
var taskList = null;
function loadTaskPage() {
	taskList = $("#taskList").pagegrid({
		title:'任务清单',
		width:'100%',
		height:350,
		dataUrl: CLIENT_PATH + "/portal/jsp/statistics/statisticsListReq.jsp",
		additionParams: '', //额外的数据源检索条件
		isFiltering: true,
		keyFields: ["productcode", "fieldtasknum", "siteid"],
		columns:[
			/*{field:'productcode',title:'产品代号',width:100},
			{field:'productorderno',title:'产品令号',width:100},
			{field:'radarnum',title:'雷达编号',width:100},
			{field:'drawno',title:'雷达图号',width:100},
			{field:'description',title:'装备名称',width:100},*/
			{field:'fieldtasknum',title:'外场任务编号',width:60},
			{field:'description',title:'任务内容',width:100},
			{field:'tasktype',title:'任务类型',width:100},
			{field:'department', title:'责任部门', width:60},
			/*{field:'displayname',title:'责任人', width:110},*/
			{field:'custname',title:'所属部队',width:100},
			{field:'locationname',title:'出差地点',width:100},
			{field:'status',title:'状态',width:60}
		],
		isFirstRowSel: false, 
		onRowSelect: null
	});
}	

/* 初始化Tab选中事件 */
var tabStats = [false, true,            true, true, true]; //总任务情况统计   出所台帐情况统计  交装台帐情况统计  完成任务情况统计  未完成任务情况统计
var tabInits = ["",    "loadOutInst()", "", "", ""]
function initTab() {
	$("#tabs").tabs();
	$("#tabs").bind('tabsselect', function(event, ui) {
		if(ui.index > 0) { //从第2个标签开始，当选中时，才加载对应页面内容
			if(true == tabStats[ui.index]) {
				eval(tabInits[ui.index]);
				tabStats[ui.index] = false;
			}
		}
		return true;
	});
}

/* //初始化页面所有控件 */
function initUiWidget() {
	initTab(); //初始化控件

	$("#outdate").sSelect({panelWidth:80});
	
	$("#outdateFrom").sSelect({panelWidth:80});
	$("#outdateTo").sSelect({panelWidth:80});
	
	
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
		
		loadOutInst();
	});
	
	$('#outdate').change(loadOutInst);
	$('#outdateFrom').change(loadOutInst);
	$('#outdateTo').change(loadOutInst);
}


/****************************************** 出所台帐部分 start ********************************************/
var outInstPage = null;
function loadOutInst() {
	var condStr = "";
	if("checked" == $("#isCompYear").attr("checked")) {
		condStr += "isCompYear=1";
		condStr += "&outdateFrom=" + $("#outdateFrom").val();
		condStr += "&outdateTo=" + $("#outdateTo").val();
	} else {
		condStr += "isCompYear=0";
		condStr += "&outdate=" + $("#outdate").val();
		condStr += "&isCompDelivery=" + ("checked" == $("#isCompDelivery").attr("checked"));
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




