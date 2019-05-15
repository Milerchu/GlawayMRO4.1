<%@ page language="java" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String CLIENT_PATH = basePath + "webclient";
%>
<!DOCTYPE html>

<html>
	<head>
		<title>GIS</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta name="viewport"
			content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0">
		<meta name="apple-mobile-web-app-capable" content="yes">

		<link type="text/css"
			href="<%=CLIENT_PATH%>/portal/js/OpenLayers-2.13/theme/default/style.css"
			rel="stylesheet">
		<script type="text/javascript"
			src="<%=CLIENT_PATH%>/portal/js/OpenLayers-2.13/OpenLayers.js"></script>

		<script type="text/javascript"
			src="<%=CLIENT_PATH%>/javascript/plugins/jquery-1.7.2.js"></script>
		
		<!-- easyui layout -->	
		<link rel="stylesheet" type="text/css" href="<%=CLIENT_PATH%>/portal/js/easyui1.2.5/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css" href="<%=CLIENT_PATH%>/portal/js/easyui1.2.5/themes/icon.css">
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/easyui1.2.5/easyui1.2.2.js"></script>
		
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/app/map/mapTools.js"></script>
		<link type="text/css" href="<%=CLIENT_PATH%>/portal/css/map.css" rel="stylesheet">
			

		<script type="text/javascript">
   		var map; //地图对象
   		var markers;//标记集合对象
   		var markerIcon;//标记所用的图标
   		
   		$(function() {
   			initMap(); //初始化地图
			loadMapData(); //加载基础地图数据
			
   		});
   		
   		/* 初始化地图 */
   		function initMap() {
   			var mapOptions = {
				//maxExtent: new OpenLayers.Bounds(-4580.87639085316, -4019.90799757006, 3259.19151950525, 3434.14638239719),
				maxExtent: new OpenLayers.Bounds(73.5615263, 17.9583333, 134.7659116, 53.5313244), 
				maxResolution: 0.140285504373138, //0.0780713760932845
				projection: "EPSG:4326",
				numZoomLevels: 7
			};
			
			map = new OpenLayers.Map( 'map', mapOptions );
			//基础图层
			/*var baseLayer = new OpenLayers.Layer.ArcGIS93Rest("ArcGIS Server Layer",
								"http://192.168.111.4/ArcGIS/rest/services/jsmap-yx/MapServer/export", 
		              				{layers: "show:0"});*/
			var baseLayer = new OpenLayers.Layer.ArcGIS93Rest("ArcGIS Server Layer", "http://192.168.111.4/ArcGIS/rest/services/jsmap-sl/MapServer/export", 
		              				{layers: "show:0,1,2,3,4,5,6"});
			//省市区域图层
			/*var dataLayer = new OpenLayers.Layer.ArcGIS93Rest( "ArcGIS Server Layer", "http://192.168.111.4/ArcGIS/rest/services/jsmap-yxzj/MapServer/export", 
                				{layers: "show:1", isBaseLayer:false, TRANSPARENT:true});*/
            
            map.addLayer(baseLayer);
			//map.addLayer(dataLayer);
			
			map.setCenter(new OpenLayers.LonLat(102.642187, 34.546813), 1); //108.463228, 35.141248
			
			//标记集合
			markers = new OpenLayers.Layer.Markers("Markers");
            map.addLayer(markers);
            
            // 标记的图标
            var size = new OpenLayers.Size(25, 25);
            var offset = new OpenLayers.Pixel(-(size.w/2), -size.h);
            
            //markerIcon = new OpenLayers.Icon('<%=CLIENT_PATH%>/portal/css/images/marker.png', size, offset);
            markerIcon = new OpenLayers.Icon('<%=CLIENT_PATH%>/portal/js/OpenLayers-2.13/img/marker-blue.png', size, offset);
   		}
   		
   		/* 加载基础地图数据 */
   		function loadMapData() {
   			clearMarkers(); //清除所有标记  
   			var lev = $("#mapLev").val();
			var eqNo = $("#eqNo").val();
			var eqType = $("#eqType").val();
			var disCont = $("#disCont").val();
			var temp = 0;
			
			if('1' == lev) {
	            temp = 1;
	        } 
	        if('2' == lev) {
	            temp = 3;
	        } 
	        if('3' == lev) {
	            temp = 5;
	        }
	        
			//var url = "http://192.168.111.10:7001/maximo/gis?gistype=" + disCont + "&gislevel=" + lev + "&gismodel=" + eqNo + "&eqType=" + eqType;
			var url = "http://192.168.111.52:7002/glawaymro/gis?gistype=" + disCont + "&gislevel=" + lev + "&gismodel=" + eqNo + "&eqType=" + eqType;
			$.ajax({
				type: "post",
				url: url,
				dataType: "jsonp",
				jsonp: "callback",
				success: function(json) {
					if(json.length>0){
		            	var lo=json[0].longitude-1+1;
		            	var la=json[0].latitude-1+1; 
		            	//map.setCenter(new OpenLayers.LonLat(lo, la), temp);
		            	if(temp == 1) {
		            		map.setCenter(new OpenLayers.LonLat(102.642187, 34.546813), temp);
		            	} else {
		            		map.setCenter(new OpenLayers.LonLat(lo, la), temp);
		            	}
		            }else{
		            	map.setCenter(new OpenLayers.LonLat(102.642187, 34.546813), 1);
		            }
		            
					for(var i = 0; i < json.length; i++) {
						markData(i, json[i].longitude, json[i].latitude, json[i].name, json[i].remark);
					}
					//markData(109.307812, 33.72434, "兰州军区", "当前位置共有2个正在进行的外出任务，有6人在执行这些任务。");
				},
				error:function(json) {
					map.setCenter(new OpenLayers.LonLat(102.642187, 34.546813), 1);
					//alert('false');
					//markData(109.307812, 33.72434, "兰州军区", "当前位置共有2个正在进行的外出任务，有6人在执行这些任务。");
				}
			});
   		}
    </script>
	</head>

	<body class="easyui-layout">
		<div region="north" border="false" split="false" style="height:61px; border: 0 none; overflow: hidden;">
			<div id="search" class="m_in_filter">
				<table>
					<tr>
						<td class="m_label">
							地图显示级别：
						</td>
						<td class="m_input">
							<select id="mapLev">
								<option value="1">
									军区
								</option>
								<option value="2">
									军分区
								</option>
								<option value="3">
									站点
								</option>
							</select>
						</td>
						<td class="m_label">
							类别：
						</td>
						<td class="m_input" colspan="2">
							<select id="eqType">
								<option value="1">
									机载雷达
								</option>
								<option value="2">
									陆装雷达
								</option>
								<option value="2">
									舰载雷达
								</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="m_label">
							型号：
						</td>
						<td class="m_input">
							<input name="eqNo" id="eqNo" type="text" />
						</td>
						<td class="m_label">
							显示内容：
						</td>
						<td class="m_input">
							<select id="disCont">
								<option value="1">
									人员信息
								</option>
								<option value="2">
									外场任务
								</option>
								<option value="3">
									装备信息
								</option>
							</select>
						</td>
						<td class="m_input">
							<input type="button" class="m_search" style="width:80px;" value="查询" onclick="loadMapData()" onfocus="blur()" />
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div region="center" border="false" style="border: 0 none; overflow:hidden;">
			<div id="map" class="m_map" style="border: 0 none;"></div>
		</div>
		
		<!-- tip start -->
		<div class="tip_x" id="tip_x">
			<table class="content">
				<tr class="tr_t">
					<td class="tr_t_c">
						<table>
	                    	<tr>
	                        	<td class="tr_t_tdl"></td>
	                            <td class="tr_t_tdc" style="width:257px;">
	                            	<div id="tipTitle"></div>
	                            </td>
	                            <td class="tr_t_tdb">
	                            	<!--  
	                            	<a href="javascript:void(0)" class="tip_b_max" onclick="maxTip()" onfocus="this.blur();"><img src="<%=CLIENT_PATH%>/portal/css/img/tip_b_max.png" /></a>
	                            	<a href="javascript:void(0)" class="tip_b_close" onclick="closeTip()" onfocus="this.blur();"><img src="<%=CLIENT_PATH%>/portal/css/img/tip_b_close.png" /></a>
	                            	-->
	                            </td>
	                            <td class="tr_t_tdr"></td>
	                        </tr>
	                    </table>
					</td>
				</tr>
	            <tr class="tr_c">
	            	<td class="tr_c_c">
	                	<div id="tipContent"></div>
	                </td>
	            </tr>
				<tr class="tr_b">
					<td class="tr_b_c">
						<table>
	                    	<tr>
	                        	<td class="tr_b_tdl"></td>
	                            <td class="tr_b_tdc"></td>
	                            <td class="tr_b_tdr"></td>
	                        </tr>
	                    </table>
					</td>
				</tr>
			</table>
	        <div class="pointer_w" id="tipPointer"></div>
		</div>
		<!-- tip end -->
	</body>

</html>
