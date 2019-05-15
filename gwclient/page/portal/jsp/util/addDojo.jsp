<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>		
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