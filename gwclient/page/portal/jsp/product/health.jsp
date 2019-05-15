<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../util/header.jsp"%>
<div  class="innerbody">
	<div class="limitcontent">
	
		<!-- health start -->
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/app/product/health.js"></script>
		<div class="contentunit">
			<table class="it" cellSpacing='0' cellPadding='0'>
				<tr>
					<td class="it_h_l"></td>
					<td class="it_h_c"><%=YEAR %>年产品健康状况月统计</td>
					<td class="it_h_r"></td>
				</tr>
				<tr>
					<td class="it_f_l"></td>
					<td class="it_f_c">
						<div id="monthHealthChart"></div>
					</td>
					<td class="it_f_r"></td>
				</tr>
			</table>
		</div>
		<div class="contentunit">
			<div id="ledger" style="width:1010px;"></div>
		</div>		
		<!-- health end -->
		
	</div>
</div>
<%@include file="../util/footer.jsp"%>
