<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../util/header3.0.7.jsp"%>
<div  class="innerbody">
	<div class="limitcontent">
	
		<!-- statistics start -->
		<link rel="stylesheet" href="<%=CLIENT_PATH%>/portal/js/jqueryui/css/jquery.ui.all.css" type="text/css" />
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/jqueryui/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/jqueryui/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/jqueryui/ui/jquery.ui.tabs.js"></script>
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/jqueryui/ui/jquery.ui.datepicker.js"></script>
		
		<link rel="stylesheet" href="<%=CLIENT_PATH%>/portal/js/jqselect/css/jqselect.css" type="text/css" />
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/jqselect/jqselect.js"></script>
		
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/app/statistics/statistic.js"></script>		
		
		<div id="tabs">
			<ul>
				<li><a href="#tabs-1">总任务情况统计</a></li>
				<li><a href="#tabs-2">出所台帐情况统计</a></li>
				<li><a href="#tabs-3">交装台帐情况统计</a></li>
				<li><a href="#tabs-4">完成任务情况统计</a></li>
				<li><a href="#tabs-5">未完成任务情况统计</a></li>
			</ul>
			<div id="tabs-1">
				<div class="contentunit">
					<table class="it" cellSpacing='0' cellPadding='0'>
						<tr>
							<td>
								<table cellSpacing='0' cellPadding='0'>
									<tr>
										<td class="it_h_l"></td>
										<td class="it_h_c" id="chartTitle">任务统计</td>
										<td class="it_h_r"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="it_f" style="padding-top:10px;">
								<div id="taskChart" style="width:1010px;"></div>		
							</td>
						</tr>
					</table>
				</div>
				<div class="contentunit">
					<div id="taskList" style="width:1010px;"></div>
				</div>
			</div>
			<div id="tabs-2">
				<!-- 出所台帐 start -->
				<div class="contentunit">
					<table class="it" cellSpacing='0' cellPadding='0'>
						<tr>
							<td>
								<table cellSpacing='0' cellPadding='0'>
									<tr>
										<td class="it_h_l"></td>
										<td class="it_h_c" id="chartTitle">查询条件</td>
										<td class="it_h_r"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="it_f" style="padding-top:10px; overflow: visible;">
								<table class="content_t" align="center">
									<tr>
										<td width="16%" class="tdr" rowspan="2">年度对比：</td>
										<td width="16%" class="tdl" rowspan="2"><input type="checkbox" id="isCompYear" /></td>
										<td width="17%" class="tdr compTop">出所年份：</td>
										<td width="17%" class="tdl compTop">
											<select name="outdate" id="outdate" style="width:80px;">
									    		<%
									    			for(int i = 0; i < 10; i++) {
									    		%>
									    			<option value="<%=(YEAR-i) %>"><%=(YEAR-i) %></option>
									    		<%		
									    			}
									    		%>
											</select>
										</td>
										<td width="17%" class="tdr compTop">与同期交装对比：</td>
										<td width="17%" class="tdl compTop"><input type="checkbox"  id="isCompDelivery" /></td>
									</tr>
									<tr>
										<td class="tdr compBtm">起始年份：</td>
										<td class="tdl compBtm">
											<select name="outdateFrom" id="outdateFrom" style="width:80px;">
									    		<%
									    			for(int i = 4; i >= 0; i--) {
									    		%>
									    			<option value="<%=(YEAR-i) %>"><%=(YEAR-i) %></option>
									    		<%		
									    			}
									    		%>
											</select>
										<td class="tdr compBtm">结束年份：</td>
										<td class="tdl compBtm">
											<select name="outdateTo" id="outdateTo" style="width:80px;">
									    		<%
									    			for(int i = 0; i < 5; i++) {
									    		%>
									    			<option value="<%=(YEAR-i) %>"><%=(YEAR-i) %></option>
									    		<%		
									    			}
									    		%>
											</select>
										</td>
									</tr>
								</table>	
							</td>
						</tr>
					</table>
				</div>
				
				<div class="contentunit">
					<table class="it" cellSpacing='0' cellPadding='0'>
						<tr>
							<td>
								<table cellSpacing='0' cellPadding='0'>
									<tr>
										<td class="it_h_l"></td>
										<td class="it_h_c" id="chartTitle">数量统计</td>
										<td class="it_h_r"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="it_f" style="padding-top:10px;">
								<div id="outInstChart" style="width:1010px;"></div>		
							</td>
						</tr>
					</table>
				</div>
				<div class="contentunit">
					<div id="outInstList" style="width:1010px;"></div>
				</div>
				
				<!-- 出所台帐 end -->
			</div>
			<div id="tabs-3">
			</div>
			<div id="tabs-4">
			</div>
			<div id="tabs-5">
			</div>
		</div>
		<!-- statistic end -->
		
	</div>
</div>
<%@include file="../util/footer.jsp"%>

