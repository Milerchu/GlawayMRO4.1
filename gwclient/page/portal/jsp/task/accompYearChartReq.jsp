<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*,psdi.mbo.*,psdi.server.*,psdi.util.*,org.w3c.dom.*,psdi.mbo.*,psdi.util.*,psdi.webclient.system.controller.*,psdi.webclient.system.beans.*,psdi.webclient.system.runtime.*,psdi.webclient.servlet.*,psdi.webclient.system.session.*,psdi.webclient.controls.*,psdi.webclient.components.*,java.util.*,java.io.*"
	errorPage=""%>
<%@page import="java.text.NumberFormat"%>
<%@page import="com.ibm.json.java.*"%>
<%@page import="java.text.DecimalFormat"%>
<%!
	/* 外场任务历史完成情况信息统计结构 */
	class CommStr {
		int total = 0;
		int comm = 0;
	}
	
	/** 
	 * 获取某年前未外场任务数、年内任务总数
	 * 获取年内完成任务总数
	*/
	StringBuffer mboToJsonString(int year)  
					throws MXException, java.rmi.RemoteException, SQLException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		psdi.security.ConnectionKey ck = MXServer.getMXServer().getDBManager().getSystemConnectionKey();
		Connection connection = MXServer.getMXServer().getDBManager().getConnection(ck);
		Statement st = connection.createStatement();
	
		String[] monthStr = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		int LENGTH = monthStr.length;
		StringBuffer categories = new StringBuffer();
		StringBuffer totals = new StringBuffer();
		StringBuffer comms = new StringBuffer();
		categories.append("[");
		totals.append("[");
		comms.append("[");
		
		CommStr comm = new CommStr();
		boolean isCommNeed = false;
		for(int i = 0; i < LENGTH; i++) {
			getCommPerMonth(st, comm, year, monthStr[i]);
			
			if(isCommNeed) {
				categories.append(", \"" + monthStr[i] + "\"");
				totals.append(", " + comm.total);
				comms.append(", " + comm.comm );
				
			} else {
				categories.append("\"" + monthStr[i] + "\"");
				totals.append(comm.total);
				comms.append(comm.comm);
				
				isCommNeed = true;
			}
		}
		
		categories.append("]");
		totals.append("]");
		comms.append("]");
		
		jsonStrTmp.append("{");
		jsonStrTmp.append("\"categories\": " + categories);
		jsonStrTmp.append(", \"totals\": " + totals);
		jsonStrTmp.append(", \"comms\": " + comms);
		jsonStrTmp.append("}");
		
		st.close();
		MXServer.getMXServer().getDBManager().freeConnection(ck);
		return jsonStrTmp;
	}
	
	/** 
	 * 获取某年某月前未完成外场任务数、某年某月内任务总数
	 * 并获取某年某月内完成任务总数
	*/
	void getCommPerMonth(Statement st, CommStr comm, int year, String month) 
			throws MXException, java.rmi.RemoteException, SQLException {
		// 获取某年某月前未完成外场任务数、某年某月内任务总数 
		String sql = "select count(*) as count " 
			+ "from fieldtask ft " 
			+ "where ft.parentnum is null " 
					+ "and to_char(ft.createdate, 'YYYY-MM') <= '" + year + "-" + month + "' " 
					+ "and ft.fieldtaskid not in ( " 
						+ "select fieldtaskid from fieldtask ft " 
						+ "where ft.parentnum is null " 
								+ "and to_char(ft.completetime, 'YYYY-MM') < '" + year + "-" + month + "' "
								+ "and ft.status in ('关闭', '完成') "
					+ ") ";
		ResultSet rs = st.executeQuery(sql);
		comm.total = getCountFromResult(rs);
		
		// 某年某月内完成任务总数
		sql = "select count(*) as count "
			+ "from fieldtask ft "
			+ "where ft.parentnum is null " 
			+ "and to_char(ft.completetime, 'YYYY-MM') = '" + year + "-" + month + "' " 
			+ "and ft.status in ('关闭', '完成') ";
		rs = st.executeQuery(sql);
		comm.comm = getCountFromResult(rs);
	}	
		
	/* 获取结果集中的数量，并将结果集关闭 */
	int getCountFromResult(ResultSet rs) throws SQLException {
		int count = 0;
		while(rs.next()) {
			count = rs.getInt("count");
			break;
		}
		rs.close();
		return count;
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
	
	String yearStr = request.getParameter("year");     //每页多少条 
	int year = -1;
	if(yearStr == null || "".equals(yearStr)) {
		year = -1;
	} else {
		year = Integer.parseInt(yearStr);
	}

	pw.write(mboToJsonString(year).toString());
%>
