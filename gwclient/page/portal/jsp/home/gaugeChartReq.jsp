<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*,psdi.mbo.*,psdi.server.*,psdi.util.*,org.w3c.dom.*,psdi.mbo.*,psdi.util.*,psdi.webclient.system.controller.*,psdi.webclient.system.beans.*,psdi.webclient.system.runtime.*,psdi.webclient.servlet.*,psdi.webclient.system.session.*,psdi.webclient.controls.*,psdi.webclient.components.*,java.util.*,java.io.*"
	errorPage=""%>
<%@page import="java.text.NumberFormat"%>
<%@page import="com.ibm.json.java.*"%>
<%@page import="java.text.DecimalFormat"%>
<%@include file="../util/CONSTANT.jsp"%>
<%!
	/**
	 * 将MBO数据转换为JSON字符串
	 * 可指定prefix值，以增加属性标识的前缀
	 * 表属性名称为指定属性列表中所提供的属性名称，且均为字母小写
	*/
	StringBuffer mboToJsonString() throws MXException, java.rmi.RemoteException, SQLException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		
		psdi.security.ConnectionKey ck = MXServer.getMXServer().getDBManager().getSystemConnectionKey();
		Connection connection = MXServer.getMXServer().getDBManager().getConnection(ck);
		Statement st = connection.createStatement();

		jsonStrTmp.append("{");
		jsonStrTmp.append("\"totalHealLedger\": " + getHealPerc(st, -1));
		jsonStrTmp.append("}");

		st.close();
		MXServer.getMXServer().getDBManager().freeConnection(ck);	
		
		return jsonStrTmp;
	}

	String getHealData(Statement st, HealStr heal, String condStr) throws SQLException {
		//已有实时台帐总数 
		String sql = "select count(*) as count "
					+ "from asset "
					+ "where assetlevel='ASSET' "
					+ condStr; 
		ResultSet rs = st.executeQuery(sql);
		heal.total = getCountFromResult(rs);
		
		sql = "select count(*) as count "
			+ "from asset "
			+ "where assetlevel='ASSET' and status in (" + RUN_LEDGER_STATUS + ") "
			+ condStr;  
		rs = st.executeQuery(sql);
		heal.health = getCountFromResult(rs);
		
		return getPercent(heal.total, heal.health);
	}

	/* 查询当前保证期内的产品台帐状态 */
	StringBuffer getHealPerc(Statement st, int type) throws SQLException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		jsonStrTmp.append("{");
		
		String condStr = "";//默认全部
		if(type == 1) { //indate
			condStr = " and qaperiod='保内' ";
		} else if (type == 0) { //outdate
			condStr = " and qaperiod='保外' ";
		}
		HealStr heal = new HealStr();
		String percent = getHealData(st, heal, condStr);

		jsonStrTmp.append("\"total\":" + heal.total);
		jsonStrTmp.append(", \"health\":" + heal.health);
		jsonStrTmp.append(", \"percent\":" + percent);
		
		jsonStrTmp.append("}");		
		return jsonStrTmp;
	}
	
	/* 台帐历史健康状况信息统计结构 */
	class HealStr {
		int total = 0;
		int health = 0;
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
	pw.write(mboToJsonString().toString());
%>
