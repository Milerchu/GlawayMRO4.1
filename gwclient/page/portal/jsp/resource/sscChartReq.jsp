<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*,psdi.mbo.*,psdi.server.*,psdi.util.*,org.w3c.dom.*,psdi.mbo.*,psdi.util.*,psdi.webclient.system.controller.*,psdi.webclient.system.beans.*,psdi.webclient.system.runtime.*,psdi.webclient.servlet.*,psdi.webclient.system.session.*,psdi.webclient.controls.*,psdi.webclient.components.*,java.util.*,java.io.*"
	errorPage=""%>
<%@page import="java.text.NumberFormat"%>
<%@page import="com.ibm.json.java.*"%>
<%@page import="java.text.DecimalFormat"%>
<%!
	/**
	 * 将MBO数据转换为JSON字符串
	 * 可指定prefix值，以增加属性标识的前缀
	 * 表属性名称为指定属性列表中所提供的属性名称，且均为字母小写
	*/
	StringBuffer mboToJsonString() throws MXException, java.rmi.RemoteException, SQLException {
		
		psdi.security.ConnectionKey ck = MXServer.getMXServer().getDBManager().getSystemConnectionKey();
		Connection connection = MXServer.getMXServer().getDBManager().getConnection(ck);
		Statement st = connection.createStatement();

		StringBuffer jsonStrTmp = getMonthTravel(st); //查询1~12月出差人员情况
		
		st.close();
		MXServer.getMXServer().getDBManager().freeConnection(ck);	
		
		return jsonStrTmp;
	}

	/* 某年某月，出差人次总数 */ 
	int getMonthTravelCount(Statement st, int year, String month) throws SQLException {
		
		String sql = "select count(*) as count "
					+ "from travelinfo "   
					+ "where to_char(departuredate, 'YYYY-MM') = ('" + year + "-" + month + "')"; 
		ResultSet rs = st.executeQuery(sql);
		return getCountFromResult(rs);
		
	}
	/* 查询1~12月台帐历史健康状态 */
	StringBuffer getMonthTravel(Statement st) throws MXException, java.rmi.RemoteException, SQLException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		jsonStrTmp.append("{");
		
		int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
		int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH) + 1;
		StringBuffer categories = new StringBuffer();
		StringBuffer datas = new StringBuffer();
		
		categories.append("[");
		datas.append("[");
		
		String[] monthStr = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
		int[] times = new int[12];
		
		int i = 0;
		int sum = 0;
		for(; i < month; i++) {
			int count = getMonthTravelCount(st, year, monthStr[i]);
			times[i] = count;
			sum += count;
		}
		double averageD = sum * 1.0 / month; 
		String average = getAverage(sum, month);

		boolean isCommNeed = false;
		i = 0;
		for(; i < month; i++) {
			if(isCommNeed) {
				categories.append(", \"" + monthStr[i] + "\"");
				datas.append(", [" + appendDataElem(averageD, average, times[i]) + "]");
			} else {
				categories.append("\"" + monthStr[i] + "\"");
				datas.append("[" + appendDataElem(averageD, average, times[i]) + "]");
				isCommNeed = true;
			}
		}
		for(; i < 12; i++) {
			categories.append(", \"" + monthStr[i] + "\"");
			datas.append(", []");
		}
		categories.append("]");
		datas.append("]");
		
		jsonStrTmp.append("\"categories\": " + categories);
		jsonStrTmp.append(", \"datas\": " + datas);
		jsonStrTmp.append(", \"average\": " + average);
		
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
	
	/* 获取平均值 */
	String getAverage(int total, int length) {
		String average = "";
		if(length != 0) {
			average = new DecimalFormat("0.0").format(total * 1.0 / length);
		} else {
			average = "0";
		}
		return average;
	}
	
	/* 拼装单个数据源字符串 */
	String appendDataElem(double averageD, String averageS, int count) {
		String dataElem = "";
		if(averageD > count) {
			dataElem = count + ", " + averageS;
		} else {
			dataElem = averageS + ", " + count;
		}
		return dataElem;
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

