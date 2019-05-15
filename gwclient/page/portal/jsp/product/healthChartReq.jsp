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
		jsonStrTmp.append("\"healMonth\": " + getHealMonthPerc(st)); //查询1~12月台帐历史健康状态
		jsonStrTmp.append("}");

		st.close();
		MXServer.getMXServer().getDBManager().freeConnection(ck);	
		
		return jsonStrTmp;
	}
		
	/* 台帐历史健康状况信息统计结构 */
	class HealStr {
		int total = 0;
		int health = 0;
	}
	void getHealPerMonth(Statement st, HealStr heal, int year, String month) throws SQLException {
		// 至某年某月，已有实时台帐总数 
		String sql = "select count(distinct a.assetid) as count "
					+ "from asset a, assetstatus st "
					+ "where a.assetnum=st.assetnum and a.siteid=st.siteid and a.assetlevel='ASSET' "
					+ "and to_char(st.changedate, 'YYYY-MM') <= ('" + year + "-" + month + "')"; 
		ResultSet rs = st.executeQuery(sql);
		heal.total = getCountFromResult(rs);
		
		// 至某年某前，且不在该月内的活动资产数 
		sql = "select count(distinct assetid) as count "
			+ "from " 
			+ "( "      
				+ "select t.*,c.isrunning from "       
				+ "( "      
					+ "select a.assetid, MAX(st.changedate) as cdate "
					+ "from asset a, assetstatus st " 
					+ "where a.assetnum=st.assetnum and a.siteid=st.siteid and a.assetlevel='ASSET' " 
							+ "and to_char(st.changedate, 'YYYY-MM') < '" + year + "-" + month + "' "
							+ "and a.assetid not in ( " 
								+ "select distinct assetid " 
								+ "from asset,assetstatus " 
								+ "where asset.assetnum=assetstatus.assetnum and asset.siteid=assetstatus.siteid and asset.assetlevel='ASSET' " 
										+ "and to_char(assetstatus.changedate, 'YYYY-MM') = '" + year + "-" + month + "' "
							+ ") " 
					+ "group by a.assetid "
				+ ") t " 
				+ "left join " 
				+ "( "
					+ "select distinct a.assetid, st.changedate, st.isrunning " 
					+ "from asset a, assetstatus st " 
					+ "where a.assetnum=st.assetnum and a.siteid=st.siteid and a.assetlevel='ASSET' " 
				+ ") c "
				+ "on t.assetid=c.assetid and t.cdate=c.changedate " 
			+ ") where isrunning=1 ";
		rs = st.executeQuery(sql);
		int healthBeforeMonth = getCountFromResult(rs);
		
		/* 某年某月内，一直活动资产数（当前月内无其他状态） */
		sql = "select count(distinct assetid) as count "
			+ "from asset a, assetstatus st " 
			+ "where a.assetnum=st.assetnum and a.siteid=st.siteid and a.assetlevel='ASSET' " 
					+ "and to_char(st.changedate, 'YYYY-MM') = '" + year + "-" + month + "' " 
					+ "and assetid not in ( "
						+ "select distinct assetid " 
						+ "from asset, assetstatus "
						+ "where asset.assetnum=assetstatus.assetnum and asset.siteid=assetstatus.siteid and asset.assetlevel='ASSET' " 
								+ "and to_char(assetstatus.changedate, 'YYYY-MM') = '" + year + "-" + month + "' and assetstatus.isrunning=0 "
					+ ") ";
		rs = st.executeQuery(sql);
		heal.health = healthBeforeMonth + getCountFromResult(rs);
	}
	/* 查询1~12月台帐历史健康状态 */
	StringBuffer getHealMonthPerc(Statement st) throws MXException, java.rmi.RemoteException, SQLException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		jsonStrTmp.append("{");
		
		int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
		int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1;
		StringBuffer categories = new StringBuffer();
		StringBuffer totals = new StringBuffer();
		StringBuffer healths = new StringBuffer();
		StringBuffer percents = new StringBuffer();
		
		categories.append("[");
		totals.append("[");
		healths.append("[");
		percents.append("[");
		
		String[] monthStr = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		HealStr heal = new HealStr();
		boolean isCommNeed = false;
		int i = 0;
		for(; i < month; i++) {
			getHealPerMonth(st, heal, year, monthStr[i]);
			if(isCommNeed) {
				categories.append(", \"" + monthStr[i] + "\"");
				totals.append(", \"" + heal.total + "\"");
				healths.append(", \"" + heal.health + "\"");
				percents.append(", " + getPercent(heal.total, heal.health));
				
			} else {
				categories.append("\"" + monthStr[i] + "\"");
				totals.append("\"" + heal.total + "\"");
				healths.append("\"" + heal.health + "\"");
				percents.append(getPercent(heal.total, heal.health));
				
				isCommNeed = true;
			}
		}
		for(; i < 12; i++) {
			categories.append(", \"" + monthStr[i] + "\"");
			//当前月未非12月时，后续月份数据为空
			/*totals.append(", \"\""); 
			healths.append(", \"\"");
			percents.append(", \"0\"");*/
		}
		categories.append("]");
		totals.append("]");
		healths.append("]");
		percents.append("]");
		
		jsonStrTmp.append("\"categories\": " + categories);
		jsonStrTmp.append(", \"totals\": " + totals);
		jsonStrTmp.append(", \"healths\": " + healths);
		jsonStrTmp.append(", \"percents\": " + percents);
		
		jsonStrTmp.append("}");				
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
	pw.write(mboToJsonString().toString());
%>

