<%@ page contentType="text/html; charset=utf-8" language="java"
	import="java.sql.*,psdi.mbo.*,psdi.server.*,psdi.util.*,org.w3c.dom.*,psdi.mbo.*,psdi.util.*,psdi.webclient.system.controller.*,psdi.webclient.system.beans.*,psdi.webclient.system.runtime.*,psdi.webclient.servlet.*,psdi.webclient.system.session.*,psdi.webclient.controls.*,psdi.webclient.components.*,java.util.*,java.io.*"
	errorPage="" pageEncoding="utf-8" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="com.ibm.json.java.*"%>
<%@include file="../util/CONSTANT.jsp"%>

<%!
	StringBuffer getPageJsonString(Statement st, int fromPos, int toPos, String[] props, StringBuffer condStr) throws SQLException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		jsonStrTmp.append("[");
		if(st == null || fromPos < 0 || fromPos >= toPos || props.length <= 0) { //异常参数
			jsonStrTmp.append("]");
			return jsonStrTmp;
		}		
		
		String sql = "select * from "
					+ "("
						+ "select rownum no, t.* from "
						+ "("
								+ "select assetcsnum as assetnum,description,radarnum,drawno,productcode,productorderno,outdate,attachstatus "
								+ "from assetcs " 
								+ "where assetcslevel='ASSET' and " + condStr
								+ "order by outdate desc "
						+ ") t " 
						+ "where rownum < " + (toPos + 1)
					+ ") where no >= " + (fromPos + 1);
		
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
				String tmp = rs.getString(prop);
				if(isCommaNeed) {
					jsonStrTmp.append(", \"" + prop + "\":\"" + tmp + "\"");
				} else {
					jsonStrTmp.append("\"" + prop + "\":\"" + tmp + "\"");
					isCommaNeed = true;
				}
			}
			jsonStrTmp.append("}");
		}
		jsonStrTmp.append("]");
		
		rs.close();
		
		return jsonStrTmp;
	}

	
	int getResultSetCount(Statement st, StringBuffer condStr) throws SQLException  {
		String sql = " select count(*) as count from assetcs where assetcslevel='ASSET' and " + condStr;
		int count = 0;
		ResultSet rs = st.executeQuery(sql);
		while(rs.next()) {
			count = rs.getInt("count");
			break;
		}
		rs.close();
		return count;
	}
	
	
	StringBuffer mboToJsonString(String pageNumberStr, String pageSizeStr, String[] props, StringBuffer condStr) throws MXException,
			java.rmi.RemoteException, SQLException {
		StringBuffer jsonStrTmp = new StringBuffer(); //json 字符串缓存对象
		/* 获取质保期内、外资产的外场任务数量 */
		psdi.security.ConnectionKey ck = MXServer.getMXServer().getDBManager()
				.getSystemConnectionKey();
		Connection connection = MXServer.getMXServer().getDBManager()
				.getConnection(ck);
		Statement st = connection.createStatement();

		int COUNT = getResultSetCount(st, condStr); //总结果集数量
		/* 获取前端对分页数据的需求 */
		int pageNumber, pageSize;
		if (pageNumberStr == null || "".equals(pageNumberStr)) {
			pageNumber = 0;
		} else {
			pageNumber = Integer.parseInt(pageNumberStr);
			pageNumber = (pageNumber >= 0 ? pageNumber : 0);
		}
		if (pageSizeStr == null || "".equals(pageSizeStr)) {
			pageSize = 10;
		} else {
			pageSize = Integer.parseInt(pageSizeStr);
			pageSize = (pageSize >= 1 ? pageSize : 10);
		}

		/* 计算当前可提供的分页起始以及终止位置 */
		int fromPos, endPos, pageTotal;//[fromPos, toPos)
		pageTotal = (COUNT <= pageSize ? 1
				: (COUNT % pageSize == 0 ? (int) (COUNT / pageSize)
						: ((int) (COUNT / pageSize) + 1)));
		if (pageNumber <= pageTotal) {
			fromPos = pageNumber * pageSize;
			fromPos = (fromPos < 0 ? 0 : fromPos);
			endPos = ((pageNumber + 1) * pageSize > COUNT) ? COUNT
					: (pageNumber + 1) * pageSize;
		} else {
			fromPos = (pageTotal - 1) * pageSize;
			fromPos = (fromPos < 0 ? 0 : fromPos);
			endPos = COUNT;
		}

		jsonStrTmp.append("{\"total\":" + COUNT);
		jsonStrTmp.append(", \"rows\":");
		jsonStrTmp.append(getPageJsonString(st, fromPos, endPos, props, condStr));
		jsonStrTmp.append("}");
		st.close();
		MXServer.getMXServer().getDBManager().freeConnection(ck);
		return jsonStrTmp;
	}
%>
<%
	String[] props = { "assetnum", "description", 
			"radarnum", "drawno", "productcode", "productorderno",
			"outdate", "attachstatus" };

	//设置回响为json格式数据输出
	response.setContentType("application/json");
	response.setCharacterEncoding("UTF-8");
	response.setHeader("Cache-Control", "no-cache");
	java.io.PrintWriter pw = response.getWriter();

	/* 获取前端对分页数据的需求 */
	String pageNumberStr = request.getParameter("pageNumber"); //第几页
	String pageSizeStr = request.getParameter("pageSize"); //每页多少条

	StringBuffer condStr = new StringBuffer(); //json 字符串缓存对象
	String isCompYearStr = request.getParameter("isCompYear"); //是否获取年度对比
	condStr.append(" 1=1 ");
	if("0".equals(isCompYearStr)) { //年内月统计
		String outdate = request.getParameter("outdate"); //出所年份
		condStr.append(" and to_char(outdate, 'YYYY')='" + outdate + "' ");
	} else { //年度间月统计
		String outdateFrom = request.getParameter("outdateFrom"); //出所年份
		String outdateTo = request.getParameter("outdateTo"); //出所年份
		condStr.append(" and to_char(outdate, 'YYYY')>='" + outdateFrom + "' ");
		condStr.append(" and to_char(outdate, 'YYYY')<='" + outdateTo + "' ");
	}
	
	/* 输出符合条件的结果集 */
	pw.write(mboToJsonString(pageNumberStr, pageSizeStr, props, condStr).toString());
%>


