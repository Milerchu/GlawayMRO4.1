<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*,psdi.mbo.*,psdi.server.*,psdi.util.*,org.w3c.dom.*,psdi.mbo.*,psdi.util.*,psdi.webclient.system.controller.*,psdi.webclient.system.beans.*,psdi.webclient.system.runtime.*,psdi.webclient.servlet.*,psdi.webclient.system.session.*,psdi.webclient.controls.*,psdi.webclient.components.*,java.util.*,java.io.*"
	errorPage=""%>
<%@page import="java.text.NumberFormat"%>
<%@page import="com.ibm.json.java.*"%>
<%@page import="java.text.DecimalFormat"%>
<%@include file="../util/CONSTANT.jsp"%>
<%!
	int getTotalCurrTaskCount(Statement st) throws SQLException {
		String sql = "select count(fieldtasknum) as count "
	   		+ "from fieldtask "
			+ "where status in (" + CURR_TASK_STATUS + ") and parentnum is null"; 
		ResultSet rs = st.executeQuery(sql);
		return getCountFromResult(rs);
	}

	/* 查询当前外场任务统计图 */
	StringBuffer createTaskChartData(Statement st) throws MXException, java.rmi.RemoteException, SQLException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		jsonStrTmp.append("{");

		int total = getTotalCurrTaskCount(st);
		StringBuffer categories = new StringBuffer();
		StringBuffer counts = new StringBuffer();
		StringBuffer percents = new StringBuffer();
		
		categories.append("[");
		counts.append("[");
		percents.append("[");
		
		String sql = "select aln.value as tasktype, t.count "
					+ "from ALNDOMAIN aln "  
					+ "left join " 
					+ "( "
						+ "select tasktype, count(fieldtasknum) as count " 
						+ "from fieldtask " 
						+ "where status in (" + CURR_TASK_STATUS + ") and parentnum is null group by tasktype " 
					+ ") t " 
					+ "on aln.value=t.tasktype "
					+ "where domainid='TASKTYPE' ";
		
		ResultSet rs = st.executeQuery(sql);		
		boolean isCommNeed = false;
		while(rs.next()) {
			String tasktype = rs.getString("tasktype");
			int count = rs.getInt("count");
			String percentStr = getPercent(total, count);
			
			if(isCommNeed) {
				categories.append(", \"" + tasktype + "\"");
				counts.append(", " + count);
				percents.append(", " + percentStr);
				
			} else {
				categories.append("\"" + tasktype + "\"");
				counts.append(count);
				percents.append(percentStr);
				
				isCommNeed = true;
			}
		}
		
		categories.append("]");
		counts.append("]");
		percents.append("]");
		
		jsonStrTmp.append("\"categories\": " + categories);
		jsonStrTmp.append(", \"total\": " + total);
		jsonStrTmp.append(", \"counts\": " + counts);
		jsonStrTmp.append(", \"percents\": " + percents);
		
		jsonStrTmp.append("}");
		
		rs.close();	
		
		return jsonStrTmp;
	}
	
	StringBuffer mboToJsonString() throws MXException,
				java.rmi.RemoteException, SQLException {
		/* 获取质保期内、外资产的外场任务数量 */
		psdi.security.ConnectionKey ck = MXServer.getMXServer().getDBManager().getSystemConnectionKey();
		Connection connection = MXServer.getMXServer().getDBManager().getConnection(ck);
		Statement st = connection.createStatement();
		
		StringBuffer jsonStrTmp = createTaskChartData(st); //json 字符串缓存对象
		
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

	/* 输出符合条件的结果集 */
	pw.write(mboToJsonString().toString());   
%>
