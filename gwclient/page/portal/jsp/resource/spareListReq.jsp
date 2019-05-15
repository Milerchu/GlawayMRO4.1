<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*,psdi.mbo.*,psdi.server.*,psdi.util.*,org.w3c.dom.*,psdi.mbo.*,psdi.util.*,psdi.webclient.system.controller.*,psdi.webclient.system.beans.*,psdi.webclient.system.runtime.*,psdi.webclient.servlet.*,psdi.webclient.system.session.*,psdi.webclient.controls.*,psdi.webclient.components.*,java.util.*,java.io.*"
	errorPage=""%>
<%@page import="java.text.NumberFormat"%>
<%@page import="com.ibm.json.java.*"%>
<%!
	
	int getResultSetCount(Statement st) throws SQLException  {
		String sql = "select count(*) as count " 
					+ "from inventory "
					+ "where location in (select location from locations where ismilitary=0) and inventory.status not in ('陈旧的')";
		int count = 0;
		ResultSet rs = st.executeQuery(sql);
		while(rs.next()) {
			count = rs.getInt("count");
			break;
		}
		rs.close();
		return count;
	}

	/**
	 * 将MBOSET中的MBO数据转换为JSON字符串
	 * 转换范围为MBOSET[fromPos, toPos)
	 * 表属性名称为指定属性列表中所提供的属性名称，且均为字母小写
	 * 针对提供的子层/相关业务关系名称，可将子层/相关业务数据以json字符串格式封装，并有此属性名称指向
	*/
	StringBuffer mboToJsonString(String pageNumberStr, String pageSizeStr) throws SQLException {
		psdi.security.ConnectionKey ck = MXServer.getMXServer().getDBManager().getSystemConnectionKey();
		Connection connection = MXServer.getMXServer().getDBManager().getConnection(ck);
		Statement st = connection.createStatement();
		
		int COUNT = getResultSetCount(st); //总结果集数量
		/* 获取前端对分页数据的需求 */
		int pageNumber, pageSize;
		if(pageNumberStr == null || "".equals(pageNumberStr)) {
			pageNumber = 1;
		} else {
			pageNumber = Integer.parseInt(pageNumberStr);
			pageNumber = (pageNumber >= 1? pageNumber: 1);
		}
		if(pageSizeStr == null || "".equals(pageSizeStr)) {
			pageSize = 10;
		} else {
			pageSize = Integer.parseInt(pageSizeStr);
			pageSize = (pageSize >= 1? pageSize: 10);
		}
		
		/* 计算当前可提供的分页起始以及终止位置 */
		int fromPos, endPos, pageTotal;//[fromPos, toPos)
		pageTotal = (COUNT<=pageSize? 1: (COUNT%pageSize==0? (int)(COUNT/pageSize): ((int)(COUNT/pageSize)+1)));
		if(pageNumber <= pageTotal) {
			fromPos = (pageNumber - 1) * pageSize;
			fromPos = (fromPos < 0? 0: fromPos);
			endPos = (pageNumber*pageSize>COUNT)? COUNT: pageNumber*pageSize;
		} else {
			fromPos = (pageTotal - 1) * pageSize;
			fromPos = (fromPos < 0? 0: fromPos);
			endPos = COUNT;
		}
		
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象

		String[] props = {"itemnum", "itemname", 
				"location", 
				"curbaltotal", "reservedqty", "avblbalance", "expiredqty", "issueunit", 
				"manufacturer", "abctype", 
				"issueytd", "issue1yrago", "issue2yrago", "issue3yrago"};
		jsonStrTmp.append("[");
		String sql = "select * from "
					+ "( "
						+ "select rownum no, t.* from " 
						+ "( "
							+ "select inventory.*, item.description as itemname from inventory " 
							+ "join item "
							+ "on inventory.itemnum=item.itemnum and inventory.itemsetid=item.itemsetid "
							+ "where location in (select location from locations where ismilitary=0) and inventory.status not in ('陈旧的')"
						+ ") t " 
						+ "where rownum < " + (endPos + 1)
					+ ")where no >= " + (fromPos + 1);
		ResultSet rs = st.executeQuery(sql);
		boolean isCommaOutNeed = false;
		while(rs.next()) {
			if(isCommaOutNeed) {
				jsonStrTmp.append(", {");
			} else {
				jsonStrTmp.append("{");
				isCommaOutNeed = true;
			}
			boolean isCommaNeed = false;
			for(String prop: props) {
				if(isCommaNeed) {
					jsonStrTmp.append(", \"" + prop + "\":\"" + tmp + "\"");
				} else {
					jsonStrTmp.append("\"" + prop + "\":\"" + tmp + "\"");
					isCommaNeed = true;
				}
			}
			jsonStrTmp.append("," + getJsonStrByCond(st, custinfoProps, "custinfo", "custnum", custnum).toString());
			jsonStrTmp.append("," + getJsonStrByCond(st, personProps, "person", "personid", header).toString());
			
			jsonStrTmp.append("}");
		}
		jsonStrTmp.append("]");
		return jsonStrTmp;
	}
%>
<%
	//设置回响为json格式数据输出
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.setHeader("Cache-Control", "no-cache");
	java.io.PrintWriter pw = response.getWriter();
	
	/* 获取前端对分页数据的需求 */
	String pageNumberStr = request.getParameter("pageNumber"); //第几页
	String pageSizeStr = request.getParameter("pageSize");     //每页多少条

	/* 输出符合条件的结果集 */
	pw.write(mboToJsonString(pageNumberStr, pageSizeStr, props).toString());   
%>
