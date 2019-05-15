<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*,psdi.mbo.*,psdi.server.*,psdi.util.*,org.w3c.dom.*,psdi.mbo.*,psdi.util.*,psdi.webclient.system.controller.*,psdi.webclient.system.beans.*,psdi.webclient.system.runtime.*,psdi.webclient.servlet.*,psdi.webclient.system.session.*,psdi.webclient.controls.*,psdi.webclient.components.*,java.util.*,java.io.*"
	errorPage=""%>
<%@page import="java.text.NumberFormat"%>
<%@page import="com.ibm.json.java.*"%>
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
	void getCommPerYear(Statement st, CommStr comm, int year) throws SQLException {
		// 至年前未完成外场任务数 +　年内所以外场任务数 
		String sql = "select count(*) as count "
			+ "from fieldtask ft " 
			+ "where ft.parentnum is null " 
					+ "and ( "
						+ "(ft.status not in ('关闭', '完成') and to_char(ft.createdate, 'YYYY') < '" + year + "' ) or (to_char(ft.createdate, 'YYYY')='" + year + "')"
					+ ") "
			+ "order by ft.fieldtaskid ";
		ResultSet rs = st.executeQuery(sql);
		comm.total = getCountFromResult(rs);
		
		// 至某年某前，且不在该月内的活动资产数
		sql = "select count(*) as count "
			+ "from fieldtask ft "
			+ "where ft.parentnum is null " 
			+ "and to_char(ft.completetime, 'YYYY') = '" + year + "' " 
			+ "and ft.status in ('关闭', '完成') ";
		rs = st.executeQuery(sql);
		comm.comm = getCountFromResult(rs);
	}	

	/**
	 * 获取外场任务外场年度完成数量
	 */
	StringBuffer getCompCountPerYear() throws MXException,
			java.rmi.RemoteException, SQLException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		psdi.security.ConnectionKey ck = MXServer.getMXServer().getDBManager().getSystemConnectionKey();
		Connection connection = MXServer.getMXServer().getDBManager().getConnection(ck);
		Statement st = connection.createStatement();
	
		int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
		int LENGTH = 5;
		int[] years = new int[5];
		for(int i = 1; i <= LENGTH; i++) {
			years[i - 1] = year - (LENGTH - i);
		}
		StringBuffer categories = new StringBuffer();
		StringBuffer totals = new StringBuffer();
		StringBuffer comms = new StringBuffer();
		categories.append("[");
		totals.append("[");
		comms.append("[");
		
		CommStr comm = new CommStr();
		boolean isCommNeed = false;
		for(int i = 0; i < LENGTH; i++) {
			getCommPerYear(st, comm, years[i]);
			
			if(isCommNeed) {
				categories.append(", " + years[i]);
				totals.append(", " + comm.total);
				comms.append(", " + comm.comm );
				
			} else {
				categories.append(years[i]);
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
%>
<%
	//设置回响为json格式数据输出
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.setHeader("Cache-Control", "no-cache");
	java.io.PrintWriter pw = response.getWriter();

	pw.write(getCompCountPerYear().toString());
%>
