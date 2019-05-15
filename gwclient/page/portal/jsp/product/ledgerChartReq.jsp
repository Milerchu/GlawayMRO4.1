<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*,psdi.mbo.*,psdi.server.*,psdi.util.*,org.w3c.dom.*,psdi.mbo.*,psdi.util.*,psdi.webclient.system.controller.*,psdi.webclient.system.beans.*,psdi.webclient.system.runtime.*,psdi.webclient.servlet.*,psdi.webclient.system.session.*,psdi.webclient.controls.*,psdi.webclient.components.*,java.util.*,java.io.*"
	errorPage=""%>
<%@page import="java.text.NumberFormat"%>
<%@page import="com.ibm.json.java.*"%>
<%@page import="java.text.DecimalFormat"%>
<%!	
	/**
	 * 将MBOSET中的MBO数据转换为JSON字符串
	 * 表属性名称为指定属性列表中所提供的属性名称，且均为字母小写
	*/
	StringBuffer getFtStaJsonStr(MboRemote asset) throws MXException, java.rmi.RemoteException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		if(asset == null) {
			return jsonStrTmp;
		}
		MboSetRemote fieldtasks;
		
		java.util.Calendar c = java.util.Calendar.getInstance(java.util.TimeZone.getTimeZone("GMT+08:00"));
		int year = c.get(java.util.Calendar.YEAR);
		//String sql = " CREATEDATE between to_date('" + year + "-01-01 00:00:00', 'yyyy-mm-dd hh24:mi:ss') and to_date('" + year + "-12-31 23:59:59', 'yyyy-mm-dd hh24:mi:ss') ";
		
		//int total = asset.getMboSet("FIELDTASK", "FIELDTASK").count();
		
		MboSetRemote taskTypes = MXServer.getMXServer().getMboSet("FIELDTASK", MXServer.getMXServer().getUserInfo("MAXADMIN")).getList("TASKTYPE");
		for(int i = 0, COUNT = taskTypes.count(); i < COUNT; i++) {
			MboRemote m = taskTypes.getMbo(i);
			String tasktype = taskTypes.getMbo(i).getString("VALUE");
			
			//fieldtasks = asset.getMboSet("FIELDTASK", "FIELDTASK", "TASKTYPE='" + tasktype + "' and " + sql);
			fieldtasks = asset.getMboSet("FIELDTASK", "FIELDTASK", "TASKTYPE='" + tasktype + "'");
			int countTmp = (fieldtasks==null? 0: fieldtasks.count());
			if(i == 0) {
				jsonStrTmp.append("[\"" + tasktype + "\", " + countTmp + "]"); 
			} else {
				jsonStrTmp.append(", [\"" + tasktype + "\", " + countTmp + "]"); 
			}
		}
		return jsonStrTmp;
	}
	
	/* 获取百分比 */
	String getPercent(int total, int health) {
		String percent = "";
		if(total != 0) {
			percent = new DecimalFormat("0.00").format(health * 1.0 / total * 100.0);
		} else {
			percent = "0";
		}
		return percent;
	}
	
%>
<%
	//设置回响为json格式数据输出
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.setHeader("Cache-Control", "no-cache");
	java.io.PrintWriter pw = response.getWriter();
	
	String assetnum = request.getParameter("assetnum"); //获取前端参数：资产编号assetnum
	String siteid = request.getParameter("siteid");    //获取前端参数：资产地点siteid
	
	/* 查询结果集 */
	MboSetRemote msr = MXServer.getMXServer().getMboSet("ASSET", MXServer.getMXServer().getUserInfo("MAXADMIN"));	
	String sqlWhere = "ASSETNUM='" + assetnum + "' AND SITEID='" + siteid + "' "; //ASSET系统级，SYSTEM分系统级别
	//sqlWhere = " and STATUS='活动'";
	msr.setWhere(sqlWhere);
	msr.reset();

	/* 输出符合条件的结果集 */
	pw.write("[");
	if(!(msr.isEmpty())) {
		MboRemote mbo = msr.getMbo(0);
		pw.write(getFtStaJsonStr(mbo).toString());
	}
	pw.write("]");   
%>
