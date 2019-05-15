<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../util/header3.0.7.jsp"%>
<div  class="innerbody">
	<div class="limitcontent">
	
		<!-- current start -->
		<link rel="stylesheet" href="<%=CLIENT_PATH%>/portal/js/jqueryui/css/jquery.ui.all.css" type="text/css" />
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/jqueryui/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/jqueryui/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/jqueryui/ui/jquery.ui.tabs.js"></script>
	
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/app/task/current.js"></script>		
		
		<div class="contentunit">
			<table class="it" cellSpacing='0' cellPadding='0'>
				<tr>
					<td>
						<table cellSpacing='0' cellPadding='0'>
							<tr>
								<td class="it_h_l"></td>
								<td class="it_h_c" id="chartTitle">当前任务统计</td>
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
		<!-- current end -->
		
	</div>
</div>
<%@include file="../util/footer.jsp"%>