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
	StringBuffer mboToJsonString(int top) throws MXException, java.rmi.RemoteException, SQLException {
		psdi.security.ConnectionKey ck = MXServer.getMXServer().getDBManager().getSystemConnectionKey();
		Connection connection = MXServer.getMXServer().getDBManager().getConnection(ck);
		
		Statement st = connection.createStatement();
		
		StringBuffer jsonStrTmp = getEquipmentYearFrq(st, top);
		
		st.close();
		MXServer.getMXServer().getDBManager().freeConnection(ck);	
		
		return jsonStrTmp;
	}

	/* 查询1~12月台帐历史健康状态 */
	StringBuffer getEquipmentYearFrq(Statement st, int top) throws SQLException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		jsonStrTmp.append("{");
		
		StringBuffer categories = new StringBuffer();
		StringBuffer datas = new StringBuffer();
		
		categories.append("[");
		datas.append("[");

		int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
		String sql = "select * " 
			+ "from ( " 
				+ "select count(*) as count, devicenum, name " 
				+ "from ( "
					+ "select distinct fd.devicenum, fd.fieldtasknum, di.description as name " 
					+ "from FTDEVICEINFO fd, deviceinfo di  "  
					+ "where fd.devicenum=di.devicenum and to_char(fd.createdate,'YYYY')=" + year  
				+ ") " 
				+ "group by devicenum, name " 
				+ "order by count desc " 
			+ ") " 
			+ "where rownum <= " + top; 
		
		ResultSet rs = st.executeQuery(sql);
		
		boolean isCommNeed = false;
		while(rs.next()) {
			int count = rs.getInt("count");
			String devicenum = rs.getString("devicenum");
			String name = rs.getString("name");
			if(name == null || "".equals(name)) {
				name = devicenum;
			} 
			if(isCommNeed) {
				categories.append(", \"" + name + "\"");
				datas.append(", " + count);
			} else {
				categories.append("\"" + name + "\"");
				datas.append(count);
				isCommNeed = true;
			}
		}


		categories.append("]");
		datas.append("]");
		
		jsonStrTmp.append("\"categories\": " + categories);
		jsonStrTmp.append(", \"datas\": " + datas);
		
		jsonStrTmp.append("}");		
		
		rs.close();
		return jsonStrTmp;
	}
%>
<%
	//设置回响为json格式数据输出
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.setHeader("Cache-Control", "no-cache");
	String topStr = request.getParameter("top");
	int top = 10;
	if(topStr != null && !"".equals(top)) {
		top = Integer.parseInt(topStr);
	}
	java.io.PrintWriter pw = response.getWriter();	
	pw.write(mboToJsonString(top).toString());
%>

