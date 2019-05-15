<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../util/header3.0.7.jsp"%>
<div  class="innerbody">
	<div class="limitcontent">
	
		<!-- plan start -->
		<link rel="stylesheet" href="<%=CLIENT_PATH%>/portal/js/jqueryui/css/jquery.ui.all.css" type="text/css" />
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/jqueryui/ui/jquery.ui.core.js"></script>
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/jqueryui/ui/jquery.ui.widget.js"></script>
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/jqueryui/ui/jquery.ui.tabs.js"></script>
	
		<link rel="stylesheet" href="<%=CLIENT_PATH%>/portal/js/ztree3.5/css/zTreeStyle/zTreeStyle.css" type="text/css" />
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/ztree3.5/js/jquery.ztree.core-3.5.js"></script>
		<script type="text/javascript" src="<%=CLIENT_PATH%>/portal/js/app/task/plan.js"></script>
		
		<div id="tabs">
			<ul>
				<li><a href="#tabs-1">列表</a></li>
				<li><a href="#tabs-2">任务计划</a></li>
			</ul>
			<div id="tabs-1">
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
				<div style="width:1010px; margin:0 auto;">
					<div id="taskList" style="width:1010px;"></div>
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
										<td class="it_h_c">外场任务基本信息</td>
										<td class="it_h_r"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="it_f">
								<!-- inner基本数据 start -->									
								<table class="content_t" id="taskBaseData" width="1010" align="center" cellSpacing='0' cellPadding='0'>
									<tr>
										<td width="16%" class="tdr">外场任务编号：</td>
										<td width="16%" class="tdl"><span id="fieldtasknum" /></td>
										<td width="17%" class="tdr">客户：</td>
										<td width="17%" class="tdl"><span id="custname" /></td>
										<td width="17%" class="tdr">策划人：</td>
										<td width="17%" class="tdl"><span id="createby" /></td>
									</tr>
									<tr>
										<td class="tdr">任务内容：</td>
										<td class="tdl"><span id="description" /></td>
										<td class="tdr">出差地点：</td>
										<td class="tdl"><span id="location" /></td>
										<td class="tdr">策划日期：</td>
										<td class="tdl"><span id="createdate" /></td>
									</tr>
									<tr>
										<td class="tdr">责任人：</td>
										<td class="tdl"><span id="header" /></td>
										<td class="tdr">类型：</td>
										<td class="tdl"><span id="tasktype" /></td>
									</tr>
								</table>			
								<!-- inner基本数据 end -->
							</td>
						</tr>
					</table>
				</div>
				<div class="contentunit">
					<div id="subTask" style="width:1010px;"></div>
				</div>
				
				
				<div class="contentunit">
					<table class="it" cellSpacing='0' cellPadding='0'>
						<tr>
							<td>
								<table cellSpacing='0' cellPadding='0'>
									<tr>
										<td class="it_h_l"></td>
										<td class="it_h_c" style="text-align: right; padding:0; width:20px"><img src="<%=CLIENT_PATH %>/skins/tivoli09/images/extend/modimg_spare1.gif" /></td>
										<td class="it_h_c" style="padding-left:5px;">计划备件</td>
										<td class="it_h_r"></td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td class="it_f">
								<!-- inner基本数据 start -->	
								<div id="spareChart"></div>
								<!-- inner基本数据 end -->
							</td>
						</tr>
					</table>
				</div>
				
				<div class="contentunit">
					<table style="width:100%;" cellSpacing='0' cellPadding='0'>
						<tr>
							<td width="50%">
								<table class="it" cellSpacing='0' cellPadding='0'>
									<tr>
										<td>
											<table cellSpacing='0' cellPadding='0'>
												<tr>
													<td class="it_h_l"></td>
													<td class="it_h_c" style="text-align: right; padding:0; width:20px"><img src="<%=CLIENT_PATH %>/portal/css/images/person.png"/></td>
													<td class="it_h_c" style="padding-left:5px;">计划人员</td>
													<td class="it_h_r"></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td class="it_f">
											<!-- inner基本数据 start -->	
											<div id="personChart"></div>
											<!-- inner基本数据 end -->
										</td>
									</tr>
								</table>
							</td>
							<td width="5px" style="width:5px;padding-left:5px;"></td>
							<td width="50%">
								<table class="it" cellSpacing='0' cellPadding='0'>
									<tr>
										<td>
											<table cellSpacing='0' cellPadding='0'>
												<tr>
													<td class="it_h_l"></td>
													<td class="it_h_c" style="text-align: right; padding:0; width:20px"><img src="<%=CLIENT_PATH %>/portal/css/images/equip.png" /></td>
													<td class="it_h_c" style="padding-left:5px;">计划设备</td>
													<td class="it_h_r"></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td class="it_f">
											<!-- inner基本数据 start -->	
											<div id="equipChart"></div>
											<!-- inner基本数据 end -->
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>			
		<!-- plan end -->
		
	</div>
</div>
<%@include file="../util/footer.jsp"%>

