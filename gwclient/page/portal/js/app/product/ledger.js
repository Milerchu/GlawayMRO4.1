
$(function() {
	/* 
	   设置缺省顶部导航选择 
	   HOME_NAV：主页；PROD_NAV：产品状态；SUPPTASK_NAV：保障任务；
	   RESO_NAV：保障资源；STAT_NAV：统计查询；ABOUT_NAV：关于我们
	 */
	setDefaultNavSelected("PROD_NAV"); 
	
	loadLedgerPage(); //加载产品台帐
	
	initTab(); //初始化Tab选中事件
});

/* 初始化easyui分页控件 */
function loadLedgerPage() {
	$("#ledger").pagegrid({
		title:'产品台帐',
		width:'100%',
		height:350,
		dataUrl: CLIENT_PATH + "/portal/jsp/product/ledgerListReq.jsp",
		additionParams: '', //额外的数据源检索条件
		keyFields: ["assetnum", "siteid"],
		columns:[
			{field:'productcode',title:'产品代号',width:100},
			{field:'productorderno',title:'产品令号',width:100},
			{field:'radarnum',title:'雷达编号',width:100},
			{field:'drawno',title:'雷达图号',width:100},
			{field:'description',title:'装备名称',width:100},
			{field:'custinfo_armytype',title:'军兵种',width:80},
			{field:'outdate',title:'出所时间',width:120},
			{field:'deliverydate',title:'交装时间',width:120},
			{field:'qaperiod',title:'质保期', width:110},
			{field:'siteid',title:'地点',width:100}
		],
		isFirstRowSel: true, 
		onRowSelect: loadLedgerContent   //加载台帐数据
	});
}

var preLedger = "";	//记录之前选择的台帐编号assetnum		
var currLedger = "";//当前操作的资产对象 
var curRowData = null;//当前选中行数据
var isInfoPageFirstLoaded = true;

/* 初始化Tab选中事件 */
function initTab() {
	$("#tabs").tabs();
	$("#tabs").bind('tabsselect', function(event, ui) {
		if(ui.index == 1) { //第2个标签被选中
			if(curRowData != null) {
				if(preLedger != currLedger) {
					loadLedgerChart(curRowData);
				}
			} else {
				return false;
			}
		}
		return true;
	});
}

/* 加载台帐数据 */
function loadLedgerContent(rowIndex, rowData) {
	if(preLedger != rowData.assetnum) {
		loadLedgerBaseData(rowData);  //加载台帐基本数据
		loadLegerStr(rowData); //加载台帐结构树
		preLedger = currLedger;
		currLedger = rowData.assetnum; //记录当前最新选中的要显示的台帐
		curRowData = rowData;
	} else {
		currLedger = rowData.assetnum;
	}
}
/* 加载台帐基本数据 */
function loadLedgerBaseData(rowData) {
	$("#ledgerBaseData").find("span").each(function() {
		$(this).html(eval("rowData." + $(this).attr("id")));
	});
}	

var ledgerStrTree = null; //台帐结构树JS对象
/* 加载台帐结构树 */
function loadLegerStr(rowData) {
	//树控件设置参数
	var setting = {
		async: {
			enable: true,
			url: CLIENT_PATH + "/portal/jsp/product/ledgerStrReq.jsp",
			autoParam:["assetnum", "siteid"],
			otherParam:{"o_assetnum":rowData.assetnum, "o_siteid":rowData.siteid, "o_assetlevel":"ASSET"}
		},
		data: {
			simpleData: {
				enable: true,
				idKey: "assetnum",
				pIdKey: "parent",
				rootPId: null
			},
			key: {
				name: "nodeName"
			}
		}
	};
	
	$.fn.zTree.init($("#ledgerStrTree"), setting);
}	

/* 选择台帐，加载其统计图表：年度保障工作情况 */
function loadLedgerChart(rowData) {
	$.ajax({ 
		url: CLIENT_PATH + "/portal/jsp/product/ledgerChartReq.jsp?assetnum=" + rowData.assetnum + "&siteid=" + rowData.siteid, 
		cache: false,
		dataType: "json",
		success: function(dataResponse){
	    	drawLedgerChart(dataResponse);
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert(textStatus)
		}
	});
}	

/* 
 * 绘制台帐报表图形 
 * 参数chartData为数组型json数据[{}, {}]，
 *    且必须符合highcharts对饼型图的数据要求，如: [['Firefox', 45.0],['IE', 26.8]]
 * 
*/
function drawLedgerChart(chartData) {
	var total = 0;
	for(var i = 0, LENGTH = chartData.length; i < LENGTH; i++) {
		total += chartData[i][1];
	}
	new Highcharts.Chart({
           chart: {
               renderTo: 'ledgerChart',
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
                  return "<b>相关外场任务总数：</b>" + total + "次<br/>" 
                            		+ "<b>" + this.point.name + "：</b>" + this.y + "次<br/>"
                            		+ "<b>所占比例：</b>" + GW_PCT(this.y, total) + "%";
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
               data: chartData
           }]
       });
}


