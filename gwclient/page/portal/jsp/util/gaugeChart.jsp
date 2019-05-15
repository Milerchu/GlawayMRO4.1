<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*,psdi.mbo.*,psdi.server.*,psdi.util.*,org.w3c.dom.*,psdi.mbo.*,psdi.util.*,psdi.webclient.system.controller.*,psdi.webclient.system.beans.*,psdi.webclient.system.runtime.*,psdi.webclient.servlet.*,psdi.webclient.system.session.*,psdi.webclient.controls.*,psdi.webclient.components.*,java.util.*,java.io.*"
	errorPage=""%>
<%@page import="java.text.NumberFormat"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String CLIENT_PATH = basePath + "webclient";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

		<script type="text/javascript" src="<%=CLIENT_PATH%>/javascript/plugins/jquery-1.7.2.js"></script>
		
		<!-- DOJO gauge  start -->
		<link rel="stylesheet" type="text/css" href="<%=CLIENT_PATH%>/javascript/dojo_1-5-0_20100902/dojo/resources/dojo.css"/>
		<link rel="stylesheet" type="text/css" href="<%=CLIENT_PATH%>/javascript/dojo_1-5-0_20100902/dijit/themes/tundra/tundra.css"/>
		<script type="text/javascript" src="<%=CLIENT_PATH%>/javascript/browser_library.js"></script>
		<script type="text/javascript" src="<%=CLIENT_PATH%>/javascript/prototype.js"></script>
		<script type="text/javascript" src="<%=CLIENT_PATH%>/utility/Gallery/JSClass/FusionCharts.js"></script>
		<script type="text/javascript" src="<%=CLIENT_PATH%>/javascript/dojo_1-5-0_20100902/dojo/dojo.js"></script>
		<script type="text/javascript" src="<%=CLIENT_PATH%>/javascript/dojo_1-5-0_20100902/dojo/window.js"></script>
		<script type="text/javascript" src="<%=CLIENT_PATH%>/javascript/dojo_library.js"></script>
		<script>
			var MAINDOC = document;
			dojo.require('layers.mbs.popuplayer');
			dojo.addOnLoad(function() {
				dojo.require('dijit._base.popup'); 
				dojo.require('dijit.Tooltip');
				dojo.require('dojo.dnd.Moveable');
				// Workaround to set the starting z index for dojo popups and tooltips above the maximo dialog.
				dojo.addOnLoad(dojohelper.fixPopupZIndex);
			});
		</script>
		<script  type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/startcenter.js"></script>
		<script>
			var hebrew=false;
			var islamic=false;
			dojo.require('layers.mbs.startcenter');
			dojo.addOnLoad(function() {
			//$(function() {
				dojo.require('dojox.charting.Chart2D');
				dojo.require('dojox.charting.action2d.Tooltip');
				dojo.require('dojox.charting.action2d.Magnify');
				dojo.require('dojox.charting.action2d.Highlight');
				dojo.require('ibm.tivoli.mbs.dijit.KpiGauge');
				dojo.require('ibm.tivoli.mbs.dijit.gauge.KpiArcRange');
				dojo.require('dojox.widget.gauge.AnalogArrowIndicator');
				dojo.require('dojox.widget.gauge.AnalogNeedleIndicator');
				dojo.require('dojox.charting.widget.Legend');
			});
		</script>
		<!-- DOJO gauge  end -->
		
		<script type="text/javascript">
			var CLIENT_PATH = "<%=CLIENT_PATH%>";
			
			var kpiGauge = null;
			function drawGaugeChart(refValue, percent, hoverStr) {	
				var dJit = dijit.byId("kpiChart0");
				if(dJit) {
					dJit.destroyRecursive(true);
				}
				kpiGauge = new ibm.tivoli.mbs.dijit.KpiGauge({
					id: "kpiChart0",
					width: 300,
					height: 180, //ie=140;ff=180
					radius: 75,
					cx: 175,
					cy: 160,
					noChange: false,
					hasParent: true,
					url: "",
					ranges: [{low:0, high:100, hover: "转到 KPI", color: '#e5e2e2'}],
					majorTicks: {offset:90, interval: 10,length: 10, color: 'black'},
					indicators: [new ibm.tivoli.mbs.dijit.gauge.KpiArcRange({startvalue: 0,endvalue: 30,   url: "", width:20, offset:75, hover: "差", color: '#a63b3a', hideValue: true}),
								 new ibm.tivoli.mbs.dijit.gauge.KpiArcRange({startvalue: 30,endvalue: 50,  url: "", width:20, offset:75, hover: "较差", color: '#a6653a', hideValue: true}),
								 new ibm.tivoli.mbs.dijit.gauge.KpiArcRange({startvalue: 50,endvalue: 70,  url: "", width:20, offset:75, hover: "一般", color: '#fff34c', hideValue: true}),
								 new ibm.tivoli.mbs.dijit.gauge.KpiArcRange({startvalue: 70,endvalue: 90,  url: "", width:20, offset:75, hover: "良好", color: '#b2ca2f', hideValue: true}),
								 new ibm.tivoli.mbs.dijit.gauge.KpiArcRange({startvalue: 90,endvalue: 100, url: "", width:20, offset:75, hover: "优", color: '#90d500', hideValue: true}),
								 new dojox.widget.gauge.AnalogNeedleIndicator({value: refValue, color: '#575657', width: 6, length: 95, hover: "目标：" + refValue + "%", easing: 'dojo.fx.easing.linear', hideValue: true}),
								 new dojox.widget.gauge.AnalogArrowIndicator({value: percent, color: '#ffa500', width: 6, length: 95, hover: hoverStr, easing: 'dojo.fx.easing.bounceOut', hideValue: true})
					]
				}, 'kpiChart');
			
				kpiGauge.startup();
				kpiGauge.setBackground({color : "white"});
			}
		</script>
	</head>
	<body style="width:100%; height:100%; align:center;">
		 <div id="kpiChart" class="chart"></div>
	</body>
</html>
