$(function() {
	/* 根据分辨率设置highcharts样式 */
	var screenWidth = window.screen.width;
	if(screenWidth < 1366) {  //1280x800
		Highcharts.setOptions({
			title:{
				style:{
					fontSize:'14px'
				}
			}
		});
	} else if(screenWidth >= 1366 && screenWidth < 1440){ //1366x768
		Highcharts.setOptions({
			title:{
				style:{
					fontSize:'16px'
				}
			}
		});
	} else if(screenWidth >= 1440 && screenWidth < 1600){ //1440x900
		Highcharts.setOptions({
			title:{
				style:{
					fontSize:'17px'
				}
			}
		});
	} else if(screenWidth >= 1600 && screenWidth < 1920){ //1600x900
		Highcharts.setOptions({
			title:{
				style:{
					fontSize:'18px'
				}
			}
		});
	} else { //1920x1080
		Highcharts.setOptions({
			title:{
				style:{
					fontSize:'20px'
				}
			}
		});
	}
	
	/* 装备统计信息 */
//	$.ajax({
//		type : "post",
//		url : servletURL + "?KPI=equip&method=getEqutotal",
//		dataType : "jsonp",
//		jsonp : "callback",
//		success : function(json) {
//			$("#totalAssetCnt").text(json[0].equTotal);
//			$("#totalFltime").text(json[0].totalFltime);
//			$("#preFreCnt").text(json[0].equTotal - json[0].preRepair);
//			$("#equIntact").text(json[0].avgUsage);
//		}
//	});
	
	$("#totalAssetCnt").text("22");
	$("#totalFltime").text("9156");
	$("#preFreCnt").text("20");
	$("#equIntact").text("91");
	
	/* 按钮样式 */
	$("#kpi-button button")
			.click(
					function() {
						$(this)
								.css("background-image",
										"url('<%=CLIENT_PATH%>/portal/css/img/selected.png')");
						$(this)
								.siblings()
								.css("background-image",
										"url('<%=CLIENT_PATH%>/portal/css/img/unselected.png')");
					});
	// 每30秒发送一次请求
	// setInterval(function(){},3000);
	// 发送请求获取图标数据
//	$.ajax({// chart2
//		type : "post",
//		url : servletURL + "?KPI=equip&method=getFHByCountry",
//		dataType : "jsonp",
//		jsonp : "callback",
//		success : function(json) {
//			drawFHByCountry(json[0].CATEGORIES, json[0].VALUES);
//		}
//	});
				
	drawFHByCountry(['英国','法国','德国'], [12,30,21]);

//	$.ajax({// chart3
//		type : "post",
//		url : servletURL + "?KPI=equip&method=getPersonEveLoc",
//		dataType : "jsonp",
//		jsonp : "callback",
//		success : function(json) {
//			 var len = json[0].VALUES.length;
//			var data = new Array();
//			for ( var i = 0; i < len; i++) {
//				data[i] = new Array();
//				data[i][0] = json[0].CATEGORIES[i];
//				data[i][1] = json[0].VALUES[i];
//			}
//			drawPersonEveLoc(data);
//		}
//	});
	
	var values = [2,2,3];
	var categories = ["A国","P国","H国"];
	var data = new Array();
	for ( var i = 0; i < values.length; i++){
		data[i] = new Array();
		data[i][0] = categories[i];
		data[i][1] = values[i];
	}
	drawPersonEveLoc(data);
	
//	$.ajax({// chart5
//		type : "post",
//		url : servletURL + "?KPI=equip&method=getPlaneFaultQty",
//		dataType : "jsonp",
//		jsonp : "callback",
//		success : function(json) {
//			drawFaultQty(json[0].CATEGORIES, json[0].VALUES);
//		}
//	});
	drawFaultQty(['发动机','电磁阀','雷达'], [2,3,1]);

//	$.ajax({// chart1
//		type : "post",
//		url : servletURL + "?KPI=equip&method=getPlaneUsage",
//		dataType : "jsonp",
//		jsonp : "callback",
//		success : function(json) {
//			drawPlaneUsage(json[0].KEY, json[0].VALUE1, json[0].VALUE2);
//		}
//	});
	
	var key = ["P国","S国","A国","P国","E国","H国"];
	var values2 = [1,1,0,0,0,0];
	var values1=[0,2,8,3,4,3];
	drawPlaneUsage(key, values1, values2);
	
//	$.ajax({// chart6
//		type : "post",
//		url : servletURL + "?KPI=equip&method=getLocPMTotal",
//		dataType : "jsonp",
//		jsonp : "callback",
//		success : function(json) {
//			drawLocPM(json[0].CATEGORIES, json[0].VALUES);
//		}
//	});
	drawLocPM(['A基地','S基地'], [12,32]);
//
//	$.ajax({// chart4
//		type : "post",
//		url : servletURL + "?KPI=equip&method=getTSBexe",
//		dataType : "jsonp",
//		jsonp : "callback",
//		success : function(json) {
//			drawTSBExe(json[0].KEY, json[0].VALUE1,json[0].VALUE2,json[0].VALUE3,json[0].VALUE4);
//		}
//	});
	var keytsb = ["E基地-2","空天中心"];
	var value4=[0,0];
	var value3=[0,1];
	var value22=[0,0];
	var value11=[3,0];
	drawTSBExe(keytsb, value11,value22,value3,value4);
});

// 技术服务通报执行统计
function drawTSBExe(key, val1,val2,val3,val4) {
	$("#EChart4").highcharts({
		chart : {
			type : 'column'
		},
		title : {
			text : '各站点技术服务通报执行统计'
		},
		xAxis : {
			categories : key,
			title : {
				text : null
			}
		},
		yAxis : {
			min : 0,
			title : {
				text : null,
				align : 'high'
			},
			labels : {
				overflow : 'justify'
			},
			 stackLabels: {
	                enabled: true,
	                style: {
	                    fontWeight: 'bold',
	                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
	                }
	         }
		},
		tooltip:{
			shared:true
		},
		plotOptions:{
			 column: {
	                stacking: 'normal'
	            }
		},
		legend : {
			enabled : false
		},
		series : [ {
				name : '待接收',
				data : val1
			} ,
			{
				name:'已接收',
				data:val2
			},
			{
				name:'执行中',
				data:val3
			},
			{
				name:'待执行',
				data:val4
			}
		]
	});
}
// 各战区装备数量
function drawTheatre(data) {
	$("#EChart2")
			.highcharts(
					{
						chart : {
							plotBackgroundColor : null,
							plotBorderWidth : null,
							plotShadow : false
						},
						title : {
							text : '飞机利用率统计'
						},
						legend : {
							layout : 'vertical',
							floating : true,
							x : 105,
							y : 0,
							itemStyle : {
								color : '#2F7ED8'
							}
						},
						tooltip : {
							pointFormat : '{series.name}: <b>{point.y}</b>'
						},
						plotOptions : {
							pie : {
								allowPointSelect : true,
								cursor : 'pointer',
								showInLegend : true,
								center : [ '35%', '38%' ],
								dataLabels : {
									enabled : true,
									format : '{point.percentage:.1f}%',
									distance : -25,
									style : {
										fontWeight : 'bold',
										color : '#FFF',
										textShadow : '0px 1px 2px black'
									}
								},
								borderWidth : 0,
								size : '120%'
							}
						},
						colors : [ '#1b75a9', '#359594', '#d7a65a', '#943233',
								'#6062ac', '#64E572', '#FF9655', '#FFF263',
								'#6AF9C4' ],
						series : [ {
							type : 'pie',
							name : '装备数量',
							data : data
						} ]
					});
}

// 各飞机利用率统计
function drawPlaneUsage(key, value1,value2) {
	$("#EChart1").highcharts({
		chart : {
			type : 'column'
		},
		title : {
			text : '各国飞机数量统计'
		},
		colors : [ '#00ff00', '#ff4500' ],
		xAxis : {
			categories : key,
			title : {
				text : null
			},
			labels:{
				rotation:-45
			}
		},
		yAxis : {
			title : {
				text : null
			},
			labels : {
				overflow : 'justify'
			},
			tickPixelInterval:30,
            stackLabels: {
                enabled: true,
                style: {
                    fontWeight: 'bold',
                    color: (Highcharts.theme && Highcharts.theme.textColor) || 'gray'
                }
            }
		},
		tooltip: {
			formatter: function () {
                return '<b>' + this.x + '</b><br/>' +
                    this.series.name + ': ' + this.y + '<br/>' +
                    '总架数: ' + this.point.stackTotal;
            }
        },
		plotOptions:{
			 column: {
	                stacking: 'normal'/*,
	                dataLabels: {
	                    enabled: true,
	                    color: (Highcharts.theme && Highcharts.theme.dataLabelsColor) || 'white',
	                    style: {
	                        textShadow: '0 0 3px black'
	                    }
	                }*/
	            },
	          series:{
	        	  point:{
	        		  events:{
	        			  click:function(){
	        				  window.parent.$("#CntDialog").dialog('open');
	        				  drawCchart1();
	        			  }
	        		  }
	        	  },
	        	  cursor:'pointer'
	          }
		},
		series : [ {
				name : '可飞架数',
				data : value1
			} ,
			{
				name: '不可飞架数',
				data: value2
			}
		]
	});
}

// 故障件数量统计
function drawLocPM(key, value1) {
	$("#EChart6").highcharts({
		chart : {
			type : 'column'
		},
		legend : {
			enabled : false
		},
		title : {
			text : '各站点故障件数量统计'
		},
		xAxis : {
			categories : key
		},
		yAxis : {
			title : {
				text : null,
				style : {
					color : '#2F7ED8'
				}
			},
			tickPixelInterval : 30
		},
		plotOptions : {
			series : {
				marker : {
					fillColor : '#FFBA00',
					lineWidth : 0
				},
				color : '#308AFF',
				point:{
	        		  events:{
	        			  click:function(){
	        				  window.parent.$("#FHDialog").dialog('open');
	        				  drawFchart1();
	        				  drawFchart2();
	        				  drawFchart3();
	        			  }
	        		  }
	        	  }
			}
		},
		series : [ {
			name : '故障件个数',
			colorByPoint:true,
			data : value1
		}]

	});
}

//各站点人员统计
function drawPersonEveLoc(data) {

	/*Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function (color) {
        return {
            radialGradient: { cx: 0.5, cy: 0.3, r: 0.8 },
            stops: [
                [0, color],
                [1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
            ]
        };
    });*/
	
	$("#EChart3").highcharts({
				chart : {
					plotBackgroundColor : null,
					plotBorderWidth : null,
					plotShadow : false
				},
				title : {
					text : '各国人员统计'
				},
				legend : {
					enabled:false
				},
				tooltip : {
					pointFormat : '{series.name}: <b>{point.y}</b>'
				},
				plotOptions : {
					pie : {
						 allowPointSelect: true,
			             cursor: 'pointer',
			             dataLabels: {
			                    enabled: true,
			                    format: '<b>{point.name}</b>: {point.y} 人',
								distance:8
			             }
					},
					series:{
						point:{
							events:{
								click:function(){
									window.parent.$("#FHDialog").dialog('open');
									drawRSchart1();
									drawRSchart2();
									drawRSchart3();
								}
							}
						}
					}
				},
				series : [ {
					type : 'pie',
					name : '人员数',
					data : data
				} ]

			});
	
}

//各国飞行小时数统计
function drawFHByCountry(name, fh) {
	$("#EChart2").highcharts({
		chart : {
			type : 'column'
		},
		legend : {
			enabled : false
		},
		title : {
			text : '各国飞行小时数统计'
		},
		plotOptions : {
			series : {
				borderWidth : 0,
				dataLabels : {
					enabled : true,
					style : {
						color : '#3e83c4',
						textShadow : 'none'
					},
					crop : false,
					overflow : 'none'
				},
				point:{
					events:{
						click:function(){
							window.parent.$("#FHDialog").dialog('open');
							drawFHchart1();
							drawFHchart2();
							drawFHchart3();
						}
					}
				},
				cursor:'pointer'
			}

		},
		xAxis : {
			gridLineDashStyle : 'solid',
			gridLineWidth : 1,
			gridLineColor : 'rgba(36,146,219,0.5)',
			type : 'category',
			labels : {
				style : {
					letterSpacing : -1.5
				}
			},
			categories : name
		},
		yAxis : {
			title : {
				text : null
			},
			tickPixelInterval : 40
		},
		series : [ {
			name : '累计飞行小时',
			colorByPoint : true,
			data : fh,
			color : '#3e83c4'
		} ]

	});
}

// 飞行小时数统计
function drawFlightTime(name, flighttime) {
	$("#EChart1").highcharts({
		chart : {
			type : 'column'
		},
		legend : {
			enabled : false
		},
		title : {
			text : '各飞机飞行小时数统计'
		},
		plotOptions : {
			series : {
				borderWidth : 0,
				dataLabels : {
					enabled : true,
					style : {
						color : '#3e83c4',
						textShadow : 'none'
					},
					crop : false,
					overflow : 'none'
				}
			/*
			 * , point:{ events:{ click:function(){ //location.href =
			 * this.options.url; location.href = "javascript:void(window.open('" +
			 * this.options.url +"','','height=1080, width=1920,
			 * top=0,left=0,toolbar=no, menubar=no, scrollbars=no,
			 * resizable=yes,location=no,status=no'))" ; } } }
			 */
			}

		},
		xAxis : {
			gridLineDashStyle : 'solid',
			gridLineWidth : 1,
			gridLineColor : 'rgba(36,146,219,0.5)',
			type : 'category',
			labels : {
				style : {
					letterSpacing : -1.5
				}
			},
			categories : name
		},
		yAxis : {
			title : {
				text : null
			},
			tickPixelInterval : 40
		},
		series : [ {
			name : '飞行小时数',
			colorByPoint : true,
			data : flighttime,
			color : '#3e83c4'
		} ]

	});
}

// 问题类型统计
/*
function drawProblem(data) {
	//var url = "<%=basePath%>ui/?event=loadapp&value=problemmg";
	$("#EChart3")
			.highcharts(
					{
						chart : {
							plotBackgroundColor : null,
							plotBorderWidth : null,
							plotShadow : false
						},
						title : {
							text : '问题类型统计'
						},
						legend : {
							enabled : true,
							layout : 'vertical',
							floating : true,
							x : 105,
							y : 0,
							itemStyle : {
								color : '#2F7ED8'
							}
						},
						tooltip : {
							pointFormat : '{series.name}: <b>{point.y}</b>'
						},
						plotOptions : {
							series : {
								point : {
									events : {
										click : function() {
											// location.href = this.options.url;
											// location.href =
											// "javascript:void(window.open('" +
											// url +"','','height=1080,
											// width=1920,
											// top=0,left=0,toolbar=no,
											// menubar=no, scrollbars=no,
											// resizable=yes,location=no,status=no'))"
											// ;
											//window.parent.$("#problemDialog").dialog('open');
											drawPchart1();
											drawPchart2();
											drawPchart3();
											drawPchart4();
										}
									}
								}
							},
							pie : {
								allowPointSelect : true,
								cursor : 'pointer',
								showInLegend : true,
								center : [ '35%', '38%' ],
								dataLabels : {
									enabled : true,
									format : '{point.percentage:.1f}%',
									distance : -25,
									style : {
										fontWeight : 'bold',
										color : '#FFF',
										textShadow : '0px 1px 2px black'
									}
								},
								borderWidth : 0,
								size : '120%'
							}
						},
						colors : [ '#1b75a9', '#359594', '#d7a65a', '#943233',
								'#6062ac', '#64E572', '#FF9655', '#FFF263',
								'#6AF9C4' ],
						series : [ {
							type : 'pie',
							name : '数量',
							data : data
						} ]
					});
}
*/
// 故障部件top10
function drawFaultQty(name, qty) {
	$("#EChart5").highcharts({
		chart : {
			type : 'column'
		},
		legend : {
			enabled : false
		},
		title : {
			text : '故障部件TOP10'
		},
		plotOptions : {
			series : {
				marker : {
					fillColor : '#FFBA5B',
					lineWidth : 0
				},
				color : '#308AFF'
			}
		},
		xAxis : {
			categories : name,
			tickmarkPlacement : 'on',
			tickWidth : 0,
			tickPosition : 'inside',
			tickColor : '#3e82c3'
		},
		yAxis : {
			title : {
				text : null
			},
			tickPixelInterval : 38
		},
		series : [{
			name : '故障次数',
			colorByPoint : true,
			data : qty
		}]

	});
}

function drawCchart1() {
	$.ajax({
		type : "post",
		url : servletURL + "?KPI=equip&method=getCchart1",
		dataType : "jsonp",
		jsonp : "callback",
		success : function(json) {
			window.parent.$("#Cchart1").highcharts({
				chart : {
					type : 'column'
				},
				title : {
					text : "各站点飞机数量统计",
					style : {
						color : '#001995',
						fontSize : '20px',
						fontWeight : 'bold',
						fontFamily : '微软雅黑',
						paddingTop : '3%'
					}
				},
				xAxis : {
					categories : json[0].CATEGORIES,
					crosshair : true,
					text : null
				},
				yAxis : {
					title:{
						text : null
					}
				},
				series : [ {
					name : "飞机数",
					data : json[0].VALUES,
					colorByPoint:true
				} ]

			});
		}
	});
}

function drawFchart1(){
	$.ajax({
		type : "post",
		url : servletURL + "?KPI=equip&method=getFchart1",
		dataType : "jsonp",
		jsonp : "callback",
		success : function(json) {
			window.parent.$("#fhchart1").highcharts({
				chart : {
					type : 'column'
				},
				legend : {
					enabled : false
				},
				title : {
					text : '飞机故障统计'
				},
				plotOptions : {
					series : {
						borderWidth : 0,
						dataLabels : {
							enabled : true,
							style : {
								color : '#3e83c4',
								textShadow : 'none'
							},
							crop : false,
							overflow : 'none'
						}
					}

				},
				xAxis : {
					type : 'category',
					labels : {
						style : {
							letterSpacing : -1.5
						}
					},
					categories : json[0].CATEGORIES
				},
				yAxis : {
					title : {
						text : null
					},
					tickPixelInterval : 40
				},
				series : [ {
					name : '安全性故障数',
					colorByPoint : true,
					data : json[0].VALUES,
					color : '#3e83c4'
				} ]

			});
		}
	});
	
}

function drawFchart3() {
	$.ajax({
		type : "post",
		url : servletURL + "?KPI=equip&method=getFchart3",
		dataType : "jsonp",
		jsonp : "callback",
		success : function(json) {
			window.parent.$("#fhchart3").highcharts({
				chart : {
					type : 'column'
				},
				title : {
					text : "各站点缺件统计",
					style : {
						color : '#001995',
						fontSize : '20px',
						fontWeight : 'bold',
						fontFamily : '微软雅黑',
						paddingTop : '3%'
					}
				},
				xAxis : {
					categories : json[0].CATEGORIES,
					crosshair : true,
					text : null
				},
				yAxis:{
					title : {
						text : null
					}
				},
				series : [ {
					name : "缺件数",
					data : json[0].VALUES,
					colorByPoint:true
				} ]

			});
		}
	});
}

function drawFchart2() {
	$.ajax({
		type : "post",
		url : servletURL + "?KPI=equip&method=getFchart2",
		dataType : "jsonp",
		jsonp : "callback",
		success : function(json) {
			window.parent.$("#fhchart2").highcharts({
				chart : {
					type : 'column'
				},
				title : {
					text : "各站点备件统计",
					style : {
						color : '#001995',
						fontSize : '20px',
						fontWeight : 'bold',
						fontFamily : '微软雅黑',
						paddingTop : '3%'
					}
				},
				xAxis : {
					categories : json[0].CATEGORIES,
					crosshair : true,
					text : null
				},
				yAxis:{
					title : {
						text : null
					}
				},
				series : [ {
					name : "备件数",
					data : json[0].VALUES,
					colorByPoint:true
				} ]

			});
		}
	});
}

function drawPchart1() {
	$.ajax({
		type : "post",
		url : servletURL + "?KPI=equip&method=getPchart1",
		dataType : "jsonp",
		jsonp : "callback",
		success : function(json) {
			window.parent.$("#pchart1").highcharts({
				chart : {
					type : 'column'
				},
				title : {
					text : "各型号飞机问题统计",
					style : {
						color : '#001995',
						fontSize : '20px',
						fontWeight : 'bold',
						fontFamily : '微软雅黑',
						paddingTop : '3%'
					}
				},
				xAxis : {
					categories : json[0].CATEGORIES,
					crosshair : true,
					text : null
				},
				yAxis : {
					text : null
				},
				legend : {
					enabled : false
				},
				series : [ {
					name : "问题数",
					data : json[0].VALUES
				} ]

			});
		}
	});
}
function drawPchart2() {
	$.ajax({
		type : "post",
		url : servletURL + "?KPI=equip&method=getPchart2",
		dataType : "jsonp",
		jsonp : "callback",
		success : function(json) {
			window.parent.$("#pchart2").highcharts({
				chart : {
					type : 'bar'
				},
				legend : {
					enabled : false
				},
				title : {
					text : '各站点问题数统计',
					style : {
						color : '#001995',
						fontSize : '20px',
						fontWeight : 'bold',
						fontFamily : '微软雅黑',
						paddingTop : '3%'
					}
				},
				xAxis : {
					categories : json[0].CATEGORIES
				},
				yAxis : {
					title : {
						text : null,
						style : {
							color : '#2F7ED8'
						}
					}
				},
				plotOptions : {
					series : {
						marker : {
							fillColor : '#FFBA00',
							lineWidth : 0
						},
						color : '#308AFF'
					}
				},
				series : [ {
					name : '问题数',
					data : json[0].VALUES
				} ]

			});
		}
	});
}
function drawPchart3() {
	$.ajax({
		type : "post",
		url : servletURL + "?KPI=equip&method=getPchart3",
		dataType : "jsonp",
		jsonp : "callback",
		success : function(json) {
			window.parent.$("#pchart3").highcharts({
				title : {
					text : '2016年度每月问题数统计',
					x : -20, // center
					style : {
						color : '#001995',
						fontSize : '20px',
						fontWeight : 'bold',
						fontFamily : '微软雅黑',
						paddingTop : '3%'
					}
				},
				xAxis : {
					categories : json[0].CATEGORIES
				},
				yAxis : {
					title : {
						text : null
					}
				},
				legend : {
					enabled : false
				},
				series : [ {
					name : '问题数',
					data : json[0].VALUES
				} ]
			});
		}
	});
}
function drawPchart4() {
	$.ajax({
		type : "post",
		url : servletURL + "?KPI=equip&method=getPchart4",
		dataType : "jsonp",
		jsonp : "callback",
		success : function(json) {
			var len = json[0].VALUES.length;
			var data = new Array();
			for ( var i = 0; i < len; i++) {
				data[i] = new Array();
				data[i][0] = json[0].CATEGORIES[i];
				data[i][1] = json[0].VALUES[i];
			}
			window.parent.$("#pchart4").highcharts(
					{
						chart : {
							plotBackgroundColor : null,
							plotBorderWidth : null,
							plotShadow : false
						},
						title : {
							text : '各类型问题平均解决时间(单位：天)',
							style : {
								color : '#001995',
								fontSize : '20px',
								fontWeight : 'bold',
								fontFamily : '微软雅黑',
								paddingTop : '3%'
							}
						},
						legend : {
							layout : 'vertical',
							floating : true,
							x : 200,
							y : 0,
							itemStyle : {
								color : '#2F7ED8'
							}
						},
						tooltip : {
							pointFormat : '{series.name}: <b>{point.y}</b>'
						},
						plotOptions : {
							pie : {
								allowPointSelect : true,
								cursor : 'pointer',
								showInLegend : true,
								center : [ '45%', '45%' ],
								dataLabels : {
									enabled : true,
									format : '{point.percentage:.1f}%',
									distance : -35,
									style : {
										fontWeight : 'bold',
										color : '#FFF',
										textShadow : '0px 1px 2px black'
									}
								},
								borderWidth : 0
							}
						},
						/*colors : [ '#1b75a9', '#359594', '#d7a65a', '#943233',
								'#6062ac', '#64E572', '#FF9655', '#FFF263',
								'#6AF9C4' ],*/
						series : [ {
							type : 'pie',
							name : '平均解决时间',
							data : data
						} ]

					});
		}
	});
}

function drawFHchart1(){
	$.ajax({
		type : "post",
		url : servletURL + "?KPI=equip&method=getFHchart1",
		dataType : "jsonp",
		jsonp : "callback",
		success : function(json) {
			window.parent.$("#fhchart1").highcharts({
				chart : {
					type : 'column'
				},
				legend : {
					enabled : false
				},
				title : {
					text : '各飞机飞行小时数统计'
				},
				plotOptions : {
					series : {
						borderWidth : 0,
						dataLabels : {
							enabled : true,
							style : {
								color : '#3e83c4',
								textShadow : 'none'
							},
							crop : false,
							overflow : 'none'
						}
					}

				},
				xAxis : {
					/*gridLineDashStyle : 'solid',
					gridLineWidth : 1,
					gridLineColor : 'rgba(36,146,219,0.5)',*/
					type : 'category',
					labels : {
						style : {
							letterSpacing : -1.5
						}
					},
					categories : json[0].CATEGORIES
				},
				yAxis : {
					title : {
						text : null
					},
					tickPixelInterval : 40
				},
				series : [ {
					name : '累计飞行小时',
					colorByPoint : true,
					data : json[0].VALUES,
					color : '#3e83c4'
				} ]

			});
		}
	});
	
}

function drawFHchart2(){
	$.ajax({
		type : "post",
		url : servletURL + "?KPI=equip&method=getFHchart2",
		dataType : "jsonp",
		jsonp : "callback",
		success : function(json) {
			var len = json[0].VALUES.length;
			var data = new Array();
			for ( var i = 0; i < len; i++) {
				data[i] = new Array();
				data[i][0] = json[0].CATEGORIES[i];
				data[i][1] = json[0].VALUES[i];
			}
			
			Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function (color) {
		        return {
		            radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
		            stops: [
		                [0, color],
		                [1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
		            ]
		        };
		    });
			
			window.parent.$("#fhchart2").highcharts({
						chart : {
							plotBackgroundColor : null,
							plotBorderWidth : null,
							plotShadow : false
						},
						title : {
							text : '各站点飞行小时数统计',
							style : {
								color : '#001995',
								fontSize : '20px',
								fontWeight : 'bold',
								fontFamily : '微软雅黑',
								paddingTop : '3%'
							}
						},
						legend : {
							enabled:false
						},
						tooltip : {
							pointFormat : '{series.name}: <b>{point.y}</b>'
						},
						plotOptions : {
							pie : {
								 allowPointSelect: true,
					             cursor: 'pointer',
					             dataLabels: {
					                    enabled: true,
					                    format: '<b>{point.name}</b>: {point.y} h',
					                    style: {
					                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
					                    },
					                    connectorColor: 'silver'
					             }
							}
						},
						series : [ {
							type : 'pie',
							name : '累计飞行',
							data : data
						} ]

					});
		}
	});
	
}

function drawFHchart3(){
	$.ajax({
		type : "post",
		url : servletURL + "?KPI=equip&method=getFHchart3",
		dataType : "jsonp",
		jsonp : "callback",
		success : function(json) {
			var len = json[0].VALUES.length;
			var data = new Array();
			for ( var i = 0; i < len; i++) {
				data[i] = new Array();
				data[i][0] = json[0].CATEGORIES[i];
				data[i][1] = json[0].VALUES[i];
			}
			
			Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function (color) {
		        return {
		            radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
		            stops: [
		                [0, color],
		                [1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
		            ]
		        };
		    });
			
			window.parent.$("#fhchart3").highcharts({
						chart : {
							plotBackgroundColor : null,
							plotBorderWidth : null,
							plotShadow : false
						},
						title : {
							text : '各飞机飞行次数统计',
							style : {
								color : '#001995',
								fontSize : '20px',
								fontWeight : 'bold',
								fontFamily : '微软雅黑',
								paddingTop : '3%'
							}
						},
						legend : {
							enabled:false
							/*layout : 'vertical',
							floating : true,
							x : 200,
							y : 0,
							itemStyle : {
								color : '#2F7ED8'
							}*/
						},
						tooltip : {
							pointFormat : '{series.name}: <b>{point.y}</b>'
						},
						plotOptions : {
							pie : {
								 allowPointSelect: true,
					             cursor: 'pointer',
					             dataLabels: {
					                    enabled: true,
					                    format: '<b>{point.name}</b>: {point.percentage:.1f} %',
					                    style: {
					                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
					                    },
					                    connectorColor: 'silver'
					             }
							}
						},
						series : [ {
							type : 'pie',
							name : '飞行次数',
							data : data
						} ]

					});
		}
	});
	
}

function drawRSchart1(){
	$.ajax({
		type : "post",
		url : servletURL + "?KPI=equip&method=getRSchart1",
		dataType : "jsonp",
		jsonp : "callback",
		success : function(json) {
			var len = json[0].VALUES.length;
			var data = new Array();
			for ( var i = 0; i < len; i++) {
				data[i] = new Array();
				data[i][0] = json[0].CATEGORIES[i];
				data[i][1] = json[0].VALUES[i];
			}
			
			Highcharts.getOptions().colors = Highcharts.map(Highcharts.getOptions().colors, function (color) {
		        return {
		            radialGradient: { cx: 0.5, cy: 0.3, r: 0.7 },
		            stops: [
		                [0, color],
		                [1, Highcharts.Color(color).brighten(-0.3).get('rgb')] // darken
		            ]
		        };
		    });
			
			window.parent.$("#fhchart1").highcharts({
						chart : {
							plotBackgroundColor : null,
							plotBorderWidth : null,
							plotShadow : false
						},
						title : {
							text : '各站点出国人数统计',
							style : {
								color : '#001995',
								fontSize : '20px',
								fontWeight : 'bold',
								fontFamily : '微软雅黑',
								paddingTop : '3%'
							}
						},
						legend : {
							enabled:false
						},
						tooltip : {
							pointFormat : '{series.name}: <b>{point.y}</b>'
						},
						plotOptions : {
							pie : {
								 allowPointSelect: true,
					             cursor: 'pointer',
					             dataLabels: {
					                    enabled: true,
					                    format: '<b>{point.name}</b>: {point.y} 人',
					                    style: {
					                        color: (Highcharts.theme && Highcharts.theme.contrastTextColor) || 'black'
					                    },
					                    connectorColor: 'silver'
					             }
							}
						},
						series : [ {
							type : 'pie',
							name : '出国人数',
							data : data
						} ]

					});
		}
	});
	
	
}

function drawRSchart2(){
	$.ajax({
		type : "post",
		url : servletURL + "?KPI=equip&method=getRSchart2",
		dataType : "jsonp",
		jsonp : "callback",
		success : function(json) {
			window.parent.$("#fhchart2").highcharts({
				chart : {
					type : 'column'
				},
				legend : {
					enabled : false
				},
				title : {
					text : '出国时间最长人员TOP10'
				},
				plotOptions : {
					series : {
						borderWidth : 0,
						dataLabels : {
							enabled : true,
							style : {
								color : '#3e83c4',
								textShadow : 'none'
							},
							crop : false,
							overflow : 'none'
						}
					}

				},
				xAxis : {
					type : 'category',
					labels : {
						style : {
							letterSpacing : -1.5
						}
					},
					categories : json[0].CATEGORIES
				},
				yAxis : {
					title : {
						text : null
					},
					tickPixelInterval : 40
				},
				series : [ {
					name : '出国天数',
					colorByPoint : true,
					data : json[0].VALUES,
					color : '#3e83c4'
				} ]

			});
		}
	});
	
}

function drawRSchart3(){
	$.ajax({
		type : "post",
		url : servletURL + "?KPI=equip&method=getRSchart3",
		dataType : "jsonp",
		jsonp : "callback",
		success : function(json) {
			window.parent.$("#fhchart3").highcharts({
				chart : {
					type : 'column'
				},
				legend : {
					enabled : false
				},
				title : {
					text : '签证即将到期人员统计'
				},
				plotOptions : {
					series : {
						borderWidth : 0,
						dataLabels : {
							enabled : true,
							style : {
								color : '#3e83c4',
								textShadow : 'none'
							},
							crop : false,
							overflow : 'none'
						}
					}

				},
				xAxis : {
					type : 'category',
					labels : {
						style : {
							letterSpacing : -1.5
						}
					},
					categories : json[0].CATEGORIES
				},
				yAxis : {
					title : {
						text : null
					},
					tickPixelInterval : 40
				},
				series : [ {
					name : '剩余天数',
					colorByPoint : true,
					data : json[0].VALUES,
					color : '#3e83c4'
				} ]

			});
		}
	});
	
}

/**
 * 添加css文件
 * @param cssname css文件名，不包含.css
 */
function impCssFile(cssname){
	$("<link>")
	.attr({ rel: "stylesheet",
			type: "text/css",
			href: client_path+"/portal/css/"+cssname+".css"
	}).appendTo("head"); 
}
