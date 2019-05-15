<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*,psdi.mbo.*,psdi.server.*,psdi.util.*,org.w3c.dom.*,psdi.mbo.*,psdi.util.*,psdi.webclient.system.controller.*,psdi.webclient.system.beans.*,psdi.webclient.system.runtime.*,psdi.webclient.servlet.*,psdi.webclient.system.session.*,psdi.webclient.controls.*,psdi.webclient.components.*,java.util.*,java.io.*"
	errorPage="" pageEncoding="utf-8" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="com.ibm.json.java.*"%>

<%!
	/* 获取年内出所台帐对比数据 */
	StringBuffer createChartDataInYear(Statement st, int outdate, boolean isCompDelivery, StringBuffer condStr) throws SQLException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		jsonStrTmp.append("[");
		if(st == null) { //异常参数
			jsonStrTmp.append("]");
			return jsonStrTmp;
		}		
		////出所台帐部分
		jsonStrTmp.append("{\"name\": \"出所台帐\", \"data\": ");
		jsonStrTmp.append("[");
		
		String sql = "select * from ( "
						 + "select count(*) as count, to_char(outdate, 'MM') as month from assetcs " 
						 + "where assetcslevel='ASSET' " 
						 		+ "and " + condStr 
						 + "group by to_char(outdate, 'MM') "
					 + ") " 
					 + "order by month asc ";
		
		String[] monthDatas = new String[12];
		int thisYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
		if(thisYear == outdate) { //查询年份为当前年份是
			initMonthDatas(monthDatas, true);
		} else {
			initMonthDatas(monthDatas, false);
		}
		
		ResultSet rs = st.executeQuery(sql);
		while(rs.next()) {
			monthDatas[rs.getInt("month") - 1] = "" + rs.getInt("count");
		}
		
		int CUR_MONTH = (thisYear==outdate? (java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1): 12);
		int i = 0;
		boolean isCommaOutNeed = false;
		for( ; i < CUR_MONTH; i++) {
			if(isCommaOutNeed) {
				jsonStrTmp.append(", " + monthDatas[i]);
			} else {
				jsonStrTmp.append(monthDatas[i]);
				isCommaOutNeed = true;
			}
		}
		jsonStrTmp.append("]");
		jsonStrTmp.append("}");
		
		////交装台帐部分
		if(isCompDelivery) { //与交装对比
			
		}
		
		jsonStrTmp.append("]");
		
		rs.close();
		
		return jsonStrTmp;
	}
	
	void initMonthDatas(String[] monthDatas, boolean isThisYear) {
		if(isThisYear) { //查询年份为当前年份的，存在不满12月情况
			int thisMonth = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1;
			int i = 0;
			for( ; i < thisMonth; i++) {
				monthDatas[i] = "0";
			}
			for( ; i < 12; i++) {
				monthDatas[i] = "";
			}
		} else { //查询年份为过去年份
			for(int i = 0, LENGTH = monthDatas.length; i < LENGTH; i++) {
				monthDatas[i] = "0";
			}
		}
	}

	/* 年内出所 */
	StringBuffer getChartData(int outdate, boolean isCompDelivery, StringBuffer condStr) throws MXException,
			java.rmi.RemoteException, SQLException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		/* 获取质保期内、外资产的外场任务数量 */
		psdi.security.ConnectionKey ck = MXServer.getMXServer().getDBManager()
				.getSystemConnectionKey();
		Connection connection = MXServer.getMXServer().getDBManager()
				.getConnection(ck);
		Statement st = connection.createStatement();

		jsonStrTmp.append(createChartDataInYear(st, outdate, isCompDelivery, condStr));
		
		st.close();
		MXServer.getMXServer().getDBManager().freeConnection(ck);
		
		return jsonStrTmp;
	}

	
	/* 获取年度出所台帐对比数据 */
	StringBuffer createChartDataBtwYear(Statement st, int outdateFrom, int outdateTo, StringBuffer condStr) throws SQLException {
		return null;
	}
		
	/* 年度出所 */
	StringBuffer getChartData(int outdateFrom, int outdateTo, StringBuffer condStr) throws MXException,
		java.rmi.RemoteException, SQLException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		/* 获取质保期内、外资产的外场任务数量 */
		psdi.security.ConnectionKey ck = MXServer.getMXServer().getDBManager()
				.getSystemConnectionKey();
		Connection connection = MXServer.getMXServer().getDBManager()
				.getConnection(ck);
		Statement st = connection.createStatement();
		
		jsonStrTmp.append(createChartDataBtwYear(st, outdateFrom, outdateTo, condStr));
		
		st.close();
		MXServer.getMXServer().getDBManager().freeConnection(ck);
		
		return jsonStrTmp;
	}
%>
<%
	//设置回响为json格式数据输出
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.setHeader("Cache-Control", "no-cache");
	java.io.PrintWriter pw = response.getWriter();

	StringBuffer condStr = new StringBuffer(); //json 字符串缓存对象
	boolean isCompYear = "1".equals(request.getParameter("isCompYear"));//是否年度对比
	condStr.append(" 1=1 ");
	if(isCompYear) { //年度间月统计
		String outdateFrom = request.getParameter("outdateFrom"); //出所年份
		String outdateTo = request.getParameter("outdateTo"); //出所年份
		condStr.append(" and to_char(outdate, 'YYYY')>='" + outdateFrom + "' ");
		condStr.append(" and to_char(outdate, 'YYYY')<='" + outdateTo + "' ");
		
		pw.write(getChartData(Integer.parseInt(outdateFrom), Integer.parseInt(outdateTo), condStr).toString()); //输出符合条件的结果集
		
	} else { //年内月统计
		String outdate = request.getParameter("outdate"); //出所年份 
		condStr.append(" and to_char(outdate, 'YYYY')='" + outdate + "' ");
		boolean isCompDelivery = "1".equals(request.getParameter("isCompDelivery"));//年内统计，是否与同期交装对比
		
		pw.write(getChartData(Integer.parseInt(outdate), isCompDelivery, condStr).toString()); //输出符合条件的结果集
	}
	
%>


