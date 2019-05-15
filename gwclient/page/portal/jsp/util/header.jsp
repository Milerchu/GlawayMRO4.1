<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*,psdi.mbo.*,psdi.server.*,psdi.util.*,org.w3c.dom.*,psdi.mbo.*,psdi.util.*,psdi.webclient.system.controller.*,psdi.webclient.system.beans.*,psdi.webclient.system.runtime.*,psdi.webclient.servlet.*,psdi.webclient.system.session.*,psdi.webclient.controls.*,psdi.webclient.components.*,java.util.*,java.io.*"
	errorPage=""%>
<%@page import="java.text.NumberFormat"%>
<%@include file="variables.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String CLIENT_PATH = basePath + "webclient";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>精益保障</title>
		<script>
			var screenWidth = window.screen.width;
			if(screenWidth < 1360) {  
				document.write("<link rel='stylesheet' type='text/css' href='<%=CLIENT_PATH%>/portal/css/style1280.css'/>");
			} else if(screenWidth >= 1360 && screenWidth < 1440){ 
				document.write("<link rel='stylesheet' type='text/css' href='<%=CLIENT_PATH%>/portal/css/style1360.css'/>");
			} else {
				document.write("<link rel='stylesheet' type='text/css' href='<%=CLIENT_PATH%>/portal/css/style1440.css'/>");
			}
		</script>
				
		<script type="text/javascript" src="<%=CLIENT_PATH%>/javascript/plugins/jquery-1.7.2.js"></script>
		
		<!-- 本地工具 -->
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/app/util/util.js"></script>
		
		<script src="<%=CLIENT_PATH%>/portal/js/highcharts/highcharts.js"></script>
		
		<script type='text/javascript' src='<%=CLIENT_PATH%>/portal/js/cover/js/jquery.simplemodal.1.4.4.js'></script>
		
		<!-- 滑动下拉 start -->
		<link rel="stylesheet" type="text/css" href="<%=CLIENT_PATH%>/portal/js/mbMenu/css/menu_black.css" />
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/mbMenu/inc/jquery.metadata.js"></script>
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/mbMenu/inc/jquery.hoverIntent.js"></script>
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/mbMenu/inc/mbMenu.js"></script>
		<!-- 滑动下拉 end -->
		
		<!-- 分页控件 start -->
		<link rel="stylesheet" href="<%=CLIENT_PATH%>/portal/js/pagegrid/css/pagegrid.css" type="text/css" />
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/pagegrid/pagegrid.js"></script>
		<!-- 分页控件 end -->
		
		<script type="text/javascript">
			var CLIENT_PATH = "<%=CLIENT_PATH%>";
			
			$(function() {
				initTabMenu(); //初始化主页tab菜单
			});
			
			/* 初始化主页tab菜单 */
			function initTabMenu() {
				$(".topMenu").buildMenu({
					additionalData:"pippo=1",
					menuWidth:165,
					openOnRight:false,
					menuSelector: ".menuContainer",
					iconPath: CLIENT_PATH + "/portal/css/images/menu/",
					hasImages:true,
					fadeInTime:100,
					fadeOutTime:300,
					adjustLeft:2,
					minZindex:"auto",
					adjustTop:10,
					opacity: 1,
					shadow:false,
					shadowColor:"#ccc",
					hoverIntent:0,
					openOnClick: false,
					closeOnMouseOut: true,
					submenuHoverIntent:200
				});
			}
			
			/* 加载菜单导航页面 */
			function loadPage(url) {
				window.location = CLIENT_PATH + "/portal/jsp/" + url;
			}
			
			/* 
			 * 设置缺省顶部导航选择 
			 * HOME_NAV：主页；PROD_NAV：产品状态；SUPPTASK_NAV：保障任务；
			 * RESO_NAV：保障资源；TASK_NAV：工作任务；ABOUT_NAV：关于我们
			 */
			function setDefaultNavSelected(nav) {
				$("#" + nav).addClass("selected");
			}
		</script>
	</head>
	
	<body>
	<div style="width:100%; text-align: center; padding:0px; margin:0px; border: 0 none;">
		<div class="logo">
			<div class="limitcontent">
				<img src="<%=CLIENT_PATH%>/portal/css/img/header_logo.jpg" border="0"/>
			</div>
		</div>
		<div class="banner">
			<div class="limitcontent">
			</div>
		</div>
		
		<div class="navigator">
			<div class="limitcontent">
				<table class="nav_table" align="center">
					<tr>
						<td class="nav_td" id="HOME_NAV">
							<a href="#" onclick="loadPage('home/home.jsp')" class="hovering">
								<table style="height: 30px;">
									<tr>
										<td>
											首&nbsp;&nbsp;&nbsp;&nbsp;页
										</td>
									</tr>
								</table>
							</a>
						</td>
						<td class="nav_td" id="PROD_NAV">
							<a onFocus="this.blur()" href="javascript:void(0)">
								<table style="height: 30px;" class="topMenu"
									id="productStatus" cellspacing='0' cellpadding='0' border='0'>
									<tr>
										<td class="rootVoice {menu: 'menu_product'}">
											产品状态
										</td>
									</tr>
								</table> </a>
						</td>
						<td class="nav_td" id="SUPPTASK_NAV">
							<a onFocus="this.blur()" href="javascript:void(0)">
								<table style="height: 30px;" class="topMenu"
									cellspacing='0' cellpadding='0' border='0'>
									<tr>
										<td class="rootVoice {menu: 'menu_support_task'}">
											保障任务
										</td>
									</tr>
								</table> </a>
						</td>
						<td class="nav_td" id="RESO_NAV">
							<a onFocus="this.blur()" href="javascript:void(0)">
								<table style="height: 30px;" class="topMenu"
									cellspacing='0' cellpadding='0' border='0'>
									<tr>
										<td class="rootVoice {menu: 'menu_support_src'}">
											保障资源
										</td>
									</tr>
								</table> 
							</a>
						</td>
						<td class="nav_td" id="STAT_NAV">
							<a onFocus="this.blur()" href="javascript:void(0)">
								<table style="height: 30px;" class="topMenu"
									cellspacing='0' cellpadding='0' border='0'>
									<tr>
										<td class="rootVoice {menu: 'menu_stat_src'}">
											统计查询
										</td>
									</tr>
								</table> 
							</a>
						</td>
						<td class="nav_td" id="ABOUT_NAV">
							<a onFocus="this.blur()" href="loadPage('about.jsp');" href="#" class="hovering">
								<table style="height: 30px;">
									<tr>
										<td>
											关于我们
										</td>
									</tr>
								</table>
							</a>
						</td>
					</tr>
				</table>
			</div>
		</div>
		
		<!-- tab menu start -->
		<div id="menu_product" class="mbmenu">
		    <a onFocus="this.blur()" href="javascript:void(0); loadPage('product/deploy.jsp');"  href="#" class="{img: 'fenbu.png'}">分布图</a>
		    <a onFocus="this.blur()" href="javascript:void(0); loadPage('product/ledger.jsp');"  href="#" class="{img: 'taizhang.png'}">产品台帐</a>
		    <a onFocus="this.blur()" href="javascript:void(0); loadPage('product/health.jsp');" href="#" class="{img: 'jiankang.png'}">健康状况</a>
		</div>
		
		<div id="menu_support_task" class="mbmenu">
		    <a onFocus="this.blur()" href="javascript:void(0); loadPage('task/current.jsp');"  href="#"  class="{img: 'dqrenwu.png'}">当前任务</a> <!-- menuvoice title-->
		    <a onFocus="this.blur()" href="javascript:void(0); loadPage('task/plan.jsp');"  href="#"  class="{img: 'rwjihua.png'}">任务计划</a> <!-- menuvoice with href-->
		    <a onFocus="this.blur()" href="javascript:void(0); loadPage('task/accomp.jsp');" href="#"  class="{img: 'rwpinggu.png'}" >完成情况</a> <!-- menuvoice with js action-->
		</div>
		
		
		<div id="menu_support_src" class="mbmenu">
		    <a onFocus="this.blur()" href="javascript:void(0);"  class="{menu:'menu_support_src_sub', img: 'beijian.png'}">备件信息</a> <!-- menuvoice title-->
		    <a onFocus="this.blur()" href="javascript:void(0); loadPage('resource/ssc.jsp');"  href="#" class="{img: 'rlziyuan.png'}" >人力资源</a> <!-- menuvoice with href-->
		    <a onFocus="this.blur()" href="javascript:void(0); loadPage('resource/equipment.jsp');" href="#"  class="{img: 'sbqicai.png'}" >设备器材</a> <!-- menuvoice with js action-->
		</div> 
		
		<div id="menu_support_src_sub" class="mbmenu">
		    <a onFocus="this.blur()" href="javascript:void(0); loadPage('resource/spareInst.jsp');" href="#"  class="{img: 'beijian.png'}">所内备件</a> <!-- menuvoice title-->
		    <a onFocus="this.blur()" href="javascript:void(0); loadPage('resource/spareArmy.jsp');"  href="#" class="{img: 'beijian.png'}" >部队备件</a> <!-- menuvoice with href-->
		</div>

		<div id="menu_stat_src" class="mbmenu">
		    <a onFocus="this.blur()" href="javascript:void(0); loadPage('statistics/outInst.jsp');"  href="#" class="{img: 'tjchaxun.png'}" >出所台帐</a>
		    <a onFocus="this.blur()" href="javascript:void(0); loadPage('statistics/statistics.jsp');"  href="#" class="{img: 'tjchaxun.png'}" >交装台帐</a>
		    <a onFocus="this.blur()" href="javascript:void(0); loadPage('statistics/statistics.jsp');"  href="#" class="{img: 'tjchaxun.png'}" >完成任务</a>
		    <a onFocus="this.blur()" href="javascript:void(0); loadPage('statistics/statistics.jsp');"  href="#" class="{img: 'tjchaxun.png'}" >未完成任务</a>
		</div>
		<!-- tab menu end -->
	