<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../util/header3.0.7.jsp"%>
<div  class="innerbody">
	<div class="limitcontent">
	
		<!-- accomplishment start -->
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/app/task/accomp.js"></script>
		
		<div class="contentunit">
			<table class="it" cellSpacing='0' cellPadding='0'>
				<tr>
					<td>
						<table cellSpacing='0' cellPadding='0'>
							<tr>
								<td class="it_h_l"></td>
								<td class="it_h_c" id="chartTitle">年度外场任务完成情况</td>
								<td class="it_h_r"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="it_f" style="padding-top:10px;">
						<!-- inner基本数据 start -->		
						<div id="yearChart" style="width:1010px;"></div>		
						<!-- inner基本数据 end -->
					</td>
				</tr>
			</table>
		</div>
		
		<div class="contentunit">
			<div id="taskList" style="width:1010px;"></div>	
		</div>
		<!-- accomplishment end -->
		
	</div>
</div>
<%@include file="../util/footer.jsp"%>
