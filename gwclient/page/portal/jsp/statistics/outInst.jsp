<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../util/header3.0.7.jsp"%>
<div  class="innerbody">
	<div class="limitcontent">
	
		<!-- statistics start -->
		<link rel="stylesheet" href="<%=CLIENT_PATH%>/portal/js/jqselect/css/jqselect.css" type="text/css" />
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/jqselect/jqselect.js"></script>
		
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/app/statistics/outInst.js"></script>		

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
								<td width="10%" class="tdr" rowspan="2">年度对比：</td>
								<td width="10%" class="tdl" rowspan="2"><input type="checkbox" id="isCompYear" /></td>
								<td width="17%" class="tdr compTop">出所年份：</td>
								<td width="17%" class="tdl compTop">
									<select name="outdate" id="outdate" style="width:100px;">
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
								<td width="12%"></td>
							</tr>
							<tr>
								<td class="tdr compBtm">起始年份：</td>
								<td class="tdl compBtm">
									<select name="outdateFrom" id="outdateFrom" style="width:100px;">
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
									<select name="outdateTo" id="outdateTo" style="width:100px;">
							    		<%
							    			for(int i = 0; i < 5; i++) {
							    		%>
							    			<option value="<%=(YEAR-i) %>"><%=(YEAR-i) %></option>
							    		<%		
							    			}
							    		%>
									</select>
								</td>
								<td></td>
							</tr>
							<tr>
								<td class="tdr">产品领域：</td>
								<td class="tdl">
									<select name="scopes" id="scopes" style="width:100px;">
										<option value="">全部</option>
										<option value="">舰载</option>
										<option value="">机载</option>
										<option value="">情报</option>
										<option value="">陆装</option>
									</select>
								</td>
								<td class="tdr">产品型号：</td>
								<td class="tdl">
									<input type="text" id="model" name="model" />
								</td>
								<td class="tdr">客户：</td>
								<td class="tdl">
									<input type="text" id="custom" name="custom" />
								</td>
								<td></td>
							</tr>
							<tr>
								<td colspan="7" class="tdr">
									<input type="button" value="查询" onclick="loadOutInst()">
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
</div>
<%@include file="../util/footer.jsp"%>

