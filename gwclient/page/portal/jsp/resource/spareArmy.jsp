<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../util/header.jsp"%>
<div  class="innerbody">
	<div class="limitcontent">
		
		<!-- spare army start -->
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/app/resource/spareArmy.js"></script>
		
		<div class="contentunit">
			<div id="spareArmy" style="width:1010px;"></div>
		</div>
		<div class="contentunit">
			<table class="it" cellSpacing='0' cellPadding='0'>
				<tr>
					<td>
						<table cellSpacing='0' cellPadding='0'>
							<tr>
								<td class="it_h_l"></td>
								<td class="it_h_c">备件发放统计</td>
								<td class="it_h_r"></td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td class="it_f" style="padding-top:10px;">
						<!-- inner基本数据 start -->	
						<div id="spareArmyChart"></div>		
						<!-- inner基本数据 end -->
					</td>
				</tr>
			</table>
		</div>
		<!-- spare army end -->
		
	</div>
</div>
<%@include file="../util/footer.jsp"%>
