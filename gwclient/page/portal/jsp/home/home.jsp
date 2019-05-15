<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../util/header3.0.7.jsp"%>
<div  class="innerbody">
	<div class="limitcontent">
	
		<!-- home start -->
		<link rel='stylesheet' type='text/css' href='<%=CLIENT_PATH%>/portal/css/home.css'/>
		<script src="<%=CLIENT_PATH%>/portal/js/app/home.js"></script>
		<script type="text/javascript">
			var CLIENT_PATH = "<%=CLIENT_PATH%>";
			$(function() {
				showCharts(); //初始化图标信息展示
				
				/* 
				   设置缺省顶部导航选择 
				   HOME_NAV：主页；PROD_NAV：产品状态；SUPPTASK_NAV：保障任务；
				   RESO_NAV：保障资源；STAT_NAV：统计查询；ABOUT_NAV：关于我们
				 */
				setDefaultNavSelected("HOME_NAV"); 
			});
		</script>
		
		<table class="h_contentTb" align="center">
			<tr>
				<!-- 左侧图表展示 start -->
				<td class="h_contentTdLeft">					
					<div class="sub_chart">
		                <div class="chart_title">
		                    <table>
		                    	<tr>
		                    		<td class="leader"></td>
		                    		<td class="title">产品状态</td>
		                    		<td class="more"><a href="<%=CLIENT_PATH%>/portal/jsp/product/ledger.jsp" target="_self">更多>></a></td>
		                    	</tr>
		                    </table>
		                </div>
		                <div id="kpiChartContent" class="chart_content">
		                    <iframe id='gaugeFrame' name="gaugeFrame" class="gaugeFrame" frameborder="0" src="<%=CLIENT_PATH%>/portal/jsp/home/gaugeChart.jsp"></iframe>
		                </div> 
		            </div>
					<div class="sub_chart">
		                <div class="chart_title">
		                	<table>
		                    	<tr>
		                    		<td class="leader"></td>
		                    		<td class="title">健康状况</td>
		                    		<td class="more"><a href="<%=CLIENT_PATH%>/portal/jsp/product/ledger.jsp" target="_self">更多>></a></td>
		                    	</tr>
		                    </table>
		                </div>
		                <div class="chart_content">
		                    <div id="monthHealthChart" class="chart"></div>
		                </div> 
		            </div>
				</td>
				<!-- 左侧图表展示 end -->
				
				<!-- 中心地图展示 start -->
				<td class="h_contentTdCenter">
					<!-- 中心地图 start -->
	        		<div class="contDiv_center" id="contDiv_center">
						<iframe id="innerFrame" class="innerFrame" frameborder="0" src="<%=CLIENT_PATH%>/portal/jsp/map/frameMap.jsp"></iframe>
			    	</div>
					<!-- 中心地图 end -->	
				</td>
				<!-- 中心地图展示 end -->
				
				<!-- 右侧列表信息展示 start -->
				<td class="h_contentTdRight">
		            <div class="sub_chart">
		                <div class="chart_title">
		                	<table>
		                    	<tr>
		                    		<td class="leader"></td>
		                    		<td class="title">保障任务</td>
		                    		<td class="more"><a href="<%=CLIENT_PATH%>/portal/jsp/task/current.jsp" target="_self">更多>></a></td>
		                    	</tr>
		                    </table>
		                </div>
		                <div class="chart_content">
		                    <div id="taskChart" class="chart"></div>
		                </div> 
		            </div>
		            <div class="sub_chart">
		                <div class="chart_title">
		                	<table>
		                    	<tr>
		                    		<td class="leader"></td>
		                    		<td class="title">当前任务</td>
		                    		<td class="more"><a href="<%=CLIENT_PATH%>/portal/jsp/task/plan.jsp" target="_self">更多>></a></td>
		                    	</tr>
		                    </table>
		                </div>
		                <div class="chart_content">
		                    <ul id="currTask">
		                    </ul>
		                </div>
		            </div>
				</td>
				<!-- 右侧列表信息展示 end -->
			</tr>
		</table>
		<!-- home end -->
		
	</div>
</div>
<%@include file="../util/footer.jsp"%>
		
