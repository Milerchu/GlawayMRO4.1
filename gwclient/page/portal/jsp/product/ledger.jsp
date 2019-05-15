<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../util/header3.0.7.jsp"%>
<div  class="innerbody">
	<div class="limitcontent">
	
		<!-- ledger start -->
		<link rel="stylesheet" href="<%=CLIENT_PATH%>/portal/js/jqueryui/css/jquery.ui.all.css" type="text/css" />
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/jqueryui/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/jqueryui/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/jqueryui/ui/jquery.ui.tabs.js"></script>
	
		<link rel="stylesheet" href="<%=CLIENT_PATH%>/portal/js/ztree3.5/css/zTreeStyle/zTreeStyle.css" type="text/css" />
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/ztree3.5/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/app/product/ledger.js"></script>
		<link rel="stylesheet" href="<%=CLIENT_PATH%>/portal/css/ledger.css" type="text/css" />
		
		<div id="tabs">
			<ul>
				<li><a href="#tabs-1">列表</a></li>
				<li><a href="#tabs-2">产品台帐</a></li>
			</ul>
			<div id="tabs-1">
				<div class="contentunit">		
					<table width="100%" cellSpacing='0' cellPadding='0'>
						<tr>
							<td>
								<div class="ledger_gauge">
									<table class="it" cellSpacing='0' cellPadding='0'>
										<tr>
											<td>
												<table cellSpacing='0' cellPadding='0'>
													<tr>
														<td class="it_h_l"></td>
														<td class="it_h_c">总健康比例</td>
														<td class="it_h_r"></td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td>
												<table cellSpacing='0' cellPadding='0'>
													<tr>
														<td class="it_f" style="height:185px">
															<iframe id='totalGauge' name="totalGauge" frameborder="0" src="<%=CLIENT_PATH%>/portal/jsp/product/ledgerGauge.jsp?type=-1"></iframe>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</div>
							</td>
							<td>
								<div class="ledger_gauge">
									<table class="it">
										<tr>
											<td>
												<table cellSpacing='0' cellPadding='0'>
													<tr>
														<td class="it_h_l"></td>
														<td class="it_h_c">保内健康比例</td>
														<td class="it_h_r"></td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td>
												<table cellSpacing='0' cellPadding='0'>
													<tr>
														<td class="it_f" style="height:185px">
															<iframe id='totalGauge' name="totalGauge" frameborder="0" src="<%=CLIENT_PATH%>/portal/jsp/product/ledgerGauge.jsp?type=1"></iframe>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</div>
							</td>
							<td>
								<div class="ledger_gauge">
									<table class="it">
										<tr>
											<td>
												<table cellSpacing='0' cellPadding='0'>
													<tr>
														<td class="it_h_l"></td>
														<td class="it_h_c">保外健康比例</td>
														<td class="it_h_r"></td>
													</tr>
												</table>
											</td>
										</tr>
										<tr>
											<td>
												<table cellSpacing='0' cellPadding='0'>
													<tr>
														<td class="it_f" style="height:185px; overflow: hidden;">
															<iframe id='totalGauge' name="totalGauge" frameborder="0" src="<%=CLIENT_PATH%>/portal/jsp/product/ledgerGauge.jsp?type=0"></iframe>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
					</table>
				</div>
				<div class="contentunit">
					<div id="ledger" name="ledger" style="width:1010px;"></div>
				</div>
			</div>
			<div id="tabs-2">
				<div class="contentunit">
					<table class="it" cellSpacing='0' cellPadding='0'>
						<tr>
							<td>
								<table cellSpacing='0' cellPadding='0'>
									<tr>
										<td class="it_h_l"></td>
										<td class="it_h_c">台帐基本信息</td>
										<td class="it_h_r"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="it_f">
								<!-- inner基本数据 start -->
								<table class="content_t" id="ledgerBaseData" align="center">
									<tr>
										<td width="16%" class="tdr">产品代号：</td>
										<td width="16%" class="tdl"><span id="productcode" /></td>
										<td width="17%" class="tdr">出所时间：</td>
										<td width="17%" class="tdl"><span id="outdate" /></td>
										<td width="17%" class="tdr">平台编号：</td>
										<td width="17%" class="tdl"><span id="platformno" /></td>
									</tr>
									<tr>
										<td class="tdr">生产令号：</td>
										<td class="tdl"><span id="productorderno" /></td>
										<td class="tdr">交装时间：</td>
										<td class="tdl"><span id="deliverydate" /></td>
										<td class="tdr">保修到期日：</td>
										<td class="tdl"><span id="qaperiod" /></td>
									</tr>
									<tr>
										<td class="tdr">雷达编号：</td>
										<td class="tdl"><span id="radarnum" /></td>
										<td class="tdr">客户编号：</td>
										<td class="tdl"><span id="custnum" /></td>
										<td class="tdr">型号：</td>
										<td class="tdl"><span id="model" /></td>
									</tr>
									<tr>
										<td class="tdr">装备图号：</td>
										<td class="tdl"><span id="drawno" /></td>
										<td class="tdr">接收单位：</td>
										<td class="tdl"><span id="receive" /></td>
										<td class="tdr">备附件状态：</td>
										<td class="tdl"><span id="attachstatus" /></td>
									</tr>
									<tr>
										<td class="tdr">装备序列号：</td>
										<td class="tdl"><span id="assetnum" /></td>
										<td class="tdr">军兵种：</td>
										<td class="tdl"><span id="custinfo_armytype" /></td>
										<td class="tdr">周转项目：</td>
										<td class="tdl"><span id="itemnum" /></td>
									</tr>
									<tr>
										<td class="tdr">装备名称：</td>
										<td class="tdl"><span id="description" /></td>
										<td class="tdr">所属部队：</td>
										<td class="tdl"><span id="custinfo_whicharmy" /></td>
										<td class="tdr">装备状态：</td>
										<td class="tdl"><span id="status" /></td>
									</tr>
									<tr>
										<!-- <td class="tdr">当前位置：</td> -->
										<td class="tdr">当前站点：</td>
										<td class="tdl"><span id="location" /></td>
										<td colspan="4"></td>
									</tr>
								</table>
								 <!-- inner基本数据 end -->
							</td>
						</tr>
					</table>
				</div>
				
				<div class="contentunit">
					<table style="width:100%">
						<tr>
							<td width="50%" valign="top">
								<table class="it" cellSpacing='0' cellPadding='0'>
									<tr>
										<td>
											<table cellSpacing='0' cellPadding='0'>
												<tr>
													<td class="it_h_l"></td>
													<td class="it_h_c">台帐结构</td>
													<td class="it_h_r"></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td class="it_f">
											<div style="width:100%; height:400px; margin:0 auto; overflow: auto;">
												<ul id="ledgerStrTree" class="ztree"></ul>
											</div>	
										</td>
									</tr>
								</table>
							</td>
							<td width="5px"></td>
							<td width="50%" valign="top">
								<table class="it" cellSpacing='0' cellPadding='0'>
									<tr>
										<td>
											<table cellSpacing='0' cellPadding='0'>
												<tr>
													<td class="it_h_l"></td>
													<td class="it_h_c">保障任务情况</td>
													<td class="it_h_r"></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td class="it_f">
											<div style="width:100%; height:400px; margin:0 auto; overflow: auto;">
												<div id="ledgerChart" ></div>
											</div>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>	
		<!-- ledger end -->
		
	</div>
</div>
<%@include file="../util/footer.jsp"%>


