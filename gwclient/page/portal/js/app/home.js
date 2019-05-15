/* 初始化图标信息展示 */
function showCharts() {
	loadHomePageData(); //加载首页数据
}
/* 加载首页显示所需后台数据 */
function loadHomePageData() {
	$.ajax({ 
		url: CLIENT_PATH + "/portal/jsp/home/homeReq.jsp", 
		cache: false,
		dataType: "json",
		error: function() {
			//alert("dd");
		},
		success: function(dataResp){    		
	    	drawPieChart(dataResp.taskChart); //显示当前任务按任务类型分类后的统计图
	    	showCurrTask(dataResp.currTask);//显示当前任务清单 
	    	drawLineChart(dataResp.healMonth);//显示每月健康台帐所占比例线图
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
				categories: healMonth.categories
            },
            yAxis: {
                title: {
					text: "健康比例（%）"
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
					return '<b>'+ parseInt(this.x + "", 10) + "月健康台帐比例：</b>" + this.y + "%<br/>" 
								 + "<b>健康台帐数量：</b>" + healths[index] + "<br/>" 
								 + "<b>总台帐数量：</b>" + totals[index];
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
	
}

function drawPieChart(chartData) {
	var total = 0;
	var maxCount = -1; 
	for(var i = 0, LENGTH = chartData.length; i < LENGTH; i++) {
		total += chartData[i][1];
		if(maxCount < chartData[i][1]) {
			maxCount = chartData[i][1];
		}
	}
	for(var i = 0, LENGTH = chartData.length; i < LENGTH; i++) {
		if(maxCount == chartData[i][1]) {
			chartData[i] = {name: chartData[i][0], y: chartData[i][1], sliced: true, selected: true}
			break;
		}
	}
	var chart;
	chart = new Highcharts.Chart({
		chart: {
			renderTo: "taskChart",
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
                 return "<b>当前外场任务总数：</b>" + total + "次<br/>" 
                           		+ "<b>" + this.point.name + "：</b>" + this.y + "次<br/>"
                           		+ "<b>所占比例：</b>" + GW_PCT(this.y, total) + "%";
             },
			percentageDecimals: 1
		},
		legend: {
			enabled: true,
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
					enabled: false,
				    color: '#000000',
				    connectorColor: '#000000',
					formatter: function() {
                        	return this.y + '次';
                            /*return "<b>相关外场任务总数：</b>" + total + "次" 
                            		+ this.point.name + ': ' + this.y + '次'
                            		+ "所占比例：" + (this.y / total);*/
                        }//this.percentage
				},
				showInLegend: true
			}
		},
		series: [{
			type: 'pie',
			data: chartData
		}]
	});	
}
/* 显示当前任务清单 */
function showCurrTask(currTask) {
	var taskDomStr = "";
	for(var i = 0, LENGTH = currTask.length; i < LENGTH; i++) {
		taskDomStr += "<li><a href='" + CLIENT_PATH + "/portal/jsp/task/plan.jsp' target='_self'>" + currTask[i].fieldtasknum + ": " + html2Str(currTask[i].description) + "</a></li>";
	}
	$("#currTask").html(taskDomStr);
}

//主区点击后，悬浮放大
function zoomin(areaStr) {
	if(areaStr == "华东") {
		$("#huadong").modal({
			minHeight: 349,
			minWidth: 403/*,
			overlayTarget: "contDiv_center" //指定需要被覆盖的DOM对象，未指定此属性时，直接覆盖	*/
		});	
		$("#huadong").find('div').betterTooltip({speed: 150, delay: 300});
	} else if(areaStr == "华北") {
		$("#huabei").modal({
			minHeight: 349,
			minWidth: 403/*,
			overlayTarget: "contDiv_center" //指定需要被覆盖的DOM对象，未指定此属性时，直接覆盖当	*/
		});	
		$("#huabei").find('div').betterTooltip({speed: 150, delay: 300});
	} else if(areaStr == "西北") {
		$("#xibei").modal({
			minHeight: 349,
			minWidth: 403/*,
			overlayTarget: "contDiv_center" //指定需要被覆盖的DOM对象，未指定此属性时，直接覆盖当*/
		});	
		$("#xibei").find('div').betterTooltip({speed: 150, delay: 300});
	} else if(areaStr == "东北") {
		$("#dongbei").modal({
			minHeight: 349,
			minWidth: 403/*,
			overlayTarget: "contDiv_center" //指定需要被覆盖的DOM对象，未指定此属性时，直接覆盖当	*/
		});	
		$("#dongbei").find('div').betterTooltip({speed: 150, delay: 300});
	} else if(areaStr == "西南") {
		$("#xinan").modal({
			minHeight: 349,
			minWidth: 403/*,
			overlayTarget: "contDiv_center" //指定需要被覆盖的DOM对象，未指定此属性时，直接覆盖当	*/
		});	
		$("#xinan").find('div').betterTooltip({speed: 150, delay: 300});
	} else if(areaStr == "华南") {
		$("#huanan").modal({
			minHeight: 349,
			minWidth: 403/*,
			overlayTarget: "contDiv_center" //指定需要被覆盖的DOM对象，未指定此属性时，直接覆盖当	*/
		});	
		$("#huanan").find('div').betterTooltip({speed: 150, delay: 300});
	} else if(areaStr == "华中") {
		$("#huazhong").modal({
			minHeight: 349,
			minWidth: 403/*,
			overlayTarget: "contDiv_center" //指定需要被覆盖的DOM对象，未指定此属性时，直接覆盖当	*/
		});	
		$("#huazhong").find('div').betterTooltip({speed: 150, delay: 300});
	}

}

/* 字符串解析 */
function html2Str(str) {
	str = ((str == null || str == "null") ? "" : str);
	str = str + "";
	return str.replace(/[&"'\<\>]/g, function (c) {
		switch (c) {
		case "&":
			return "&amp;";
		case "'":
			return "&#39;";
		case '"':
			return "&quot;";
		case "<":
			return "&lt;";
		case ">":
			return "&gt;";
		}
	})
}	


